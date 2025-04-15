package com.gamjacoding.personalnewsletterapi.application.subscription;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gamjacoding.personalnewsletterapi.controller.subscription.SubscribeRequest;
import com.gamjacoding.personalnewsletterapi.controller.subscription.SubscribeResponse;
import com.gamjacoding.personalnewsletterapi.domain.subscriber.OperationResult;
import com.gamjacoding.personalnewsletterapi.domain.subscriber.Subscriber;
import com.gamjacoding.personalnewsletterapi.domain.subscriber.SubscriberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscribeService {

    private final SubscriberRepository subscriberRepository;

    @Transactional
    public SubscribeResponse subscribe(SubscribeRequest request) {
        try {
            Subscriber existing = subscriberRepository.findByEmail(request.getEmail());

            if (existing == null) {
                Subscriber newSubscriber = new Subscriber(
                    request.getEmail(), 
                    request.getKeywords(), 
                    LocalDateTime.now()
                );
                int saved = subscriberRepository.save(newSubscriber);
                return handleSaveResult(saved, request.getEmail());
            }

            existing.updateKeywords(request.getKeywords());
            int updated = subscriberRepository.save(existing);
            return handleUpdateResult(updated, request.getEmail());
        } catch (IllegalArgumentException e) {
            log.error("구독 처리 중 유효성 검사 실패: {}", e.getMessage());
            return new SubscribeResponse("400", e.getMessage(), request.getEmail());
        } catch (Exception e) {
            log.error("구독 처리 중 예외 발생: {}", e.getMessage());
            return new SubscribeResponse("500", "서버 내부 오류가 발생했습니다.", request.getEmail());
        }
    }

    @Transactional
    public SubscribeResponse unsubscribe(String email, List<String> keywords) {
        try {
            Subscriber existing = subscriberRepository.findByEmail(email);
            if (existing == null) {
                return new SubscribeResponse("404", "구독 정보를 찾을 수 없습니다.", email);
            }

            existing.removeKeywords(keywords);
            int updated = subscriberRepository.save(existing);
            return handleUpdateResult(updated, email);
        } catch (IllegalArgumentException e) {
            log.error("구독 해제 중 유효성 검사 실패: {}", e.getMessage());
            return new SubscribeResponse("400", e.getMessage(), email);
        } catch (Exception e) {
            log.error("구독 해제 중 예외 발생: {}", e.getMessage());
            return new SubscribeResponse("500", "서버 내부 오류가 발생했습니다.", email);
        }
    }

    @Transactional(readOnly = true)
    public SubscribeResponse isEmailExists(String email) {
        try {
            Subscriber subscriber = subscriberRepository.findByEmail(email);
            return subscriber != null ? 
                new SubscribeResponse("200", "이메일이 존재합니다.", email) :
                new SubscribeResponse("404", "이메일이 존재하지 않습니다.", email);
        } catch (Exception e) {
            log.error("이메일 확인 중 예외 발생: {}", e.getMessage());
            return new SubscribeResponse("500", "서버 내부 오류가 발생했습니다.", email);
        }
    }

    @Transactional(readOnly = true)
    public SubscribeResponse getKeywords(String email) {
        try {
            Subscriber subscriber = subscriberRepository.findByEmail(email);
            if (subscriber == null) {
                return new SubscribeResponse("404", "구독 정보를 찾을 수 없습니다.", email);
            }
            return new SubscribeResponse("200", "키워드 조회 성공", email, subscriber.getKeywords());
        } catch (Exception e) {
            log.error("키워드 조회 중 예외 발생: {}", e.getMessage());
            return new SubscribeResponse("500", "서버 내부 오류가 발생했습니다.", email);
        }
    }

    private SubscribeResponse handleSaveResult(int result, String email) {
        return result == OperationResult.SUCCESS.getCode() ?
            new SubscribeResponse("200", "구독 처리 성공", email) :
            new SubscribeResponse("500", "구독 처리 실패", email);
    }

    private SubscribeResponse handleUpdateResult(int result, String email) {
        return result == OperationResult.SUCCESS.getCode() ?
            new SubscribeResponse("200", "구독 갱신 성공", email) :
            new SubscribeResponse("500", "구독 갱신 실패", email);
    }
}
