package com.clases.dam.gestion.salesianos.Asignatura;

import com.clases.dam.gestion.salesianos.Curso.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AsignaturaRepository extends JpaRepository<Asignatura,Long> {
    Optional<Asignatura> findFirstBynombreAsignatura(String nombre);
}
