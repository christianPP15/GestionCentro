package com.clases.dam.gestion.salesianos.Alumno;

import com.clases.dam.gestion.salesianos.Usuario.Usuario;
import lombok.*;

import javax.persistence.Entity;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
public class Alumno extends Usuario {


}
