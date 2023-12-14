package com.ark.center.product.domain.attr.repository;

import com.ark.center.product.client.attr.dto.AttrDTO;
import com.ark.center.product.client.attr.dto.AttrOptionDTO;
import com.ark.center.product.client.attr.query.AttrPageQry;
import com.ark.center.product.domain.attr.Attr;
import com.ark.center.product.domain.attr.AttrOption;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

public interface AttrGateway {

    IPage<AttrDTO> selectPages(AttrPageQry queryDTO);

    List<AttrDTO> selectByGroupIds(List<Long> groupIds);

    AttrDTO selectById(Long attrId);

    void insert(Attr attr);

    boolean update(Attr attr);

    void deleteOptions(Long attrId);

    void saveOptions(List<AttrOption> options);

    boolean remove(Long id);

    List<AttrOption> selectOptions(Long attrId);

    List<AttrOptionDTO> selectOptions(List<Long> attrIds);
}
