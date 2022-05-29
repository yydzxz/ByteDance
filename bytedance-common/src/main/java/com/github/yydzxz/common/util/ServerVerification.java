package com.github.yydzxz.common.util;

import lombok.extern.slf4j.Slf4j;
import java.security.MessageDigest;
import java.util.Arrays;

@Slf4j
public class ServerVerification {

    public ServerVerification() {
    }

    public static String getMsgSignature(String token, String timestamp, String nonce, String encrypt) throws Exception {
        String[] values = new String[]{token, timestamp, nonce, encrypt};
        Arrays.sort(values);
        StringBuilder sb = new StringBuilder();
        String[] var6 = values;
        int var7 = values.length;

        for(int var8 = 0; var8 < var7; ++var8) {
            String value = var6[var8];
            sb.append(value);
        }

        String str = sb.toString();

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.update(str.getBytes());
            byte[] messageDigestByte = messageDigest.digest();
            StringBuilder hexStr = new StringBuilder();
            byte[] var10 = messageDigestByte;
            int var11 = messageDigestByte.length;

            for(int var12 = 0; var12 < var11; ++var12) {
                byte b = var10[var12];
                String shaHex = Integer.toHexString(b & 255);
                if (shaHex.length() < 2) {
                    hexStr.append(0);
                }

                hexStr.append(shaHex);
            }

            return hexStr.toString();
        } catch (Exception var15) {
            log.error(var15.getMessage(), var15);
            throw new Exception("不能生成签名");
        }
    }

    public static boolean checkSignature(String msgSignature, String token, String timestamp, String nonce, String encrypt) {
        try {
            String newMsgSignature = ServerVerification.getMsgSignature(token, timestamp, nonce, encrypt);
            return newMsgSignature.equals(msgSignature);
        } catch (Exception e) {
            log.error("Checking signature failed, and the reason is :" + e.getMessage());
            log.error("msgSignature=[{}], token=[{}], timestamp=[{}], nonce=[{}], encrypt=[{}]",
                    msgSignature, token, timestamp, nonce, encrypt);
            return false;
        }
    }
}
