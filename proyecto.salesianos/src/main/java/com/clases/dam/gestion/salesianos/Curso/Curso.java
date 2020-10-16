package com.clases.dam.gestion.salesianos.Curso;

import com.clases.dam.gestion.salesianos.Alumno.Alumno;
import com.clases.dam.gestion.salesianos.Asignatura.Asignatura;
import com.clases.dam.gestion.salesianos.Titulo.Titulo;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Curso {
    @Id @GeneratedValue
    private long id;
    private String nombre;

    @ManyToOne
    private Titulo titulos;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy="curso")
    private List<Asignatura> asignatura = new ArrayList<>();

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy="curso")
    private List<Alumno> alumnos = new ArrayList<>();

    public void addAsignatura(Asignatura a) {
        this.asignatura.add(a);
        a.setCurso(this);
    }

    public void removeAsignatura(Asignatura a) {
        this.asignatura.remove(a);
        a.setCurso(null);
    }

    public void addAlumno(Alumno a) {
        this.alumnos.add(a);
        a.setCurso(this);
    }

    public void removeAlumno(Asignatura a) {
        this.asignatura.remove(a);
        a.setCurso(null);
    }
    public Curso(String nombre) {
        this.nombre = nombre;
    }

    public Curso(String nombre, List<Asignatura> asignatura) {
        this.nombre = nombre;
        this.asignatura = asignatura;
    }
}
