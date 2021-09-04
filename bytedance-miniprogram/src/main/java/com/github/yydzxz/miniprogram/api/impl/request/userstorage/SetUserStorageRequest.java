package com.github.yydzxz.miniprogram.api.impl.request.userstorage;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.yydzxz.common.http.IByteDanceRequest;
import com.github.yydzxz.miniprogram.api.impl.request.INeedAccessTokenRequest;
import com.google.gson.annotations.SerializedName;
import java.util.List;
import lombok.Data;

/**
 * @author yangyidian
 * @date 2021/09/05
 **/
@Data
public class SetUserStorageRequest implements IByteDanceRequest, INeedAccessTokenRequest {

    @JSONField(name = "access_token")
    @JsonAlias("access_token")
    @JsonProperty("access_token")
    @SerializedName("access_token")
    private String accessToken;

    private String openid;

    private String signature;

    @JSONField(name = "sig_method")
    @JsonAlias("sig_method")
    @JsonProperty("sig_method")
    @SerializedName("sig_method")
    private String sigMethod;

    @JSONField(name = "kv_list")
    @JsonAlias("kv_list")
    @JsonProperty("kv_list")
    @SerializedName("kv_list")
    private List<KV> kvList;

    @Data
    public static class KV{
        private String key;
        private String value;
    }


}
