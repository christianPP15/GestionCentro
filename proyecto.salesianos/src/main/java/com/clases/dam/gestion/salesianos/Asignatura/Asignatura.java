package com.clases.dam.gestion.salesianos.Asignatura;

import com.clases.dam.gestion.salesianos.Alumno.Alumno;
import com.clases.dam.gestion.salesianos.Curso.Curso;
import com.clases.dam.gestion.salesianos.Horario.Horario;
import lombok.*;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
@NoArgsConstructor @AllArgsConstructor @Builder
public class Asignatura {
    @Id
    @GeneratedValue
    private  long id;

    private String nombreAsignatura;

    @ManyToOne
    private Curso curso;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy="asignatura")
    private List<Horario> horario=new ArrayList<>();

    public void addHorario(Horario a) {
        this.horario.add(a);
        a.setAsignatura(this);
    }

    public void removeHorario(Horario a) {
        this.horario.remove(a);
        a.setAsignatura(null);
    }

    public Asignatura(String nombreAsignatura) {
        this.nombreAsignatura = nombreAsignatura;
    }


}
