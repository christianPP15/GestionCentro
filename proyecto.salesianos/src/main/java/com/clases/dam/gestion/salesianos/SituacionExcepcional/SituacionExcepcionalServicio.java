package com.clases.dam.gestion.salesianos.SituacionExcepcional;

import com.clases.dam.gestion.salesianos.Alumno.Alumno;
import com.clases.dam.gestion.salesianos.Asignatura.Asignatura;
import com.clases.dam.gestion.salesianos.Servicios.BaseServiceImpl;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SituacionExcepcionalServicio extends BaseServiceImpl<SituacionExcepcional,Long,SituacionExcepcionalRepository> {

    public SituacionExcepcionalServicio(SituacionExcepcionalRepository repo) {
        super(repo);
    }




    public Optional<SituacionExcepcional> buscarExistencia(Asignatura Asig, Alumno alumno) {
        return this.repositorio.buscarExistencia(Asig, alumno);
    }
}
