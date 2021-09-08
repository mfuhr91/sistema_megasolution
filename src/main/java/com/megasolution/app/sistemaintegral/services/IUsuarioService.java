package com.megasolution.app.sistemaintegral.services;

import java.util.List;

import com.megasolution.app.sistemaintegral.models.entities.Usuario;

public interface IUsuarioService {
    
    public List<Usuario> buscarTodos();

    public Usuario buscarPorId(Integer id);

    public Usuario buscarPorNombreUsuario(String nombreUsuario);

    public void guardar(Usuario usuario);

    public void eliminar(Integer id);
    
}
