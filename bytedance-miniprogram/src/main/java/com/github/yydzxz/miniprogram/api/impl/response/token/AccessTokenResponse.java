package com.github.yydzxz.miniprogram.api.impl.response.token;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.yydzxz.common.http.IByteDanceResponse;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * @author yangyidian
 * @date 2021/08/30
 **/
@Data
public class AccessTokenResponse implements IByteDanceResponse {

    @JSONField(name = "access_token")
    @JsonAlias("access_token")
    @JsonProperty("access_token")
    @SerializedName("access_token")
    private String accessToken;

    @JSONField(name = "expires_in")
    @JsonAlias("expires_in")
    @JsonProperty("expires_in")
    @SerializedName("expires_in")
    private Integer expiresIn;
}
