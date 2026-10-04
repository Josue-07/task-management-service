package com.josuelima.task_management.dto.request;

import com.josuelima.task_management.enums.Prioridade;
import com.josuelima.task_management.enums.Status;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record TaskRequestDto(
        @NotBlank(message = "O nome da tarefa é obrigatório.")
        String nome,

        @NotNull
        String descricao,

        @NotNull(message = "O nome da prioridade da tarefa é obrigatoria. (BAIXA, MEDIA, ALTA, URGENTE)")
        Prioridade prioridade,

        @NotNull(message = "O status da tarefa é obrigatoria. (PENDENTE, EM_ANDAMENTO, CONCLUIDO ou COM_IMPEDIMENTO)")
        Status status,

        @NotNull(message = "O prazo é obrigatório.")
        @Future(message = "O prazo precisa ser uma data futura.")
        LocalDateTime prazo
) {
}
