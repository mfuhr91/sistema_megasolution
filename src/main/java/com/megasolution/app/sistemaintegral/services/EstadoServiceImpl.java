package com.megasolution.app.sistemaintegral.services;

import java.util.List;

import com.megasolution.app.sistemaintegral.models.entities.Estado;
import com.megasolution.app.sistemaintegral.models.repositories.IEstadoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EstadoServiceImpl implements IEstadoService {

    @Autowired
    private IEstadoRepository estadoRepo;

    @Override
    @Transactional(readOnly = true)
    public Estado buscarPorId(Integer id) {
        return estadoRepo.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Estado> buscarTodos() {
        return estadoRepo.findAll();
    }

    @Override
    public Estado buscarPorCodigo(String codigo) {
       return estadoRepo.findByCodigo(codigo);
    }
    
}
