package com.gamjacoding.personalnewsletterapi.controller.subscription;

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
}
