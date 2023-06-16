package com.api.todolist.services;

import com.api.todolist.controllers.TarefaController;
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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class TarefaService {

    @Autowired
    private TarefaRepository repository;

    @Transactional
    public TarefaModel criarTarefa(TarefaRecordDTO tarefaRecordDTO)
    {
        return repository.save(converterTarefaRecordDtoParaTarefaModel(tarefaRecordDTO));
    }

    public List<TarefaModel> buscarTarefas()
    {
        var tarefas = repository.findAll();
        if (!tarefas.isEmpty())
        {
            for (TarefaModel tarefa : tarefas)
            {
                adicionarLinkParaRecuperarTarefaPorId(tarefa);
            }
        }
        return tarefas;
    }

    public TarefaModel buscarTarefaPorId(UUID id)
    {
        var tarefa = repository.findById(id);
        if (tarefa.isEmpty())
        {
            throw new TarefaNaoEncontradaException("Tarefa não encontrada.");
        }
        adicionarLinkParaRecuperarListaDeTarefas(tarefa.get());
        return tarefa.get();
    }

    public List<TarefaModel> buscarTarefasPorStatus(String status)
    {
        var tarefas = repository.findByStatus(status);
        if (!tarefas.isEmpty())
        {
            for (TarefaModel tarefa : tarefas)
            {
                adicionarLinkParaRecuperarTarefaPorId(tarefa);
                adicionarLinkParaRecuperarListaDeTarefas(tarefa);
            }
        }
        return tarefas;
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
        adicionarLinkParaRecuperarListaDeTarefas(tarefaModelRecuperada.get());
        return tarefaModelRecuperada.get();
    }

    @Transactional
    public Object deletarTarefa(UUID id)
    {
        var tarefaRecuperada = repository.findById(id);
        if (tarefaRecuperada.isEmpty())
        {
            throw new TarefaNaoEncontradaException("Tarefa não encontrada.");
        }
        repository.delete(tarefaRecuperada.get());
        return "Tarefa deletada com sucesso.";
    }

    public TarefaModel converterTarefaRecordDtoParaTarefaModel(TarefaRecordDTO tarefaRecordDTO)
    {
        var tarefaModel = new TarefaModel();
        BeanUtils.copyProperties(tarefaRecordDTO, tarefaModel);
        return tarefaModel;
    }

    public void adicionarLinkParaRecuperarTarefaPorId(TarefaModel tarefa)
    {
            tarefa.add(linkTo(methodOn(TarefaController.class)
                    .buscarTarefaPorId(tarefa.getId()))
                    .withSelfRel());
    }

    public void adicionarLinkParaRecuperarListaDeTarefas(TarefaModel tarefa)
    {
        tarefa.add(linkTo(methodOn(TarefaController.class)
                .buscarTarefas())
                .withRel("Lista de Tarefas"));
    }

}
