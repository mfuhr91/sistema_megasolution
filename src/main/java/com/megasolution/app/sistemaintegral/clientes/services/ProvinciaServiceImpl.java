package com.megasolution.app.sistemaintegral.clientes.services;

import java.util.List;

import com.megasolution.app.sistemaintegral.clientes.models.entities.Provincia;
import com.megasolution.app.sistemaintegral.clientes.models.repositories.IProvinciaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProvinciaServiceImpl implements IProvinciaService {

    @Autowired
    private IProvinciaRepository provinciaRepo;

    @Override
    @Transactional(readOnly = true)
    public List<Provincia> buscarTodos() {
        return provinciaRepo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Provincia buscarPorId(Integer id) {
        return provinciaRepo.findById(id).orElse(null);
    }


       
}
