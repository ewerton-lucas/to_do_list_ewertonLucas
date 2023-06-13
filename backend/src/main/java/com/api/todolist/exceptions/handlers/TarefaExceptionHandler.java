package com.api.todolist.exceptions.handlers;

import com.api.todolist.dtos.MensagemDeErroRecordDTO;
import com.api.todolist.exceptions.TarefaNaoEncontradaException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class TarefaExceptionHandler {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    @ExceptionHandler(TarefaNaoEncontradaException.class)
    public MensagemDeErroRecordDTO handlerError404(TarefaNaoEncontradaException exception)
    {
        return new MensagemDeErroRecordDTO(exception.getMensagem());
    }

}
