package com.clases.dam.gestion.salesianos.Alumno;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AlumnoRepository extends JpaRepository<Alumno,Long> {
    @Query("select c from Usuario c where TYPE(c) = Alumno")
    public List<Alumno> alumnos();
}
