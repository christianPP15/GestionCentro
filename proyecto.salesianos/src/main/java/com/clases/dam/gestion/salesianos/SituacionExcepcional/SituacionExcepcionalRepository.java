package com.clases.dam.gestion.salesianos.SituacionExcepcional;

import com.clases.dam.gestion.salesianos.Alumno.Alumno;
import com.clases.dam.gestion.salesianos.Asignatura.Asignatura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SituacionExcepcionalRepository extends JpaRepository<SituacionExcepcional,Long> {
    @Query("Select e From SituacionExcepcional e Where e.asignatura= :ASIGNATURA and e.alumno= :AlUMNO")
    public Optional<SituacionExcepcional> buscarExistencia(@Param("ASIGNATURA") Asignatura Asig, @Param("AlUMNO") Alumno alumno);
    @Query("Select e From SituacionExcepcional e Where e.estado=false")
    public List<SituacionExcepcional> buscarExistenciaNoTerminadas();
    @Query("Select e From SituacionExcepcional e Where e.asignatura= :ASIGNATURA and e.alumno= :AlUMNO and e.estado=false")
    public Optional<SituacionExcepcional> buscarExistenciaTerminada(@Param("ASIGNATURA") Asignatura Asig, @Param("AlUMNO") Alumno alumno);
}
