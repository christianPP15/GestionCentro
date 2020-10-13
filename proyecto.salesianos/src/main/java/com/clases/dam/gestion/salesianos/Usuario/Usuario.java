package com.clases.dam.gestion.salesianos.Usuario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;


@Data @NoArgsConstructor @AllArgsConstructor
@MappedSuperclass
public class Usuario {
    @Id
    @GeneratedValue
    private long id;

    private String nombre;
    private String apellidos;
    private String email;
    private String password;

}
