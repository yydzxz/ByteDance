package com.github.yydzxz.common.util;

import com.github.yydzxz.common.http.IByteDanceRequest;
import com.github.yydzxz.common.util.json.ByteDanceJsonBuilder;
import java.lang.reflect.Field;

/**
 * @author yangyidian
 * @date 2021/09/05
 **/
public class UrlUtil {

    public static String buildUrl(String url, Object request) {
        if(request == null) {
            return url;
        }
        StringBuilder sb;
        if(url.contains("?")){
            sb = new StringBuilder(url);
            sb.append("&");
        }else {
            sb = new StringBuilder(url);
            sb.append("?");
        }
        Field[] fields = request.getClass().getDeclaredFields();
        for(Field field : fields){
            field.setAccessible(true);
            Object value = null;
            try {
                value = field.get(request);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            String fieldAliasName = ByteDanceJsonBuilder.instance().getFieldAliasName(field);
            if(value != null){
                sb.append(fieldAliasName)
                    .append("=")
                    .append(value)
                    .append("&");
            }
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();

    }
}
