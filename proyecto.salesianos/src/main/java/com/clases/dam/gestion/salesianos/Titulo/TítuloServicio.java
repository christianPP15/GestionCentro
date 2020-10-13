package com.clases.dam.gestion.salesianos.Titulo;

import com.clases.dam.gestion.salesianos.Servicios.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class TítuloServicio extends BaseServiceImpl<Titulo,Long,TituloRepository> {

    public TítuloServicio(TituloRepository repo) {
        super(repo);
    }
}
