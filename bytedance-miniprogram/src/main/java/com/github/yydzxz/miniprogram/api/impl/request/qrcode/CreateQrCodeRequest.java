package com.github.yydzxz.miniprogram.api.impl.request.qrcode;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.yydzxz.common.http.IByteDanceRequest;
import com.github.yydzxz.miniprogram.api.impl.request.INeedAccessTokenRequest;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * @author Gadfly
 * @since 2022-01-05 12:16
 */
@Data
public class CreateQrCodeRequest implements IByteDanceRequest, INeedAccessTokenRequest {
    @JSONField(name = "access_token")
    @JsonAlias("access_token")
    @JsonProperty("access_token")
    @SerializedName("access_token")
    private String accessToken;

    /**
     * 是打开二维码的字节系 app 名称，默认为今日头条
     * <pre>
     * | appname      | 对应字节系 app |
     * |--------------|----------------|
     * | toutiao      | 今日头条       |
     * | toutiao_lite | 今日头条极速版 |
     * | douyin       | 抖音           |
     * | douyin_lite  | 抖音极速版     |
     * | pipixia      | 皮皮虾         |
     * | huoshan      | 火山小视频     |
     * | xigua        | 西瓜视频       |
     * </pre>
     */
    private String appname;

    /**
     * 小程序/小游戏启动参数，小程序则格式为 encode({path}?{query})，小游戏则格式为 JSON 字符串，默认为空
     */
    private String path;

    /**
     * 二维码宽度，单位 px，最小 280px，最大 1280px，默认为 430px
     */
    private String width;

    /**
     * 二维码线条颜色，默认为黑色
     */
    @JSONField(name = "line_color")
    @JsonAlias("line_color")
    @JsonProperty("line_color")
    @SerializedName("line_color")
    private Rgb lineColor;

    /**
     * 二维码背景颜色，默认为白色
     */
    private Rgb background;

    /**
     * 是否展示小程序/小游戏 icon，默认不展示
     */
    @JSONField(name = "set_icon")
    @JsonAlias("set_icon")
    @JsonProperty("set_icon")
    @SerializedName("set_icon")
    private Boolean setIcon;

    @Data
    public static class Rgb {
        @JSONField(name = "r")
        @JsonAlias("r")
        @JsonProperty("r")
        @SerializedName("r")
        private Integer r;

        @JSONField(name = "g")
        @JsonAlias("g")
        @JsonProperty("g")
        @SerializedName("g")
        private Integer g;

        @JSONField(name = "b")
        @JsonAlias("b")
        @JsonProperty("b")
        @SerializedName("b")
        private Integer b;
    }
}
