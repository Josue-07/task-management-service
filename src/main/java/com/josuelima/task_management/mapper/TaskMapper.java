package com.josuelima.task_management.mapper;

import com.josuelima.task_management.dto.response.ListTasksResponseDto;
import com.josuelima.task_management.dto.response.TaskResponseDto;
import com.josuelima.task_management.model.Task;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring")
public interface TaskMapper {

    TaskResponseDto toTaskResponseDto(Task task);
    List<TaskResponseDto> toTaskResponseDtoList(List<Task> tasks);

    default ListTasksResponseDto toListTaskResponseDto(List<Task> tasks){
        return new ListTasksResponseDto(toTaskResponseDtoList(tasks));
    }

}
