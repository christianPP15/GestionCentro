package com.clases.dam.gestion.salesianos.Curso;

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
    private String nombreCurso;

    @ManyToOne
    private Titulo titulos;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy="curso")
    private List<Asignatura> asignatura = new ArrayList<>();

    public void addAsignatura(Asignatura a) {
        this.asignatura.add(a);
        a.setCurso(this);
    }

    public void removeAsignatura(Asignatura a) {
        this.asignatura.remove(a);
        a.setCurso(null);
    }

    public Curso(String nombreCurso) {
        this.nombreCurso = nombreCurso;
    }
}
