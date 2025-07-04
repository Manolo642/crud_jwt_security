package com.demo.crud_jwt_security.service;

import com.demo.crud_jwt_security.model.Usuario;
import com.demo.crud_jwt_security.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Optional<Usuario> findByUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    public void eliminarUsuario(Long id){
        usuarioRepository.deleteById(id);
    }

    public List<Usuario> findAll(){
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> findById(Long id){
        return usuarioRepository.findById(id);
    }

    public Usuario guardar(Usuario usuario){
        return usuarioRepository.save(usuario);
    }


}
