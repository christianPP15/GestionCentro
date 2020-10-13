package com.clases.dam.gestion.salesianos.Curso;

import com.clases.dam.gestion.salesianos.Servicios.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CursoServicio extends BaseServiceImpl<Curso,Long,CursoRepository> {

    public CursoServicio(CursoRepository repo) {
        super(repo);
    }
    @Override
    public List<Curso> findAll() {
        return this.repositorio.findAllJoin();
    }
}
