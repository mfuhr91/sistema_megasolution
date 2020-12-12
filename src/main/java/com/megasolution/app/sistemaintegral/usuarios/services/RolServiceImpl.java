package com.megasolution.app.sistemaintegral.usuarios.services;

import java.util.List;


import com.megasolution.app.sistemaintegral.usuarios.models.entities.Rol;
import com.megasolution.app.sistemaintegral.usuarios.models.repositories.IRolRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RolServiceImpl implements IRolService {

    @Autowired
    private IRolRepository rolRepo;
    
    @Override
    @Transactional(readOnly = true)
    public List<Rol> buscarTodos() {
        return rolRepo.findAll();
    }
    
}
