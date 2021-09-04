package com.github.yydzxz.miniprogram.api.impl;

import com.github.yydzxz.common.redis.IByteDanceRedisOps;
import com.github.yydzxz.miniprogram.api.impl.response.token.AccessTokenResponse;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 * @author yangyidian
 * @date 2020/06/24
 **/
@Slf4j
public class ByteDanceMiniProgramInRedisConfigStorage extends AbstractByteDanceMiniProgramInRedisConfigStorage {

    private final IByteDanceRedisOps redisOps;

    public ByteDanceMiniProgramInRedisConfigStorage(@NonNull IByteDanceRedisOps redisOps, String keyPrefix) {
        this.redisOps = redisOps;
        this.globalKeyPrefix = keyPrefix;
    }

    @Override
    public boolean autoRefreshToken() {
        return false;
    }

    @Override
    public IByteDanceRedisOps getByteDanceRedisOps() {
        return redisOps;
    }

    @Override
    public String getAccessToken(String appid) {
        return redisOps.getValue(getKey(ACCESS_TOKEN_KEY, appid));
    }

    @Override
    public boolean isAccessTokenExpired(String appid) {
        Long expire = redisOps.getExpire(getKey(ACCESS_TOKEN_KEY, appid));
        return expire == null || expire < 2;
    }

    @Override
    public void expireAccessToken(String appid) {
        redisOps.expire(getKey(ACCESS_TOKEN_KEY, appid), 0, TimeUnit.SECONDS);
    }

    @Override
    public void updateAccessToken(String appid, AccessTokenResponse accessToken) {
        redisOps.setValue(getKey(ACCESS_TOKEN_KEY, appid), accessToken.getAccessToken(), accessToken.getExpiresIn(), TimeUnit.SECONDS);
    }

    @Override
    public Lock getLockByKey(String key) {
        return redisOps.getLock(key);
    }

    @Override
    public void updateAccessToken(String appid, String accessToken, int expiresInSeconds) {
        redisOps.setValue(getKey(ACCESS_TOKEN_KEY, appid), accessToken, expiresInSeconds - 200, TimeUnit.SECONDS);
    }

    @Override
    public Lock getAccessTokenLock(String appId) {
        return getLockByKey(LOCK_KEY.concat(":").concat("accessToken").concat(":").concat(appId));
    }
}
