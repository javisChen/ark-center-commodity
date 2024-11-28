package com.ark.center.product.infra.product.db;

import com.ark.center.product.client.goods.dto.GoodsAttrDTO;
import com.ark.center.product.infra.spu.SpuAttr;
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

    List<GoodsAttrDTO> selectSpuSpecs(@Param("spuIds") List<Long> spuIds);
}
