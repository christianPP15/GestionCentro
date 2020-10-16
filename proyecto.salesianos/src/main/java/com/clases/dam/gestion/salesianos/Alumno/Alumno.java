package com.clases.dam.gestion.salesianos.Alumno;

import com.clases.dam.gestion.salesianos.Curso.Curso;
import com.clases.dam.gestion.salesianos.Usuario.Usuario;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Arrays;
import java.util.Collection;
@Getter @Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@Entity
public class Alumno extends Usuario {

    @ManyToOne
    private Curso curso;

    public Alumno(String nombre, String apellidos, String email, String password, String codigoSeguridad) {
        super(nombre, apellidos, email, password, codigoSeguridad);
    }

    public Alumno(String nombre, String apellidos, String email, String password) {
        super(nombre, apellidos, email, password);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_ALUMNO"));
    }
}
