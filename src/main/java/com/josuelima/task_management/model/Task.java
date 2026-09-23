package com.josuelima.task_management.model;

import com.josuelima.task_management.enums.Prioridade;
import com.josuelima.task_management.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter @Setter
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
    private Status status = Status.PENDENTE;
    @Enumerated(EnumType.STRING)
    private Prioridade  prioridade = Prioridade.BAIXA;
    private LocalDateTime prazo;
    private LocalDateTime dataCriacao;
    private LocalDateTime ultimaAtualizacao;


}
