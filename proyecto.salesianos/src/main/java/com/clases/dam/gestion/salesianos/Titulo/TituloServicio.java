package com.clases.dam.gestion.salesianos.Titulo;

import com.clases.dam.gestion.salesianos.Curso.Curso;
import com.clases.dam.gestion.salesianos.Servicios.BaseServiceImpl;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TituloServicio extends BaseServiceImpl<Titulo,Long,TituloRepository> {

    public TituloServicio(TituloRepository repo) {
        super(repo);
    }

    public Optional<Titulo> findFirstBynombre(String nombre) {
        return repositorio.findFirstBynombre(nombre);
    }


    public List<Titulo> listaTituloActivos() {
        return repositorio.listaTituloActivos();
    }
}
