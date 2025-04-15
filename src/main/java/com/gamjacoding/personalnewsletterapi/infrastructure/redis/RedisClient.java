package com.gamjacoding.personalnewsletterapi.infrastructure.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;
import redis.clients.jedis.Jedis;

@Component
public class RedisClient {

    private Jedis jedis;

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.ssl:false}")
    private boolean ssl;

    @PostConstruct
    public void init() {
        jedis = new Jedis(host, port, ssl);
        if (password != null && !password.isEmpty()) {
            jedis.auth(password);
        }
        jedis.connect();
    }

    public Jedis getJedisInstance() {
        if(jedis == null || !jedis.isConnected()) {
            jedis = new Jedis(host, port, ssl);
            if (password != null && !password.isEmpty()) {
                jedis.auth(password);
            }
            jedis.connect();
        }
        return jedis;
    }
}
