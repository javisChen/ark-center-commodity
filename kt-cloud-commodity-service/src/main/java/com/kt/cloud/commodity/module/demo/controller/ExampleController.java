package com.kt.cloud.commodity.module.demo.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.kt.component.dto.MultiResponse;
import com.kt.component.dto.PageResponse;
import com.kt.component.dto.ServerResponse;
import com.kt.component.dto.SingleResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/example")
@RestController
public class ExampleController {

    /**
     * http://localhost:8080/example/ok
     */
    @GetMapping("/ok")
    public ServerResponse ok() {
        return ServerResponse.ok();
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
