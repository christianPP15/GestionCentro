package com.clases.dam.gestion.salesianos.Asignatura;

import com.clases.dam.gestion.salesianos.Servicios.BaseServiceImpl;

public class AsignaturaServicio extends BaseServiceImpl<Asignatura,Long,AsignaturaRepository> {
    public AsignaturaServicio(AsignaturaRepository repo) {
        super(repo);
    }

}
