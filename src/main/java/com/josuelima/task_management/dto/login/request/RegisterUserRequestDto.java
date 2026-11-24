package com.josuelima.task_management.dto.login.request;

import jakarta.validation.constraints.NotBlank;

public record RegisterUserRequestDto(
        @NotBlank (message = "Usuario é obrigatório.")
        String username,
        @NotBlank (message = "Email é obrigatório.")
        String email,
        @NotBlank (message = "Senha é obrigatória.")
        String password
) {
}
