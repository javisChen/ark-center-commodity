package com.ark.center.product.infra.product.gateway.es;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.query_dsl.*;
import co.elastic.clients.json.JsonData;
import com.ark.center.product.client.search.query.SearchQry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.ByQueryResponse;
import org.springframework.data.elasticsearch.core.query.HighlightQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.core.query.highlight.Highlight;
import org.springframework.data.elasticsearch.core.query.highlight.HighlightField;
import org.springframework.data.elasticsearch.core.query.highlight.HighlightParameters;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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
        NativeQuery nativeQueryBuilder = buildNativeQueryBuilder(searchQry);
        log.info("Search dsl -> {}", nativeQueryBuilder.getQuery());
        SearchHits<SkuDoc> search = elasticsearchTemplate.search(nativeQueryBuilder, SkuDoc.class);
        return search.stream().map(SearchHit::getContent).toList();
    }

    public NativeQuery buildNativeQueryBuilder(SearchQry searchQry) {
        return new NativeQueryBuilder()
                .withQuery(buildQuery(searchQry))
                .withPageable(buildPageable(searchQry))
                .withSort(buildSort(searchQry))
                .withHighlightQuery(buildHighlightQuery(searchQry))
                .build();
    }

    private Sort buildSort(SearchQry searchQry) {
        String sortField = searchQry.getSortField();
        String sortDirection = searchQry.getSortDirection();
        if (StringUtils.isNotBlank(sortField)) {
            if (StringUtils.isNotBlank(sortDirection)) {
                return Sort.by(Sort.Direction.DESC, sortField);
            }
            return Sort.by(Sort.Direction.valueOf(sortDirection.toUpperCase()), sortField);
        }
        return Sort.by(Sort.Direction.DESC, "updateTime");
    }

    private PageRequest buildPageable(SearchQry searchQry) {
        return PageRequest.of(searchQry.getCurrent() - 1, searchQry.getSize());
    }

    private HighlightQuery buildHighlightQuery(SearchQry searchQry) {
        List<HighlightField> fields = new ArrayList<>();
        HighlightField field = new HighlightField("skuName");
        fields.add(field);
        Highlight highlight = new Highlight(HighlightParameters.builder()
                .withPostTags("</b>")
                .withPreTags("<b>")
                .build(), fields);
        return new HighlightQuery(highlight, null);
    }

    private co.elastic.clients.elasticsearch._types.query_dsl.Query buildQuery(SearchQry searchQry) {
        String brand = searchQry.getBrand();
        Long categoryId = searchQry.getCategory();
        String keyword = searchQry.getKeyword();
        String priceRange = searchQry.getPriceRange();
        String specs = searchQry.getAttrs();

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
