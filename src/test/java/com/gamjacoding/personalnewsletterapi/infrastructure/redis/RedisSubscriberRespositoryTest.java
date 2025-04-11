package com.gamjacoding.personalnewsletterapi.infrastructure.redis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import com.gamjacoding.personalnewsletterapi.domain.subscriber.OperationResult;
import com.gamjacoding.personalnewsletterapi.domain.subscriber.Subscriber;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class RedisSubscriberRespositoryTest {

    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @Mock
    private ValueOperations<String, Object> valueOperations;

    @InjectMocks
    private RedisSubscriberRepository redisSubscriberRepository;

    @Autowired
    private RedisSubscriberRepository redisSubscriberRepositoryIntegrationTest;

    @Test
    public void testFindByEmail() {
        String email = "test@example.com";
        Subscriber subscriber = new Subscriber();

        when(valueOperations.get(email)).thenReturn(subscriber);

        Subscriber result = redisSubscriberRepository.findByEmail(email);

        assertEquals(subscriber, result);
    }

    @Test
    public void testSave() {
        Subscriber subscriber = new Subscriber("test@example.com", List.of("test"), LocalDateTime.now());
        
        // Mock 객체에 동작 정의
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        doNothing().when(valueOperations).set(anyString(), any(Subscriber.class));
        
        int result = redisSubscriberRepository.save(subscriber);
        assertEquals(OperationResult.SUCCESS.getCode(), result);
        
        Subscriber found = redisSubscriberRepository.findByEmail("test@example.com");
        assertNotNull(found);
        assertEquals("test@example.com", found.getEmail());
    }
    
    @Test
    public void testSaveIntegration() {
        Subscriber subscriber = new Subscriber("test@example.com", List.of("test", "apple"), LocalDateTime.now());
        int result = redisSubscriberRepositoryIntegrationTest.save(subscriber);
        assertEquals(OperationResult.SUCCESS.getCode(), result);
    }
}
