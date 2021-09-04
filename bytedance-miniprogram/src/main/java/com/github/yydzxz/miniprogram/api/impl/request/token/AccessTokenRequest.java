package com.github.yydzxz.miniprogram.api.impl.request.token;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * @author yangyidian
 * @date 2021/08/30
 **/
@Data
public class AccessTokenRequest {

    private String appid;

    private String secret;

    @JSONField(name = "grant_type")
    @JsonAlias("grant_type")
    @JsonProperty("grant_type")
    @SerializedName("grant_type")
    private String grantType;

    public AccessTokenRequest(String appid, String secret) {
        this.appid = appid;
        this.secret = secret;
    }
}
