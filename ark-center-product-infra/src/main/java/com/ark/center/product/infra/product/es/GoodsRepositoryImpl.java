package com.ark.center.product.infra.product.es;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;
import co.elastic.clients.elasticsearch._types.aggregations.TermsAggregation;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.NestedQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.RangeQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.TermsQueryField;
import co.elastic.clients.json.JsonData;
import com.ark.center.product.client.search.query.SearchQuery;
import com.ark.center.product.infra.product.es.doc.SkuDoc;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.math3.stat.descriptive.summary.Product;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.data.elasticsearch.core.query.highlight.Highlight;
import org.springframework.data.elasticsearch.core.query.highlight.HighlightField;
import org.springframework.data.elasticsearch.core.query.highlight.HighlightParameters;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.StreamSupport;

@Component
@RequiredArgsConstructor
@Slf4j
public class GoodsRepositoryImpl implements GoodsRepository, InitializingBean {

    public final static String BRAND_NAME_AGG_KEY = "brand_name_agg";
    public final static String CATEGORY_NAME_AGG_KEY = "category_name_agg";
    public final static String ATTR_VALUE_AGG_KEY = "attr_value_agg";
    public final static String ATTR_NAME_AGG_KEY = "attr_name_agg";
    public final static String BRAND_AGG_KEY = "brand_agg";
    public final static String CATEGORY_AGG_KEY = "category_agg";
    public final static String ATTR_AGG_KEY = "attr_agg";
    public final static String ATTR_ID_AGG_KEY = "attr_id_agg";
    
    private final ElasticsearchTemplate elasticsearchTemplate;
    private final ElasticsearchOperations elasticsearchOperations;

    @Override
    public void saveAll(Iterable<SkuDoc> docs) {

        List<IndexQuery> queries = StreamSupport.stream(docs.spliterator(), false)
                .map(product -> new IndexQueryBuilder()
                        .withObject(product)
                        .build())
                .toList();

        elasticsearchOperations.bulkIndex(queries, Product.class);
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
        elasticsearchTemplate.delete(idsQuery, SkuDoc.class);
    }

    @Override
    public SearchHits<SkuDoc> search(SearchQuery searchQuery) {
        NativeQuery nativeQueryBuilder = buildNativeQueryBuilder(searchQuery);
        if (log.isDebugEnabled()) {
            log.info("Search DSL -> {}", nativeQueryBuilder.getQuery());
        }
        return elasticsearchTemplate.search(nativeQueryBuilder, SkuDoc.class);
    }

    public NativeQuery buildNativeQueryBuilder(SearchQuery searchQuery) {
        NativeQueryBuilder nativeQueryBuilder = new NativeQueryBuilder()
                .withQuery(buildQuery(searchQuery))
                .withPageable(buildPageable(searchQuery))
                .withSort(buildSort(searchQuery))
                .withHighlightQuery(buildHighlightQuery(searchQuery));
        return buildAggregation(nativeQueryBuilder).build();
    }

    private NativeQueryBuilder buildAggregation(NativeQueryBuilder nativeQueryBuilder) {
        // brand
        Aggregation brandNameAgg = Aggregation.of(sub -> sub.terms(TermsAggregation.of(terms -> terms.field("brandName"))));
        Aggregation brandIdAgg = Aggregation.of(
                fn -> fn.terms(TermsAggregation.of(terms -> terms.field("brandId")))
                        .aggregations(BRAND_NAME_AGG_KEY, brandNameAgg));

        // category
        Aggregation categoryNameAgg = Aggregation.of(sub -> sub.terms(TermsAggregation.of(terms -> terms.field("categoryName"))));
        Aggregation categoryIdAgg = Aggregation.of(
                fn -> fn.terms(TermsAggregation.of(terms -> terms.field("categoryId")))
                        .aggregations(CATEGORY_NAME_AGG_KEY, categoryNameAgg));

        // attr
        Aggregation attrValueAgg = Aggregation.of(sub2 -> sub2.terms(TermsAggregation.of(terms -> terms.field("attrs.attrValue"))));
        Aggregation attrNameAgg = Aggregation.of(sub -> sub.terms(TermsAggregation.of(terms -> terms.field("attrs.attrName")))
                .aggregations(ATTR_VALUE_AGG_KEY, attrValueAgg));
        Aggregation attrAgg = Aggregation.of(agg -> agg
                .terms(TermsAggregation.of(terms -> terms.field("attrs.attrId")))
                .aggregations(ATTR_NAME_AGG_KEY, attrNameAgg));
        return nativeQueryBuilder
                .withAggregation(BRAND_AGG_KEY, brandIdAgg)
                .withAggregation(CATEGORY_AGG_KEY, categoryIdAgg)
                .withAggregation(ATTR_AGG_KEY, Aggregation.of(fn -> fn
                        .nested(nested -> nested.path("attrs"))
                        .aggregations(ATTR_ID_AGG_KEY, attrAgg)));
    }

    private Sort buildSort(SearchQuery searchQuery) {
        String sortField = searchQuery.getSortField();
        String sortDirection = searchQuery.getSortDirection();
        if (StringUtils.isNotBlank(sortField)) {
            if (StringUtils.isBlank(sortDirection)) {
                return Sort.by(Sort.Direction.DESC, sortField);
            }
            return Sort.by(Sort.Direction.valueOf(sortDirection.toUpperCase()), sortField);
        }
        return Sort.by(Sort.Direction.DESC, "updateTime");
    }

    private PageRequest buildPageable(SearchQuery searchQuery) {
        return PageRequest.of(searchQuery.getCurrent() - 1, searchQuery.getSize());
    }

    private HighlightQuery buildHighlightQuery(SearchQuery searchQuery) {
        List<HighlightField> fields = new ArrayList<>();
        HighlightField field = new HighlightField("skuName");
        fields.add(field);
        Highlight highlight = new Highlight(HighlightParameters.builder()
                .withPreTags("<b style=\"color: red\">")
                .withPostTags("</b>")
                .build(), fields);
        return new HighlightQuery(highlight, null);
    }

    private co.elastic.clients.elasticsearch._types.query_dsl.Query buildQuery(SearchQuery searchQuery) {
        String brand = searchQuery.getBrand();
        Long categoryId = searchQuery.getCategory();
        String keyword = searchQuery.getKeyword();
        String priceRange = searchQuery.getPriceRange();
        String specs = searchQuery.getAttrs();

        BoolQuery.Builder bool = new BoolQuery.Builder();

        if (StringUtils.isNotBlank(keyword)) {
            bool.must(builder -> builder.match(mc -> mc.field("skuName").query(keyword)));
        }

        if (StringUtils.isNotBlank(brand)) {
            String[] array = StringUtils.splitByWholeSeparatorPreserveAllTokens(brand, "^");
            bool.filter(builder -> builder.terms(mc -> mc
                    .field("brandId")
                    .terms(TermsQueryField.of(
                                    fn -> fn.value(Arrays.stream(array)
                                            .map(FieldValue::of)
                                            .toList())
                            )
                    )
            ));
        }

        if (categoryId != null) {
            bool.filter(builder -> builder.term(mc -> mc.field("categoryId").value(categoryId)));
        }

        if (StringUtils.isNotBlank(priceRange)) {
            bool.filter(builder -> {
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
                return builder;
            });

        }

        if (StringUtils.isNotBlank(specs)) {
            bool.filter(buildAttrQuery(specs));
        }
        return bool.build()._toQuery();
    }

    /**
     * 以^分隔每组规格，以_分隔规格id和value，以||分隔多个value
     * {"nested":{"path":"attrs","query":{"bool":{"must":[{"term":{"attrs.attrId":{"value":"8"}}},{"terms":{"attrs.attrValue":["红","蓝"]}}]}}}}
     * {"nested":{"path":"attrs","query":{"bool":{"must":[{"term":{"attrs.attrId":{"value":"9"}}},{"terms":{"attrs.attrValue":["32G"]}}]}}}}
     * {"nested":{"path":"attrs","query":{"bool":{"must":[{"term":{"attrs.attrId":{"value":"10"}}},{"terms":{"attrs.attrValue":["256"]}}]}}}}
     *
     * @param specs 格式attrId_attrValueA||attrValueB^attrId_attrValue^attrId_attrValue
     * @return Query
     */
    public List<co.elastic.clients.elasticsearch._types.query_dsl.Query> buildAttrQuery(String specs) {
        String[] attrGroup = StringUtils.splitByWholeSeparator(specs, "^");
        if (ArrayUtils.isEmpty(attrGroup)) {
            return null;
        }
        List<co.elastic.clients.elasticsearch._types.query_dsl.Query> nestedQueries = new ArrayList<>();
        for (String attr : attrGroup) {
            String[] idAndValue = StringUtils.splitByWholeSeparator(attr, "_");
            if (idAndValue.length != 2) {
                continue;
            }
            String attrId = idAndValue[0];
            String attrValue = idAndValue[1];
            if (StringUtils.isEmpty(attrId) || StringUtils.isEmpty(attrValue)) {
                continue;
            }
            String[] values = StringUtils.splitByWholeSeparator(attrValue, "||");

            NestedQuery.Builder nestedBuilder = new NestedQuery.Builder();
            nestedBuilder
                    .path("attrs")
                    .query(builder -> {
                        BoolQuery.Builder attrsBuilder = new BoolQuery.Builder();
                        attrsBuilder
                                .must(mustBuilder -> {
                                    mustBuilder.term(termsBuilder -> termsBuilder
                                            .field("attrs.attrId")
                                            .value(attrId)
                                    );
                                    return mustBuilder;
                                })
                                .must(mustBuilder -> {
                                    mustBuilder.terms(termsBuilder -> termsBuilder
                                            .field("attrs.attrValue")
                                            .terms(TermsQueryField.of(fn -> fn.value(Arrays.stream(values)
                                                            .map(FieldValue::of).toList())
                                                    )
                                            )
                                    );
                                    return mustBuilder;
                                });
                        return builder.bool(attrsBuilder.build());
                    });
            nestedQueries.add(nestedBuilder.build()._toQuery());
        }
        return nestedQueries;
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
