package com.investor.config;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.redis.RedisVectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPooled;

@Configuration
public class VectorStoreConfig {
    @Value("${spring.data.redis.host}")
    private String redisHost;
    @Value("${spring.data.redis.port}")
    private int redisPort;
    @Value("${spring.data.redis.password}")
    private String redisPassword;

    @Bean
    public VectorStore vectorStore(EmbeddingModel embedding) {
        JedisPooled jedis = new JedisPooled(redisHost, redisPort, null, redisPassword);
        return RedisVectorStore.builder(jedis, embedding)
                .indexName("knowledge-index")
                .prefix("knowledge:")
                .build();
    }
}
