package com.megasolution.app.sistemaintegral.services;

import com.megasolution.app.sistemaintegral.models.entities.Sector;
import com.megasolution.app.sistemaintegral.models.repositories.ISectorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SectorService {

    private final Logger log = LoggerFactory.getLogger(SectorService.class);

    private ISectorRepository sectorRepo;

    public SectorService(ISectorRepository sectorRepo) {
        this.sectorRepo = sectorRepo;
    }

    @Transactional(readOnly = true)
    public List<Sector> buscarTodos() {
        return sectorRepo.findAll();
    }

    @Transactional(readOnly = true)
    public Sector buscarPorId(Integer id) {
        return sectorRepo.findById(id).orElse(null);
    }

    public void guardar(Sector sector) {
        log.info("sector guardado!");
        sectorRepo.save(sector);

    }

    public void eliminar(Integer id) {
        log.info("sector eliminado!");
        sectorRepo.deleteById(id);
    }

    public Sector buscarPorNombre(String nombre) {
        return sectorRepo.findByNombre(nombre);
    }

    public List<Sector> buscarDisponibles() {
        return sectorRepo.findByDisponible();
    }

    public List<Sector> buscarPorParametro(String param) {

        param = param.toLowerCase();

        List<Sector> servicios = this.sectorRepo.findByParam(param);
        return servicios;
    }

    public Integer contarTodos(){ return sectorRepo.countAllBy();}

    
}
