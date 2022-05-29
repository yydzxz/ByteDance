package com.github.yydzxz.miniprogram.api;

import com.github.yydzxz.miniprogram.api.impl.AbstractByteDanceMiniProgramInRedisConfigStorage;
import com.google.common.collect.Multimap;

/**
 * @author yangyidian
 * @date 2021/08/30
 **/
public interface IByteDanceMiniProgramService {

    IByteDanceMiniProgramConfigStorage getByteDanceMiniProgramConfigStorage();

    IByteDanceMiniProgramAccessTokenService getByteDanceMiniProgramTokenService();

    IByteDanceMiniProgramLoginService getByteDanceMiniProgramLoginService();

    IByteDanceMiniProgramQrCodeService getByteDanceMiniProgramQrCodeService();

    IByteDanceMiniProgramKefuMessageService getByteDanceMiniProgramKefuMessageService();

    boolean switchover(String appid);

    AbstractByteDanceMiniProgramInRedisConfigStorage.MiniProgramInfo getMiniProgramConfig();

    String getAccessToken(String appid);

    String getAccessToken(String appid, boolean forceRefresh);

    String get(String url);

    <T> T get(String url, Class<T> t);
    //TODO
    //<T> T get(String url, Object request, Class<T> t);
    <T> T post(String url, Object request, Class<T> t);

    <T> T postWithHeaders(String url, Multimap<String, String> headers, Object request, Class<T> t);

}
