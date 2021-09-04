package com.github.yydzxz.miniprogram.api.impl;

import cn.hutool.core.util.StrUtil;
import com.github.yydzxz.miniprogram.api.IByteDanceMiniProgramAccessTokenService;
import com.github.yydzxz.miniprogram.api.IByteDanceMiniProgramService;
import com.github.yydzxz.miniprogram.api.impl.request.token.AccessTokenRequest;
import com.github.yydzxz.miniprogram.api.impl.response.token.AccessTokenResponse;

/**
 * @author yangyidian
 * @date 2021/08/30
 **/
public class ByteDanceMiniProgramAccessTokenServiceImpl implements IByteDanceMiniProgramAccessTokenService {

    private IByteDanceMiniProgramService byteDanceMiniProgramService;

    public ByteDanceMiniProgramAccessTokenServiceImpl(IByteDanceMiniProgramService byteDanceMiniProgramService) {
        this.byteDanceMiniProgramService = byteDanceMiniProgramService;
    }

    @Override
    public AccessTokenResponse getAccessToken(AccessTokenRequest request) {
        if(StrUtil.isBlank(request.getGrantType())){
            request.setGrantType("client_credential");
        }
        String url = String.format(TOKEN_URL+"?appid=%s&secret=%s&grant_type=%s", request.getAppid(), request.getSecret(), request.getGrantType());
        return byteDanceMiniProgramService.get(url, AccessTokenResponse.class);
    }
}
