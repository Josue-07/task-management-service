package com.josuelima.task_management.dto.login.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record LoginResponseDto(

        @JsonProperty("access_token")
        String token,
        String type,
        String expireAt
) {
}
