package com.clases.dam.gestion.salesianos.SituacionExcepcional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class SituacionExcepcionald implements Serializable {

    private static final long serialVersionUID = 1L;

    private long asignatura_id;

    private long alumno_id;


}
