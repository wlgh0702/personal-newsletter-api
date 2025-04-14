package com.gamjacoding.personalnewsletterapi.application.subscription;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.gamjacoding.personalnewsletterapi.controller.subscription.SubscribeRequest;
import com.gamjacoding.personalnewsletterapi.controller.subscription.SubscribeResponse;
import com.gamjacoding.personalnewsletterapi.domain.subscriber.OperationResult;
import com.gamjacoding.personalnewsletterapi.domain.subscriber.Subscriber;
import com.gamjacoding.personalnewsletterapi.domain.subscriber.SubscriberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SubscribeService {

    private final SubscriberRepository subscribeRepository;

    public SubscribeResponse subscribe(SubscribeRequest request) {
        Subscriber existing = subscribeRepository.findByEmail(request.getEmail());

        if(existing == null) {
            Subscriber newSubscriber = new Subscriber(request.getEmail(), request.getKeywords(), LocalDateTime.now());
            int saved = subscribeRepository.save(newSubscriber);
            if(saved == OperationResult.SUCCESS.getCode()) {
                return new SubscribeResponse("200", "구독 처리 성공", request.getEmail());
            } else {
                return new SubscribeResponse("500", "구독 처리 실패", request.getEmail());
            }
        }

        existing.updateKeywords(request.getKeywords());
        int updated = subscribeRepository.save(existing);
        if(updated == OperationResult.SUCCESS.getCode()) {
            return new SubscribeResponse("200", "구독 갱신 성공", request.getEmail());
        } else {
            return new SubscribeResponse("500", "구독 갱신 실패", request.getEmail());
        }
    }

    public SubscribeResponse unsubscribe(String email) {
        int deleted = subscribeRepository.delete(email);
        if(deleted == OperationResult.SUCCESS.getCode()) {
            return new SubscribeResponse("200", "구독 취소 성공", email);
        }
        return new SubscribeResponse("500", "구독 취소 실패", email);
    }

    public SubscribeResponse isEmailExists(String email) {
        Subscriber existing = subscribeRepository.findByEmail(email);
        if(existing != null) {
            return new SubscribeResponse("200", "존재합니다", email);
        }
        return new SubscribeResponse("500", "존재하지 않습니다", email);
    }

    public SubscribeResponse getKeyword(String email) {
        Subscriber existing = subscribeRepository.findByEmail(email);
        if(existing != null) {
            return new SubscribeResponse("200", "키워드 조회 성공", subscribeRepository.findAllKeywords(email));
        }
        return new SubscribeResponse("500", "키워드 조회 실패 (이메일 존재하지 않음)", email);
    }
}
