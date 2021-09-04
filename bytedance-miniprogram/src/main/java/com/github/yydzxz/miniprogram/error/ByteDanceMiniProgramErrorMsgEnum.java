package com.github.yydzxz.miniprogram.error;

/**
 * @author yangyidian
 * @date 2021/09/04
 **/
public enum  ByteDanceMiniProgramErrorMsgEnum {

    CODE_NEGATIVE_1(-1, "系统错误"),
    CODE_10011(10011, "传入 accessToken 为空"),
    CODE_11016(11016, "accessToken 校验不通过");

    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    ByteDanceMiniProgramErrorMsgEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 通过错误代码查找其中文含义.
     */
    public static String findMsgByCode(int code) {
        for (ByteDanceMiniProgramErrorMsgEnum value : ByteDanceMiniProgramErrorMsgEnum.values()) {
            if (value.code == code) {
                return value.msg;
            }
        }
        return "";
    }
}
