package com.kt.cloud.commodity.module.brand;

import cn.hutool.core.collection.CollectionUtil;
import com.kt.cloud.commodity.module.brand.dto.request.BrandCreateReqDTO;
import com.kt.component.dto.MultiResponse;
import com.kt.component.dto.PageResponse;
import com.kt.component.dto.ServerResponse;
import com.kt.component.dto.SingleResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/v1/brand")
@RestController
public class BrandController {

    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    /**
     * http://localhost:8080/example/ok
     */
    @GetMapping("/create")
    public SingleResponse<Long> create(@RequestBody @Validated BrandCreateReqDTO reqDTO) {
        return SingleResponse.ok(brandService.create(reqDTO));
    }

    /**
     * http://localhost:8080/example/obj
     */
    @GetMapping("/obj")
    public ServerResponse obj() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "hello");
        map.put("age", 18);
        return SingleResponse.ok(map);
    }

    /**
     * http://localhost:8080/example/list
     */
    @GetMapping("/list")
    public ServerResponse list() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "hello");
        map.put("age", 18);
        return MultiResponse.ok(CollectionUtil.newArrayList(map));
    }

    /**
     * http://localhost:8080/example/pageList
     */
    @GetMapping("/pageList")
    public PageResponse<Map<String, Object>> pageList() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "hello");
        map.put("age", 18);
        return PageResponse.build(1, 10 , 10, CollectionUtil.newArrayList(map));
    }
}
