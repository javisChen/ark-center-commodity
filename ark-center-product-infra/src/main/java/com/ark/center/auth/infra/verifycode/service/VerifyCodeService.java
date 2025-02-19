package com.ark.center.auth.infra.verifycode.service;

import com.ark.center.auth.client.verifycode.command.VerifyCodeCommand;
import com.ark.center.auth.client.verifycode.common.VerifyCodeScene;
import com.ark.center.auth.client.verifycode.common.VerifyCodeType;
import com.ark.center.auth.infra.verifycode.VerifyCodeFacade;
import com.ark.component.microservice.rpc.util.RpcUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerifyCodeService {

    private final VerifyCodeFacade verifyCodeFacade;

    public boolean verify(String mobile, String code, String verifyCodeId, VerifyCodeScene scene) {
        VerifyCodeCommand command = new VerifyCodeCommand();
        command.setType(VerifyCodeType.SMS);
        command.setTarget(mobile);
        command.setCode(code);
        command.setVerifyCodeId(verifyCodeId);
        command.setScene(scene);
        return RpcUtils.checkAndGetData(verifyCodeFacade.verify(command));
    }
}
