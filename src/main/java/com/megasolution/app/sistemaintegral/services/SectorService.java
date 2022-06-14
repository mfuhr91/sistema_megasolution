package com.megasolution.app.sistemaintegral.services;

import com.megasolution.app.sistemaintegral.models.entities.Sector;
import com.megasolution.app.sistemaintegral.models.repositories.ISectorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
        List<Sector> sectores = sectorRepo.findAll();
        List<Sector> newList = new ArrayList<>();

        for (int i = 0; i < sectores.size(); i++) {
            if ( sectores.get(i).getNombre().trim().length() <= 2 ) {
                newList.add(sectores.get(i));
                sectores.remove(sectores.get(i));
                i--;
            }
        }
        for (int i = 0; i < sectores.size(); i++) {
            if ( sectores.get(i).getNombre().length() == 3 ) {
                newList.add(sectores.get(i));
                sectores.remove(sectores.get(i));
                i--;
            }
        }
        newList.addAll(sectores);

        return newList;
    }

    @Transactional(readOnly = true)
    public Sector buscarPorId(Integer id) {
        return sectorRepo.findById(id).orElse(null);
    }

    public void guardar(Sector sector) {
        sectorRepo.save(sector);
        log.info("sector {} guardado!", sector.getNombre());

    }

    @Transactional()
    public void eliminar(Integer id) {
        Sector sector = buscarPorId(id);
        sectorRepo.disableSector(id);
        log.info("sector {} deshabilitado!", sector.getNombre());
    }

    public Sector buscarPorNombre(String nombre) {
        return sectorRepo.findByNombre(nombre);
    }

    public List<Sector> buscarDisponibles() {
        List<Sector> sectores = sectorRepo.findByDisponible();
        List<Sector> newList = new ArrayList<>();

        for (int i = 0; i < sectores.size(); i++) {
            if ( sectores.get(i).getNombre().trim().length() <= 2 ) {
                newList.add(sectores.get(i));
                System.out.println(sectores.get(i));
                sectores.remove(sectores.get(i));
                i--;
            }
        }
        for (int i = 0; i < sectores.size(); i++) {
            if ( sectores.get(i).getNombre().length() == 3 ) {
                newList.add(sectores.get(i));
                System.out.println(sectores.get(i));
                sectores.remove(sectores.get(i));
                i--;
            }
        }
        newList.addAll(sectores);
        return newList;
    }

    public List<Sector> buscarPorParametro(String param) {

        param = param.toLowerCase();

        List<Sector> servicios = this.sectorRepo.findByParam(param);
        return servicios;
    }

    public Integer contarTodos(){ return sectorRepo.countAllBy();}

    
}
