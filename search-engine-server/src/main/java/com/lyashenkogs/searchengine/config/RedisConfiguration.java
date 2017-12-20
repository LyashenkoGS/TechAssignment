package com.lyashenkogs.searchengine.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import redis.clients.jedis.Jedis;
import redis.embedded.RedisServer;

import java.io.IOException;

@Configuration
public class RedisConfiguration {

    @Value("${redis.port}")
    private int redisPort;

    @Value("${redis.host}")
    private String redisHost;

    @ConditionalOnProperty("enable.embedded.redis")
    @Bean(initMethod = "start", destroyMethod = "stop", name = "redisServer")
    public RedisServer redisServerForDocuments() throws IOException {
        return new RedisServer(redisPort);
    }

    @Bean(name = "documents")
    @DependsOn("redisServer")
    public Jedis jedis() {
        Jedis jedis = new Jedis(redisHost, redisPort);
        jedis.select(0);
        return jedis;
    }

    @Bean("invertedIndex")
    @DependsOn("redisServer")
    public Jedis jedisInvertedIndex() {
        Jedis jedis = new Jedis(redisHost, redisPort);
        jedis.select(1);
        return jedis;
    }


}
