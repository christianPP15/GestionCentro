package com.clases.dam.gestion.salesianos.Curso;

import com.clases.dam.gestion.salesianos.Servicios.BaseServiceImpl;
import com.clases.dam.gestion.salesianos.Titulo.Titulo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CursoServicio extends BaseServiceImpl<Curso,Long,CursoRepository> {

    public CursoServicio(CursoRepository repo) {
        super(repo);
    }
    @Override
    public List<Curso> findAll() {
        return this.repositorio.findAllJoin();
    }

    public Optional<Curso> findFirstBynombre(String nombre, Titulo titulo) {
        return repositorio.findFirstBynombre(nombre,titulo);
    }
}
