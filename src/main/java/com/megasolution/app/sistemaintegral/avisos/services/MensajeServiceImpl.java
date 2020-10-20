package com.megasolution.app.sistemaintegral.avisos.services;

import java.util.List;

import com.megasolution.app.sistemaintegral.avisos.models.entities.Mensaje;
import com.megasolution.app.sistemaintegral.avisos.models.repositories.IMensajeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MensajeServiceImpl implements IMensajeService {

    @Autowired
    private IMensajeRepository mensajeRepo;

    @Override
    @Transactional(readOnly = true)
    public Mensaje buscarPorId(Integer id) {
        return mensajeRepo.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void actualizar(Mensaje mensaje) {
        mensajeRepo.save(mensaje);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Mensaje> buscarTodos() {
        return mensajeRepo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public String buscarAvisoDelMensaje(Integer id) {
        return mensajeRepo.buscarAvisoDelMensaje(id);
    }

   
}
