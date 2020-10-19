package com.clases.dam.gestion.salesianos.Titulo;

import com.clases.dam.gestion.salesianos.Servicios.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class TituloServicio extends BaseServiceImpl<Titulo,Long,TituloRepository> {

    public TituloServicio(TituloRepository repo) {
        super(repo);
    }
}
