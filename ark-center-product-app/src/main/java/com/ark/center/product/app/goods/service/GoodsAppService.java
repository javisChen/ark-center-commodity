package com.ark.center.product.app.goods.service;

import co.elastic.clients.elasticsearch._types.aggregations.Aggregate;
import co.elastic.clients.elasticsearch._types.aggregations.LongTermsBucket;
import co.elastic.clients.elasticsearch._types.aggregations.NestedAggregate;
import co.elastic.clients.elasticsearch._types.aggregations.StringTermsBucket;
import com.ark.center.product.app.goods.assembler.GoodsSearchAssembler;
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
import com.ark.center.product.client.search.dto.AggDTO;
import com.ark.center.product.client.search.dto.SearchResultDTO;
import com.ark.center.product.client.search.query.SearchQry;
import com.ark.center.product.domain.category.Category;
import com.ark.center.product.domain.category.gateway.CategoryGateway;
import com.ark.center.product.domain.spu.Spu;
import com.ark.center.product.domain.spu.gateway.SpuGateway;
import com.ark.center.product.infra.product.gateway.es.GoodsRepository;
import com.ark.center.product.infra.product.gateway.es.GoodsRepositoryImpl;
import com.ark.center.product.infra.product.gateway.es.SkuDoc;
import com.ark.component.dto.PageResponse;
import com.ark.component.orm.mybatis.base.BaseEntity;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchAggregation;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchAggregations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    private final GoodsSearchAssembler goodsSearchAssembler;

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

    public SearchResultDTO search(SearchQry searchQry) {
        SearchResultDTO resultDTO = new SearchResultDTO();
        SearchHits<SkuDoc> searchHits = goodsRepository.search(searchQry);
        if (!searchHits.hasSearchHits()) {
            return resultDTO;
        }
        List<SkuDoc> hits = searchHits.getSearchHits().stream().map(SearchHit::getContent).toList();
        resultDTO.setSkus(goodsSearchAssembler.toDTO(hits));
        if (searchHits.hasAggregations()) {
            List<AggDTO> agg = new ArrayList<>();
            ElasticsearchAggregations aggregations = ((ElasticsearchAggregations) searchHits.getAggregations());
            Map<String, ElasticsearchAggregation> aggregationsAsMap = aggregations.aggregationsAsMap();

            ElasticsearchAggregation brandAgg = aggregationsAsMap.get(GoodsRepositoryImpl.BRAND_AGG_KEY);
            AggDTO brandAggDTO = new AggDTO();
            brandAggDTO.setType("brand");
            List<LongTermsBucket> brandIdBuckets = brandAgg.aggregation().getAggregate().lterms().buckets().array();
            List<AggDTO.Option> brandAggOptions = new ArrayList<>(brandIdBuckets.size());
            for (LongTermsBucket bucket : brandIdBuckets) {
                AggDTO.Option option = new AggDTO.Option();
                Aggregate aggregate = bucket.aggregations().get(GoodsRepositoryImpl.BRAND_NAME_AGG_KEY);
                StringTermsBucket brandNameBucket = aggregate.sterms().buckets().array().get(0);
                option.setLabel(brandNameBucket.key().stringValue());
                option.setValue(String.valueOf(bucket.key()));
                brandAggOptions.add(option);
            }

            brandAggDTO.setOptions(brandAggOptions);

            ElasticsearchAggregation categoryAgg = aggregationsAsMap.get(GoodsRepositoryImpl.CATEGORY_AGG_KEY);
            AggDTO categoryAggDTO = new AggDTO();
            categoryAggDTO.setType("category");
            List<LongTermsBucket> categoryIdBuckets = categoryAgg.aggregation().getAggregate().lterms().buckets().array();
            List<AggDTO.Option> categoryAggOptions = new ArrayList<>(brandIdBuckets.size());
            for (LongTermsBucket bucket : categoryIdBuckets) {
                AggDTO.Option option = new AggDTO.Option();
                Aggregate aggregate = bucket.aggregations().get(GoodsRepositoryImpl.CATEGORY_NAME_AGG_KEY);
                StringTermsBucket brandNameBucket = aggregate.sterms().buckets().array().get(0);
                option.setLabel(brandNameBucket.key().stringValue());
                option.setValue(String.valueOf(bucket.key()));
                categoryAggOptions.add(option);
            }
            categoryAggDTO.setOptions(categoryAggOptions);


            ElasticsearchAggregation attrAgg = aggregationsAsMap.get(GoodsRepositoryImpl.ATTR_AGG_KEY);
            NestedAggregate attrBuckets = attrAgg.aggregation().getAggregate().nested();
            List<LongTermsBucket> attrIdBuckets = attrBuckets.aggregations().get(GoodsRepositoryImpl.ATTR_ID_AGG_KEY).lterms().buckets().array();
            for (LongTermsBucket attrIdBucket : attrIdBuckets) {
                StringTermsBucket attrNameBucket = attrIdBucket.aggregations().get(GoodsRepositoryImpl.ATTR_NAME_AGG_KEY).sterms().buckets().array().get(0);
                AggDTO attrAggDTO = new AggDTO();
                attrAggDTO.setId(attrIdBucket.key());
                attrAggDTO.setLabel(attrNameBucket.key().stringValue());
                attrAggDTO.setType("attrs");
                attrAggDTO.setOptions(attrNameBucket.aggregations().get(GoodsRepositoryImpl.ATTR_VALUE_AGG_KEY)
                        .sterms()
                        .buckets()
                        .array().stream().toList().stream().map(item -> {
                            AggDTO.Option option = new AggDTO.Option();
                            String value = item.key().stringValue();
                            option.setValue(value);
                            option.setLabel(value);
                            return option;
                        }).toList());
                agg.add(attrAggDTO);
            }
            agg.add(brandAggDTO);
            agg.add(categoryAggDTO);
            resultDTO.setAgg(agg);
        }
        return resultDTO;
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
