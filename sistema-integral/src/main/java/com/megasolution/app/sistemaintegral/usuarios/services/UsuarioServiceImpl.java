package com.megasolution.app.sistemaintegral.usuarios.services;

import java.util.List;

import com.megasolution.app.sistemaintegral.usuarios.models.entities.Usuario;
import com.megasolution.app.sistemaintegral.usuarios.models.repositories.IUsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioServiceImpl implements IUsuarioService {
    
    @Autowired
    private IUsuarioRepository usuarioRepo;
    
    @Override
    @Transactional(readOnly = true)
    public List<Usuario> buscarTodos() {
        return usuarioRepo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario buscarPorId(Integer id) {
        return usuarioRepo.findById(id).orElse(null);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Usuario buscarPorNombreUsuario(String nombreUsuario) {
        return usuarioRepo.findByNombreUsuario(nombreUsuario);
    }

    @Override
    @Transactional
    public void guardar(Usuario usuario) {
        usuarioRepo.save(usuario);

    }

    @Override
    @Transactional
    public void eliminar(Integer id) {
        usuarioRepo.deleteById(id);
    }


}
