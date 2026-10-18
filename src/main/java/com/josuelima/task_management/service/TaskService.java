package com.josuelima.task_management.service;

import com.josuelima.task_management.dto.TaskPaginationDto;
import com.josuelima.task_management.dto.request.TaskRequestDto;
import com.josuelima.task_management.dto.request.TaskStatusUpdateDto;
import com.josuelima.task_management.dto.response.ListTasksResponseDto;
import com.josuelima.task_management.dto.response.TaskResponseDto;
import com.josuelima.task_management.exceptions.NotFoundTaskException;
import com.josuelima.task_management.dto.mapper.TaskMapper;
import com.josuelima.task_management.model.Task;
import com.josuelima.task_management.repositories.TaskRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;


@Slf4j
@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public TaskService(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    public TaskResponseDto createTask(TaskRequestDto taskRequestDto) {
        log.debug("Iniciando criação de uma nova tarefa");

        Task task = new Task(
                taskRequestDto.nome(),
                taskRequestDto.descricao(),
                taskRequestDto.status(),
                taskRequestDto.prioridade(),
                taskRequestDto.prazo());

        taskRepository.save(task);
        log.info("Tarefa criada com sucesso. id={}", task.getId());

        return taskMapper.toTaskResponseDto(task);
    }

    public TaskPaginationDto<ListTasksResponseDto> listTasks(int page, int size) {
        log.debug("Iniciando listagem de todas as tasks");
        Page<Task> taskPage = taskRepository.findAll(PageRequest.of(page, size));
        return new TaskPaginationDto<>( taskMapper.toListTaskResponseDto(taskPage.getContent()), taskPage.getNumber(), taskPage.getSize(), taskPage.getTotalElements(), taskPage.getTotalPages());

    }

    public TaskResponseDto getTaskById(UUID id) {
        log.debug("Iniciando busca pela task de id={}", id);

        Task task = taskRepository.findById(id).orElseThrow(() -> new NotFoundTaskException("Task não encontrada ou não existe."));
        log.info("Tarefa encontrada com sucesso. id={}", id);
        return taskMapper.toTaskResponseDto(task);
    }

    public TaskResponseDto updateTask(UUID id, TaskRequestDto taskRequestDto) {

        Task task = taskMapper.toTask(this.getTaskById(id));

        log.debug("Iniciando atualização da task de id={}", id);
        task.setNome(taskRequestDto.nome());
        task.setDescricao(taskRequestDto.descricao());
        task.setStatus(taskRequestDto.status());
        task.setPrioridade(taskRequestDto.prioridade());
        task.setPrazo(taskRequestDto.prazo());
        task.setUltimaAtualizacao(LocalDateTime.now());

        taskRepository.save(task);
        log.info("Tarefa atualizada com sucesso. id={}", id);
        return taskMapper.toTaskResponseDto(task);
    }

    public TaskResponseDto updateStatusTask(UUID id, TaskStatusUpdateDto taskStatusUpdateDto) {

        Task task = taskMapper.toTask(this.getTaskById(id));

        log.debug("Iniciando atualizacao do Status da task de id={}", id);
        task.setStatus(taskStatusUpdateDto.status());
        task.setUltimaAtualizacao(LocalDateTime.now());

        taskRepository.save(task);
        log.info("Status da Tarefa atualizada com sucesso. id={}", id);
        return taskMapper.toTaskResponseDto(task);
    }

    public void deleteTask(UUID id) {
        log.debug("Iniciando deleção da task de id={}", id);

        Task task = taskMapper.toTask(this.getTaskById(id));

        taskRepository.delete(task);
        log.info("Tarefa deletada com sucesso. id={}", id);
    }


}
