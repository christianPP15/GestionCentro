package com.clases.dam.gestion.salesianos.SolicitudAmpliacionMatricula;

import com.clases.dam.gestion.salesianos.Alumno.Alumno;
import com.clases.dam.gestion.salesianos.Asignatura.Asignatura;
import com.clases.dam.gestion.salesianos.SituacionExcepcional.SituacionExcepcional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SolicitudAmpliacionMatriculaRepository extends JpaRepository<SolicitudAmpliacionMatricula,Long> {

    @Query("Select e From SolicitudAmpliacionMatricula e Where e.asignatura= :ASIGNATURA and e.alumno= :AlUMNO")
    public Optional<SolicitudAmpliacionMatricula> buscarExistencia(@Param("ASIGNATURA") Asignatura Asig, @Param("AlUMNO") Alumno alumno);

    @Query("Select e From SolicitudAmpliacionMatricula e Where e.asignatura= :ASIGNATURA and e.alumno= :AlUMNO and e.estado=true")
    public Optional<SolicitudAmpliacionMatricula> buscarExistenciaConfirmadas(@Param("ASIGNATURA") Asignatura Asig, @Param("AlUMNO") Alumno alumno);

    @Query("Select e From SolicitudAmpliacionMatricula e Where e.asignatura= :ASIGNATURA and e.alumno= :AlUMNO and e.estado=false")
    public Optional<SolicitudAmpliacionMatricula> buscarExistenciaPendientes(@Param("ASIGNATURA") Asignatura Asig, @Param("AlUMNO") Alumno alumno);
}
