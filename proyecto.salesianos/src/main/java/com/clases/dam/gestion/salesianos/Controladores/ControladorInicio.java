package com.clases.dam.gestion.salesianos.Controladores;

import com.clases.dam.gestion.salesianos.Alumno.Alumno;
import com.clases.dam.gestion.salesianos.Alumno.AlumnoServicio;
import com.clases.dam.gestion.salesianos.Formularios.CodigoActivacion;
import com.clases.dam.gestion.salesianos.Horario.HorarioServicio;
import com.clases.dam.gestion.salesianos.SolicitudAmpliacionMatricula.SolicitudAmpliacionMatriculaServicio;
import com.clases.dam.gestion.salesianos.Usuario.Usuario;
import com.clases.dam.gestion.salesianos.Usuario.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;
import java.util.Random;

@Controller
public class ControladorInicio {
    @Autowired
    private UsuarioServicio serviUsu;
    @Autowired
    private AlumnoServicio alumnoServicio;
    @Autowired
    private HorarioServicio horarioServicio;
    @Autowired
    private SolicitudAmpliacionMatriculaServicio solicitudAmpliacionMatriculaServicio;
    @GetMapping("/login")
    public String paginaLogin(){
        return "login";
    }

    @GetMapping({"/","/index"})
    public String inicio(@AuthenticationPrincipal Usuario log, Model model){
        if (!log.isPrimeraVez()){
            model.addAttribute("codigo",new CodigoActivacion());
            return "PrimeraVez";
        }else {
            Alumno al=alumnoServicio.findById(log.getId()).orElse(null);
            if (al!=null){
                model.addAttribute("horarios",horarioServicio.ordenarFinal(horarioServicio.horariosPorAlumno(al,solicitudAmpliacionMatriculaServicio.findAll())));
            }
            return "index";
        }
    }
    @PostMapping("/submit/codigo")
    public String confirmarCódigo(@ModelAttribute("codigo") CodigoActivacion codigo,
                                  @AuthenticationPrincipal Usuario usuarioLog, BCryptPasswordEncoder encode){
        if(usuarioLog.getCodigoSeguridad().contentEquals(codigo.getCodigo())){
            usuarioLog.setPrimeraVez(true);
            usuarioLog.setPassword(encode.encode(codigo.getContra()));
            usuarioLog.setCodigoSeguridad(null);
            serviUsu.edit(usuarioLog);
        }
            return "redirect:/index";
    }

}
