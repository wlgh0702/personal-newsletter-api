package com.gamjacoding.personalnewsletterapi.domain.subscriber;

import java.util.List;

public interface SubscriberRepository {
    Subscriber findByEmail(String email);
    int save(Subscriber subscriber);
    int update(Subscriber subscriber);
    int delete(String email);
    List<String> findAllKeywords(String email);
}
