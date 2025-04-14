package com.gamjacoding.personalnewsletterapi.controller.subscription;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gamjacoding.personalnewsletterapi.application.subscription.SubscribeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
@Tag(name = "Subscribe", description = "구독 관리 API")
public class SubscribeController {

    private final SubscribeService subscribeService;

    @PostMapping("/subscribe")
    @Operation(summary = "구독 처리", description = "구독 처리를 위한 API")
    public ResponseEntity<SubscribeResponse> subscribe(@RequestBody SubscribeRequest request) {
        SubscribeResponse response = subscribeService.subscribe(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/unsubscribe")
    @Operation(summary = "구독 해지", description = "구독 해지를 위한 API")
    public ResponseEntity<SubscribeResponse> unsubscribe(@RequestParam String email) {
        SubscribeResponse response = subscribeService.unsubscribe(email);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getKeyword")
    @Operation(summary = "키워드 조회", description = "키워드 조회를 위한 API")
    public ResponseEntity<SubscribeResponse> getKeyword(@RequestParam String email) {
        SubscribeResponse response = subscribeService.getKeyword(email);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/exists")
    @Operation(summary = "이메일 존재 여부 확인", description = "이메일 존재 여부를 확인하기 위한 API")
    public ResponseEntity<SubscribeResponse> isEmailExists(@RequestParam String email) {
        SubscribeResponse response = subscribeService.isEmailExists(email);
        return ResponseEntity.ok(response);
    }
}
