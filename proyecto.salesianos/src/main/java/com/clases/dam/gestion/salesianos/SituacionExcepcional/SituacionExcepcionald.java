package com.clases.dam.gestion.salesianos.SituacionExcepcional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class SituacionExcepcionald implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id @GeneratedValue
    private long id;

    private long asignatura_id;

    private long alumno_id;

    public SituacionExcepcionald(long asignatura_id, long alumno_id) {
        this.asignatura_id = asignatura_id;
        this.alumno_id = alumno_id;
    }
}
