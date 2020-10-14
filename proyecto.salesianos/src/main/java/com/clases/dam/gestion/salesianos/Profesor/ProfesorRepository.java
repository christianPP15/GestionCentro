package com.clases.dam.gestion.salesianos.Profesor;

import com.clases.dam.gestion.salesianos.Alumno.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProfesorRepository extends JpaRepository<Profesor,Long> {

    @Query("select c from Usuario c where TYPE(c) = Profesor")
    public List<Profesor> listaProfesores();
}
