package com.clases.dam.gestion.salesianos.Titulo;

import com.clases.dam.gestion.salesianos.Curso.Curso;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor @Data @AllArgsConstructor @Builder
public class Titulo {
    @Id @GeneratedValue
    private long id;

    private String nombre;
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy="titulos", fetch =FetchType.EAGER,cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Curso> cursos=new ArrayList<>();

    private boolean activo=true;

    public void addCurso(Curso a) {
        this.cursos.add(a);
        a.setTitulos(this);
    }

    public void removeCurso(Curso a) {
        this.cursos.remove(a);
        a.setTitulos(null);
    }

    public Titulo(String nombre) {
        this.nombre = nombre;
    }
}
