package com.megasolution.app.sistemaintegral.services;

import java.util.List;

import com.megasolution.app.sistemaintegral.models.entities.Mensaje;

public interface IMensajeService {

    public List<Mensaje> buscarTodos();

    public Mensaje buscarPorId(Integer id);

    public void actualizar(Mensaje mensaje);

    public String buscarAvisoDelMensaje(Integer id);

}
