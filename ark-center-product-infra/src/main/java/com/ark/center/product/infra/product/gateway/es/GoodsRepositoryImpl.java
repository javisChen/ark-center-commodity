package com.ark.center.product.infra.product.gateway.es;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.query_dsl.*;
import co.elastic.clients.json.JsonData;
import com.ark.center.product.client.search.query.SearchQry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.index.query.*;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.ByQueryResponse;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class GoodsRepositoryImpl implements GoodsRepository, InitializingBean {

    private final ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public void saveAll(Iterable<SkuDoc> docs) {
        elasticsearchTemplate.save(docs);
    }

    @Override
    public void save(SkuDoc doc) {
        elasticsearchTemplate.save(doc);
    }

    @Override
    public void deleteById(Long id) {
        elasticsearchTemplate.delete(id.toString(), SkuDoc.class);
    }

    @Override
    public void deleteAllById(List<Long> list) {
        Query idsQuery = elasticsearchTemplate.idsQuery(list.stream().map(Object::toString).toList());
        ByQueryResponse delete = elasticsearchTemplate.delete(idsQuery, SkuDoc.class);
        System.out.println(delete);
    }

    @Override
    public Iterable<SkuDoc> search(SearchQry searchQry) {
//        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder()
//                .must(new MatchQueryBuilder("skuName", searchQry.getKeyword()))
//                .filter(new RangeQueryBuilder("showPrice").gte(1000))
//                .filter(new TermQueryBuilder("categoryId", "1566616161236848642"))
//                .filter(new TermsQueryBuilder("brandId", "1523326203994103809"))
//                .filter(new NestedQueryBuilder("attrs",
//                        QueryBuilders.boolQuery()
//                                .filter(new TermsQueryBuilder("attrs.attrId", "1706958257767624705", "1706958003538276354"))
//                                .filter(new TermsQueryBuilder("attrs.attrValue", "晴雪")), ScoreMode.None));

//        MatchQueryBuilder skuName = new MatchQueryBuilder("skuName", searchQry.getKeyword());
//
        NativeQuery nativeQueryBuilder = new NativeQueryBuilder()
                .withQuery(buildQuery(searchQry))
                .withPageable(buildPageable(searchQry))
//                .withHighlightQuery()
//                .withHighlightFields(new HighlightBuilder.Field("skuName").preTags("<b>").postTags("</b>"))
                .build();
        log.info("Search dsl -> {}", dsl);
        log.info("Search dsl -> {}", nativeQueryBuilder.getQuery().toString());
//        SearchHits<SkuDoc> search = elasticsearchTemplate.search(new StringQuery(dsl), SkuDoc.class);
        SearchHits<SkuDoc> search = elasticsearchTemplate.search(nativeQueryBuilder, SkuDoc.class);
        return search.stream().map(SearchHit::getContent).toList();
    }

    private PageRequest buildPageable(SearchQry searchQry) {
        return PageRequest.of(searchQry.getCurrent(), searchQry.getSize());
    }

    private co.elastic.clients.elasticsearch._types.query_dsl.Query buildQuery(SearchQry searchQry) {
        List<Long> brandIds = searchQry.getBrandIds();
        Long categoryId = searchQry.getCategoryId();
        String keyword = searchQry.getKeyword();
        String priceRange = searchQry.getPriceRange();
        String specs = searchQry.getSpecs();

        BoolQuery.Builder bool = new BoolQuery.Builder();
        bool
                .must(builder -> {
                    if (StringUtils.isNotBlank(keyword)) {
                        builder.match(mc -> mc.field("skuName").query(keyword));
                    }
                    return builder;
                })
                .filter(builder -> {
                    if (brandIds != null) {
                        builder.terms(mc -> mc
                                .field("brandId")
                                .terms(new TermsQueryField.Builder().value(brandIds.stream()
                                        .map(item -> new FieldValue.Builder().longValue(item).build())
                                        .toList()).build())
                        );
                    }
                    if (categoryId != null) {
                        builder.term(mc -> mc.field("categoryId").value(categoryId));
                    }
                    if (StringUtils.isNotBlank(priceRange)) {
                        String[] split = StringUtils.splitPreserveAllTokens(priceRange, "-");
                        if (split != null && split.length > 0) {
                            RangeQuery.Builder rangeBuilder = new RangeQuery.Builder();
                            rangeBuilder.field("showPrice");
                            String minPrice = split[0];
                            if (StringUtils.isNotBlank(minPrice)) {
                                rangeBuilder.gte(JsonData.of(minPrice));
                            }
                            if (split.length > 1) {
                                String maxPrice = split[1];
                                if (StringUtils.isNotBlank(maxPrice)) {
                                    rangeBuilder.lte(JsonData.of(maxPrice));
                                }
                            }
                            builder.range(rangeBuilder.build());
                        }
                    }

                    // 以^分隔每组规格，以_分隔规格id和value，以||分隔多个value，格式attrId_attrValueA||attrValueB^attrId_attrValue^attrId_attrValue
                    if (CollectionUtils.isNotEmpty(specs)) {
                        BoolQuery.Builder attrsBuilder = new BoolQuery.Builder();
                        String[] attrGroup = StringUtils.splitByWholeSeparator(specs, "^");
                        if (ArrayUtils.isNotEmpty(attrGroup)) {
                            List<Long> attrIds = extractAttrIds(attrGroup);
                        }
                        attrsBuilder.filter(filterBuilder
                                -> filterBuilder.terms(termsBuilder -> termsBuilder.field("attrs.attrIds").terms()))
                        builder.nested(nestedBuilder ->
                                nestedBuilder
                                        .path("attrs")
                                        .query(attrsBuilder.build()._toQuery())
                        );
                    }
                    return builder;
                });
        return bool.build()._toQuery();
    }

    private List<Long> extractAttrIds(String[] attrGroup) {
        Arrays.stream(attrGroup).map(attr -> {
            StringUtils.splitByWholeSeparator(attr, "_")[]
        })

        return null;
    }

    public static void main(String[] args) {

        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder()
                .must(new MatchQueryBuilder("skuName", "米"))
                .filter(new RangeQueryBuilder("showPrice").gte(1000))
                .filter(new TermQueryBuilder("categoryId", "1566616161236848642"))
                .filter(new TermsQueryBuilder("brandId", "1523326203994103809"))
                .filter(new NestedQueryBuilder("attrs",
                        QueryBuilders.boolQuery()
                                .filter(new TermsQueryBuilder("attrs.attrId", "1706958257767624705", "1706958003538276354"))
                                .filter(new TermsQueryBuilder("attrs.attrValue", "晴雪")), ScoreMode.None
                ));
        System.out.println(boolQueryBuilder.toString());
    }

    @Override
    public void afterPropertiesSet() {
        IndexOperations indexOperations = elasticsearchTemplate.indexOps(SkuDoc.class);
        if (!indexOperations.exists()) {
            boolean created = indexOperations.createWithMapping();
            log.info("sku index created {}", created);
        }
    }
}
