package com.example.demokl.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import java.util.UUID;

public class CommunityUtil {
    //生成随机字符串
    public static String generateUUID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }


    //MD5加密
    //但是只能加密不能解密,并且如果用简单密码如hello 就会加密成一个固定的字符串，而且每次加密都一样，容易被黑客破解
    //所以可以给密码后面加上一个随机字符串，再去加密，就能解决
    public static String md5(String key){
        //判断key是否为空，为空就不做处理，因为没有意义
        if (StringUtils.isBlank(key)){
        return null;
        }
        //不为空，就加密
        return DigestUtils.md5DigestAsHex(key.getBytes());
    }
}
