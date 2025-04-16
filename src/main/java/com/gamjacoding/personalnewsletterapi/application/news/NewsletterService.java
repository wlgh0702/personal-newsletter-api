package com.gamjacoding.personalnewsletterapi.application.news;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.gamjacoding.personalnewsletterapi.domain.subscriber.Subscriber;
import com.gamjacoding.personalnewsletterapi.domain.subscriber.SubscriberRepository;
import com.gamjacoding.personalnewsletterapi.infrastructure.news.NewsApiClient;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NewsletterService {

    private final SubscriberRepository subscriberRepository;
    private final NewsApiClient newsApiClient;

    
    @Transactional(readOnly = true)
    public void sendNewsletter() {

        List<Subscriber> subscribers = subscriberRepository.getAllSubscribers();
        for(Subscriber subscriber : subscribers) {
            List<String> keywords = subscriber.getKeywords();

            StringBuilder emailContent = new StringBuilder();

            for(String keyword : keywords) {
                // 키워드로 뉴스 조회
                String newsContent = newsApiClient.fetchNews(keyword);
                // 뉴스 내용 번역 및 요약
                //String summary = openAIClient.summarize(newsContent);
                //emailContent.append('[').append(keyword).append(']').append(summary);
            }

            //emailService.send(subscriber.getEmail(), '오늘의 뉴스 요약', emailContent.toString());
        }

    }
}
