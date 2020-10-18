package com.clases.dam.gestion.salesianos.Controladores;

import com.clases.dam.gestion.salesianos.Alumno.Alumno;
import com.clases.dam.gestion.salesianos.Curso.Curso;
import com.clases.dam.gestion.salesianos.Curso.CursoServicio;
import com.clases.dam.gestion.salesianos.Formularios.NuevoAlumnoFormulario;
import com.clases.dam.gestion.salesianos.Profesor.Profesor;
import com.clases.dam.gestion.salesianos.Servicios.Mail;
import com.clases.dam.gestion.salesianos.Titulo.TituloServicio;
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
public class JefeEstudiosAltaController {
    @Autowired
    private UsuarioServicio serviUsuario;
    @Autowired
    private TituloServicio serviTitulo;
    @Autowired
    private CursoServicio serviCurso;

    @GetMapping("/jefeEstudio/alta/JefeEstudio")
    public String crearNuevoJefeEstudios(Model model){
        model.addAttribute("usuario",new Profesor());
        return "JefeEstudios/Alta/NuevoJefeEstudios";
    }
    @GetMapping("/jefeEstudio/alta/Profesor")
    public String crearNuevoProfesor(Model model){
        model.addAttribute("usuario",new Profesor());
        return "JefeEstudios/Alta/NuevoProfesor";
    }
    @GetMapping("/jefeEstudio/alta/alumnos")
    public String crearNuevoAlumno(Model model){
        model.addAttribute("usuario",new NuevoAlumnoFormulario());
        model.addAttribute("listaCursos",serviCurso.findAll());
        return "JefeEstudios/Alta/NuevoAlumno";
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
    @PostMapping("/submit/nuevo/jefe/profesor")
    public String nuevoProfesorCompleto(@ModelAttribute("usuario") Profesor usuario, BCryptPasswordEncoder passwordEncoder) throws MessagingException {
        Usuario usu= new Profesor(usuario.getNombre(),usuario.getApellidos()
                ,usuario.getEmail(),passwordEncoder.encode("1234"),generarCódigo(),false);
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
    @PostMapping("/submit/nuevo/jefe/alumno")
    public String nuevoAlumnoCompleto(@ModelAttribute("usuario") NuevoAlumnoFormulario usuario, BCryptPasswordEncoder passwordEncoder) throws MessagingException {
        System.out.println(usuario);
        Alumno usu= new Alumno(usuario.getNombre(),usuario.getApellidos()
                ,usuario.getEmail(),passwordEncoder.encode("1234"),generarCódigo());
        serviUsuario.save(usu);
        Curso aux=serviCurso.findById(usuario.getId()).get();
        aux.addAlumno(usu);
        serviCurso.edit(aux);
        serviUsuario.edit(usu);
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
    public String nuevoJefeEstudiosCompletoCsv()  throws IOException {

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
