package com.clases.dam.gestion.salesianos.Formularios;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor  @Data @AllArgsConstructor
public class InformacionRechazoAceptacion {
    private String mensaje;

    private Long idUsuario;

    private Long idAsignatura;

    private boolean aceptado;
}
