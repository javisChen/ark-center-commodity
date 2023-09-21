package com.ark.center.commodity.infra.attr.gateway.impl;

import com.ark.center.commodity.client.attr.dto.AttrDTO;
import com.ark.center.commodity.client.attr.query.AttrPageQry;
import com.ark.center.commodity.domain.attr.repository.AttrGateway;
import com.ark.center.commodity.domain.category.Category;
import com.ark.center.commodity.domain.category.gateway.CategoryGateway;
import com.ark.center.commodity.infra.attr.convertor.AttrConvertor;
import com.ark.center.commodity.domain.attr.Attr;
import com.ark.center.commodity.infra.attr.gateway.db.AttrMapper;
import com.ark.center.commodity.domain.attr.AttrOption;
import com.ark.center.commodity.infra.attr.gateway.db.AttrOptionMapper;
import com.ark.component.exception.ExceptionFactory;
import com.ark.component.orm.mybatis.base.BaseEntity;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
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

    @Override
    public void insert(Attr attr) {
        save(attr);
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
            Map<Long, List<AttrOption>> attrValueMap = attrOptionList
                    .stream()
                    .collect(Collectors.groupingBy(AttrOption::getAttrId));
            for (AttrDTO record : records) {
                List<AttrOption> valueList = MapUtils.getObject(attrValueMap, record.getId());
                if (CollectionUtils.isNotEmpty(valueList)) {
                    record.setOptionList(attrConvertor.toOptionDTO(valueList));
                }
            }
        }
    }

    public boolean update(Attr aggregate) {
        return updateById(aggregate);
    }

    @Override
    public void deleteOptions(Long attrId) {
        List<AttrOption> records = selectOptionsByAttrId(attrId);
        if (CollectionUtils.isNotEmpty(records)) {
            List<Long> ids = records.stream().map(BaseEntity::getId).sorted().toList();
            LambdaUpdateWrapper<AttrOption> wrapper = Wrappers
                    .lambdaUpdate(AttrOption.class)
                    .in(BaseEntity::getId, ids);
            attrOptionMapper.delete(wrapper);
        }
    }

    @Override
    public List<AttrOption> selectOptionsByAttrId(Long attrId) {
        LambdaQueryWrapper<AttrOption> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(BaseEntity::getId)
                .eq(AttrOption::getAttrId, attrId);
        return attrOptionMapper.selectList(queryWrapper);
    }

    @Override
    public void saveOptions(List<AttrOption> options) {
        if (CollectionUtils.isNotEmpty(options)) {
            options.forEach(attrOptionMapper::insert);
        }
    }

    public boolean remove(Long id) {
        return removeById(id);
    }


    @Override
    public IPage<AttrDTO> selectPages(AttrPageQry queryDTO) {

        if (queryDTO.getCategoryId() != null) {
            Category category = Optional.ofNullable(categoryGateway.selectById(queryDTO.getCategoryId()))
                    .orElseThrow(() -> ExceptionFactory.userException("商品类目不存在"));
            queryDTO.setAttrTemplateId(category.getAttrTemplateId());
        }

        IPage<AttrDTO> page = lambdaQuery()
                .eq(Objects.nonNull(queryDTO.getAttrTemplateId()), Attr::getAttrTemplateId, queryDTO.getAttrTemplateId())
                .eq(Objects.nonNull(queryDTO.getType()), Attr::getType, queryDTO.getType())
                .like(StringUtils.isNotEmpty(queryDTO.getName()), Attr::getName, queryDTO.getName())
                .page(new Page<>(queryDTO.getCurrent(), queryDTO.getSize()))
                .convert(attrConvertor::toDTO)
                ;
        List<AttrDTO> records = page.getRecords();
        if (CollectionUtils.isEmpty(records)) {
            return page;
        }
        List<Long> attrIdList = records.stream().map(AttrDTO::getId).collect(Collectors.toList());
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
    public List<AttrDTO> selectByGroupIds(List<Long> groupIds) {
        List<Attr> records = lambdaQuery()
                .eq(Attr::getType, Attr.Type.PARAM.getValue())
                .in(Attr::getAttrGroupId, groupIds)
                .list();
        return attrConvertor.toDTO(records);
    }
}
