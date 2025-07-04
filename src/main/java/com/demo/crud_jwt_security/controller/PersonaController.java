package com.demo.crud_jwt_security.controller;


import com.demo.crud_jwt_security.model.Persona;
import com.demo.crud_jwt_security.service.PersonaService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/personas")
public class PersonaController {


    private final PersonaService personaService;

    public PersonaController(PersonaService personaService) {
        this.personaService = personaService;
    }


    @GetMapping("/listar")
    public List<Persona> listar() {

        return personaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Persona> buscarPorId(@PathVariable Long id) {
        return personaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Persona> agregar(@RequestBody Persona persona) {
        return ResponseEntity.ok(personaService.guardar(persona));
    }


    @PutMapping("/{id}")
    public ResponseEntity<Persona> actualizar(@PathVariable Long id, @RequestBody Persona persona) {
        return personaService.obtenerPorId(id).map(p -> {
            p.setNombre(persona.getNombre());
            p.setEmail(persona.getEmail());
            return ResponseEntity.ok(personaService.guardar(p));
        }).orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (personaService.obtenerPorId(id).isPresent()) {
            personaService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }


}
