package com.gamjacoding.personalnewsletterapi.controller.subscription;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;

@Getter
public class SubscribeRequest {

    @Email
    private String email;

    @NotEmpty
    private List<String> keywords;
}
