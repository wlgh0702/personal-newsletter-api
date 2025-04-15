package com.gamjacoding.personalnewsletterapi.infrastructure.redis;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.gamjacoding.personalnewsletterapi.domain.subscriber.OperationResult;
import com.gamjacoding.personalnewsletterapi.domain.subscriber.Subscriber;
import com.gamjacoding.personalnewsletterapi.domain.subscriber.SubscriberRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import redis.clients.jedis.Jedis;

@Repository
@RequiredArgsConstructor
public class RedisSubscriberRepository implements SubscriberRepository {

    private final RedisClient redisClient;
    private Jedis jedis;
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @PostConstruct
    public void init() {
        this.jedis = redisClient.getJedisInstance();
    }

    @Override
    public Subscriber findByEmail(String email) {
        try {
            String jsonString = jedis.get(email);
            return jsonString != null ? objectMapper.readValue(jsonString, Subscriber.class) : null;
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse Subscriber from Redis", e);
        }
    }

    @Override
    public int save(Subscriber subscriber) {
        try {
            String json = objectMapper.writeValueAsString(subscriber);
            jedis.set(subscriber.getEmail(), json);
            return OperationResult.SUCCESS.getCode();
        } catch (Exception e) {
            e.printStackTrace();
            return OperationResult.FAIL.getCode();
        }
    }

    @Override
    public int update(Subscriber subscriber) {
        return save(subscriber);  // Redis는 덮어쓰기로 업데이트
    }

    @Override
    public int delete(String email) {
        try {
            Long result = jedis.del(email);
            return result > 0 ? OperationResult.SUCCESS.getCode() : OperationResult.FAIL.getCode();
        } catch (Exception e) {
            e.printStackTrace();
            return OperationResult.FAIL.getCode();
        }
    }

    @Override
    public List<String> findAllKeywords(String email) {
        try {
            String jsonString = jedis.get(email);
            if (jsonString == null) {
                return List.of();
            }
            Subscriber subscriber = objectMapper.readValue(jsonString, Subscriber.class);
            return subscriber.getKeywords();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get keywords from Redis", e);
        }
    }
}
