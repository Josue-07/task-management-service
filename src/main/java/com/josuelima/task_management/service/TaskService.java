package com.josuelima.task_management.service;

import com.josuelima.task_management.dto.request.TaskRequestDto;
import com.josuelima.task_management.dto.request.TaskStatusUpdateDto;
import com.josuelima.task_management.dto.response.ListTasksResponseDto;
import com.josuelima.task_management.dto.response.TaskResponseDto;
import com.josuelima.task_management.mapper.TaskMapper;
import com.josuelima.task_management.model.Task;
import com.josuelima.task_management.repositories.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public TaskService(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    public TaskResponseDto createTask(TaskRequestDto taskRequestDto) {
        Task task = new Task(
                taskRequestDto.nome(),
                taskRequestDto.descricao(),
                taskRequestDto.status(),
                taskRequestDto.prioridade(),
                taskRequestDto.prazo());

        taskRepository.save(task);

        return taskMapper.toTaskResponseDto(task);
    }

    public ListTasksResponseDto listTasks() {
        return taskMapper.toListTaskResponseDto(taskRepository.findAll());
    }

    public TaskResponseDto getTaskById(UUID id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task não encontrada"));
        return taskMapper.toTaskResponseDto(task);
    }

    public TaskResponseDto updateTask(UUID id, TaskRequestDto taskRequestDto) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task não existe ou não encontrado."));

        task.setNome(taskRequestDto.nome());
        task.setDescricao(taskRequestDto.descricao());
        task.setStatus(taskRequestDto.status());
        task.setPrioridade(taskRequestDto.prioridade());
        task.setPrazo(taskRequestDto.prazo());
        task.setUltimaAtualizacao(LocalDateTime.now());

        taskRepository.save(task);
        return taskMapper.toTaskResponseDto(task);
    }

    public TaskResponseDto updateStatusTask(UUID id, TaskStatusUpdateDto taskStatusUpdateDto) {

        Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task não existe ou não encontrado."));

        task.setStatus(taskStatusUpdateDto.status());
        task.setUltimaAtualizacao(LocalDateTime.now());

        taskRepository.save(task);
        return taskMapper.toTaskResponseDto(task);
    }

    public void deleteTask(UUID id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task não existe ou não encontrado."));
        taskRepository.delete(task);
    }


}
