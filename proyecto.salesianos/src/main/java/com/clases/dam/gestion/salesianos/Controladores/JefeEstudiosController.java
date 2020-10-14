package com.clases.dam.gestion.salesianos.Controladores;

import com.clases.dam.gestion.salesianos.Profesor.Profesor;
import com.clases.dam.gestion.salesianos.Servicios.Mail;
import com.clases.dam.gestion.salesianos.Usuario.Usuario;
import com.clases.dam.gestion.salesianos.Usuario.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.Random;

@Controller
public class JefeEstudiosController {
    @Autowired
    private UsuarioServicio serviUsuario;


    @GetMapping("/jefeEstudio/alta/JefeEstudio")
    public String crearNuevoJefeEstudios(Model model){
        model.addAttribute("usuario",new Profesor());
        return "JefeEstudios/NuevoJefeEstudios";
    }
    @PostMapping("/submit/nuevo/jefe/estudio")
    public String nuevoJefeEstudiosCompleto(@ModelAttribute("usuario") Profesor usuario, BCryptPasswordEncoder passwordEncoder) throws MessagingException {
        Usuario usu= new Profesor(usuario.getNombre(),usuario.getApellidos()
                ,usuario.getEmail(),passwordEncoder.encode("1234"),generarCódigo(),true);
        serviUsuario.save(usu);

        try {
            Mail m = new Mail("Config/configuracion.properties");

            m.enviarEmail("Código de acceso", "Bienvenido a la web de gestión Salesianos Triana" +
                    " ingrese este código la primera vez que acceda a la web: "+usu.getCodigoSeguridad()
                    +".\nLa contraseña por defecto es '1234' deberá cambiarla la primera vez que accede", usu.getEmail());

        } catch (InvalidParameterException | MessagingException | IOException ex) {
            System.out.println(ex.getMessage());
        }
        return "redirect:/index";
    }
    @PostMapping("/submit/nuevo/jefe/estudio/csv")
    public String nuevoJefeEstudiosCompletoCsv(){

        return "redirect:/index";
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
