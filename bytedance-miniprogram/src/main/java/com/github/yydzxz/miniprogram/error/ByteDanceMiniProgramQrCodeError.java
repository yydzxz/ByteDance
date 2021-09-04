package com.github.yydzxz.miniprogram.error;

import com.github.yydzxz.common.error.IByteDanceError;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * @author yangyidian
 * @date 2021/09/04
 **/
public class ByteDanceMiniProgramQrCodeError implements IByteDanceError {

    private Integer errcode;

    private String errmsg;

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
        return Objects.equals(this.errcode, 0);
    }

    @Override
    public boolean accessTokenError() {
        return Objects.equals(this.errcode, 40002);
    }
}
