package com.api.todolist.controllers;

import com.api.todolist.dtos.TarefaRecordDTO;
import com.api.todolist.models.TarefaModel;
import com.api.todolist.services.TarefaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/todolist/tarefas")
@CrossOrigin(origins = "*")
public class TarefaController {
    @Autowired
    private TarefaService service;

    @PostMapping
    public ResponseEntity<TarefaModel> criarTarefa(@RequestBody @Valid TarefaRecordDTO tarefaRecordDTO)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criarTarefa(tarefaRecordDTO));
    }

}
