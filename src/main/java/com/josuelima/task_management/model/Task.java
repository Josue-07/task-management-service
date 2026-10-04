package com.josuelima.task_management.model;

import com.josuelima.task_management.enums.Prioridade;
import com.josuelima.task_management.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity()
@Table(name = "TASKS")
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String nome;
    private String descricao;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Enumerated(EnumType.STRING)
    private Prioridade prioridade;
    private LocalDateTime prazo;
    private LocalDateTime dataCriacao;
    private LocalDateTime ultimaAtualizacao;

    public Task(String nome, String descricao, Status status, Prioridade prioridade, LocalDateTime prazo) {
        this.nome = nome;
        this.descricao = descricao;
        this.status = status;
        this.prioridade = prioridade;
        this.prazo = prazo;
        this.dataCriacao = LocalDateTime.now();
        this.ultimaAtualizacao = this.dataCriacao;
    }


}
