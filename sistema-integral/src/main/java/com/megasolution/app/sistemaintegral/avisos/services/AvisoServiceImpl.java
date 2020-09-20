package com.megasolution.app.sistemaintegral.avisos.services;

import java.util.Date;
import java.util.List;

import com.megasolution.app.sistemaintegral.avisos.models.entities.Aviso;
import com.megasolution.app.sistemaintegral.avisos.models.repositories.IAvisoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@EnableScheduling
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
     @Transactional(readOnly = true)
    public List<Aviso> buscarAvisosNoLeidos() {
        return avisoRepo.buscarAvisosNoLeidos();

        
    }

    @Override
    @Transactional(readOnly = true)
    public Aviso buscarAvisoPorServicioId(Integer id) {
        return avisoRepo.buscarAvisoPorServicioId(id);
    }

    @Override
    @Scheduled(fixedDelay = 1800000)
    public void cambiarANoLeido() {
       List<Aviso> avisos = avisoRepo.buscarAvisosLeidos();
       Date fechaActual = new Date();
       for (Aviso aviso : avisos) {
            if(aviso.getLlamado() != null){
                if(aviso.getLlamado().getId() != 1){      
                        if(aviso.getLeido()  
                            && (fechaActual.getTime() - aviso.getFechaLeido().getTime()) >= aviso.getLlamado().getHoras() * 3600000 // 1hr = 3600000 miliseg
                            && aviso.getLlamado().getId() == 2){ // 1 dia = 86400000 miliseg       
                            aviso.setLeido(false);
                            aviso.setFechaLeido(null);
                            avisoRepo.save(aviso);
                        } else if(aviso.getLeido()  
                                    && (fechaActual.getTime() - aviso.getFechaLeido().getTime()) >= aviso.getLlamado().getHoras() * 3600000
                                    && aviso.getLlamado().getId() == 3){                 
                            aviso.setLeido(false);
                            aviso.setFechaLeido(null);
                            avisoRepo.save(aviso);
                        }
                }
            }
       }  
    }
}
