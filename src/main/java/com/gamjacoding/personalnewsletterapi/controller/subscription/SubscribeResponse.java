package com.gamjacoding.personalnewsletterapi.controller.subscription;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "구독 응답 정보")
public class SubscribeResponse {
    
    @Schema(description = "응답 코드", example = "SUCCESS")
    private String resultCode;
    
    @Schema(description = "응답 메시지", example = "구독이 성공적으로 처리되었습니다.")
    private String message;
    
    @Schema(description = "응답 데이터", example = "{\"email\": \"user@example.com\", \"keywords\": [\"Java\", \"Spring\"]}")
    private Object data;
    
    @Schema(description = "구독자 이메일", example = "user@example.com")
    private String email;
    
    @Schema(description = "구독 키워드 목록", example = "[\"Java\", \"Spring\"]")
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
    @Schema(description = "키워드 응답 정보")
    private static class KeywordResponse {
        @Schema(description = "구독자 이메일", example = "user@example.com")
        private String email;
        
        @Schema(description = "구독 키워드 목록", example = "[\"Java\", \"Spring\"]")
        private List<String> keywords;
    }
}
