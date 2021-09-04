package com.github.yydzxz.miniprogram.error;

import com.github.yydzxz.common.error.IByteDanceError;
import java.util.Objects;

/**
 * 图片检测错误
 * @author yangyidian
 * @date 2021/09/04
 **/
public class ByteDanceMiniProgramImageCheckError implements IByteDanceError {

    private Integer error;

    private String message;

    @Override
    public Integer errorCode() {
        return error;
    }

    @Override
    public String errorMessage() {
        return message;
    }

    @Override
    public Boolean checkSuccess() {
        return Objects.equals(this.error, 0);
    }

    @Override
    public boolean accessTokenError() {
        return Objects.equals(this.error, 2);
    }
}
