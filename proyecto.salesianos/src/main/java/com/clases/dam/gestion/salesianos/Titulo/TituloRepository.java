package com.clases.dam.gestion.salesianos.Titulo;

import com.clases.dam.gestion.salesianos.Curso.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TituloRepository extends JpaRepository<Titulo,Long> {
    Optional<Titulo> findFirstBynombre(String nombre);

    @Query("Select e From Titulo e Where e.activo=true")
    List<Titulo> listaTituloActivos();


}
