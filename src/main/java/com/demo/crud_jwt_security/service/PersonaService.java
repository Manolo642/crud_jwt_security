package com.demo.crud_jwt_security.service;

import com.demo.crud_jwt_security.model.Persona;
import com.demo.crud_jwt_security.repository.PersonaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonaService  {


    private PersonaRepository personaRepository;

    public PersonaService(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    public List<Persona> findAll() {
        return personaRepository.findAll();
    }

    public Persona guardar(Persona persona) {
        return personaRepository.save(persona);
    }

    public void deleteById(Long id) {
        personaRepository.deleteById(id);
    }

    public Optional<Persona> obtenerPorId(Long id) {
        return personaRepository.findById(id);
    }


}
