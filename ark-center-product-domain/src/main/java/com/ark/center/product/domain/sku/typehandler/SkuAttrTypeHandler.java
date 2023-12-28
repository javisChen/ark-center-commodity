package com.ark.center.product.domain.sku.typehandler;

import com.alibaba.fastjson2.JSON;
import com.ark.center.product.domain.sku.SkuAttr;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SkuAttrTypeHandler extends BaseTypeHandler<List<SkuAttr>> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, List<SkuAttr> parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, JSON.toJSONString(parameter));
    }

    @Override
    public List<SkuAttr> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String object = rs.getObject(columnName).toString();
        return JSON.parseArray(object, SkuAttr.class);
    }

    @Override
    public List<SkuAttr> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String object = rs.getObject(columnIndex).toString();
        return JSON.parseArray(object, SkuAttr.class);
    }

    @Override
    public List<SkuAttr> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String object = cs.getObject(columnIndex).toString();
        return JSON.parseArray(object, SkuAttr.class);
    }
}
