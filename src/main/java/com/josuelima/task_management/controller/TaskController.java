package com.josuelima.task_management.controller;

import com.josuelima.task_management.dto.request.TaskRequestDto;
import com.josuelima.task_management.dto.request.TaskStatusUpdateDto;
import com.josuelima.task_management.dto.response.ListTasksResponseDto;
import com.josuelima.task_management.dto.response.TaskResponseDto;
import com.josuelima.task_management.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/task")
    public ResponseEntity<TaskResponseDto> createTask(@Valid @RequestBody TaskRequestDto taskRequestDto) {
        TaskResponseDto taskCreateResponseDto = taskService.createTask(taskRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(taskCreateResponseDto);
    }

    @GetMapping("/tasks")
    public ResponseEntity<ListTasksResponseDto> listAllTasks() {
        return ResponseEntity.ok(taskService.listTasks());
    }

    @GetMapping("/task/{id}")
    public ResponseEntity<TaskResponseDto> getTaskById(@PathVariable UUID id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @PutMapping("/task/{id}")
    public ResponseEntity<TaskResponseDto> updateTask(@PathVariable UUID id,
                                                      @Valid @RequestBody TaskRequestDto taskRequestDto) {
        TaskResponseDto taskUpdateResponseDto = taskService.updateTask(id, taskRequestDto);
        return ResponseEntity.ok(taskUpdateResponseDto);
    }

    @PatchMapping("/task/{id}/status")
    public ResponseEntity<TaskResponseDto> updateTaskStatus(@PathVariable UUID id,
                                                            @Valid @RequestBody TaskStatusUpdateDto taskStatusUpdateDto) {
        TaskResponseDto taskResponseDto = taskService.updateStatusTask(id, taskStatusUpdateDto);
        return ResponseEntity.ok(taskResponseDto);
    }

    @DeleteMapping("/task/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable UUID id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

}
