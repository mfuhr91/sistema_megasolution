package com.megasolution.app.sistemaintegral.services;

import java.io.IOException;
import java.util.*;

import com.megasolution.app.sistemaintegral.models.ClienteModel;
import com.megasolution.app.sistemaintegral.models.Paises;
import com.megasolution.app.sistemaintegral.models.Provincias;
import com.megasolution.app.sistemaintegral.models.entities.*;
import com.megasolution.app.sistemaintegral.models.repositories.IClienteRepository;

import com.megasolution.app.sistemaintegral.utils.Constantes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;

@Service
public class ClienteServiceImpl implements IClienteService {

    @Autowired
    private IClienteRepository clienteRepo;

    private final Logger log = LoggerFactory.getLogger(ClienteServiceImpl.class);

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
    @Override
    @Transactional
    public void guardar(Cliente cliente) {
        log.info("cliente guardado");
        clienteRepo.save(cliente);
    }

    @Override
    @Transactional
    public void eliminar(Integer id) {
        log.info("cliente eliminado");
        clienteRepo.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Integer contarClientes() {
        return clienteRepo.contarClientes();
    }

    @Override
    @Transactional(readOnly = true)
    public Cliente buscarPorDniCuit(Long dniCuit) {
        return clienteRepo.findByDniCuit(dniCuit);
    }

    @Override
    public List<Cliente> buscarPorParametro(String param) {
        param = param.toLowerCase();
        String[] params = param.split(" ");
        Set<Cliente> clientes = new LinkedHashSet<>();
        Arrays.stream(params).forEach(prm -> clientes.addAll(this.clienteRepo.findByParam(prm)) );
        return List.copyOf(clientes);
    }

    @Override
    public List<Cliente> buscar100() {
        return this.clienteRepo.find100();
    }

    @Override
    public Model enviarModelo(ClienteModel clienteModel, Model model) throws IOException {

        if(!ObjectUtils.isEmpty(clienteModel.getCliente()) && !ObjectUtils.isEmpty(clienteModel.getCliente().getProvincia())) {
            clienteModel.setLocalidades(Provincias.getCiudadesDeProvincia(clienteModel.getCliente().getProvincia()));
        } else {
            clienteModel.setLocalidades(Provincias.getCiudadesDeProvincia(Provincias.TIERRA_DEL_FUEGO.getNombre()));
        }
        clienteModel.setProvincias(Provincias.getProvincias());
        clienteModel.setPaises(Paises.ARGENTINA);

        model.addAttribute(Constantes.ACTIVE, Constantes.CLIENTES);

        if(ObjectUtils.isEmpty(clienteModel.getCliente())) {
            model.addAttribute(Constantes.TITULO, Constantes.TITULO_CLIENTES);
            model.addAttribute(Constantes.CLIENTES, clienteModel.getClientes());
            return model;
        }
        if(clienteModel.getCliente().getId() == null){
            model.addAttribute(Constantes.TITULO, Constantes.TITULO_AGREGAR_CLIENTE);
        }else{
            model.addAttribute(Constantes.TITULO, Constantes.TITULO_EDITAR_CLIENTE);
        }

        model.addAttribute(Constantes.CLIENTE, clienteModel.getCliente());
        model.addAttribute(Constantes.LOCALIDADES, clienteModel.getLocalidades());
        model.addAttribute(Constantes.PROVINCIAS, clienteModel.getProvincias());
        model.addAttribute(Constantes.PAISES, clienteModel.getPaises());

        return model;
    }

    @Override
    public Model enviarModeloErrorDniCuit(ClienteModel clienteModel, Model model) {
        model.addAttribute(Constantes.ALERT_DANGER_DNI_CUIT, Constantes.ESPACIO_ALERT_DANGER);
        model.addAttribute(Constantes.ERROR_DNI_CUIT, Constantes.MSJ_DNI_CUIT_EXISTENTE);
        return model;
    }

}
