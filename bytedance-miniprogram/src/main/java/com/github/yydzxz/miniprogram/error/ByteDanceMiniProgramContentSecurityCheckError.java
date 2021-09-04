package com.github.yydzxz.miniprogram.error;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.yydzxz.common.error.IByteDanceError;
import com.google.gson.annotations.SerializedName;
import java.util.Objects;

/**
 * 内容安全检测错误
 * @author yangyidian
 * @date 2021/09/04
 **/
public class ByteDanceMiniProgramContentSecurityCheckError implements IByteDanceError {

    @JSONField(name = "error_id")
    @JsonAlias("error_id")
    @JsonProperty("error_id")
    @SerializedName("error_id")
    private String errorId;

    private Integer code;

    private String message;

    private String exception;

    @Override
    public Integer errorCode() {
        return code;
    }

    @Override
    public String errorMessage() {
        return message;
    }

    @Override
    public Boolean checkSuccess() {
        return Objects.equals(this.code, 0);
    }

    @Override
    public boolean accessTokenError() {
        return Objects.equals(this.code, 401);
    }
}
