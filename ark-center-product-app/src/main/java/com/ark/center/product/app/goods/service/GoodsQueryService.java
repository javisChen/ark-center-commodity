package com.ark.center.product.app.goods.service;

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
import com.ark.center.product.client.search.dto.GoodsSearchDTO;
import com.ark.center.product.client.search.query.SearchQuery;
import com.ark.center.product.infra.category.Category;
import com.ark.center.product.infra.category.service.CategoryService;
import com.ark.center.product.infra.product.convertor.GoodsSearchResultConvertor;
import com.ark.center.product.infra.product.es.GoodsRepository;
import com.ark.center.product.infra.product.es.doc.SkuDoc;
import com.ark.center.product.infra.product.service.SpuService;
import com.ark.center.product.infra.spu.Spu;
import com.ark.component.dto.PageResponse;
import com.ark.component.orm.mybatis.base.BaseEntity;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.elasticsearch.core.SearchHits;
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
public class GoodsQueryService {

    private final SpuService spuService;

    private final CategoryService categoryService;

    private final GoodsQryExe goodsQryExe;

    private final GoodsCmdExe goodsCmdExe;

    private final GoodsShelfCmdExe goodsShelfCmdExe;

    private final GoodsRepository goodsRepository;

    private final GoodsSearchResultConvertor goodsSearchResultConvertor;

    @Transactional(rollbackFor = Throwable.class)
    public Long save(GoodsCmd cmd) {
        return goodsCmdExe.execute(cmd);
    }

    public PageResponse<GoodsDTO> queryPages(GoodsQry goodsQry) {
        return goodsQryExe.queryPages(goodsQry);
    }

    public GoodsDTO queryDetails(Long spuId) {
        return goodsQryExe.queryDetails(spuId);
    }

    public GoodsSearchDTO search(SearchQuery searchQuery) {
        SearchHits<SkuDoc> searchHits = goodsRepository.search(searchQuery);
        if (!searchHits.hasSearchHits()) {
            return new GoodsSearchDTO();
        }
        return goodsSearchResultConvertor.fromES(searchHits);
    }


    /**
     * todo 这个实现比较随意，不会考虑性能问题，该功能会重新实现
     */
    public List<HomeCategoryDTO> queryHomeCategories() {
        CategoryPageQry qry = new CategoryPageQry();
        qry.setLevel(1);
        List<HomeCategoryDTO> result = new ArrayList<>();
        List<CategoryDTO> categoryDTOS = categoryService.selectList(qry);
        for (CategoryDTO categoryDTO : categoryDTOS) {
            HomeCategoryDTO dto = new HomeCategoryDTO();
            dto.setCategoryId(categoryDTO.getId());
            dto.setCategoryName(categoryDTO.getName());
            List<Category> subCategories = categoryService.selectByLevelPath(categoryDTO.getLevelPath());
            if (CollectionUtils.isNotEmpty(subCategories)) {
                List<Long> ids = subCategories.stream().map(BaseEntity::getId).toList();
                List<Spu> spuList = spuService.selectByCategoryIds(ids);
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
