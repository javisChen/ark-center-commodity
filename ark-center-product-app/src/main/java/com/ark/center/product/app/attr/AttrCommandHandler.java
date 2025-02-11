package com.ark.center.product.app.attr;

import com.ark.center.product.app.attr.executor.AttrCreateCmdExe;
import com.ark.center.product.client.attr.command.AttrCreateCmd;
import com.ark.center.product.infra.attr.gateway.impl.AttrService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AttrCommandHandler {
    
    private final AttrService attrService;
    private final AttrCreateCmdExe attrCreateCmdExe;
    
    public Long saveAttr(AttrCreateCmd cmd) {
        return attrCreateCmdExe.execute(cmd);
    }
    
    public void removeByAttrId(Long id) {
        attrService.removeById(id);
    }
}