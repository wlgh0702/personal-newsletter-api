package com.gamjacoding.personalnewsletterapi.domain.subscriber;

public interface SubscriberRepository {
    Subscriber findByEmail(String email);
    int save(Subscriber subscriber);
    int update(Subscriber subscriber);
    int delete(String email);
}
