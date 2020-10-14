package com.clases.dam.gestion.salesianos.Controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Random;

@Controller
public class ControladorInicio {

    @GetMapping("/login")
    public String paginaLogin(){
        return "login";
    }
    @GetMapping("/")
    public String inicio(){
        return "index";
    }

    private String generarCódigo(){
        Random aleatorio = new Random();

        String alfa = "ABCDEFGHIJKLMNOPQRSTVWXYZ";

        String cadena = "";    //Inicializamos la Variable//

        int numero;

        int forma;
        forma=(int)(aleatorio.nextDouble() * alfa.length()-1+0);
        numero=(int)(aleatorio.nextDouble() * 99+100);

        cadena=cadena+alfa.charAt(forma)+numero;

        return cadena;
    }
}
