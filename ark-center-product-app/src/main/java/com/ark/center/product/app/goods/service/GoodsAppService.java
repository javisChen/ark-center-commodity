package com.ark.center.product.app.goods.service;

import cn.hutool.core.bean.BeanUtil;
import com.ark.center.product.app.goods.executor.GoodsCmdExe;
import com.ark.center.product.app.goods.executor.GoodsShelfCmdExe;
import com.ark.center.product.app.goods.query.GoodsQryExe;
import com.ark.center.product.client.category.dto.CategoryDTO;
import com.ark.center.product.client.category.dto.HomeCategoryDTO;
import com.ark.center.product.client.category.query.CategoryPageQry;
import com.ark.center.product.client.goods.command.GoodsCmd;
import com.ark.center.product.client.goods.command.GoodsShelfCmd;
import com.ark.center.product.client.goods.dto.GoodsDTO;
import com.ark.center.product.client.goods.query.GoodsQry;
import com.ark.center.product.client.search.query.SearchQry;
import com.ark.center.product.domain.category.Category;
import com.ark.center.product.domain.category.gateway.CategoryGateway;
import com.ark.center.product.domain.spu.Spu;
import com.ark.center.product.domain.spu.gateway.SpuGateway;
import com.ark.center.product.infra.product.gateway.es.GoodsRepository;
import com.ark.center.product.infra.product.gateway.es.SkuDoc;
import com.ark.component.dto.PageResponse;
import com.ark.component.orm.mybatis.base.BaseEntity;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 商品服务实现类
 * </p>
 *
 * @author EOP
 * @since 2022-03-05
 */
@Service
@RequiredArgsConstructor
public class GoodsAppService {

    private final SpuGateway spuGateway;

    private final CategoryGateway categoryGateway;

    private final GoodsQryExe goodsQryExe;

    private final GoodsCmdExe goodsCmdExe;

    private final GoodsShelfCmdExe goodsShelfCmdExe;

    private final GoodsRepository goodsRepository;

    @Transactional(rollbackFor = Exception.class)
    public Long save(GoodsCmd cmd) {
        return goodsCmdExe.execute(cmd);
    }

    public PageResponse<GoodsDTO> queryPages(GoodsQry goodsQry) {
        return goodsQryExe.queryPages(goodsQry);
    }
    public GoodsDTO queryDetails(Long spuId) {
        return goodsQryExe.queryDetails(spuId);
    }
    public List<SkuDoc> search(SearchQry searchQry) {
        Iterable<SkuDoc> all = goodsRepository.search(searchQry);
        return Lists.newArrayList(all);
    }

    /**
     * todo 临时方法
     */
    public void initES() {
        GoodsQry queryDTO = new GoodsQry();
        queryDTO.setSize(99999);
        PageResponse<GoodsDTO> response = queryPages(queryDTO);
        for (GoodsDTO record : response.getRecords()) {
            SkuDoc target = new SkuDoc();
            BeanUtil.copyProperties(record, target);
            goodsRepository.save(target);
        }
    }

    /**
     * todo 这个实现比较随意，不会考虑性能问题，该功能会重新实现
     */
    public List<HomeCategoryDTO> queryHomeCategories() {
        CategoryPageQry qry = new CategoryPageQry();
        qry.setLevel(1);
        List<HomeCategoryDTO> result = new ArrayList<>();
        List<CategoryDTO> categoryDTOS = categoryGateway.selectList(qry);
        for (CategoryDTO categoryDTO : categoryDTOS) {
            HomeCategoryDTO dto = new HomeCategoryDTO();
            dto.setCategoryId(categoryDTO.getId());
            dto.setCategoryName(categoryDTO.getName());
            List<Category> subCategories = categoryGateway.selectByLevelPath(categoryDTO.getLevelPath());
            if (CollectionUtils.isNotEmpty(subCategories)) {
                List<Long> ids = subCategories.stream().map(BaseEntity::getId).toList();
                List<Spu> spuList = spuGateway.selectByCategoryIds(ids);
                dto.setCommodities(spuList.stream().map(e -> {
                    HomeCategoryDTO.SubCommodity commodity = new HomeCategoryDTO.SubCommodity();
                    commodity.setPicUrl(e.getMainPicture());
                    commodity.setSpuId(e.getId());
                    commodity.setSpuName(e.getName());
                    return commodity;
                }).toList());
            }
            result.add(dto);
        }
        return result;
    }

    public void shelf(GoodsShelfCmd cmd) {
        goodsShelfCmdExe.execute(cmd);
    }
}
