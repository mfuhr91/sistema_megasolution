package com.megasolution.app.sistemaintegral.services;

import java.util.List;

import com.megasolution.app.sistemaintegral.models.entities.Sector;
import com.megasolution.app.sistemaintegral.models.repositories.ISectorRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SectorServiceImpl implements ISectorService {

    @Autowired
    private ISectorRepository sectorRepo;

    private final Logger log = LoggerFactory.getLogger(SectorServiceImpl.class);


    @Override
    @Transactional(readOnly = true)
    public List<Sector> buscarTodos() {
        return sectorRepo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Sector buscarPorId(Integer id) {
        return sectorRepo.findById(id).orElse(null);
    }

    @Override
    public void guardar(Sector sector) {
        log.info("sector guardado!");
        sectorRepo.save(sector);

    }

    @Override
    public void eliminar(Integer id) {
        log.info("sector eliminado!");
        sectorRepo.deleteById(id);
    }

    @Override
    public Sector buscarPorNombre(String nombre) {
        return sectorRepo.findByNombre(nombre);
    }

    @Override
    public List<Sector> buscarDisponibles() {
        return sectorRepo.findByDisponible();
    }

    @Override
    public List<Sector> buscarPorParametro(String param) {

        param = param.toLowerCase();

        List<Sector> servicios = this.sectorRepo.findByParam(param);
        return servicios;
    }
    
}
