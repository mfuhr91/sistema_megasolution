package com.megasolution.app.sistemaintegral.services;

import com.megasolution.app.sistemaintegral.models.entities.Rol;
import com.megasolution.app.sistemaintegral.models.repositories.IRolRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RolService {

    private IRolRepository rolRepo;

    public RolService(IRolRepository rolRepo) {
        this.rolRepo = rolRepo;
    }

    @Transactional(readOnly = true)
    public List<Rol> buscarTodos() {
        return rolRepo.findAll();
    }
    
}
