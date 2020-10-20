package com.clases.dam.gestion.salesianos.Asignatura;

import com.clases.dam.gestion.salesianos.Servicios.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AsignaturaServicio extends BaseServiceImpl<Asignatura,Long,AsignaturaRepository> {
    public AsignaturaServicio(AsignaturaRepository repo) {
        super(repo);
    }

    public Optional<Asignatura> findFirstBynombreAsignatura(String nombre) {
        return repositorio.findFirstBynombreAsignatura(nombre);
    }
}
