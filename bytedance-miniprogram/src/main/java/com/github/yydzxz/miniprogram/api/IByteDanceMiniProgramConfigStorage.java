package com.github.yydzxz.miniprogram.api;

import com.github.yydzxz.common.redis.IByteDanceRedisOps;
import com.github.yydzxz.miniprogram.api.impl.AbstractByteDanceMiniProgramInRedisConfigStorage.MiniProgramInfo;
import com.github.yydzxz.miniprogram.api.impl.response.token.AccessTokenResponse;
import java.util.Map;
import java.util.concurrent.locks.Lock;

/**
 * @author yangyidian
 * @date 2020/06/23
 **/
public interface IByteDanceMiniProgramConfigStorage {

    IByteDanceRedisOps getByteDanceRedisOps();

    String getGlobalKeyPrefix();

    void setGlobalKeyPrefix(String globalKeyPrefix);

    String getAccessToken(String appid);

    boolean isAccessTokenExpired(String appid);

    void expireAccessToken(String appid);

    void updateAccessToken(String appid, AccessTokenResponse accessToken);

    Lock getLockByKey(String key);

    /**
     * 应该是线程安全的
     *
     * @param accessToken 新的accessToken值
     * @param expiresInSeconds     过期时间，以秒为单位
     */
    void updateAccessToken(String appid, String accessToken, int expiresInSeconds);

    /**
     * 是否自动刷新token
     */
    boolean autoRefreshToken();

    Lock getAccessTokenLock(String appId);

    Map<String, MiniProgramInfo> getMiniProgramInfos();

    void setMiniProgramInfos(Map<String, MiniProgramInfo> miniProgramInfos);

    void addMiniProgramInfo(String appid, String secret);
}
