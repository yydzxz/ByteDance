package com.github.yydzxz.miniprogram.api.impl;

import com.github.yydzxz.miniprogram.api.IByteDanceMiniProgramKefuMessageService;
import com.github.yydzxz.miniprogram.api.IByteDanceMiniProgramService;
import com.github.yydzxz.miniprogram.api.impl.request.kefumessage.SendRequest;
import com.github.yydzxz.miniprogram.api.impl.response.kefumessage.SendResponse;

public class ByteDanceMiniProgramKefuMessageServiceImpl implements IByteDanceMiniProgramKefuMessageService {

    private final IByteDanceMiniProgramService byteDanceMiniProgramService;

    public ByteDanceMiniProgramKefuMessageServiceImpl(IByteDanceMiniProgramService byteDanceMiniProgramService) {
        this.byteDanceMiniProgramService = byteDanceMiniProgramService;
    }

    @Override
    public SendResponse send(SendRequest request) {
        return byteDanceMiniProgramService.post(SEND_URL, request, SendResponse.class);
    }
}
