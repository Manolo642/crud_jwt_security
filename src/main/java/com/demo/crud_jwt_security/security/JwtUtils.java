package com.demo.crud_jwt_security.security;


import com.demo.crud_jwt_security.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtils {

    @Value("${app.jwt.secret}")
    private String secret;

    @Value("${app.jwt.expiration}")
    private Long expirationMs;

    private SecretKey secretKey;


    /*
    Keys.hmacShaKeyFor(...) crea una clave segura para el algoritmo HS512.
    Esto se hace una vez al iniciar la app, por eso usamos @PostConstruct.
    */
    @PostConstruct
    public void init() {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    // ✅ Generar token con claims adicionales como rol
    public String generateToken(Usuario usuario) {
        Map<String, Object> claims = new HashMap<> ();
                claims.put("id", usuario.getId());
                claims.put("username", usuario.getUsername());
                claims.put("rol",usuario.getRol());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(usuario.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    // ✅ Obtener claims desde el token
    public Claims getAllClaimsFromToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // ✅ Obtener username (subject)
    public String getUsernameFromToken(String token){
        return getAllClaimsFromToken(token).getSubject();
    }

    // ✅ Validar token (firma, expiración, formato)
    public boolean validateToken(String token){
        try {
            Claims claims = getAllClaimsFromToken(token);
            return true;
        }catch (JwtException | IllegalArgumentException e){
            System.err.println("Invalid token" + e.getMessage());
            return false;
        }
    }






}
