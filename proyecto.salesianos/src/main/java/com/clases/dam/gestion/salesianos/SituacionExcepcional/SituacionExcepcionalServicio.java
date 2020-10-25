package com.clases.dam.gestion.salesianos.SituacionExcepcional;

import com.clases.dam.gestion.salesianos.Alumno.Alumno;
import com.clases.dam.gestion.salesianos.Asignatura.Asignatura;
import com.clases.dam.gestion.salesianos.Servicios.BaseServiceImpl;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SituacionExcepcionalServicio extends BaseServiceImpl<SituacionExcepcional,Long,SituacionExcepcionalRepository> {

    public SituacionExcepcionalServicio(SituacionExcepcionalRepository repo) {
        super(repo);
    }

    public List<SituacionExcepcional> buscarExistenciaNoTerminadas() {
        return this.repositorio.buscarExistenciaNoTerminadas();
    }


    public Optional<SituacionExcepcional> buscarExistencia(Asignatura Asig, Alumno alumno) {
        return this.repositorio.buscarExistencia(Asig, alumno);
    }

    @Query("Select e From SituacionExcepcional e Where e.asignatura= :ASIGNATURA and e.alumno= :AlUMNO and e.estado=true and e.tipo=false")
    public Optional<SituacionExcepcional> buscarExistenciaTerminadaConvalidacion(Asignatura Asig, Alumno alumno) {
        return repositorio.buscarExistenciaTerminadaConvalidacion(Asig, alumno);
    }

    @Query("Select e From SituacionExcepcional e Where e.asignatura= :ASIGNATURA and e.alumno= :AlUMNO and e.estado=true and e.tipo=true")
    public Optional<SituacionExcepcional> buscarExistenciaTerminadaExcepcion(Asignatura Asig, Alumno alumno) {
        return repositorio.buscarExistenciaTerminadaExcepcion(Asig, alumno);
    }


    public Optional<SituacionExcepcional> buscarExistenciaTerminada(Asignatura Asig, Alumno alumno) {
        return repositorio.buscarExistenciaTerminada(Asig, alumno);
    }
}
