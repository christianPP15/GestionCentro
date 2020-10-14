package com.clases.dam.gestion.salesianos.Alumno;

import com.clases.dam.gestion.salesianos.Servicios.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlumnoServicio extends BaseServiceImpl<Alumno,Long,AlumnoRepository> {

    public AlumnoServicio(AlumnoRepository repo) {
        super(repo);
    }

    public List<Alumno> listaAlumnos(){
    return this.repositorio.alumnos();
    }
}
