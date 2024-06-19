package com.pockyr;

import com.pockyr.utils.JWTUtils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//@SpringBootTest // 无需用到web的测试方法可以把注解注释掉，提高测试的速度
class SpringDemoApplicationTests {

    @Test
    void contextLoads() {
    }

    /**
     * 生成JWT令牌
     */
    @Test
    void testGenerateJWT() {
        // 设置荷载的数据
        Map<String, Object> claims = new HashMap<>();
        claims.put("name", "Tom");
        claims.put("email", "tom@gmail.com");
        String jwt = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, "TheSecretSizeShouldGreaterThan256bitsForHS256Algorithm") // 设置签名算法和加密的key
                .setClaims(claims) // 设置荷载的数据
                .setExpiration(new Date(System.currentTimeMillis() + 3600 * 1000)) // 设置生命周期
                .compact(); // 生成jwt字符串
        System.out.println(jwt);
        // eyJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoiVG9tIiwiZW1haWwiOiJ0b21AZ21haWwuY29tIiwiZXhwIjoxNzE4ODEyNzU5fQ.QFOManQknUB05AmCBSAudTfUmii0u1k0R29I6j_Xmro
    }

    /**
     * 解析JWT令牌
     */
    @Test
    void testParseJWT() {
        String jwt = "eyJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoiVG9tIiwiZW1haWwiOiJ0b21AZ21haWwuY29tIiwiZXhwIjoxNzE4ODEyNzU5fQ.QFOManQknUB05AmCBSAudTfUmii0u1k0R29I6j_Xmro";
        String key = "TheSecretSizeShouldGreaterThan256bitsForHS256Algorithm";
        // 只用设置key不用设置签名算法，因为jwt里的header里已经有了
        System.out.println(Jwts.parserBuilder().setSigningKey(key).build().parse(jwt).toString());
        // header={alg=HS256},body={name=Tom, email=tom@gmail.com, exp=1.718812759E9},signature=QFOManQknUB05AmCBSAudTfUmii0u1k0R29I6j_Xmro
    }

    /**
     * 测试包装了对JWT令牌的生成和解析操作的工具类JWTUtils的功能
     */
    @Test
    void testJWTUtils(){
        Map<String, Object> claims = new HashMap<>();
        claims.put("name", "Tom");
        claims.put("email", "tom@gmail.com");
        String jwt = JWTUtils.generateJWT(claims);
        System.out.println(jwt);

        String claimStr = JWTUtils.parseJWT(jwt);
        System.out.println(claimStr);
    }

}
