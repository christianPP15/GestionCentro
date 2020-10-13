package com.clases.dam.gestion.salesianos.Profesor;

import com.clases.dam.gestion.salesianos.Usuario.Usuario;
import lombok.*;

import javax.persistence.Entity;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
public class Profesor extends Usuario {

    private boolean esJefeEstudio;
}
