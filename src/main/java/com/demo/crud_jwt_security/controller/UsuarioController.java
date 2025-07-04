package com.demo.crud_jwt_security.controller;

import com.demo.crud_jwt_security.model.Usuario;
import com.demo.crud_jwt_security.repository.UsuarioRepository;
import com.demo.crud_jwt_security.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioRepository usuarioRepository;

    public UsuarioController(UsuarioService usuarioService, PasswordEncoder passwordEncoder, UsuarioRepository usuarioRepository) {
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
        this.usuarioRepository = usuarioRepository;
    }


    @GetMapping("/listar")
    public List<Usuario> listar(){
        return usuarioService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerById(@PathVariable Long id){
        return usuarioService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/crear")
    public ResponseEntity<Usuario> crear(@RequestBody Usuario usuario){
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return ResponseEntity.ok(usuarioService.guardar(usuario));
    }


    /*hasRole('ADMIN'): permite si el usuario tiene rol ROLE_ADMIN
    #username == authentication.name: permite si el parámetro username es igual al
     username del token (que se guarda en authentication.name)*/
    @PreAuthorize("hasRole('ADMIN') or #username == authentication.name")
    @GetMapping("/user/{username}")
    public Usuario getUserByUsername(@PathVariable String username){
        return usuarioRepository.findByUsername(username).orElseThrow();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Usuario> eliminar(@PathVariable Long id){
        if(usuarioService.findById(id).isPresent()){
            usuarioService.eliminarUsuario(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizar(@PathVariable Long id, @RequestBody Usuario usuarioActualizado) {
        return usuarioService.findById(id).map(usuario -> {
            usuario.setUsername(usuarioActualizado.getUsername());
            usuario.setPassword(usuarioActualizado.getPassword());
            usuario.setRol(usuarioActualizado.getRol());
            return ResponseEntity.ok(usuarioService.guardar(usuario));
        }).orElse(ResponseEntity.notFound().build());
    }

}
