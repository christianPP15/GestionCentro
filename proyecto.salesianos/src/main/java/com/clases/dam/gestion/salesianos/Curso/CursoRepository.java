package com.clases.dam.gestion.salesianos.Curso;

import com.clases.dam.gestion.salesianos.Alumno.Alumno;
import com.clases.dam.gestion.salesianos.Asignatura.Asignatura;
import com.clases.dam.gestion.salesianos.Titulo.Titulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CursoRepository extends JpaRepository<Curso,Long> {

    @Query("select distinct c from Curso c join fetch c.titulos")
    List<Curso> findAllJoin();

    @Query("select e from Curso e Where e.nombre= :NOMBRE and e.titulos= :TITULO")
    Optional<Curso> findFirstBynombre(@Param("NOMBRE") String nombre, @Param("TITULO") Titulo titulo);
}
