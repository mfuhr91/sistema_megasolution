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
    // TODO: LIMPIAR COD COMENTADO
    //@Scheduled(fixedDelay = 1000)
    public void cambiarANoLeido() {
       List<Aviso> avisos = avisoRepo.buscarAvisosLeidos();
       Date fechaActual = new Date();
       System.out.println("CONTROLANDO.....");
       for (Aviso aviso : avisos) {
            System.out.println("AVISO ID: "+aviso.getId());
            
            System.out.println("AVISO LLAMADO: "+aviso.getLlamado().getHoras());
            System.out.println("AVISO FECHA LEIDO: "+aviso.getFechaLeido());
            if(aviso.getLlamado() != null){
                System.out.println("AVISO FECHA ACTUAL - FECHA LEIDO: "+ (fechaActual.getTime() - aviso.getFechaLeido().getTime()));
                System.out.println("AVISO LLAMADO * 3000: "+aviso.getLlamado().getHoras() * 3000);
            }
            

            if(aviso.getLeido()  
            && (fechaActual.getTime() - aviso.getFechaLeido().getTime()) >= aviso.getLlamado().getHoras() * 3000 // 1hr = 3600000 miliseg
            && aviso.getLlamado().getId() == 2){ // 1 dia = 86400000 miliseg
                System.out.println("SEGUNDO AVISO MOSTRADO");
                
               aviso.setLeido(false);
               aviso.setFechaLeido(null);
               avisoRepo.save(aviso);
           } else if(aviso.getLeido()  
           && (fechaActual.getTime() - aviso.getFechaLeido().getTime()) >= aviso.getLlamado().getHoras() * 3000
           && aviso.getLlamado().getId() == 3){
            System.out.println("TERCER AVISO MOSTRADO");
                
            aviso.setLeido(false);
            aviso.setFechaLeido(null);
            avisoRepo.save(aviso);
           }
       }  
    }
}
