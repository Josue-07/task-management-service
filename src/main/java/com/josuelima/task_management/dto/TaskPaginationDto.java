package com.josuelima.task_management.dto;

import java.util.List;

public record TaskPaginationDto<T>(
        T data,
        int page,
        int size,
        long totalElements,
        int totalPages
) {
}
