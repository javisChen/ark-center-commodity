package com.ark.center.commodity.infra.commodity.gateway.db;

import com.ark.center.commodity.client.commodity.dto.AttrDTO;
import com.ark.center.commodity.domain.spu.SpuAttr;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * spu参数属性 Mapper 接口
 * </p>
 *
 * @author EOP
 * @since 2022-04-22
 */
public interface SpuAttrMapper extends BaseMapper<SpuAttr> {

    List<AttrDTO> selectSpuSpecs(@Param("spuId") Long spuId);
}
