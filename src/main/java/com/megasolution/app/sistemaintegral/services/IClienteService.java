package com.megasolution.app.sistemaintegral.services;

import java.util.List;

import com.megasolution.app.sistemaintegral.models.ClienteModel;
import com.megasolution.app.sistemaintegral.models.entities.Cliente;
import com.megasolution.app.sistemaintegral.models.entities.Sector;
import com.megasolution.app.sistemaintegral.models.entities.Servicio;
import com.megasolution.app.sistemaintegral.utils.Estado;
import org.springframework.ui.Model;

public interface IClienteService {

    public List<Cliente> buscarTodos();

    public List<Cliente> buscar100();

    public Cliente buscarPorId(Integer id);

    public void guardar(Cliente cliente);

    public void eliminar(Integer id);

    public Integer contarClientes();

    public Cliente buscarPorDniCuit(Long dniCuit);

    public List<Cliente> buscarPorParametro(String param);

    public Model enviarModelo(ClienteModel clienteModel, Model model);

    Model enviarModeloErrorDniCuit(ClienteModel clienteModel, Model model);
}
