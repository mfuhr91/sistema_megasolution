package com.megasolution.app.sistemaintegral.services;

import java.util.List;

import com.megasolution.app.sistemaintegral.models.entities.Llamado;
import com.megasolution.app.sistemaintegral.models.repositories.ILlamadoReposity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LlamadoServiceImpl implements ILlamadoService {

    @Autowired
    private ILlamadoReposity llamadoRepo;

    @Override
    @Transactional(readOnly = true)
    public List<Llamado> buscarTodos() {
        return llamadoRepo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Llamado buscarPorId(Integer id) {
        return llamadoRepo.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void actualizar(Llamado llamado) {
        llamadoRepo.save(llamado);

    }
    
}
