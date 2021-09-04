package com.github.yydzxz.miniprogram.error;

import com.github.yydzxz.common.error.IByteDanceError;
import java.util.Objects;

/**
 * 数据缓存错误
 * @author yangyidian
 * @date 2021/09/04
 **/
public class ByteDanceMiniProgramUserStorageError implements IByteDanceError {

    private Integer errcode;

    private String errmsg;

    private Integer error;

    private String message;

    @Override
    public Integer errorCode() {
        return errcode;
    }

    @Override
    public String errorMessage() {
        return errmsg;
    }

    @Override
    public Boolean checkSuccess() {
        return Objects.equals(this.error, 0);
    }

    @Override
    public boolean accessTokenError() {
        return Objects.equals(errcode, 40002);
    }
}
