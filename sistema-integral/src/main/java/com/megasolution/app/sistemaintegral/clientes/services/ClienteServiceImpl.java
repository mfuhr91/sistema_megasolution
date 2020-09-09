package com.megasolution.app.sistemaintegral.clientes.services;

import java.util.List;

import com.megasolution.app.sistemaintegral.clientes.models.entities.Cliente;
import com.megasolution.app.sistemaintegral.clientes.models.repositories.IClienteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteServiceImpl implements IClienteService {

    @Autowired
    private IClienteRepository clienteRepo;

    @Override
    @Transactional(readOnly = true)
    public List<Cliente> buscarTodos() {
        return clienteRepo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Cliente buscarPorId(Integer id) {
        return clienteRepo.findById(id).orElse(null);
    }
/* 
    @Override
    @Transactional(readOnly = true)
    public Cliente buscarPorRazonSocial(String razonSocial) {
        return clienteRepo.findByRazonSocial(razonSocial);
    }

    @Override
    @Transactional(readOnly = true)
    public Cliente buscarPorContacto(String contacto) {
        return clienteRepo.findByContacto(contacto);
    }

    @Override
    @Transactional(readOnly = true)
    public Cliente buscarPorTelefono(Long telefono) {
        return clienteRepo.findByTelefono(telefono);
    }
 */
    @Override
    @Transactional
    public void guardar(Cliente cliente) {

        clienteRepo.save(cliente);
    }

    @Override
    @Transactional
    public void eliminar(Integer id) {
        clienteRepo.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Integer contarClientes() {
        return clienteRepo.contarClientes();
    }
/* 
    @Override
    @Transactional(readOnly = true)
    public Cliente buscarPorDniCuit(String dniCuit) {
        return clienteRepo.findByDniCuit(dniCuit);
    } */
}
