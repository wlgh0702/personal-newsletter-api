package com.gamjacoding.personalnewsletterapi.infrastructure.redis;

import java.util.List;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.gamjacoding.personalnewsletterapi.domain.subscriber.OperationResult;
import com.gamjacoding.personalnewsletterapi.domain.subscriber.Subscriber;
import com.gamjacoding.personalnewsletterapi.domain.subscriber.SubscriberRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class RedisSubscriberRepository implements SubscriberRepository {

    private final RedisTemplate<String, Subscriber> redisTemplate;

    @Override
    public Subscriber findByEmail(String email) {
        return redisTemplate.opsForValue().get(email);
    }

    @Override
    public int save(Subscriber subscriber) {
        try {
            System.out.println("Redis 저장 시도 - 이메일: " + subscriber.getEmail());
            redisTemplate.opsForValue().set(subscriber.getEmail(), subscriber);
            System.out.println("구독 정보 저장 성공");
            return OperationResult.SUCCESS.getCode();
        } catch (Exception e) {
            System.err.println("Redis 저장 실패!");
            System.err.println("예외 타입: " + e.getClass().getName());
            System.err.println("예외 메시지: " + e.getMessage());
            e.printStackTrace();
            return OperationResult.FAIL.getCode();
        }
    }

    @Override
    public int update(Subscriber subscriber) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public int delete(String email) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public List<String> findAllKeywords(String email) {
        
        Subscriber subscriber = redisTemplate.opsForValue().get(email);
        List<String> keywords = subscriber.getKeywords();
        return keywords;
    }

}
