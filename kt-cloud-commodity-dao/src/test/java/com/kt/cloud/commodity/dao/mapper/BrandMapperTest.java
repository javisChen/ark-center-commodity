package com.kt.cloud.commodity.dao.mapper;

import com.kt.cloud.commodity.BaseTests;
import com.kt.cloud.commodity.dao.entity.Brand;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BrandMapperTest extends BaseTests  {

    @Autowired
    private BrandMapper brandMapper;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void test() {
        List<Brand> brands = brandMapper.selectList(null);
        System.out.println(brands);
    }
}