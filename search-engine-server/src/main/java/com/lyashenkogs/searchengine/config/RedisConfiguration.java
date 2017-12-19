package com.lyashenkogs.searchengine.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import redis.clients.jedis.Jedis;
import redis.embedded.RedisServer;

import java.io.IOException;

@Configuration
public class RedisConfiguration {

    @Bean(initMethod = "start", destroyMethod = "stop", name = "redisServer")
    public RedisServer redisServerForDocuments() throws IOException {
        return new RedisServer(6379);
    }

    @Bean(name = "documents")
    @DependsOn("redisServer")
    public Jedis jedis() {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.select(0);
        return jedis;
    }

    @Bean("invertedIndex")
    @DependsOn("redisServer")
    public Jedis jedisInvertedIndex() {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.select(1);
        return jedis;
    }


}
