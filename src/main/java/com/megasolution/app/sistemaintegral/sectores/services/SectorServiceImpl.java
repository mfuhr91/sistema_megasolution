package com.megasolution.app.sistemaintegral.sectores.services;

import java.util.List;

import com.megasolution.app.sistemaintegral.sectores.models.entities.Sector;
import com.megasolution.app.sistemaintegral.sectores.models.repositories.ISectorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SectorServiceImpl implements ISectorService {

    @Autowired
    private ISectorRepository sectorRepo;

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
        sectorRepo.save(sector);

    }

    @Override
    public void eliminar(Integer id) {
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
    
}
