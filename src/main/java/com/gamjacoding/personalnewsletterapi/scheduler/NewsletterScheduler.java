package com.gamjacoding.personalnewsletterapi.scheduler;

import com.gamjacoding.personalnewsletterapi.application.news.NewsletterService;
import com.gamjacoding.personalnewsletterapi.application.subscription.SubscribeService;
import com.gamjacoding.personalnewsletterapi.domain.subscriber.Subscriber;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NewsletterScheduler {

    private final NewsletterService newsletterService;

    @Scheduled(cron = "1/10 * * * * *")
    public void sendDailyNewsletter() {
        newsletterService.sendNewsletter();
    }
}
