package com.investor.util;
import java.util.Map;
import java.util.Date;
import java.util.HashMap;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;  // ← 这才对！
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
@Component
public class JwtUtil {

    private static String SECRET;
    private static long EXPIRATION;

    @Value("${jwt.secret}")
    public void setSecret(String secret) {
        JwtUtil.SECRET = secret;
    }

    @Value("${jwt.expiration}")
    public void setExpiration(long expiration) {
        JwtUtil.EXPIRATION = expiration;
    }

    public static String createToken(String username, Long userId,String role) {

        return JWT.create().setPayload("username", username)
                .setPayload("userId", userId)
                .setPayload("role", role)
                .setExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION))
                .setKey(SECRET.getBytes())
                .sign();
    }

    public static Map<String, Object> getUserId(String token) {
        JWT jwt=JWTUtil.parseToken(token);
        Map<String, Object> map=new HashMap<>();
        map.put("username", jwt.getPayload("username"));  
        map.put("userId", jwt.getPayload("userId"));  
        map.put("role", jwt.getPayload("role"));  
        return map;

    }   

    public static boolean verify(String token) {
         return JWTUtil.verify(token, SECRET.getBytes());

    }

}
