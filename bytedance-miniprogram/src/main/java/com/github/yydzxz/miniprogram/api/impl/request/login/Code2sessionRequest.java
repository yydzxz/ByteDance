package com.github.yydzxz.miniprogram.api.impl.request.login;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.yydzxz.common.http.IByteDanceRequest;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * @author yangyidian
 * @date 2021/09/05
 **/
@Data
public class Code2sessionRequest implements IByteDanceRequest {

    private String appid;

    private String secret;

    private String code;

    @JSONField(name = "anonymous_code")
    @JsonAlias("anonymous_code")
    @JsonProperty("anonymous_code")
    @SerializedName("anonymous_code")
    private String anonymousCode;
}
