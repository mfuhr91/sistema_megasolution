package com.megasolution.app.sistemaintegral.servicios.services;

import java.util.List;

import com.megasolution.app.sistemaintegral.servicios.models.entities.Estado;
import com.megasolution.app.sistemaintegral.servicios.models.repositories.IEstadoRepository;

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
    
}
