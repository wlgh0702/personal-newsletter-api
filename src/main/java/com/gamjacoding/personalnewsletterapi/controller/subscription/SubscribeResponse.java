package com.gamjacoding.personalnewsletterapi.controller.subscription;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubscribeResponse {
    private String resultCode;
    private String message;
    private Object data;
    private String email;
    private List<String> keywords;

    public SubscribeResponse(String resultCode, String message, String email) {
        this.resultCode = resultCode;
        this.message = message;
        this.data = email;
        this.email = email;
        this.keywords = null;
    }

    public SubscribeResponse(String resultCode, String message, String email, List<String> keywords) {
        this.resultCode = resultCode;
        this.message = message;
        this.data = new KeywordResponse(email, keywords);
        this.email = email;
        this.keywords = keywords;
    }

    @Getter
    @AllArgsConstructor
    private static class KeywordResponse {
        private String email;
        private List<String> keywords;
    }
}
