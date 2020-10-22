package com.clases.dam.gestion.salesianos.Asignatura;

import com.clases.dam.gestion.salesianos.Curso.Curso;
import com.clases.dam.gestion.salesianos.Servicios.BaseServiceImpl;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AsignaturaServicio extends BaseServiceImpl<Asignatura,Long,AsignaturaRepository> {
    public AsignaturaServicio(AsignaturaRepository repo) {
        super(repo);
    }

    public Optional<Asignatura> findFirstBynombre(String nombre, Curso curso) {
        return repositorio.findFirstBynombre(nombre,curso);
    }

    public List<Asignatura> encontrarListaAsignaturasActiva(Curso curso) {
        return repositorio.encontrarListaAsignaturasActiva(curso);
    }
}
