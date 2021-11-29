package com.megasolution.app.sistemaintegral.services;

import com.megasolution.app.sistemaintegral.models.entities.Aviso;
import com.megasolution.app.sistemaintegral.models.repositories.IAvisoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

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
    public Aviso buscarPorId(Long id) {
        return avisoRepo.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void guardar(Aviso aviso) {
        avisoRepo.save(aviso);

    }

    @Override
    @Transactional
    public void eliminar(Long id) {
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
    @Scheduled(fixedDelay = 1_800_000) // 30 minutos
    public void cambiarANoLeido() {
       List<Aviso> avisos = avisoRepo.buscarAvisosLeidos();
       LocalDate fechaActual = LocalDate.now();
       for (Aviso aviso : avisos) {
            if(aviso.getLlamado() != null){
                if(aviso.getLlamado().getId() != 1){
                    long ms = ChronoUnit.MILLIS.between(aviso.getFechaLeido(), fechaActual);
                    long horasLlamadoEnMS = aviso.getLlamado().getHoras() * 3_600_000;
                    if(aviso.getLeido()
                        && ( ms >= horasLlamadoEnMS && aviso.getLlamado().getId() == 2) ){ // 1 dia = 86_400_000 miliseg // 1hr = 3_600_000 miliseg
                        aviso.setLeido(false);
                        aviso.setFechaLeido(null);
                        avisoRepo.save(aviso);
                    } else if(aviso.getLeido()
                                && (ms >= horasLlamadoEnMS && aviso.getLlamado().getId() == 3) ){
                        aviso.setLeido(false);
                        aviso.setFechaLeido(null);
                        avisoRepo.save(aviso);
                    }
                }
            }
       }  
    }
}
