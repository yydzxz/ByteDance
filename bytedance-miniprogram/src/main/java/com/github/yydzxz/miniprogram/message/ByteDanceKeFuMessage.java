package com.github.yydzxz.miniprogram.message;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.yydzxz.common.util.json.ByteDanceJsonBuilder;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class ByteDanceKeFuMessage {

    /**
     * 消息信息
     * json格式的字符串，可以调用 <code>parseMsg()</code>获取该json解析后的对象
     */
    private String msg;
    @JSONField(name = "msg_signature")
    @JsonAlias("msg_signature")
    @JsonProperty("msg_signature")
    @SerializedName("msg_signature")
    private String msgSignature;

    private String nonce;

    private String timestamp;

    public MsgContent parseMsg(){
        return ByteDanceJsonBuilder.instance().parse(msg, MsgContent.class);
    }

    @Data
    public static class MsgContent{
        @JSONField(name = "ToUserName")
        @JsonAlias("ToUserName")
        @JsonProperty("ToUserName")
        @SerializedName("ToUserName")
        private String toUserName;

        @JSONField(name = "FromUserName")
        @JsonAlias("FromUserName")
        @JsonProperty("FromUserName")
        @SerializedName("FromUserName")
        private String fromUserName;

        @JSONField(name = "CreateTime")
        @JsonAlias("CreateTime")
        @JsonProperty("CreateTime")
        @SerializedName("CreateTime")
        private Long CreateTime;

        @JSONField(name = "MsgType")
        @JsonAlias("MsgType")
        @JsonProperty("MsgType")
        @SerializedName("MsgType")
        private String msgType;

        @JSONField(name = "PicUrl")
        @JsonAlias("PicUrl")
        @JsonProperty("PicUrl")
        @SerializedName("PicUrl")
        private String picUrl;

        @JSONField(name = "Content")
        @JsonAlias("Content")
        @JsonProperty("Content")
        @SerializedName("Content")
        private String content;
    }


}
