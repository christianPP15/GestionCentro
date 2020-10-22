package com.clases.dam.gestion.salesianos.SolicitudAmpliacionMatricula;

import com.clases.dam.gestion.salesianos.Alumno.Alumno;
import com.clases.dam.gestion.salesianos.Asignatura.Asignatura;
import com.clases.dam.gestion.salesianos.Servicios.BaseServiceImpl;
import com.clases.dam.gestion.salesianos.SituacionExcepcional.SituacionExcepcional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SolicitudAmpliacionMatriculaServicio extends
        BaseServiceImpl<SolicitudAmpliacionMatricula, Long, SolicitudAmpliacionMatriculaRepository> {

    public SolicitudAmpliacionMatriculaServicio(SolicitudAmpliacionMatriculaRepository repo) {
        super(repo);
    }


    public Optional<SolicitudAmpliacionMatricula> buscarExistencia(Asignatura Asig, Alumno alumno) {
        return repositorio.buscarExistencia(Asig, alumno);
    }

    @Query("Select e From SolicitudAmpliacionMatricula e Where e.asignatura= :ASIGNATURA and e.alumno= :AlUMNO e.estado=true")
    public Optional<SolicitudAmpliacionMatricula> buscarExistenciaConfirmadas(Asignatura Asig, Alumno alumno) {
        return repositorio.buscarExistenciaConfirmadas(Asig, alumno);
    }

    @Query("Select e From SolicitudAmpliacionMatricula e Where e.asignatura= :ASIGNATURA and e.alumno= :AlUMNO e.estado=false")
    public Optional<SolicitudAmpliacionMatricula> buscarExistenciaPendientes(Asignatura Asig, Alumno alumno) {
        return repositorio.buscarExistenciaPendientes(Asig, alumno);
    }

    @Query("Select e From SolicitudAmpliacionMatricula e Where e.estado=false")
    public List<SolicitudAmpliacionMatricula> buscarExistenciasNoTerminadas() {
        return repositorio.buscarExistenciasNoTerminadas();
    }
}
