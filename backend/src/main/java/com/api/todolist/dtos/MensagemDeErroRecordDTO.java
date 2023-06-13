package com.api.todolist.dtos;

import jakarta.validation.constraints.NotBlank;

public record MensagemDeErroRecordDTO(@NotBlank String mensagem) {
}
