package com.clases.dam.gestion.salesianos.Profesor;

import com.clases.dam.gestion.salesianos.Servicios.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfesorServicio extends BaseServiceImpl<Profesor,Long,ProfesorRepository> {
    public ProfesorServicio(ProfesorRepository repo) {
        super(repo);
        }
    public List<Profesor> listaProfesores( ){
    return this.repositorio.listaProfesores();
    }
}
