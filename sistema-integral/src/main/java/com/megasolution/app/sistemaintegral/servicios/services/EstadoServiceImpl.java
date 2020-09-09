package com.megasolution.app.sistemaintegral.servicios.services;

import java.util.List;

import com.megasolution.app.sistemaintegral.servicios.models.entities.Estado;
import com.megasolution.app.sistemaintegral.servicios.models.repositories.IEstadoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstadoServiceImpl implements IEstadoService {

    @Autowired
    private IEstadoRepository estadoRepo;

    @Override
    public Estado buscarPorId(Integer id) {
        return estadoRepo.findById(id).orElse(null);
    }

    @Override
    public List<Estado> buscarTodos() {
        return estadoRepo.findAll();
    }
    
}
