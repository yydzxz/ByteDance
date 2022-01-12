package com.github.yydzxz.miniprogram.api.impl;

import cn.hutool.core.util.StrUtil;
import com.github.yydzxz.common.error.ByteDanceErrorException;
import com.github.yydzxz.common.error.IByteDanceError;
import com.github.yydzxz.common.http.IByteDanceHttpClient;
import com.github.yydzxz.common.http.IExecutable;
import com.github.yydzxz.common.http.IRetryableExecutor;
import com.github.yydzxz.common.util.ByteDanceAppIdHolder;
import com.github.yydzxz.miniprogram.api.IByteDanceMiniProgramAccessTokenService;
import com.github.yydzxz.miniprogram.api.IByteDanceMiniProgramConfigStorage;
import com.github.yydzxz.miniprogram.api.IByteDanceMiniProgramLoginService;
import com.github.yydzxz.miniprogram.api.IByteDanceMiniProgramQrCodeService;
import com.github.yydzxz.miniprogram.api.IByteDanceMiniProgramService;
import com.github.yydzxz.miniprogram.api.impl.request.INeedAccessTokenRequest;
import com.github.yydzxz.miniprogram.api.impl.request.token.AccessTokenRequest;
import com.github.yydzxz.miniprogram.api.impl.response.token.AccessTokenResponse;
import com.github.yydzxz.miniprogram.error.ByteDanceMiniProgramCommonError;
import com.github.yydzxz.miniprogram.error.ByteDanceMiniProgramContentSecurityCheckError;
import com.github.yydzxz.miniprogram.error.ByteDanceMiniProgramErrorMsgEnum;
import com.github.yydzxz.miniprogram.error.ByteDanceMiniProgramException;
import com.github.yydzxz.miniprogram.error.ByteDanceMiniProgramImageCheckError;
import com.github.yydzxz.miniprogram.error.ByteDanceMiniProgramQrCodeError;
import com.github.yydzxz.miniprogram.error.ByteDanceMiniProgramUserStorageError;
import com.google.common.collect.Multimap;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.locks.Lock;

import static com.github.yydzxz.miniprogram.api.impl.AbstractByteDanceMiniProgramInRedisConfigStorage.MiniProgramInfo;

/**
 * @author yangyidian
 * @date 2021/08/31
 **/
@Slf4j
public class ByteDanceMiniProgramServiceImpl implements IByteDanceMiniProgramService, IRetryableExecutor {

    @Getter
    @Setter
    private long retrySleepMillis = 1000;

    @Getter
    @Setter
    private int maxRetryTimes = 5;

    @Getter
    @Setter
    private IByteDanceHttpClient byteDanceHttpClient;

    private IByteDanceMiniProgramConfigStorage byteDanceMiniProgramConfigStorage;

    private IByteDanceMiniProgramAccessTokenService byteDanceMiniProgramTokenService;

    private IByteDanceMiniProgramLoginService byteDanceMiniProgramLoginService;

    private IByteDanceMiniProgramQrCodeService byteDanceMiniProgramQrCodeService;

    public ByteDanceMiniProgramServiceImpl(IByteDanceHttpClient byteDanceHttpClient, IByteDanceMiniProgramConfigStorage byteDanceMiniProgramConfigStorage) {
        this.byteDanceHttpClient = byteDanceHttpClient;
        this.byteDanceMiniProgramConfigStorage = byteDanceMiniProgramConfigStorage;
        this.byteDanceMiniProgramTokenService = new ByteDanceMiniProgramAccessTokenServiceImpl(this);
        this.byteDanceMiniProgramLoginService = new ByteDanceMiniProgramLoginServiceImpl(this);
        this.byteDanceMiniProgramQrCodeService = new ByteDanceMiniProgramQrCodeServiceImpl(this);
    }

    @Override
    public IByteDanceMiniProgramConfigStorage getByteDanceMiniProgramConfigStorage() {
        return byteDanceMiniProgramConfigStorage;
    }

    @Override
    public IByteDanceMiniProgramAccessTokenService getByteDanceMiniProgramTokenService() {
        return byteDanceMiniProgramTokenService;
    }

    @Override
    public IByteDanceMiniProgramLoginService getByteDanceMiniProgramLoginService() {
        return byteDanceMiniProgramLoginService;
    }

    @Override
    public IByteDanceMiniProgramQrCodeService getByteDanceMiniProgramQrCodeService() {
        return byteDanceMiniProgramQrCodeService;
    }

    @Override
    public boolean switchover(String appid) {
        Map<String, MiniProgramInfo> configs = byteDanceMiniProgramConfigStorage.getMiniProgramInfos();
        if (configs.containsKey(appid)) {
            ByteDanceAppIdHolder.set(appid);
            return true;
        }
        return false;
    }

    @Override
    public MiniProgramInfo getMiniProgramConfig() {
        Map<String, MiniProgramInfo> configs = byteDanceMiniProgramConfigStorage.getMiniProgramInfos();
        if (configs.isEmpty()) {
            throw new RuntimeException("请至少配置一个应用");
        }
        if (configs.size() == 1) {
            // 只有一个小程序配置，直接返回其配置即可
            return configs.values().iterator().next();
        }
        MiniProgramInfo programInfo = configs.get(ByteDanceAppIdHolder.get());
        if (programInfo == null) {
            throw new RuntimeException("应用不存在，请检查appid及配置");
        }
        return programInfo;
    }

    @Override
    public String getAccessToken(String appid) {
        return getAccessToken(appid, false);
    }

    @Override
    public String getAccessToken(String appId, boolean forceRefresh) {
        if (!byteDanceMiniProgramConfigStorage.isAccessTokenExpired(appId) && !forceRefresh) {
            return byteDanceMiniProgramConfigStorage.getAccessToken(appId);
        }
        Lock lock = byteDanceMiniProgramConfigStorage.getAccessTokenLock(appId);
        lock.lock();
        try {
            if (!byteDanceMiniProgramConfigStorage.isAccessTokenExpired(appId) && !forceRefresh) {
                return byteDanceMiniProgramConfigStorage.getAccessToken(appId);
            }
            String appSecret = byteDanceMiniProgramConfigStorage.getMiniProgramInfos().get(appId).getAppSecret();
//            String url = String.format("https://developer.toutiao.com/api/apps/token" + "?appid=%s&secret=%s&grant_type=client_credential", appId, appSecret);
//            AccessTokenResponse response = get(url, AccessTokenResponse.class);

            AccessTokenRequest request = new AccessTokenRequest(appId, appSecret);
            AccessTokenResponse response = byteDanceMiniProgramTokenService.getAccessToken(request);
            byteDanceMiniProgramConfigStorage.updateAccessToken(appId, response.getAccessToken(), response.getExpiresIn());
            return byteDanceMiniProgramConfigStorage.getAccessToken(appId);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(appId + "获取 AccessToken 失败");
        } finally {
            lock.unlock();
        }
    }

    @Override
    public String get(String url) {
        return get(url, String.class);
    }

    @Override
    public <T> T get(String url, Class<T> t) {
        return retryableExecuteRequest((url2, headers, request2, t2) -> {
            return getInternal(url2, t2);
        }, url, null, null, t);
    }

    private <T> T getInternal(String url, Class<T> t) {
        return executeRequest(
                (uriWithCommonParam, headers, request2, t2) -> {
                    return byteDanceHttpClient.get(uriWithCommonParam, t2);
                }, url, null, null, t
        );
    }

    @Override
    public <T> T post(String url, Object request, Class<T> t) {
        return retryableExecuteRequest((url2, headers, request2, t2) -> {
            return postInternal(url2, request2, t2);
        }, url, null, request, t);
    }

    private <T> T postInternal(String url, Object request, Class<T> t) {
        return executeRequest(
                (uriWithCommonParam, headers, request2, t2) -> {
                    return byteDanceHttpClient.post(uriWithCommonParam, request2, t2);
                }, url, null, request, t
        );
    }

    @Override
    public <T> T postWithHeaders(String url, Multimap<String, String> headers, Object request, Class<T> t) {
        return retryableExecuteRequest((url2, headers2, request2, t2) -> {
            return postWithHeadersInternal(url2, headers2, request2, t2);
        }, url, headers, request, t);
    }

    private <T> T postWithHeadersInternal(String url, Multimap<String, String> headers, Object request, Class<T> t) {
        return executeRequest(
                (uriWithCommonParam, headers2, request2, t2) -> {
                    return byteDanceHttpClient.postWithHeaders(uriWithCommonParam, headers2, request2, t2);
                }, url, headers, request, t
        );
    }

    private <T> T executeRequest(IExecutable<T> executable, String url, Multimap<String, String> headers, Object request, Class<T> t) {
        T response = null;
        if (!(request instanceof INeedAccessTokenRequest)) {
            try {
                return executable.execute(url, headers, request, t);
            } catch (Exception e) {
                log.error("\n【请求地址】: {}\n【异常信息】: {}", url, e.getMessage());
                throw new RuntimeException(e);
            }
        }

        MiniProgramInfo miniProgramInfo = this.getMiniProgramConfig();
        String appId = miniProgramInfo.getAppId();
        String accessToken = getAccessToken(appId);
        try {
            String uriWithCommonParam = url + (url.contains("?") ? "&" : "?") + "access_token=" + accessToken;
            response = executable.execute(uriWithCommonParam, headers, request, t);
        } catch (ByteDanceErrorException e) {
            if ((shouldExpireAccessToken(e.getError()))) {
                // 强制设置access token过期，这样在下一次请求里就会刷新access token
                Lock lock = getByteDanceMiniProgramConfigStorage().getAccessTokenLock(appId);
                lock.lock();
                try {
                    if (StrUtil.equals(getAccessToken(appId, false), accessToken)) {
                        getByteDanceMiniProgramConfigStorage().expireAccessToken(appId);
                    }
                } catch (Exception ex) {
                    getByteDanceMiniProgramConfigStorage().expireAccessToken(appId);
                } finally {
                    lock.unlock();
                }
            }
            if (!e.getError().checkSuccess()) {
                log.error("\n【请求地址】: {}\n【错误信息】: {}", url, e.getError());
                throw new ByteDanceMiniProgramException(appId, e.getError(), e);
            }
        } catch (Exception e) {
            log.error("\n【请求地址】: {}\n【异常信息】: {}", url, e.getMessage());
            throw new RuntimeException(e);
        }
        return response;
    }

    /**
     * 是否让本地存储的accessToken过期
     * 当字节跳动系统返回accessToken相关错误时，应该让本地存储的accessToken过期，不再可用
     *
     * @param error
     * @return
     */
    protected boolean shouldExpireAccessToken(IByteDanceError error) {
        return error.accessTokenError();
    }

    /**
     * 是否应该进行请求重试
     * 当字节跳动系统响应系统错误或者accessToken失效时，应该重试
     *
     * @param error
     * @return
     */
    @Override
    public boolean shouldRetry(IByteDanceError error) {
        if (error.accessTokenError()) {
            return true;
        }
        if (error instanceof ByteDanceMiniProgramCommonError) {
            return ByteDanceMiniProgramErrorMsgEnum.CODE_NEGATIVE_1.getCode() == error.errorCode();
        } else if (error instanceof ByteDanceMiniProgramContentSecurityCheckError) {
            return ByteDanceMiniProgramErrorMsgEnum.CODE_NEGATIVE_1.getCode() == error.errorCode();
        } else if (error instanceof ByteDanceMiniProgramUserStorageError) {
            return ByteDanceMiniProgramErrorMsgEnum.CODE_NEGATIVE_1.getCode() == error.errorCode();
        } else if (error instanceof ByteDanceMiniProgramImageCheckError) {
            return Objects.equals(error.errorCode(), 4);
        } else if (error instanceof ByteDanceMiniProgramQrCodeError) {
            return ByteDanceMiniProgramErrorMsgEnum.CODE_NEGATIVE_1.getCode() == error.errorCode();
        }
        return false;
    }

    @Override
    public Logger getLogger() {
        return log;
    }
}
