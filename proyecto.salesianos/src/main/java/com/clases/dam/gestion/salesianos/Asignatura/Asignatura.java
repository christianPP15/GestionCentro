package com.clases.dam.gestion.salesianos.Asignatura;

import com.clases.dam.gestion.salesianos.Curso.Curso;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@NoArgsConstructor@AllArgsConstructor@Builder@Data
public class Asignatura {
    @Id
    @GeneratedValue
    private  long id;

    private String nombreAsignatura;

    @ManyToOne
    private Curso curso;

    //Introducir horario

}
