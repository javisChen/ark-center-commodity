package com.ark.center.commodity.infrastructure.attr.repository.impl;

import com.ark.center.commodity.client.attr.query.AttrPageQry;
import com.ark.center.commodity.domain.attr.aggregate.Attr;
import com.ark.center.commodity.domain.attr.repository.AttrRepository;
import com.ark.center.commodity.domain.attr.vo.AttrOption;
import com.ark.center.commodity.infrastructure.attr.convertor.AttrConvertor;
import com.ark.center.commodity.infrastructure.attr.convertor.AttrOptionConvertor;
import com.ark.center.commodity.infrastructure.attr.repository.db.AttrDO;
import com.ark.center.commodity.infrastructure.attr.repository.db.AttrMapper;
import com.ark.center.commodity.infrastructure.attr.repository.db.AttrOptionDO;
import com.ark.center.commodity.infrastructure.attr.repository.db.AttrOptionMapper;
import com.ark.component.web.util.bean.BeanConvertor;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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

    private final AttrOptionMapper attrOptionMapper;

    private final AttrConvertor attrConvertor;
    private final AttrOptionConvertor attrOptionConvertor;

    @Override
    public Long store(Attr aggregate) {
        AttrDO attr = attrConvertor.fromDomain(aggregate);

        save(attr);

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
    public Attr findById(Long id) {
        AttrDO dataObject = getById(id);
        return attrConvertor.toAggregate(dataObject);
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
        return lambdaQuery()
                .eq(Objects.nonNull(queryDTO.getAttrTemplateId()), AttrDO::getAttrTemplateId, queryDTO.getAttrTemplateId())
                .eq(Objects.nonNull(queryDTO.getType()), AttrDO::getType, queryDTO.getType())
                .like(StringUtils.isNotEmpty(queryDTO.getName()), AttrDO::getName, queryDTO.getName())
                .page(new Page<>(queryDTO.getCurrent(), queryDTO.getSize()))
                .convert(item -> BeanConvertor.copy(item, Attr.class));
    }
}
