package com.github.yydzxz.miniprogram.api.impl;

import com.github.yydzxz.miniprogram.api.IByteDanceMiniProgramConfigStorage;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import lombok.Data;

/**
 * @author yangyidian
 * @date 2020/06/23
 **/
@Data
public abstract class AbstractByteDanceMiniProgramInRedisConfigStorage implements IByteDanceMiniProgramConfigStorage {

    protected final static String ACCESS_TOKEN_KEY = "bytedance_access_token:";

    protected final static String LOCK_KEY = "bytedance_lock:";

    /**
     * TODO
     * redis 存储的 key 的前缀，可为空
     */
    protected String globalKeyPrefix;

    private Map<String, Lock> locks = new ConcurrentHashMap<>();
    private Map<String, MiniProgramInfo> miniProgramInfos = new ConcurrentHashMap<>();



    //    @Data
//    private static class Token {
//        private String token;
//        private Long expiresTime;
//    }

    @Data
    public static class MiniProgramInfo{
        private String appId;
        private String appSecret;

        public MiniProgramInfo() {
        }

        public MiniProgramInfo(String appId, String appSecret) {
            this.appId = appId;
            this.appSecret = appSecret;
        }
    }

    @Override
    public void addMiniProgramInfo(String appid, String secret){
        if(miniProgramInfos.get(appid) == null){
            MiniProgramInfo miniProgramInfo = new MiniProgramInfo(appid, secret);
            miniProgramInfos.put(appid, miniProgramInfo);
        }else {
            miniProgramInfos.get(appid).setAppSecret(secret);
        }
    }

    protected String getKey(String prefix, String appId) {
        return prefix.endsWith(":") ? prefix.concat(appId) : prefix.concat(":").concat(appId);
    }
}
