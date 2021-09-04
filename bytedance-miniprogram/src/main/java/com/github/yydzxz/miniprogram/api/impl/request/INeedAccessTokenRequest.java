package com.github.yydzxz.miniprogram.api.impl.request;

/**
 * 需要accessToken的请求
 * @author yangyidian
 * @date 2021/09/05
 **/
public interface INeedAccessTokenRequest {

    String getAccessToken();

    void setAccessToken(String accessToken);
}
