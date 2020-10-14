package com.clases.dam.gestion.salesianos.Controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class JefeEstudiosController {

    @GetMapping("/jefeEstudio/alta/JefeEstudio")
    public String crearNuevoJefeEstudios(){

        return "NuevoJefeEstudios";
    }
}
