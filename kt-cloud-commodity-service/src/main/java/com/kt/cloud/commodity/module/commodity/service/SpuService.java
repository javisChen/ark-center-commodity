package com.kt.cloud.commodity.module.commodity.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kt.cloud.commodity.constants.AttachmentBizType;
import com.kt.cloud.commodity.dao.entity.*;
import com.kt.cloud.commodity.dao.mapper.SpuMapper;
import com.kt.cloud.commodity.module.attachment.service.AttachmentService;
import com.kt.cloud.commodity.module.attr.service.AttrOptionService;
import com.kt.cloud.commodity.module.brand.service.BrandService;
import com.kt.cloud.commodity.module.category.service.CategoryAdminService;
import com.kt.cloud.commodity.module.commodity.dto.request.AttrOptionReqDTO;
import com.kt.cloud.commodity.module.commodity.dto.request.AttrReqDTO;
import com.kt.cloud.commodity.module.commodity.dto.request.CommodityPageQueryReqDTO;
import com.kt.cloud.commodity.module.commodity.dto.request.CommodityUpdateReqDTO;
import com.kt.cloud.commodity.module.commodity.dto.response.CommodityPageRespDTO;
import com.kt.component.orm.mybatis.base.BaseEntity;
import com.kt.component.web.util.bean.BeanConvertor;
import org.apache.commons.collections4.CollectionUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.kt.cloud.commodity.module.commodity.support.CommodityHelper.isUpdateAction;

/**
 * <p>
 * spu主表 服务实现类
 * </p>
 *
 * @author EOP
 * @since 2022-03-05
 */
@Service
public class SpuService extends ServiceImpl<SpuMapper, SpuDO> implements IService<SpuDO> {

    private final AttachmentService attachmentService;
    private final SpuAttrService spuAttrService;
    private final SpuSalesService spuSalesService;
    private final AttrOptionService attrOptionService;
    private final BrandService brandService;
    private final CategoryAdminService categoryAdminService;

    public SpuService(AttachmentService attachmentService,
                      SpuAttrService spuAttrService,
                      SpuSalesService spuSalesService,
                      AttrOptionService attrOptionService, BrandService brandService, CategoryAdminService categoryAdminService) {
        this.attachmentService = attachmentService;
        this.spuAttrService = spuAttrService;
        this.spuSalesService = spuSalesService;
        this.attrOptionService = attrOptionService;
        this.brandService = brandService;
        this.categoryAdminService = categoryAdminService;
    }

    public IPage<CommodityPageRespDTO> getPageList(CommodityPageQueryReqDTO queryDTO) {
        Page<SpuDO> page = lambdaQuery()
                .orderByDesc(BaseEntity::getGmtModified)
                .page(new Page<>(queryDTO.getCurrent(), queryDTO.getSize()));
        List<SpuDO> records = page.getRecords();
        if (CollectionUtils.isEmpty(records)) {
            return new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        }
        List<Long> brandIds = records.stream().map(SpuDO::getBrandId).collect(Collectors.toList());
        List<Long> categoryIds = records.stream().map(SpuDO::getCategoryId).collect(Collectors.toList());
        Map<Long, String> brandMap = brandService.listByIds(brandIds).stream().collect(Collectors.toMap(BaseEntity::getId, BrandDO::getName));
        Map<Long, String> categoryMap = categoryAdminService.listByIds(categoryIds).stream().collect(Collectors.toMap(BaseEntity::getId, CategoryDO::getName));

        return page.convert(record -> {
            CommodityPageRespDTO respDTO = BeanConvertor.copy(record, CommodityPageRespDTO.class);
            respDTO.setBrandName(brandMap.get(record.getBrandId()));
            respDTO.setCategoryName(categoryMap.get(record.getCategoryId()));
            return respDTO;
        });
    }

    @Transactional(rollbackFor = Exception.class)
    public Long saveSpu(CommodityUpdateReqDTO reqDTO) {
        SpuDO spuDO = assembleSpuDO(reqDTO);
        // 保存SPU基本信息
        saveBaseInfo(spuDO);
        // 保存销售信息
        saveSalesInfo(spuDO, reqDTO);
        // 保存图片
        savePictures(reqDTO, spuDO);
        // 保存参数属性
        saveParams(reqDTO, spuDO);
        // 保存属性项
        saveAttrOptions(reqDTO, spuDO);
        return spuDO.getId();

    }

    private void saveBaseInfo(SpuDO spuDO) {
        saveOrUpdate(spuDO);
    }

    private void saveAttrOptions(CommodityUpdateReqDTO reqDTO, SpuDO spuDO) {
        List<AttrOptionReqDTO> newAttrOptionList = reqDTO.getNewAttrOptionList();
        if (CollectionUtils.isNotEmpty(newAttrOptionList)) {
            for (AttrOptionReqDTO attrOptionReqDTO : newAttrOptionList) {
                attrOptionService.batchSave(attrOptionReqDTO.getAttrId(), attrOptionReqDTO.getValueList(),
                        AttrOptionDO.Type.EXCLUSIVE.getValue(), spuDO.getId());
            }
        }
    }

    private void saveSalesInfo(SpuDO spuDO, CommodityUpdateReqDTO reqDTO) {
        SpuSalesDO entity = new SpuSalesDO();
        entity.setSpuId(spuDO.getId());
        entity.setFreightTemplateId(reqDTO.getFreightTemplateId());
        entity.setPcDetailHtml(reqDTO.getPcDetailHtml());
        entity.setMobileDetailHtml(reqDTO.getMobileDetailHtml());
        entity.setParamData(JSONObject.toJSONString(reqDTO.getParamList()));

        if (isUpdateAction(reqDTO)) {
            spuSalesService.update(entity, new LambdaUpdateWrapper<SpuSalesDO>().eq(SpuSalesDO::getSpuId, spuDO.getId()));
        } else {
            spuSalesService.save(entity);
        }
    }

    private void saveParams(CommodityUpdateReqDTO reqDTO, SpuDO spuDO) {
        if (isUpdateAction(reqDTO)) {
            spuAttrService.removeBySpuId(spuDO.getId());
        }
        List<AttrReqDTO> paramList = reqDTO.getParamList();
        List<SpuAttrDO> attrDOList = paramList.stream().map(item -> {
            SpuAttrDO spuAttrDO = new SpuAttrDO();
            spuAttrDO.setSpuId(spuDO.getId());
            spuAttrDO.setAttrValue(item.getAttrValue());
            return spuAttrDO;
        }).collect(Collectors.toList());
        spuAttrService.saveBatch(attrDOList);
    }

    private void savePictures(CommodityUpdateReqDTO reqDTO, SpuDO spuDO) {
        if (isUpdateAction(reqDTO)) {
            attachmentService.removeAttachments(AttachmentBizType.SPU_PIC, spuDO.getId());
        }
        List<String> picList = reqDTO.getPicList();
        if (CollectionUtils.isNotEmpty(picList)) {
            attachmentService.saveAttachments(AttachmentBizType.SPU_PIC, spuDO.getId(), picList);
        }
    }

    @NotNull
    private SpuDO assembleSpuDO(CommodityUpdateReqDTO reqDTO) {
        SpuDO spuDO = new SpuDO();
        if (isUpdateAction(reqDTO)) {
            spuDO.setId(reqDTO.getId());
        }
        spuDO.setName(reqDTO.getName());
        spuDO.setCode(reqDTO.getCode());
        spuDO.setDescription(reqDTO.getDescription());
        spuDO.setMainPicture(reqDTO.getMainPicture());
        spuDO.setShelfStatus(reqDTO.getShelfStatus());
        spuDO.setShowPrice(reqDTO.getShowPrice());
        spuDO.setUnit(reqDTO.getUnit());
        spuDO.setWeight(reqDTO.getWeight());
        spuDO.setBrandId(reqDTO.getBrandId());
        spuDO.setCategoryId(reqDTO.getCategoryId());
        if (CollectionUtils.isNotEmpty(reqDTO.getPicList())) {
            spuDO.setMainPicture(reqDTO.getPicList().get(0));
        }
        return spuDO;
    }

    public List<String> getPicList(Long spuId) {
        List<AttachmentDO> attachmentDOS =  attachmentService.listByBizTypeAndBizId(AttachmentBizType.SPU_PIC, spuId);
        if (CollectionUtils.isEmpty(attachmentDOS)) {
            return Collections.emptyList();
        }
        return attachmentDOS.stream().map(AttachmentDO::getUrl).collect(Collectors.toList());
    }

    public SpuSalesDO getSalesInfo(Long spuId) {
        return spuSalesService.getBySpuId(spuId);
    }

    public List<SpuDO> listByCategoryIds(List<Long> categoryIds) {
        return lambdaQuery()
                .in(SpuDO::getCategoryId, categoryIds)
                .list();
    }
}
