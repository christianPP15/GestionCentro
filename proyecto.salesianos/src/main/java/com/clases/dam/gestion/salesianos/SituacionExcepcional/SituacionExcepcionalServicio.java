package com.clases.dam.gestion.salesianos.SituacionExcepcional;

import com.clases.dam.gestion.salesianos.Servicios.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class SituacionExcepcionalServicio extends BaseServiceImpl<SituacionExcepcional,Long,SituacionExcepcionalRepository> {

    public SituacionExcepcionalServicio(SituacionExcepcionalRepository repo) {
        super(repo);
    }
}
