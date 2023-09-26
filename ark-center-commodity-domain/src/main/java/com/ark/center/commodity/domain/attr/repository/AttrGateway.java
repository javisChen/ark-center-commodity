package com.ark.center.commodity.domain.attr.repository;

import com.ark.center.commodity.client.attr.dto.AttrDTO;
import com.ark.center.commodity.client.attr.query.AttrPageQry;
import com.ark.center.commodity.domain.attr.Attr;
import com.ark.center.commodity.domain.attr.AttrOption;
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

    List<AttrOption> selectOptionsByAttrId(Long attrId);
}
