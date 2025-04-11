package com.gamjacoding.personalnewsletterapi.domain.subscriber;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Subscriber implements Serializable {
    private static final long serialVersionUID = 1L;
    private String email;
    private List<String> keywords;
    private LocalDateTime subscribedAt;

    public Subscriber(String email, List<String> keywords, LocalDateTime subscribedAt) {
        this.email = email;
        this.keywords = keywords;
        this.subscribedAt = subscribedAt;
    }

    public void updateKeywords(List<String> keywords) {
        this.keywords = keywords;
        this.subscribedAt = LocalDateTime.now();
    }
}
