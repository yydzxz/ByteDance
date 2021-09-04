package com.github.yydzxz.miniprogram.api;

import com.github.yydzxz.miniprogram.api.impl.request.token.AccessTokenRequest;
import com.github.yydzxz.miniprogram.api.impl.response.token.AccessTokenResponse;

/**
 * 接口调用凭证service
 * @author yangyidian
 * @date 2021/08/30
 **/
public interface IByteDanceMiniProgramAccessTokenService {


    String TOKEN_URL = "https://developer.toutiao.com/api/apps/token";

    AccessTokenResponse getAccessToken(AccessTokenRequest request);
}
