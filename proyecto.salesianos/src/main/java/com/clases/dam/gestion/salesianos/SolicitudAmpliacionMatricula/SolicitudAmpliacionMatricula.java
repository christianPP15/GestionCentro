package com.clases.dam.gestion.salesianos.SolicitudAmpliacionMatricula;

import com.clases.dam.gestion.salesianos.Alumno.Alumno;
import com.clases.dam.gestion.salesianos.Asignatura.Asignatura;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class SolicitudAmpliacionMatricula {

    @EmbeddedId
    private SolicitudAmpliacionMatriculaId id;

    @ManyToOne
    @MapsId("asignatura_id")
    @JoinColumn(name = "asignatura_id")
    private Asignatura asignatura;

    @ManyToOne
    @MapsId("alumno_id")
    @JoinColumn(name = "alumno_id")
    private Alumno alumno;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaEntrega;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaResolucion;

    private boolean estado=false;

    public SolicitudAmpliacionMatricula(SolicitudAmpliacionMatriculaId id, Asignatura asignatura, Alumno alumno, LocalDate fechaEntrega) {
        this.id = id;
        this.asignatura = asignatura;
        this.alumno = alumno;
        this.fechaEntrega = fechaEntrega;
    }
}
