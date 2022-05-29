package com.github.yydzxz.miniprogram.api.impl.response.kefumessage;

import com.github.yydzxz.common.http.IByteDanceResponse;
import lombok.Data;

@Data
public class SendResponse implements IByteDanceResponse {
    private Integer errno;
    private String msg;
}
