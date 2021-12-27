package com.megasolution.app.sistemaintegral.services;

import java.io.IOException;
import java.util.List;

import com.megasolution.app.sistemaintegral.models.ClienteModel;
import com.megasolution.app.sistemaintegral.models.entities.Cliente;
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

    public Model enviarModelo(ClienteModel clienteModel, Model model) throws IOException;

    Model enviarModeloErrorDniCuit(ClienteModel clienteModel, Model model);
}
