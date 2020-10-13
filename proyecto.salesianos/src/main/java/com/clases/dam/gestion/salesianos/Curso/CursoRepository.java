package com.clases.dam.gestion.salesianos.Curso;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CursoRepository extends JpaRepository<Curso,Long> {

    @Query("select distinct c from Curso c join fetch c.titulos")
    List<Curso> findAllJoin();
}
