package com.gamjacoding.personalnewsletterapi.config.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.gamjacoding.personalnewsletterapi.domain.subscriber.Subscriber;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Subscriber> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Subscriber> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        
        // Key Serializer
        template.setKeySerializer(new StringRedisSerializer());
        
        // Value Serializer
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        Jackson2JsonRedisSerializer<Subscriber> serializer = new Jackson2JsonRedisSerializer<>(objectMapper, Subscriber.class);
        template.setValueSerializer(serializer);
        
        template.afterPropertiesSet();
        return template;
    }
}
