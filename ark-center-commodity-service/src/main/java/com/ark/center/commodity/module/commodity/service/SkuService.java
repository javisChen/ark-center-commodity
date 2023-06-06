//package com.ark.center.commodity.module.commodity.service;
//
//import com.alibaba.fastjson2.JSONObject;
//import com.ark.center.commodity.client.commodity.query.SkuQry;
//import com.ark.center.commodity.client.commodity.dto.SkuRespDTO;
//import com.ark.center.commodity.infra.commodity.repository.db.SkuAttrDO;
//import com.ark.center.commodity.infra.commodity.repository.db.SkuDO;
//import com.ark.center.commodity.infra.commodity.repository.db.SkuMapper;
//import com.ark.center.commodity.module.commodity.dto.request.AttrReqDTO;
//import com.ark.center.commodity.module.commodity.dto.request.CommodityUpdateReqDTO;
//import com.ark.center.commodity.module.commodity.dto.request.SkuUpdateReqDTO;
//import com.ark.center.commodity.module.commodity.support.CommodityConvertor;
//import com.ark.center.commodity.module.commodity.support.CommodityHelper;
//import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
//import com.baomidou.mybatisplus.extension.service.IService;
//import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import com.ark.component.orm.mybatis.base.BaseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
///**
// * <p>
// * sku 服务实现类
// * </p>
// *
// * @author EOP
// * @since 2022-03-05
// */
//@Service
//public class SkuService extends ServiceImpl<SkuMapper, SkuDO> implements IService<SkuDO> {
//
//    private final SkuAttrService skuAttrService;
//
//    public SkuService(SkuAttrService skuAttrService) {
//        this.skuAttrService = skuAttrService;
//    }
//
//    @Transactional(rollbackFor = Exception.class)
//    public void saveSku(Long spuId, CommodityUpdateReqDTO reqDTO) {
//        if (CommodityHelper.isUpdateAction(reqDTO) && reqDTO.getFlushSku()) {
//            // todo 把当前的SKU记录到历史表里面
//            removeSku(spuId);
//            removeSkuAttrs(spuId);
//        }
//        List<SkuUpdateReqDTO> skuList = reqDTO.getSkuList();
//        for (SkuUpdateReqDTO skuUpdateReqDTO : skuList) {
//            SkuDO skuDO = assembleSkuDTO(spuId, reqDTO.getPicList().get(0), skuUpdateReqDTO);
//            if (CommodityHelper.isUpdateAction(reqDTO) && !reqDTO.getFlushSku()) {
//                updateById(skuDO);
//            } else {
//                save(skuDO);
//            }
//            saveSkuAttrs(skuDO.getId(), skuUpdateReqDTO.getSpecList());
//        }
//    }
//
//    private void removeSku(Long spuId) {
//
//        remove(new LambdaUpdateWrapper<SkuDO>().eq(SkuDO::getSpuId, spuId));
//    }
//
//    private void removeSkuAttrs(Long spuId) {
//        List<SkuDO> skuDOList = listBySpuId(spuId);
//        List<Long> skuIds = skuDOList.stream().map(BaseEntity::getId).collect(Collectors.toList());
//        skuAttrService.removeByIds(skuIds);
//    }
//
//    private void saveSkuAttrs(Long skuId, List<AttrReqDTO> skuAttrList) {
//        List<SkuAttrDO> doList = skuAttrList.stream().map(item -> {
//            SkuAttrDO skuAttrDO = new SkuAttrDO();
//            skuAttrDO.setSkuId(skuId);
//            skuAttrDO.setAttrValue(item.getAttrValue());
//            return skuAttrDO;
//        }).collect(Collectors.toList());
//        skuAttrService.saveBatch(doList);
//    }
//
//    private SkuDO assembleSkuDTO(Long spuId, String mainPicture, SkuUpdateReqDTO entity) {
//        SkuDO skuDO = new SkuDO();
//        if (entity.getId() != null && entity.getId() > 0) {
//            skuDO.setId(entity.getId());
//        }
//        skuDO.setSpuId(spuId);
//        skuDO.setCode(entity.getCode());
//        skuDO.setSalesPrice(entity.getSalesPrice());
//        skuDO.setCostPrice(entity.getCostPrice());
//        skuDO.setStock(entity.getStock());
//        skuDO.setWarnStock(entity.getWarnStock());
//        skuDO.setSpecData(JSONObject.toJSONString(entity.getSpecList()));
//        // todo 暂时用spu的主图设置，后面再增加sku图片的功能
//        skuDO.setMainPicture(mainPicture);
//        return skuDO;
//    }
//
//    public List<SkuDO> listBySpuId(Long spuId) {
//        return lambdaQuery()
//                .eq(SkuDO::getSpuId, spuId)
//                .list();
//    }
//
//    public List<SkuRespDTO> findByIds(SkuQry reqDTO) {
//        List<Long> skuIds = reqDTO.getSkuIds();
//        List<SkuDO> skuList = listByIds(skuIds);
//        return CommodityConvertor.convertToSkuDTO(skuList);
//    }
//
//}
