package com.github.yydzxz.miniprogram.api.impl.response.login;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.yydzxz.common.http.IByteDanceResponse;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * @author yangyidian
 * @date 2021/09/05
 **/
@Data
public class Code2sessionResponse implements IByteDanceResponse {

    @JSONField(name = "session_key")
    @JsonAlias("session_key")
    @JsonProperty("session_key")
    @SerializedName("session_key")
    private String sessionKey;

    private String openid;

    @JSONField(name = "anonymous_openid")
    @JsonAlias("anonymous_openid")
    @JsonProperty("anonymous_openid")
    @SerializedName("anonymous_openid")
    private String anonymousOpenid;

    private String unionid;
}
