package com.clases.dam.gestion.salesianos.Alumno;

import com.clases.dam.gestion.salesianos.Servicios.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class AlumnoServicio extends BaseServiceImpl<Alumno,Long,AlumnoRepository> {

    public AlumnoServicio(AlumnoRepository repo) {
        super(repo);
    }
}
