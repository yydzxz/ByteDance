package com.github.yydzxz.miniprogram.api;

import com.github.yydzxz.miniprogram.api.impl.request.kefumessage.SendRequest;
import com.github.yydzxz.miniprogram.api.impl.response.kefumessage.SendResponse;

public interface IByteDanceMiniProgramKefuMessageService {
    String SEND_URL = "https://developer.toutiao.com/api/apps/message/custom/send";

    SendResponse send(SendRequest request);
}
