package com.demo.crud_jwt_security.controller;

import com.demo.crud_jwt_security.security.JwtUtils;
import com.demo.crud_jwt_security.dto.LoginRequest;
import com.demo.crud_jwt_security.dto.LoginResponse;
import com.demo.crud_jwt_security.model.Usuario;
import com.demo.crud_jwt_security.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public AuthController(UsuarioRepository usuarioRepository, JwtUtils jwtUtils, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        // Buscar usuario por username
        Usuario usuario = usuarioRepository.findByUsername(loginRequest.getUsername()).orElse(null);

        if(usuario == null || !passwordEncoder.matches(loginRequest.getPassword(), usuario.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciale inválidas");
        }

        // Generar token JWT
        String token = "Bearer " + jwtUtils.generateToken(usuario);

        return ResponseEntity.ok(new LoginResponse(token));
    }


}
