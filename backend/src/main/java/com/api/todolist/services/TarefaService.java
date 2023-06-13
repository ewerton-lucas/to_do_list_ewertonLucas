package com.api.todolist.services;

import com.api.todolist.dtos.TarefaRecordDTO;
import com.api.todolist.models.TarefaModel;
import com.api.todolist.repositories.TarefaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TarefaService {

    @Autowired
    private TarefaRepository repository;

    @Transactional
    public TarefaModel criarTarefa(TarefaRecordDTO tarefaRecordDTO)
    {
        return repository.save(converterTarefaRecordDtoParaTarefaModel(tarefaRecordDTO));
    }

    private TarefaModel converterTarefaRecordDtoParaTarefaModel(TarefaRecordDTO tarefaRecordDTO)
    {
        var tarefaModel = new TarefaModel();
        BeanUtils.copyProperties(tarefaRecordDTO, tarefaModel);
        return tarefaModel;
    }
}
