package com.github.yydzxz.miniprogram.error;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.yydzxz.common.error.IByteDanceError;
import com.google.gson.annotations.SerializedName;
import java.util.Objects;

/**
 * @author yangyidian
 * @date 2021/09/02
 **/
public class ByteDanceMiniProgramCommonError implements IByteDanceError {

    @JSONField(name = "err_no")
    @JsonAlias("err_no")
    @JsonProperty("err_no")
    @SerializedName("err_no")
    private Integer errNo;

    @JSONField(name = "err_tips")
    @JsonAlias("err_tips")
    @JsonProperty("err_tips")
    @SerializedName("err_tips")
    private String errTips;

    @Override
    public Integer errorCode() {
        return errNo;
    }

    @Override
    public String errorMessage() {
        return errTips;
    }

    @Override
    public Boolean checkSuccess() {
        return Objects.equals(this.errNo, 0);
    }

    @Override
    public boolean accessTokenError() {
        return ByteDanceMiniProgramErrorMsgEnum.CODE_10011.getCode() == errorCode()
            || ByteDanceMiniProgramErrorMsgEnum.CODE_11016.getCode() == errorCode();
    }
}
