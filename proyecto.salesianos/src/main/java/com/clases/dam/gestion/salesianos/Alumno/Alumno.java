package com.clases.dam.gestion.salesianos.Alumno;

import com.clases.dam.gestion.salesianos.Asignatura.Asignatura;
import com.clases.dam.gestion.salesianos.Curso.Curso;
import com.clases.dam.gestion.salesianos.Usuario.Usuario;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.*;

@Getter @Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@Entity
public class Alumno extends Usuario {

    @ManyToOne
    private Curso curso;

    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(name="alumno_id"),
            inverseJoinColumns = @JoinColumn(name="asignatura_id")
    )
    private Set<Asignatura> asignaturas = new HashSet<>();

    public void addAsignatura(Asignatura a) {
        asignaturas.add(a);
        a.getAlumnos().add(this);
    }

    public void removeAsignatura(Asignatura a) {
        asignaturas.remove(a);
        a.getAlumnos().remove(this);
    }

    public Alumno(String nombre, String apellidos, String email, String password) {
        super(nombre, apellidos, email, password);
    }

    public Alumno(String nombre, String apellidos, String email, String password, Curso curso) {
        super(nombre, apellidos, email, password);
        this.curso = curso;
    }


    public Alumno(String nombre, String apellidos, String email, String password, boolean primeraVez) {
        super(nombre, apellidos, email, password, primeraVez);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_ALUMNO"));
    }
}
