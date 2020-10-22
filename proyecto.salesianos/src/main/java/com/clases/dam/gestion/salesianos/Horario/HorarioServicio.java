package com.clases.dam.gestion.salesianos.Horario;

import com.clases.dam.gestion.salesianos.Asignatura.Asignatura;
import com.clases.dam.gestion.salesianos.Servicios.BaseServiceImpl;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HorarioServicio extends BaseServiceImpl<Horario,Long,HorarioRepository> {
    public HorarioServicio(HorarioRepository repo) {
        super(repo);
    }


    public List<Horario> listaHorasActivas(Asignatura asignatura) {
        return repositorio.listaHorasActivas(asignatura);
    }
}
