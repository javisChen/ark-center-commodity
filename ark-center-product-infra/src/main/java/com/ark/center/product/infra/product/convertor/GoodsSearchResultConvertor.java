package com.ark.center.product.infra.product.convertor;

import co.elastic.clients.elasticsearch._types.aggregations.Aggregate;
import co.elastic.clients.elasticsearch._types.aggregations.LongTermsBucket;
import co.elastic.clients.elasticsearch._types.aggregations.NestedAggregate;
import co.elastic.clients.elasticsearch._types.aggregations.StringTermsBucket;
import com.ark.center.product.client.search.dto.AggDTO;
import com.ark.center.product.client.search.dto.GoodsSearchDTO;
import com.ark.center.product.client.search.dto.SkuAttrDTO;
import com.ark.center.product.client.search.dto.SkuDTO;
import com.ark.center.product.infra.product.es.GoodsRepositoryImpl;
import com.ark.center.product.infra.product.es.doc.SkuAttrDoc;
import com.ark.center.product.infra.product.es.doc.SkuDoc;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchAggregation;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchAggregations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface GoodsSearchResultConvertor {

    default List<SkuDTO> toDTO(SearchHits<SkuDoc> searchHits) {
        List<SkuDTO> records = Lists.newArrayListWithCapacity(searchHits.getSearchHits().size());
        for (SearchHit<SkuDoc> searchHit : searchHits) {
            List<String> highlights = searchHit.getHighlightField("skuName");
            SkuDoc skuDoc = searchHit.getContent();
            if (CollectionUtils.isNotEmpty(highlights)) {
                skuDoc.setSkuName(highlights.getFirst());
            }
            records.add(toDTO(skuDoc));
        }
        return records;
    }

    default SkuDTO toDTO(SkuDoc skuDoc) {
        SkuDTO skuDTO = new SkuDTO();
        skuDTO.setSkuId(skuDoc.getSkuId());
        skuDTO.setSpuId(skuDoc.getSpuId());
        skuDTO.setSkuName(skuDoc.getSkuName());
        skuDTO.setBrandId(skuDoc.getBrandId());
        skuDTO.setCategoryId(skuDoc.getCategoryId());
        skuDTO.setSalesPrice(skuDoc.getSalesPrice());
        skuDTO.setPictures(skuDoc.getPictures());
        List<SkuAttrDTO> attrDTOList = skuDoc.getAttrs().stream().map(s -> {
            SkuAttrDTO skuAttrDTO = new SkuAttrDTO();
            skuAttrDTO.setAttrId(s.getAttrId());
            skuAttrDTO.setAttrName(s.getAttrName());
            skuAttrDTO.setAttrValue(s.getAttrValue());
            return skuAttrDTO;
        }).toList();
        skuDTO.setAttrs(attrDTOList);
        return skuDTO;
    }

    default GoodsSearchDTO fromES(SearchHits<SkuDoc> searchHits) {
        GoodsSearchDTO resultDTO = new GoodsSearchDTO();
        List<SkuDTO> skus = toDTO(searchHits);
        resultDTO.setSkus(skus);
        if (searchHits.hasAggregations()) {
            List<AggDTO> agg = new ArrayList<>();
            ElasticsearchAggregations aggregations = ((ElasticsearchAggregations) searchHits.getAggregations());
            if (aggregations != null) {

                Map<String, ElasticsearchAggregation> aggregationsAsMap = aggregations.aggregationsAsMap();

                // 聚合品牌
                ElasticsearchAggregation brandAgg = aggregationsAsMap.get(GoodsRepositoryImpl.BRAND_AGG_KEY);
                AggDTO brandAggDTO = new AggDTO();
                brandAggDTO.setType("brand");
                List<LongTermsBucket> brandIdBuckets = brandAgg.aggregation().getAggregate().lterms().buckets().array();
                List<AggDTO.Option> brandAggOptions = new ArrayList<>(brandIdBuckets.size());
                for (LongTermsBucket bucket : brandIdBuckets) {
                    AggDTO.Option option = new AggDTO.Option();
                    Aggregate aggregate = bucket.aggregations().get(GoodsRepositoryImpl.BRAND_NAME_AGG_KEY);
                    StringTermsBucket brandNameBucket = aggregate.sterms().buckets().array().getFirst();
                    option.setLabel(brandNameBucket.key().stringValue());
                    option.setValue(String.valueOf(bucket.key()));
                    brandAggOptions.add(option);
                }

                brandAggDTO.setOptions(brandAggOptions);

                // 聚合分类
                ElasticsearchAggregation categoryAgg = aggregationsAsMap.get(GoodsRepositoryImpl.CATEGORY_AGG_KEY);
                AggDTO categoryAggDTO = new AggDTO();
                categoryAggDTO.setType("category");
                List<LongTermsBucket> categoryIdBuckets = categoryAgg.aggregation().getAggregate().lterms().buckets().array();
                List<AggDTO.Option> categoryAggOptions = new ArrayList<>(brandIdBuckets.size());
                for (LongTermsBucket bucket : categoryIdBuckets) {
                    AggDTO.Option option = new AggDTO.Option();
                    Aggregate aggregate = bucket.aggregations().get(GoodsRepositoryImpl.CATEGORY_NAME_AGG_KEY);
                    StringTermsBucket brandNameBucket = aggregate.sterms().buckets().array().getFirst();
                    option.setLabel(brandNameBucket.key().stringValue());
                    option.setValue(String.valueOf(bucket.key()));
                    categoryAggOptions.add(option);
                }
                categoryAggDTO.setOptions(categoryAggOptions);

                ElasticsearchAggregation attrAgg = aggregationsAsMap.get(GoodsRepositoryImpl.ATTR_AGG_KEY);
                NestedAggregate attrBuckets = attrAgg.aggregation().getAggregate().nested();
                List<LongTermsBucket> attrIdBuckets = attrBuckets.aggregations().get(GoodsRepositoryImpl.ATTR_ID_AGG_KEY).lterms().buckets().array();
                for (LongTermsBucket attrIdBucket : attrIdBuckets) {
                    StringTermsBucket attrNameBucket = attrIdBucket.aggregations().get(GoodsRepositoryImpl.ATTR_NAME_AGG_KEY).sterms().buckets().array().getFirst();
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
        }
        return resultDTO;
    }
}
