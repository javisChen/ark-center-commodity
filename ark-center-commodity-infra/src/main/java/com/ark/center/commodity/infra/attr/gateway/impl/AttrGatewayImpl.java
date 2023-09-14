package com.ark.center.commodity.infra.attr.gateway.impl;

import com.ark.center.commodity.client.attr.dto.AttrDTO;
import com.ark.center.commodity.client.attr.query.AttrPageQry;
import com.ark.center.commodity.domain.attr.repository.AttrGateway;
import com.ark.center.commodity.domain.category.Category;
import com.ark.center.commodity.domain.category.gateway.CategoryGateway;
import com.ark.center.commodity.infra.attr.convertor.AttrConvertor;
import com.ark.center.commodity.infra.attr.convertor.AttrOptionConvertor;
import com.ark.center.commodity.domain.attr.Attr;
import com.ark.center.commodity.infra.attr.gateway.db.AttrMapper;
import com.ark.center.commodity.domain.attr.AttrOption;
import com.ark.center.commodity.infra.attr.gateway.db.AttrOptionMapper;
import com.ark.component.exception.ExceptionFactory;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 品牌表 服务实现类
 * </p>
 *
 * @author EOP
 * @since 2022-02-25
 */
@Service
@RequiredArgsConstructor
public class AttrGatewayImpl extends ServiceImpl<AttrMapper, Attr> implements IService<Attr>, AttrGateway {

    private final CategoryGateway categoryGateway;
    private final AttrOptionMapper attrOptionMapper;
    private final AttrConvertor attrConvertor;
    private final AttrOptionConvertor attrOptionConvertor;

    @Override
    public Long store(Attr aggregate) {
        Attr attr = attrConvertor.fromDomain(aggregate);

        if (attr.getId() != null) {
            updateById(attr);
        } else {
            save(attr);
        }

        Long attrId = attr.getId();

        // 先清空选项再保存
        removeOptions(attrId);

        saveOptions(attrId, aggregate);
        return attrId;
    }

    private void removeOptions(Long attrId) {
        LambdaUpdateWrapper<AttrOption> uw = new LambdaUpdateWrapper<>();
        uw.eq(AttrOption::getAttrId, attrId);
        attrOptionMapper.delete(uw);
    }

    private void saveOptions(Long attrId, Attr attr) {
        if (attr.isSelectInputType()) {
            List<com.ark.center.commodity.domain.attr.vo.AttrOption> options = attr.getop();
            if (CollectionUtils.isNotEmpty(options)) {
                for (com.ark.center.commodity.domain.attr.vo.AttrOption option : options) {
                    AttrOption optionDO = attrOptionConvertor.fromDomain(option);
                    optionDO.setAttrId(attrId);
                    // todo 优化批量
                    attrOptionMapper.insert(optionDO);
                }
            }
        }
    }

    @Override
    public AttrDTO selectById(Long id) {
        Attr attr = getById(id);
        AttrDTO attrDTO = attrConvertor.toDTO(attr);
        List<AttrOption> optionList = selectOptionsByAttrId(attr);
        if (CollectionUtils.isNotEmpty(optionList)) {
            fillOptions(attrDTO, optionList);
        }
        return attrDTO;
    }

    private List<AttrOption> selectOptionsByAttrId(Attr attr) {
        LambdaQueryWrapper<AttrOption> qw = new LambdaQueryWrapper<>();
        qw.eq(AttrOption::getAttrId, attr.getId());
        return attrOptionMapper.selectList(qw);
    }

    private void fillOptions(AttrDTO attrDTO, List<AttrOption> optionList) {
        List<AttrDTO> es = Lists.newArrayList(attrDTO);
        fillOptions(es, optionList);
    }

    public void fillOptions(List<AttrDTO> records, List<AttrOption> attrOptionList) {
        if (CollectionUtils.isNotEmpty(attrOptionList)) {
            Map<Long, List<AttrOption>> attrValueMap = attrOptionList.stream()
                    .collect(Collectors.groupingBy(AttrOption::getAttrId));
            for (AttrDTO record : records) {
                List<AttrOption> valueList = attrValueMap.get(record.getId());
                if (CollectionUtils.isNotEmpty(valueList)) {
                    record.setOptionList(attrConvertor.toOptionDTO(valueList));
                }
            }
        }
    }

    public boolean update(Attr aggregate) {
        return updateById(aggregate);
    }

    public boolean remove(Long id) {
        return removeById(id);
    }


    @Override
    public IPage<Attr> selectPages(AttrPageQry queryDTO) {

        if (queryDTO.getCategoryId() != null) {
            Category category = Optional.ofNullable(categoryGateway.selectById(queryDTO.getCategoryId()))
                    .orElseThrow(() -> ExceptionFactory.userException("商品类目不存在"));
            queryDTO.setAttrTemplateId(category.getAttrTemplateId());
        }

        Page<Attr> page = lambdaQuery()
                .eq(Objects.nonNull(queryDTO.getAttrTemplateId()), Attr::getAttrTemplateId, queryDTO.getAttrTemplateId())
                .eq(Objects.nonNull(queryDTO.getType()), Attr::getType, queryDTO.getType())
                .like(StringUtils.isNotEmpty(queryDTO.getName()), Attr::getName, queryDTO.getName())
                .page(new Page<>(queryDTO.getCurrent(), queryDTO.getSize()));
        List<Attr> records = page.getRecords();
        if (CollectionUtils.isEmpty(records)) {
            return page;
        }
        List<Long> attrIdList = records.stream().map(Attr::getId).collect(Collectors.toList());
        if (queryDTO.getWithOptions()) {
            List<AttrOption> attrOptionList = listOptionsByAttrIdsAndType(attrIdList, AttrOption.Type.COMMON.getValue());
            // 如果商品ID指明了，就把专属的属性项查出来
            if (queryDTO.getSpuId() != null) {
                attrOptionList.addAll(listOptionsBySpuId(queryDTO.getSpuId()));
            }
            fillOptions(records, attrOptionList);
        }
        return page;
    }

    private List<AttrOption> listOptionsBySpuId(Long spuId) {
        LambdaQueryWrapper<AttrOption> qw = new LambdaQueryWrapper<>();
        qw.eq(AttrOption::getSpuId, spuId)
                .eq(AttrOption::getType, AttrOption.Type.EXCLUSIVE.getValue());
        return attrOptionMapper.selectList(qw);
    }

    private List<AttrOption> listOptionsByAttrIdsAndType(List<Long> attrIdList, Integer type) {
        LambdaQueryWrapper<AttrOption> qw = new LambdaQueryWrapper<>();
        qw.in(AttrOption::getAttrId, attrIdList)
                .eq(AttrOption::getType, type);
        return attrOptionMapper.selectList(qw);
    }

    @Override
    public List<Attr> selectByGroupIds(List<Long> groupIds) {
        List<Attr> list = lambdaQuery()
                .eq(Attr::getType, Attr.Type.PARAM.getValue())
                .in(Attr::getAttrGroupId, groupIds)
                .list();
        return attrConvertor.toAggregate(list);
    }
}
