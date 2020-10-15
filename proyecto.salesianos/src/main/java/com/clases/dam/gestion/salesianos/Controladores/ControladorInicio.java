package com.clases.dam.gestion.salesianos.Controladores;

import com.clases.dam.gestion.salesianos.Formularios.CodigoActivacion;
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
    UsuarioServicio serviUsu;
    @GetMapping("/login")
    public String paginaLogin(){
        return "login";
    }

    @GetMapping({"/","/index"})
    public String inicio(@AuthenticationPrincipal Usuario usuarioLog, Model model){
        if (!usuarioLog.isPrimeraVez()){
            model.addAttribute("codigo",new CodigoActivacion());
            return "PrimeraVez";
        }else {
            return "index";
        }
    }
    @PostMapping("/submit/codigo")
    public String confirmarCódigo(@ModelAttribute("codigo") CodigoActivacion codigo,
                                  @AuthenticationPrincipal Usuario usuarioLog, BCryptPasswordEncoder encode){
        if(usuarioLog.getCodigoSeguridad().contentEquals(codigo.getCodigo())){
            usuarioLog.setPrimeraVez(true);
            usuarioLog.setPassword(encode.encode(usuarioLog.getPassword()));
            serviUsu.edit(usuarioLog);
        }
        return "redirect:/index";
    }

}
