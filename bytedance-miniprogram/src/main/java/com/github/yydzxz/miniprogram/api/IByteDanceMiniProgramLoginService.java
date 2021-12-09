package com.github.yydzxz.miniprogram.api;

import com.github.yydzxz.miniprogram.api.impl.request.login.Code2sessionRequest;
import com.github.yydzxz.miniprogram.api.impl.response.login.Code2sessionResponse;

/**
 * 登录service
 * @author yangyidian
 * @date 2021/08/30
 **/
public interface IByteDanceMiniProgramLoginService {

    String CODE_2_SESSION_URL = "https://developer.toutiao.com/api/apps/jscode2session";

    Code2sessionResponse code2session(Code2sessionRequest request);

    Code2sessionResponse code2sessionWithCode(String code, String anonymousCode);

}
