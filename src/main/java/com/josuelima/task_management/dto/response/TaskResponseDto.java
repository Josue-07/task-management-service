package com.josuelima.task_management.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.josuelima.task_management.enums.Prioridade;
import com.josuelima.task_management.enums.Status;
import java.time.LocalDateTime;
import java.util.UUID;

public record TaskResponseDto(
        UUID id,
        String nome,
        String descricao,
        Status status,
        Prioridade prioridade,

        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime prazo,

        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime dataCriacao,

        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime ultimaAtualizacao
) {
}
