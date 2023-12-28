package com.ark.center.product.domain.sku.typehandler;

import com.alibaba.fastjson2.JSON;
import com.ark.center.product.domain.spu.SpuAttr;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SpuAttrTypeHandler extends BaseTypeHandler<List<SpuAttr>> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, List<SpuAttr> parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, JSON.toJSONString(parameter));
    }

    @Override
    public List<SpuAttr> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String object = rs.getObject(columnName).toString();
        return JSON.parseArray(object, SpuAttr.class);
    }

    @Override
    public List<SpuAttr> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String object = rs.getObject(columnIndex).toString();
        return JSON.parseArray(object, SpuAttr.class);
    }

    @Override
    public List<SpuAttr> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String object = cs.getObject(columnIndex).toString();
        return JSON.parseArray(object, SpuAttr.class);
    }
}
