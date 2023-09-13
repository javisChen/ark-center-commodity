package com.ark.center.commodity.infra.attr.repository.impl;

import com.ark.center.commodity.client.attr.query.AttrPageQry;
import com.ark.center.commodity.domain.attr.aggregate.Attr;
import com.ark.center.commodity.domain.attr.repository.AttrRepository;
import com.ark.center.commodity.domain.attr.vo.AttrOption;
import com.ark.center.commodity.domain.category.Category;
import com.ark.center.commodity.domain.category.repository.CategoryGateway;
import com.ark.center.commodity.infra.attr.convertor.AttrConvertor;
import com.ark.center.commodity.infra.attr.convertor.AttrOptionConvertor;
import com.ark.center.commodity.domain.attr.AttrDO;
import com.ark.center.commodity.infra.attr.repository.db.AttrMapper;
import com.ark.center.commodity.domain.attr.AttrOptionDO;
import com.ark.center.commodity.infra.attr.repository.db.AttrOptionMapper;
import com.ark.component.exception.ExceptionFactory;
import com.ark.component.web.util.bean.BeanConvertor;
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
public class AttrRepositoryImpl extends ServiceImpl<AttrMapper, AttrDO> implements IService<AttrDO>, AttrRepository {

    private final AttrMapper attrMapper;

    private final CategoryGateway categoryGateway;

    private final AttrOptionMapper attrOptionMapper;

    private final AttrConvertor attrConvertor;
    private final AttrOptionConvertor attrOptionConvertor;

    @Override
    public Long store(Attr aggregate) {
        AttrDO attr = attrConvertor.fromDomain(aggregate);

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
        LambdaUpdateWrapper<AttrOptionDO> uw = new LambdaUpdateWrapper<>();
        uw.eq(AttrOptionDO::getAttrId, attrId);
        attrOptionMapper.delete(uw);
    }

    private void saveOptions(Long attrId, Attr attr) {
        if (attr.isSelectInputType()) {
            List<AttrOption> options = attr.getOptions();
            if (CollectionUtils.isNotEmpty(options)) {
                for (AttrOption option : options) {
                    AttrOptionDO optionDO = attrOptionConvertor.fromDomain(option);
                    optionDO.setAttrId(attrId);
                    // todo 优化批量
                    attrOptionMapper.insert(optionDO);
                }
            }
        }
    }

    @Override
    public Attr selectById(Long id) {
        AttrDO dataObject = getById(id);
        Attr attr = attrConvertor.toAggregate(dataObject);
        LambdaQueryWrapper<AttrOptionDO> qw = new LambdaQueryWrapper<>();
        qw.eq(AttrOptionDO::getAttrId, attr.getId());
        List<AttrOptionDO> optionList = attrOptionMapper.selectList(qw);
        if (CollectionUtils.isNotEmpty(optionList)) {
            fillOptions(attr, optionList);
        }
        return attr;
    }

    private void fillOptions(Attr attr, List<AttrOptionDO> optionList) {
        fillOptions(Lists.newArrayList(attr), optionList);
    }

    public void fillOptions(List<Attr> records, List<AttrOptionDO> attrOptionDOList) {
        if (CollectionUtils.isNotEmpty(attrOptionDOList)) {
            Map<Long, List<AttrOptionDO>> attrValueMap = attrOptionDOList.stream()
                    .collect(Collectors.groupingBy(AttrOptionDO::getAttrId));
            for (Attr record : records) {
                List<AttrOptionDO> valueList = attrValueMap.get(record.getId());
                if (CollectionUtils.isNotEmpty(valueList)) {
                    record.setOptions(BeanConvertor.copyList(valueList, AttrOption.class));
                }
            }
        }
    }

    @Override
    public boolean update(Attr aggregate) {
        AttrDO entity = attrConvertor.fromDomain(aggregate);
        return updateById(entity);
    }

    @Override
    public boolean remove(Long id) {
        return removeById(id);
    }


    @Override
    public IPage<Attr> pageList(AttrPageQry queryDTO) {

        if (queryDTO.getCategoryId() != null) {
            Category category = Optional.ofNullable(categoryGateway.selectById(queryDTO.getCategoryId()))
                    .orElseThrow(() -> ExceptionFactory.userException("商品类目不存在"));
            queryDTO.setAttrTemplateId(category.getAttrTemplateId());
        }

        IPage<Attr> page = lambdaQuery()
                .eq(Objects.nonNull(queryDTO.getAttrTemplateId()), AttrDO::getAttrTemplateId, queryDTO.getAttrTemplateId())
                .eq(Objects.nonNull(queryDTO.getType()), AttrDO::getType, queryDTO.getType())
                .like(StringUtils.isNotEmpty(queryDTO.getName()), AttrDO::getName, queryDTO.getName())
                .page(new Page<>(queryDTO.getCurrent(), queryDTO.getSize()))
                .convert(attrConvertor::toAggregate);

        List<Attr> records = page.getRecords();
        if (CollectionUtils.isEmpty(records)) {
            return page;
        }
        List<Long> attrIdList = records.stream().map(Attr::getId).collect(Collectors.toList());
        if (queryDTO.getWithOptions()) {
            List<AttrOptionDO> attrOptionDOList = listOptionsByAttrIdsAndType(attrIdList, AttrOptionDO.Type.COMMON.getValue());
            // 如果商品ID指明了，就把专属的属性项查出来
            if (queryDTO.getSpuId() != null) {
                attrOptionDOList.addAll(listOptionsBySpuId(queryDTO.getSpuId()));
            }
            fillOptions(records, attrOptionDOList);
        }
        return page;
    }

    private List<AttrOptionDO> listOptionsBySpuId(Long spuId) {
        LambdaQueryWrapper<AttrOptionDO> qw = new LambdaQueryWrapper<>();
        qw.eq(AttrOptionDO::getSpuId, spuId)
                .eq(AttrOptionDO::getType, AttrOptionDO.Type.EXCLUSIVE.getValue());
        return attrOptionMapper.selectList(qw);
    }

    private List<AttrOptionDO> listOptionsByAttrIdsAndType(List<Long> attrIdList, Integer type) {
        LambdaQueryWrapper<AttrOptionDO> qw = new LambdaQueryWrapper<>();
        qw.in(AttrOptionDO::getAttrId, attrIdList)
                .eq(AttrOptionDO::getType, type);
        List<AttrOptionDO> attrOptionDOS = attrOptionMapper.selectList(qw);
        return attrOptionDOS;
    }

    @Override
    public List<Attr> findByGroupIds(List<Long> groupIds) {
        List<AttrDO> list = lambdaQuery()
                .eq(AttrDO::getType, AttrDO.Type.PARAM.getValue())
                .in(AttrDO::getAttrGroupId, groupIds)
                .list();
        return attrConvertor.toAggregate(list);
    }
}
