//package com.ark.center.product.infra.product.service;
//
//import com.ark.center.product.client.goods.dto.GoodsAttrDTO;
//import com.ark.center.product.client.goods.dto.SkuDTO;
//import com.ark.center.product.infra.attr.gateway.AttrGateway;
//import com.ark.center.product.infra.sku.Sku;
//import com.ark.center.product.infra.sku.SkuAttr;
//import com.ark.center.product.infra.sku.assembler.SkuAssembler;
//import com.ark.center.product.infra.product.gateway.db.SkuAttrMapper;
//import com.ark.center.product.infra.product.gateway.db.SkuMapper;
//import com.ark.component.orm.mybatis.base.BaseEntity;
//import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
//import com.baomidou.mybatisplus.core.toolkit.Wrappers;
//import com.baomidou.mybatisplus.extension.service.IService;
//import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import lombok.RequiredArgsConstructor;
//import org.apache.commons.collections4.CollectionUtils;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Component
//@RequiredArgsConstructor
//public class SkuService extends ServiceImpl<SkuMapper, Sku> implements IService<Sku> {
//
//    private final SkuAssembler skuAssembler;
//    private final SkuAttrMapper skuAttrMapper;
//    private final AttrGateway attrGateway;
//
//    public List<Sku> queryByIds(List<Long> ids) {
//        return listByIds(ids);
//    }
//
//    public List<SkuDTO> selectBySpuId(Long spuId) {
//        List<Sku> skuList = lambdaQuery()
//                .eq(Sku::getSpuId, spuId)
//                .list();
//        return skuAssembler.toSkuDTO(skuList);
//    }
//
//    public List<SkuDTO> selectBySpuIds(List<Long> spuIds) {
//        List<Sku> skuList = lambdaQuery()
//                .in(Sku::getSpuId, spuIds)
//                .list();
//        List<SkuDTO> skuDTO = skuAssembler.toSkuDTO(skuList);
//        for (SkuDTO dto : skuDTO) {
//            List<GoodsAttrDTO> specs = dto.getSpecs();
//        }
//        return skuDTO;
//    }
//
//    public void insert(Sku sku) {
//        save(sku);
//    }
//
//    public void saveAttrs(List<SkuAttr> attrs) {
//
//    }
//
//    public void deleteBySpuId(Long spuId) {
//        List<Sku> records = lambdaQuery()
//                .select(BaseEntity::getId)
//                .eq(Sku::getSpuId, spuId)
//                .list();
//        if (CollectionUtils.isNotEmpty(records)) {
//            removeByIds(records);
//        }
//    }
//
//    public void deleteAttrsBySpuId(Long spuId, List<SkuDTO> originalSkus) {
//        List<Sku> skuList = lambdaQuery()
//                .select(BaseEntity::getId)
//                .eq(Sku::getSpuId, spuId)
//                .list();
//        if (CollectionUtils.isEmpty(skuList)) {
//            return;
//        }
//
//        List<Long> skuIds = skuList.stream().map(BaseEntity::getId).sorted().collect(Collectors.toList());
//
//        deleteAttrsBySkuIds(skuIds);
//    }
//
//    public void deleteAttrsBySkuIds(List<Long> skuIds) {
//        LambdaQueryWrapper<SkuAttr> qw = Wrappers.lambdaQuery(SkuAttr.class)
//                .select(BaseEntity::getId)
//                .in(SkuAttr::getSkuId, skuIds);
//        List<SkuAttr> skuAttrs = skuAttrMapper.selectList(qw);
//        if (CollectionUtils.isEmpty(skuAttrs)) {
//            return;
//        }
//        List<Long> ids = skuAttrs.stream().map(BaseEntity::getId).sorted().toList();
//        skuAttrMapper.deleteBatchIds(ids);
//    }
//
//    public void deleteBySkuIds(List<Long> skuIds) {
//        removeByIds(skuIds);
//    }
//
//    public void update(Sku sku) {
//        updateById(sku);
//    }
//}
