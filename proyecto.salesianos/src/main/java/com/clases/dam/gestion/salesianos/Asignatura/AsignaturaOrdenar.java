package com.clases.dam.gestion.salesianos.Asignatura;

import java.util.Comparator;

public class AsignaturaOrdenar implements Comparator<Asignatura> {
    @Override
    public int compare(Asignatura o1, Asignatura o2) {
        return o1.getNombreAsignatura().compareTo(o2.getNombreAsignatura());
    }
}
