package com.demo.crud_jwt_security.repository;

import com.demo.crud_jwt_security.model.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonaRepository extends JpaRepository<Persona, Long> {



}
