package com.megasolution.app.sistemaintegral.services;

import java.util.List;

import com.megasolution.app.sistemaintegral.models.entities.Localidad;
import com.megasolution.app.sistemaintegral.models.repositories.ILocalidadRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LocalidadServiceImpl implements ILocalidadService {

    @Autowired
    private ILocalidadRepository localidadRepo;
    @Override
    @Transactional(readOnly = true)
    public List<Localidad> buscarTodos() {
        return localidadRepo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Localidad buscarPorId(Integer id) {
        return localidadRepo.findById(id).orElse(null);
    }
    
}
