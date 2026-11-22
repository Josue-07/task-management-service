package com.josuelima.task_management.dto.login.request;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDto(
        @NotBlank(message = "Usuario é obrigatório.")
        String username,
        @NotBlank(message = "Senha é obrigatória.")
        String password
) {
}
