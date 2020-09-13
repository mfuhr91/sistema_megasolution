package com.megasolution.app.sistemaintegral.avisos.services;

import java.util.List;

import com.megasolution.app.sistemaintegral.avisos.models.entities.Aviso;
import com.megasolution.app.sistemaintegral.avisos.models.repositories.IAvisoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AvisoServiceImpl implements IAvisoService {

    @Autowired
    private IAvisoRepository avisoRepo;

    @Override
    @Transactional(readOnly = true)
    public List<Aviso> buscarTodos() {
        return avisoRepo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Aviso buscarPorId(Integer id) {
        return avisoRepo.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void guardar(Aviso aviso) {
        avisoRepo.save(aviso);

    }

    @Override
    @Transactional
    public void eliminar(Integer id) {
        avisoRepo.deleteById(id);

    }

    @Override
    @Transactional(readOnly = true)
    public Integer contarAvisos() {
        return avisoRepo.contarAvisos();
    }

    @Override
    public List<Aviso> buscarAvisosNoLeidos() {
        return avisoRepo.buscarAvisosNoLeidos();

        
    }

    
    
}
