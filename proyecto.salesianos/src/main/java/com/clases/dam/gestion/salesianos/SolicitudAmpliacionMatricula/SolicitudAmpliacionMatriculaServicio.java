package com.clases.dam.gestion.salesianos.SolicitudAmpliacionMatricula;

import com.clases.dam.gestion.salesianos.Servicios.BaseServiceImpl;

public class SolicitudAmpliacionMatriculaServicio extends
        BaseServiceImpl<SolicitudAmpliacionMatricula, Long, SolicitudAmpliacionMatriculaRepository> {

    public SolicitudAmpliacionMatriculaServicio(SolicitudAmpliacionMatriculaRepository repo) {
        super(repo);
    }

}
