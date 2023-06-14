package com.api.todolist.services;

import com.api.todolist.dtos.TarefaRecordDTO;
import com.api.todolist.exceptions.TarefaNaoEncontradaException;
import com.api.todolist.models.TarefaModel;
import com.api.todolist.repositories.TarefaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TarefaService {

    @Autowired
    private TarefaRepository repository;

    @Transactional
    public TarefaModel criarTarefa(TarefaRecordDTO tarefaRecordDTO)
    {
        return repository.save(converterTarefaRecordDtoParaTarefaModel(tarefaRecordDTO));
    }

    public TarefaModel converterTarefaRecordDtoParaTarefaModel(TarefaRecordDTO tarefaRecordDTO)
    {
        var tarefaModel = new TarefaModel();
        BeanUtils.copyProperties(tarefaRecordDTO, tarefaModel);
        return tarefaModel;
    }

    public List<TarefaModel> buscarTarefas()
    {
        return repository.findAll();
    }

    public TarefaModel buscarTarefaPorId(UUID id)
    {
        var tarefaModelRecuperada = repository.findById(id);
        if (tarefaModelRecuperada.isEmpty())
        {
            throw new TarefaNaoEncontradaException("Tarefa não encontrada.");
        }
        return tarefaModelRecuperada.get();
    }

    public List<TarefaModel> buscarTarefasPorStatus(String status)
    {
        return repository.findByStatus(status);
    }

    @Transactional
    public TarefaModel atualizarTarefa(UUID id, TarefaRecordDTO tarefaRecordDTO)
    {
        var tarefaModelRecuperada = repository.findById(id);
        if (tarefaModelRecuperada.isEmpty())
        {
            throw new TarefaNaoEncontradaException("Tarefa não encontrada.");
        }
        var tarefaAtualizadaConvertida = converterTarefaRecordDtoParaTarefaModel(tarefaRecordDTO);
        tarefaModelRecuperada.get().setTitulo(tarefaAtualizadaConvertida.getTitulo());
        tarefaModelRecuperada.get().setDescricao(tarefaAtualizadaConvertida.getDescricao());
        tarefaModelRecuperada.get().setStatus(tarefaAtualizadaConvertida.getStatus());
        return tarefaModelRecuperada.get();
    }

}
