package com.doublecat.utils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

/**
 * @Author Zongmin
 * @Date Create in 2021/8/3 19:31
 * @Modified By:
 */
public class HttpEntityUtil {
    /**
     * 默认的UTF-8格式APPLICATION/JSON ENTITY
     * @return
     */
    public static HttpEntity defaultHttpEntity(String jsonParam){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(jsonParam, headers);
    }
}
