package com.github.yydzxz.miniprogram.error;

import com.github.yydzxz.common.error.ByteDanceErrorException;
import com.github.yydzxz.common.error.IByteDanceError;

/**
 * @author yangyidian
 * @date 2020/07/29
 **/
public class ByteDanceMiniProgramException extends ByteDanceErrorException {

    private String appid;

    public ByteDanceMiniProgramException(String appid, IByteDanceError error) {
        super(error);
        this.appid = appid;
    }

    public ByteDanceMiniProgramException(String appid, IByteDanceError error, Throwable cause) {
        super(error, cause);
        this.appid = appid;
    }

    public String getAppid() {
        return appid;
    }
}
