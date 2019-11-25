package com.leyou.commom.pojo;

import org.springframework.util.DigestUtils;

import java.util.Random;

/**
 * Created by zhenglongfei 2019-11-25.
 *
 * @VERSION 1.0
 */
public class CodecUtils {
    //盐，用于混交md5
    private static final String slat = "&%5123***&&%%$$#@";

    public static String md5Hex(String password, String slat) {
        String base = password + "/" + slat;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

    public static String generateSalt() {
        return slat.substring(0, new Random().nextInt(16) + 1);
    }
}
