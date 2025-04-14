package com.gamjacoding.personalnewsletterapi.config.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class RedisConfigLogger {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int redisPort;

    @PostConstruct
    public void logRedisConfig() {
        System.out.println("Redis Host: " + host);
        System.out.println("Redis Port: " + redisPort);
    }
}
