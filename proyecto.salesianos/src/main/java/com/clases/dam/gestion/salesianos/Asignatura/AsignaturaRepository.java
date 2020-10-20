package com.clases.dam.gestion.salesianos.Asignatura;

import com.clases.dam.gestion.salesianos.Curso.Curso;
import com.clases.dam.gestion.salesianos.Titulo.Titulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AsignaturaRepository extends JpaRepository<Asignatura,Long> {


    @Query("select e from Asignatura e Where e.nombreAsignatura= :NOMBRE and e.curso= :CURSO")
    Optional<Asignatura> findFirstBynombre(@Param("NOMBRE") String nombre, @Param("CURSO") Curso curso);
}
