package com.clases.dam.gestion.salesianos.Curso;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CursoRepository extends JpaRepository<Curso,Long> {

    @Query("select distinct c from Curso c join fetch c.titulos")
    List<Curso> findAllJoin();

    Optional<Curso> findFirstBynombre(String nombre);
}
