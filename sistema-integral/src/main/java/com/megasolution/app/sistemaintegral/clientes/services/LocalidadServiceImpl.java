package com.megasolution.app.sistemaintegral.clientes.services;

import java.util.List;

import com.megasolution.app.sistemaintegral.clientes.models.entities.Localidad;
import com.megasolution.app.sistemaintegral.clientes.models.repositories.ILocalidadRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocalidadServiceImpl implements ILocalidadService {

    @Autowired
    private ILocalidadRepository localidadRepo;
    @Override
    public List<Localidad> buscarTodos() {
        return localidadRepo.findAll();
    }

    @Override
    public Localidad buscarPorId(Integer id) {
        return localidadRepo.findById(id).orElse(null);
    }
    
}
