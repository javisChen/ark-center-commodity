package com.ark.center.product.app.attr;

import com.ark.center.product.client.attr.dto.AttrDTO;
import com.ark.center.product.client.attr.query.AttrPageQry;
import com.ark.center.product.infra.attr.gateway.impl.AttrService;
import com.ark.component.dto.PageResponse;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AttrQueryService {

    private final AttrService attrService;
    
    public PageResponse<AttrDTO> queryAttrTemplatePages(AttrPageQry queryDTO) {
        IPage<AttrDTO> pageList = attrService.selectPages(queryDTO);
        return PageResponse.of(pageList);
    }
    
    public AttrDTO queryAttrDetails(Long id) {
        return attrService.selectById(id);
    }
} 