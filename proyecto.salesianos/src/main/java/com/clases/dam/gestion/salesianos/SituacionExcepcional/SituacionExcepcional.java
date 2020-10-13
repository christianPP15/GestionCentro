package com.clases.dam.gestion.salesianos.SituacionExcepcional;

import com.clases.dam.gestion.salesianos.Alumno.Alumno;
import com.clases.dam.gestion.salesianos.Asignatura.Asignatura;
import com.clases.dam.gestion.salesianos.SolicitudAmpliacionMatricula.SolicitudAmpliacionMatriculaId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.File;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SituacionExcepcional {

    @EmbeddedId
    private SituacionExcepcionald id;

    @ManyToOne
    @MapsId("asignatura_id")
    @JoinColumn(name = "asignatura_id")
    private Asignatura asignatura;

    @ManyToOne
    @MapsId("alumno_id")
    @JoinColumn(name = "alumno_id")
    private Alumno alumno;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime fechaSolicitud;

    private String tipo;

    private File adjunto;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime fechaResolucion;

    private boolean estado;


}
