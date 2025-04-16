package com.gamjacoding.personalnewsletterapi.scheduler;

import com.gamjacoding.personalnewsletterapi.application.subscription.SubscribeService;
import com.gamjacoding.personalnewsletterapi.domain.subscriber.Subscriber;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NewsletterScheduler {

    private final SubscribeService subscribeService;

    @Scheduled(cron = "0 0 8 * * *")
    public void sendDailyNewsletter() {
        subscribeService.sendNewsletter();
    }
}
