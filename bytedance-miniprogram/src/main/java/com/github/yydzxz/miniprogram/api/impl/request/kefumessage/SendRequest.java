package com.github.yydzxz.miniprogram.api.impl.request.kefumessage;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.yydzxz.common.http.IByteDanceRequest;
import com.github.yydzxz.miniprogram.api.impl.request.INeedAccessTokenRequest;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class SendRequest implements IByteDanceRequest, INeedAccessTokenRequest {
    @JSONField(name = "access_token")
    @JsonAlias("access_token")
    @JsonProperty("access_token")
    @SerializedName("access_token")
    private String accessToken;

    @JSONField(name = "open_id")
    @JsonAlias("open_id")
    @JsonProperty("open_id")
    @SerializedName("open_id")
    private String open_id;

    @JSONField(name = "msg_type")
    @JsonAlias("msg_type")
    @JsonProperty("msg_type")
    @SerializedName("msg_type")
    private String msg_type;

    private String content;

    @JSONField(name = "pic_url")
    @JsonAlias("pic_url")
    @JsonProperty("pic_url")
    @SerializedName("pic_url")
    private String pic_url;
}
