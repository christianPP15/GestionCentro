package com.clases.dam.gestion.salesianos.Controladores;

import com.clases.dam.gestion.salesianos.Alumno.Alumno;
import com.clases.dam.gestion.salesianos.Asignatura.AsignaturaServicio;
import com.clases.dam.gestion.salesianos.Curso.CursoServicio;
import com.clases.dam.gestion.salesianos.Formularios.SituacionExcepcionalFormulario;
import com.clases.dam.gestion.salesianos.Horario.HorarioServicio;
import com.clases.dam.gestion.salesianos.Servicios.upload.StorageService;
import com.clases.dam.gestion.salesianos.SituacionExcepcional.SituacionExcepcional;
import com.clases.dam.gestion.salesianos.SituacionExcepcional.SituacionExcepcionalServicio;
import com.clases.dam.gestion.salesianos.SituacionExcepcional.SituacionExcepcionald;
import com.clases.dam.gestion.salesianos.Titulo.TituloServicio;
import com.clases.dam.gestion.salesianos.Usuario.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.time.LocalDateTime;

@Controller
public class ConvalidacionesExcepcionesController {
    @Autowired
    private SituacionExcepcionalServicio situacionExcepcionalServicio;
    @Autowired
    private UsuarioServicio serviUsuario;
    @Autowired
    private TituloServicio serviTitulo;
    @Autowired
    private StorageService storageService;
    @Autowired
    private CursoServicio serviCurso;
    @Autowired
    private AsignaturaServicio asignaturaServicio;
    @Autowired
    private HorarioServicio horarioServicio;
    @GetMapping("/solicitarCambio")
    public String convalidacionesExepciones(Model model, @AuthenticationPrincipal Alumno alumno){
            model.addAttribute("NuevaConvi",new SituacionExcepcionalFormulario());
            model.addAttribute("asignaturas",alumno.getCurso().getAsignatura());
        return "Alumno/convalidacion";
    }
    @PostMapping("/nueva/solicitud/cambio")
    public String convalidacionesExepcionesNuevo(@ModelAttribute("NuevaConvi") SituacionExcepcionalFormulario situacionExcepcional
            , @AuthenticationPrincipal Alumno alumno, @RequestParam("file") MultipartFile file){
        SituacionExcepcionald id=new SituacionExcepcionald(situacionExcepcional.getIdAsignatura(),alumno.getId());
        SituacionExcepcional situacionExcepcional1=new SituacionExcepcional(id,asignaturaServicio.findById(situacionExcepcional.getIdAsignatura()).get()
                ,alumno, LocalDateTime.now(),situacionExcepcional.isTipo(),false);
        situacionExcepcionalServicio.save(situacionExcepcional1);
       String idAux =String.valueOf(situacionExcepcional1.getId().getAlumno_id()+situacionExcepcional1.getId().getAsignatura_id());
        if (!file.isEmpty()) {
            String avatar = storageService.store(file, Long.parseLong(idAux));
            situacionExcepcional1.setAdjunto(MvcUriComponentsBuilder
                    .fromMethodName(ConvalidacionesExcepcionesController.class, "serveFile", avatar).build().toUriString());
            situacionExcepcionalServicio.edit(situacionExcepcional1);
        }

        return "redirect:/index";
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().body(file);
    }
}
