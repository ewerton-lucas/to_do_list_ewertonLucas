package com.api.todolist.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TarefaRecordDTO(@NotBlank String titulo,
                              @NotBlank @Size(max = 250) String descricao,
                              @NotBlank String status) {
}
