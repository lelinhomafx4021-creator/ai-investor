package com.investor.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void testSet() {
        // 存字符串
        redisTemplate.opsForValue().set("test:name", "张三");
        System.out.println("存入成功！");
    }
    @Test
    public void testGet() {
        // 取值
        Object value = redisTemplate.opsForValue().get("test:name");
        System.out.println("取出的值：" + value);
    }

    @Test
    public void testObject() {
        // 存对象（测试 JSON 序列化）
        java.util.HashMap<String, Object> map = new java.util.HashMap<>();
        map.put("code", "600519");
        map.put("name", "茅台");
        map.put("price", 1856);
        redisTemplate.opsForValue().set("test:stock", map);
        System.out.println("对象存入成功！");

        // 取出来
        Object result = redisTemplate.opsForValue().get("test:stock");
        System.out.println("取出的对象：" + result);
    }
}