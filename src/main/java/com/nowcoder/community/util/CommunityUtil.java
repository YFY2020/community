package com.nowcoder.community.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import java.util.UUID;

public class CommunityUtil {

    // 生成随机的字符串
    public static String generateUUID(){
        // 生成随机的字符串，并将字符串中的“-”替换成空的字符串“”
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    //MD5加密
    public static String md5(String key){
        //对参数key进行判断，如果为空就不加密
        if(StringUtils.isBlank(key)){
           //isBlank：如果为null、空串、空格都认为是空的
           return null;
        }
        return DigestUtils.md5DigestAsHex(key.getBytes());
    }
}
