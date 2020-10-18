package com.clases.dam.gestion.salesianos.Formularios;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor @AllArgsConstructor @Data
public class NuevoAlumnoFormulario {

    private String nombre;
    private String apellidos;
    private String email;
    private Long id;
}
