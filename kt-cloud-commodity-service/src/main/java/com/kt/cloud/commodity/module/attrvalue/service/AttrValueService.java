package com.kt.cloud.commodity.module.attrvalue.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kt.cloud.commodity.dao.entity.AttrValueDO;
import com.kt.cloud.commodity.dao.mapper.AttrValueMapper;
import com.kt.cloud.commodity.module.attrvalue.dto.request.AttrValueCreateReqDTO;
import com.kt.cloud.commodity.module.attrvalue.dto.request.AttrValueUpdateReqDTO;
import com.kt.cloud.commodity.module.attrvalue.dto.request.AttrValuePageQueryReqDTO;
import com.kt.cloud.commodity.module.attrvalue.dto.response.AttrValueRespDTO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kt.component.dto.PageResponse;
import org.springframework.stereotype.Service;
import com.kt.component.web.util.bean.BeanConvertor;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 商品属性值 服务实现类
 * </p>
 *
 * @author EOP
 * @since 2022-03-08
 */
@Service
public class AttrValueService extends ServiceImpl<AttrValueMapper, AttrValueDO> implements IService<AttrValueDO> {

    public Long createAttrValue(AttrValueCreateReqDTO reqDTO) {
        AttrValueDO entity = BeanConvertor.copy(reqDTO, AttrValueDO.class);
        save(entity);
        return entity.getId();
    }

    public PageResponse<AttrValueRespDTO> getPageList(AttrValuePageQueryReqDTO queryDTO) {
        IPage<AttrValueRespDTO> page = lambdaQuery()
                .page(new Page<>(queryDTO.getCurrent(), queryDTO.getSize()))
                .convert(item -> BeanConvertor.copy(item, AttrValueRespDTO.class));
        return BeanConvertor.copyPage(page, AttrValueRespDTO.class);
    }

    public Long updateAttrValue(AttrValueUpdateReqDTO reqDTO) {
        AttrValueDO entity = BeanConvertor.copy(reqDTO, AttrValueDO.class);
        updateById(entity);
        return entity.getId();
    }

    public AttrValueRespDTO getAttrValueInfo(Long AttrValueId) {
        AttrValueDO entity = getById(AttrValueId);
        return BeanConvertor.copy(entity, AttrValueRespDTO.class);
    }

    public void batchSave(Long attrId, List<String> values, Integer type) {
        List<AttrValueDO> dos = new ArrayList<>(values.size());
        for (String value : values) {
            AttrValueDO valueDO = new AttrValueDO();
            valueDO.setAttrId(attrId);
            valueDO.setValue(value);
            valueDO.setType(type);
            dos.add(valueDO);
        }
        saveBatch(dos);
    }

    public void removeByAttrId(Long attrId) {
        lambdaUpdate()
                .eq(AttrValueDO::getAttrId, attrId)
                .remove();
    }

    public List<AttrValueDO> listByAttrId(Long attrId) {
        return lambdaQuery()
                .eq(AttrValueDO::getAttrId, attrId)
                .list();
    }
}
