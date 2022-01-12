package com.github.yydzxz.miniprogram.api;

import com.github.yydzxz.miniprogram.api.impl.request.qrcode.CreateQrCodeRequest;

import java.io.File;
import java.io.IOException;

/**
 * 获取二维码api
 *
 * @author Gadfly
 * @since 2022-01-05 11:37
 */
public interface IByteDanceMiniProgramQrCodeService {
    String CREATE_QR_CODE = "https://developer.toutiao.com/api/apps/qrcode";

    /**
     * 获取小程序页面二维码.
     *
     * @return 文件内容字节数组
     */
    byte[] createQrcodeBytes(CreateQrCodeRequest request);

    /**
     * 获取小程序页面二维码.
     *
     * @return 文件对象
     */
    File createQrcode(CreateQrCodeRequest request, String filePath) throws IOException;
}
