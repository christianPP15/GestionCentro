package com.clases.dam.gestion.salesianos.Titulo;

import com.clases.dam.gestion.salesianos.Curso.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TituloRepository extends JpaRepository<Titulo,Long> {
    Optional<Titulo> findFirstBynombre(String nombre);
}
