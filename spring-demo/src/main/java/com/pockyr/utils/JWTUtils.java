package com.pockyr.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

public class JWTUtils {
    private final static String signingKey = "TheSecretSizeShouldGreaterThan256bitsForHS256Algorithm";
    private final static Long expirationTime = 43200000L;

    /**
     * 根据荷载的键值对信息生成JWT令牌
     * @param claims 荷载的键值对信息
     * @return 生成的JWT令牌字符串
     */
    public static String generateJWT(Map<String, Object> claims) {
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, signingKey) // 设置签名算法和加密的key
                .setClaims(claims) // 设置荷载的数据
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime)) // 设置生命周期
                .compact(); // 生成jwt字符串
    }

    /**
     * 解析JWT令牌字符串
     * @param jwt JWT令牌字符串
     * @return JWT荷载部分的内容的字符串
     */
    public static String parseJWT(String jwt) {
        return Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parse(jwt).toString();
    }
}
