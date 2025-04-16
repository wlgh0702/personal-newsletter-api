package com.gamjacoding.personalnewsletterapi.infrastructure.news;

import org.springframework.stereotype.Component;

@Component
public class NewsApiClient {

    public String fetchNews(String keyword) {
        // TODO: 뉴스 조회
        return "news";
    }
}
