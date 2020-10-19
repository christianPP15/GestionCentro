package com.clases.dam.gestion.salesianos.Horario;

import com.clases.dam.gestion.salesianos.Asignatura.Asignatura;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Horario {
    @Id
    @GeneratedValue
    private Long id;

    private String dia;

    private LocalTime horaComienzo;

    private LocalTime horaFinalizacion;
    @ManyToOne
    private Asignatura asignatura;

    public Horario(String dia, LocalTime horaComienzo,LocalTime horaFinalizacion) {
        this.dia = dia;
        this.horaComienzo = horaComienzo;
        this.horaFinalizacion = horaFinalizacion;
    }

    public Horario(String dia, LocalTime horaComienzo, Asignatura asignatura,LocalTime horaFinalizacion) {
        this.dia = dia;
        this.horaComienzo = horaComienzo;
        this.asignatura = asignatura;
        this.horaFinalizacion = horaFinalizacion;
    }
}
