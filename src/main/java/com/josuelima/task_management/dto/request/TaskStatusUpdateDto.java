package com.josuelima.task_management.dto.request;

import com.josuelima.task_management.enums.Status;
import jakarta.validation.constraints.NotNull;

public record TaskStatusUpdateDto(

        @NotNull(message = "O status da tarefa é obrigatoria. (PENDENTE, EM_ANDAMENTO, CONCLUIDO ou COM_IMPEDIMENTO)")
        Status status
) {
}
