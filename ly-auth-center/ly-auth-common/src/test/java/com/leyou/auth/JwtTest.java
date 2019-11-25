package com.leyou.auth;

import com.leyou.utils.JwtUtils;
import com.leyou.utils.RsaUtils;
import org.junit.Before;
import org.junit.Test;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * Created by zhenglongfei 2019-11-25.
 *
 * @VERSION 1.0
 */
public class JwtTest {
    private static final String pubKeyPath = "/Users/zhenglongfei/jd/key/rsa.pub";

    private static final String priKeyPath = "/Users/zhenglongfei/jd/key/rsa.pri";

    private PublicKey publicKey;

    private PrivateKey privateKey;

    @Test
    public void testRsa() throws Exception {
        RsaUtils.generateKey(pubKeyPath, priKeyPath, "234");
    }

    @Before
    public void testGetRsa() throws Exception {
        this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
        this.privateKey = RsaUtils.getPrivateKey(priKeyPath);
    }

    @Test
    public void testGenerateToken() throws Exception {
        // 生成token
        String token = JwtUtils.generateToken(new UserInfo(20L, "jack"), privateKey, 5);
        System.out.println("token = " + token);
    }

    @Test
    public void testParseToken() throws Exception {
        String token = "eyJhbGciOiJSUzI1NiJ9.eyJpZCI6MjAsInVzZXJuYW1lIjoiamFjayIsImV4cCI6MTU3NDY2NTMzMX0.ALHV8bWUsijPzgS5j2onqz9f2EcpFxEfZbYmJqy2v-xLNxlXGPD0cK6FXTOlKQ9jOKGS2gzm8DM7N5lVOZDjy966Dxu-gyrKxGVLGuQ40auluO4Crt2K83HnuQPQSyCp3m_LNs7msieBnxSlCxhcmIvJUhlmjzQLmyAlMFHNypY";

        // 解析token
        UserInfo user = JwtUtils.getInfoFromToken(token, publicKey);
        System.out.println("id: " + user.getId());
        System.out.println("userName: " + user.getUsername());
    }
}
