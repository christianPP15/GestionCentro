package com.clases.dam.gestion.salesianos.Profesor;

import com.clases.dam.gestion.salesianos.Usuario.Usuario;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.Entity;
import java.util.Arrays;
import java.util.Collection;
@Getter @Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@Entity
public class Profesor extends Usuario {

    private boolean esJefeEstudio;


    public Profesor(String nombre, String apellidos, String email, String password, boolean primeraVez, boolean esJefeEstudio) {
        super(nombre, apellidos, email, password, primeraVez);
        this.esJefeEstudio = esJefeEstudio;
    }

    public Profesor(String nombre, String apellidos, String email, String password, boolean esJefeEstudio) {
        super(nombre, apellidos, email, password);
        this.esJefeEstudio = esJefeEstudio;
    }

    public Profesor(String nombre, String apellidos, String email) {
        super(nombre, apellidos, email);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String role = "ROLE_";
        if (esJefeEstudio) {
            role += "ADMIN";
        } else {
            role += "PROFESOR";
        }
        return Arrays.asList(new SimpleGrantedAuthority(role));
    }
}
