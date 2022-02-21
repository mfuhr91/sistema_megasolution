package com.megasolution.app.sistemaintegral.services;

import com.megasolution.app.sistemaintegral.models.entities.Usuario;
import com.megasolution.app.sistemaintegral.models.repositories.IUsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioService {

    private IUsuarioRepository usuarioRepo;

    public UsuarioService(IUsuarioRepository usuarioRepo) {
        this.usuarioRepo = usuarioRepo;
    }

    @Transactional(readOnly = true)
    public List<Usuario> buscarTodos() {
        return usuarioRepo.findAll();
    }

    @Transactional(readOnly = true)
    public Usuario buscarPorId(Integer id) {
        return usuarioRepo.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public Usuario buscarPorNombreUsuario(String nombreUsuario) {
        return usuarioRepo.findByNombreUsuario(nombreUsuario);
    }

    @Transactional
    public void guardar(Usuario usuario) {
        usuarioRepo.save(usuario);
    }

    @Transactional
    public void eliminar(Integer id) {
        usuarioRepo.deleteById(id);
    }


}
