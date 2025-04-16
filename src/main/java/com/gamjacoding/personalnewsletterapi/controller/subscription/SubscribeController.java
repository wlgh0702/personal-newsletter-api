package com.gamjacoding.personalnewsletterapi.controller.subscription;

import java.util.List;

import com.gamjacoding.personalnewsletterapi.domain.subscriber.Subscriber;
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
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
@Tag(name = "Subscribe", description = "구독 관리 API")
public class SubscribeController {

    private final SubscribeService subscribeService;

    @PostMapping("/subscribe")
    @Operation(
        summary = "구독 처리",
        description = "이메일과 키워드 목록을 받아 새로운 구독을 생성하거나 기존 구독을 업데이트합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "구독 성공",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = SubscribeResponse.class),
                examples = @ExampleObject(
                    value = "{\"resultCode\": \"SUCCESS\", \"message\": \"구독이 성공적으로 처리되었습니다.\", \"email\": \"user@example.com\", \"keywords\": [\"Java\", \"Spring\"]}"
                )
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "잘못된 요청",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    value = "{\"resultCode\": \"FAIL\", \"message\": \"이메일 형식이 올바르지 않습니다.\"}"
                )
            )
        )
    })
    public ResponseEntity<SubscribeResponse> subscribe(@RequestBody SubscribeRequest request) {
        SubscribeResponse response = subscribeService.subscribe(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/unsubscribe")
    @Operation(
        summary = "구독 해지",
        description = "이메일과 선택적으로 키워드 목록을 받아 구독을 해지합니다. 키워드가 지정되지 않은 경우 모든 구독을 해지합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "구독 해지 성공",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = SubscribeResponse.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "구독 정보를 찾을 수 없음",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    value = "{\"resultCode\": \"FAIL\", \"message\": \"구독 정보를 찾을 수 없습니다.\"}"
                )
            )
        )
    })
    public ResponseEntity<SubscribeResponse> unsubscribe(
        @Parameter(description = "구독자 이메일", example = "user@example.com", required = true)
        @RequestParam String email,
        
        @Parameter(description = "해지할 키워드 목록 (선택사항, 기본값: all)", example = "[\"Java\", \"Spring\"]")
        @RequestParam(required = false, defaultValue = "all") List<String> keywords
    ) {
        SubscribeResponse response = subscribeService.unsubscribe(email, keywords);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getKeyword")
    @Operation(
        summary = "키워드 조회",
        description = "이메일을 받아 해당 구독자의 키워드 목록을 조회합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "키워드 조회 성공",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = SubscribeResponse.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "구독 정보를 찾을 수 없음",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    value = "{\"resultCode\": \"FAIL\", \"message\": \"구독 정보를 찾을 수 없습니다.\"}"
                )
            )
        )
    })
    public ResponseEntity<SubscribeResponse> getKeyword(
        @Parameter(description = "구독자 이메일", example = "user@example.com", required = true)
        @RequestParam String email
    ) {
        SubscribeResponse response = subscribeService.getKeywords(email);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/exists")
    @Operation(
        summary = "이메일 존재 여부 확인",
        description = "이메일을 받아 해당 구독자가 존재하는지 확인합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "이메일 존재 여부 확인 성공",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = SubscribeResponse.class)
            )
        )
    })
    public ResponseEntity<SubscribeResponse> isEmailExists(
        @Parameter(description = "확인할 이메일", example = "user@example.com", required = true)
        @RequestParam String email
    ) {
        SubscribeResponse response = subscribeService.isEmailExists(email);
        return ResponseEntity.ok(response);
    }
}
