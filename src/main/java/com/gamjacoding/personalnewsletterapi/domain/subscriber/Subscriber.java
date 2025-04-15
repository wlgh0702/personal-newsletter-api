package com.gamjacoding.personalnewsletterapi.domain.subscriber;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Pattern;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Subscriber implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final int MAX_KEYWORDS = 10;
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    private String email;
    private List<String> keywords;
    private LocalDateTime subscribedAt;

    public Subscriber(String email, List<String> keywords, LocalDateTime subscribedAt) {
        validateEmail(email);
        validateKeywords(keywords);
        this.email = email;
        this.keywords = keywords;
        this.subscribedAt = subscribedAt;
    }

    private void validateEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("이메일은 필수입니다.");
        }
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("유효하지 않은 이메일 형식입니다.");
        }
    }

    private void validateKeywords(List<String> keywords) {
        if (keywords == null) {
            throw new IllegalArgumentException("키워드 목록은 null이 될 수 없습니다.");
        }
        if (keywords.size() > MAX_KEYWORDS) {
            throw new IllegalArgumentException("키워드는 최대 " + MAX_KEYWORDS + "개까지만 등록 가능합니다.");
        }
        if (keywords.stream().anyMatch(String::isBlank)) {
            throw new IllegalArgumentException("키워드는 빈 문자열이 될 수 없습니다.");
        }
    }

    public void updateKeywords(List<String> newKeywords) {
        validateKeywords(newKeywords);
        for (String keyword : newKeywords) {
            if (!this.keywords.contains(keyword)) {
                this.keywords.add(keyword);
            }
        }
        if (this.keywords.size() > MAX_KEYWORDS) {
            throw new IllegalArgumentException("키워드는 최대 " + MAX_KEYWORDS + "개까지만 등록 가능합니다.");
        }
        this.subscribedAt = LocalDateTime.now();
    }

    public void removeKeywords(List<String> keywordsToRemove) {
        if (keywordsToRemove == null) {
            throw new IllegalArgumentException("제거할 키워드 목록은 null이 될 수 없습니다.");
        }
        this.keywords.removeAll(keywordsToRemove);
    }

    public boolean hasKeyword(String keyword) {
        return keywords.contains(keyword);
    }

    public boolean hasAnyKeyword(List<String> keywords) {
        return keywords.stream().anyMatch(this::hasKeyword);
    }
}
