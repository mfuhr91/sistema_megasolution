package com.megasolution.app.sistemaintegral.usuarios.models.repositories;

import com.megasolution.app.sistemaintegral.usuarios.models.entities.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Integer> {

    public Usuario findByNombreUsuario(String nombreUsuario);
}
