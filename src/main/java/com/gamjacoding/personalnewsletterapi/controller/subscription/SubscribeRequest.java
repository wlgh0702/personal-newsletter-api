package com.gamjacoding.personalnewsletterapi.controller.subscription;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "구독 요청 정보")
public class SubscribeRequest {

    @Email
    @Schema(description = "구독자 이메일", example = "user@example.com", required = true)
    private String email;

    @NotEmpty
    @Schema(description = "구독할 키워드 목록", example = "[\"Java\", \"Spring\", \"Redis\"]", required = true)
    private List<String> keywords;
}
