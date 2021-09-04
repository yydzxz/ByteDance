package com.github.yydzxz.miniprogram.api.impl;

import cn.hutool.core.util.StrUtil;
import com.github.yydzxz.common.util.UrlUtil;
import com.github.yydzxz.miniprogram.api.IByteDanceMiniProgramLoginService;
import com.github.yydzxz.miniprogram.api.IByteDanceMiniProgramService;
import com.github.yydzxz.miniprogram.api.impl.request.login.Code2sessionRequest;
import com.github.yydzxz.miniprogram.api.impl.response.login.Code2sessionResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * @author yangyidian
 * @date 2021/09/02
 **/
@Slf4j
public class ByteDanceMiniProgramLoginServiceImpl implements IByteDanceMiniProgramLoginService {

    private IByteDanceMiniProgramService byteDanceMiniProgramService;

    public ByteDanceMiniProgramLoginServiceImpl(IByteDanceMiniProgramService byteDanceMiniProgramService) {
        this.byteDanceMiniProgramService = byteDanceMiniProgramService;
    }

    @Override
    public Code2sessionResponse code2session(Code2sessionRequest request) {
        String url = UrlUtil.buildUrl(CODE_2_SESSION_URL, request);
        return byteDanceMiniProgramService.get(url, Code2sessionResponse.class);
    }
}
