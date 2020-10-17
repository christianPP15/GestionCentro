package com.clases.dam.gestion.salesianos.Horario;

import com.clases.dam.gestion.salesianos.Servicios.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class HorarioServicio extends BaseServiceImpl<Horario,Long,HorarioRepository> {
    public HorarioServicio(HorarioRepository repo) {
        super(repo);
    }
}
