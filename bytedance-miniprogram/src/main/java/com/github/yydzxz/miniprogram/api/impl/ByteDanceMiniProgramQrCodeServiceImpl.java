package com.github.yydzxz.miniprogram.api.impl;

import com.github.yydzxz.common.util.FileUtil;
import com.github.yydzxz.common.util.UrlUtil;
import com.github.yydzxz.miniprogram.api.IByteDanceMiniProgramQrCodeService;
import com.github.yydzxz.miniprogram.api.IByteDanceMiniProgramService;
import com.github.yydzxz.miniprogram.api.impl.request.qrcode.CreateQrCodeRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.util.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * @author Gadfly
 * @since 2022-01-05 14:20
 */
@RequiredArgsConstructor
public class ByteDanceMiniProgramQrCodeServiceImpl implements IByteDanceMiniProgramQrCodeService {

    private final IByteDanceMiniProgramService byteDanceMiniProgramService;

    /**
     * 获取小程序页面二维码.
     *
     * @return 文件内容字节数组
     */
    @SneakyThrows
    @Override
    public byte[] createQrcodeBytes(CreateQrCodeRequest request) {
        String url = UrlUtil.buildUrl(CREATE_QR_CODE, request);
        request.setAccessToken(
                byteDanceMiniProgramService.getAccessToken(
                        byteDanceMiniProgramService.getMiniProgramConfig().getAppId()));
        request.setPath(URLEncoder.encode(request.getPath(), StandardCharsets.UTF_8.name()));
        return byteDanceMiniProgramService.post(url, request, byte[].class);
    }

    /**
     * 获取小程序页面二维码.
     *
     * @param filePath 二维码生成的文件路径，例如: /var/temp
     * @return 文件对象
     */
    @Override
    public File createQrcode(CreateQrCodeRequest request, String filePath) throws IOException {
        byte[] bytes = this.createQrcodeBytes(request);
        InputStream inputStream = new ByteArrayInputStream(bytes);
        if (!StringUtils.hasText(filePath)) {
            return FileUtil.createTmpFile(inputStream, UUID.randomUUID().toString(), "png");
        }
        return FileUtil.createTmpFile(inputStream, UUID.randomUUID().toString(), "png", Paths.get(filePath).toFile());
    }
}
