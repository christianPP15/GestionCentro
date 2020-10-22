package com.clases.dam.gestion.salesianos.Horario;

import com.clases.dam.gestion.salesianos.Asignatura.Asignatura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface HorarioRepository extends JpaRepository<Horario,Long> {
    @Query("Select e from Horario e Where e.asignatura=:ASIGNATURA and e.activo=true")
    List<Horario> listaHorasActivas(@Param("ASIGNATURA")Asignatura asignatura);
}
