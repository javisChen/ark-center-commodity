package com.kt.cloud.commodity.module.commodity.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kt.cloud.commodity.constants.AttachmentBizType;
import com.kt.cloud.commodity.dao.entity.AttachmentDO;
import com.kt.cloud.commodity.dao.entity.SpuAttrDO;
import com.kt.cloud.commodity.dao.entity.SpuDO;
import com.kt.cloud.commodity.dao.entity.SpuSalesDO;
import com.kt.cloud.commodity.dao.mapper.SpuMapper;
import com.kt.cloud.commodity.module.attachment.service.AttachmentService;
import com.kt.cloud.commodity.module.commodity.dto.request.AttrReqDTO;
import com.kt.cloud.commodity.module.commodity.dto.request.CommodityPageQueryReqDTO;
import com.kt.cloud.commodity.module.commodity.dto.request.CommodityUpdateReqDTO;
import com.kt.cloud.commodity.module.commodity.dto.response.CommodityPageRespDTO;
import com.kt.component.web.util.bean.BeanConvertor;
import org.apache.commons.collections4.CollectionUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

    public SpuService(AttachmentService attachmentService,
                      SpuAttrService spuAttrService,
                      SpuSalesService spuSalesService) {
        this.attachmentService = attachmentService;
        this.spuAttrService = spuAttrService;
        this.spuSalesService = spuSalesService;
    }

    public IPage<CommodityPageRespDTO> getPageList(CommodityPageQueryReqDTO queryDTO) {
        return lambdaQuery()
                .page(new PageDTO<>(queryDTO.getCurrent(), queryDTO.getSize()))
                .convert(item -> BeanConvertor.copy(item, CommodityPageRespDTO.class));
    }

    @Transactional(rollbackFor = Exception.class)
    public Long saveSpu(CommodityUpdateReqDTO reqDTO) {
        SpuDO spuDO = assembleSpuDO(reqDTO);
        // 保存SPU基本信息
        save(spuDO);
        // 保存销售信息
        saveSalesInfo(spuDO, reqDTO);
        // 保存图片
        savePictures(reqDTO, spuDO);
        // 保存参数属性
        saveParams(reqDTO, spuDO);
        return spuDO.getId();

    }

    private void saveSalesInfo(SpuDO spuDO, CommodityUpdateReqDTO reqDTO) {
        SpuSalesDO entity = new SpuSalesDO();
        entity.setSpuId(spuDO.getId());
        entity.setFreightTemplateId(reqDTO.getFreightTemplateId());
        entity.setPcDetailHtml(reqDTO.getPcDetailHtml());
        entity.setMobileDetailHtml(reqDTO.getMobileDetailHtml());
        spuSalesService.save(entity);
    }

    private void saveParams(CommodityUpdateReqDTO reqDTO, SpuDO spuDO) {
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
        List<String> picList = reqDTO.getPicList();
        if (CollectionUtils.isNotEmpty(picList)) {
            attachmentService.saveAttachments(AttachmentBizType.SPU_PIC, spuDO.getId(), picList);
        }
    }


    @NotNull
    private SpuDO assembleSpuDO(CommodityUpdateReqDTO reqDTO) {
        SpuDO spuDO = new SpuDO();
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
}
