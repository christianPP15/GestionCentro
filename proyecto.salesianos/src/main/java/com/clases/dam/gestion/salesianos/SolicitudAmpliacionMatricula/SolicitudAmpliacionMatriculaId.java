package com.clases.dam.gestion.salesianos.SolicitudAmpliacionMatricula;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class SolicitudAmpliacionMatriculaId implements Serializable {

    private static final long serialVersionUID = 1L;

    private long alumno_id;

    private long asignatura_id;
}
