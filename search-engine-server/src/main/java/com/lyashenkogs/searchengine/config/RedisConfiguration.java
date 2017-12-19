package com.lyashenkogs.searchengine.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;
import redis.embedded.RedisServer;

import java.io.IOException;

@Configuration
public class RedisConfiguration {

    @Bean(initMethod = "start", destroyMethod = "stop")
    public RedisServer redisServer() throws IOException {
        return new RedisServer(6379);
    }

    @Bean
    public Jedis jedis(RedisServer redisServer) {
        return new Jedis();
    }
}
