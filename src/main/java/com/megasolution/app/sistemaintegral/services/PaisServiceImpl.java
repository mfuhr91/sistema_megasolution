package com.megasolution.app.sistemaintegral.services;

import java.util.List;

import com.megasolution.app.sistemaintegral.models.entities.Pais;
import com.megasolution.app.sistemaintegral.models.repositories.IPaisRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaisServiceImpl implements IPaisService {

    @Autowired
    private IPaisRepository paisRepo;
    @Override
    public List<Pais> buscarTodos() {
        return paisRepo.findAll();
    }

    @Override
    public Pais buscarPorId(Integer id) {
        return paisRepo.findById(id).orElse(null);
    }
    
}
