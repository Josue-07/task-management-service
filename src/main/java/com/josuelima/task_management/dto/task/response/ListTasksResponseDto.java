package com.josuelima.task_management.dto.task.response;

import java.util.List;

public record ListTasksResponseDto(
        List<TaskResponseDto> tasks
) {

}
