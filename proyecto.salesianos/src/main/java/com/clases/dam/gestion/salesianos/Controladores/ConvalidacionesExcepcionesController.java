package com.clases.dam.gestion.salesianos.Controladores;

import com.clases.dam.gestion.salesianos.Alumno.Alumno;
import com.clases.dam.gestion.salesianos.Alumno.AlumnoServicio;
import com.clases.dam.gestion.salesianos.Asignatura.Asignatura;
import com.clases.dam.gestion.salesianos.Asignatura.AsignaturaServicio;
import com.clases.dam.gestion.salesianos.Curso.CursoServicio;
import com.clases.dam.gestion.salesianos.Formularios.InformacionRechazoAceptacion;
import com.clases.dam.gestion.salesianos.Formularios.SituacionExcepcionalFormulario;
import com.clases.dam.gestion.salesianos.Horario.HorarioServicio;
import com.clases.dam.gestion.salesianos.Servicios.Mail;
import com.clases.dam.gestion.salesianos.Servicios.upload.StorageService;
import com.clases.dam.gestion.salesianos.SituacionExcepcional.SituacionExcepcional;
import com.clases.dam.gestion.salesianos.SituacionExcepcional.SituacionExcepcionalServicio;
import com.clases.dam.gestion.salesianos.SituacionExcepcional.SituacionExcepcionald;
import com.clases.dam.gestion.salesianos.Titulo.TituloServicio;
import com.clases.dam.gestion.salesianos.Usuario.UsuarioServicio;
import com.clases.dam.gestion.salesianos.proveedorId.ProveedorId;
import com.clases.dam.gestion.salesianos.proveedorId.ProveedorIdServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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

import javax.mail.MessagingException;
import javax.servlet.ServletContext;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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
    @Autowired private ServletContext context;
    @Autowired
    private AsignaturaServicio asignaturaServicio;
    @Autowired
    private HorarioServicio horarioServicio;
    @Autowired
    private ProveedorIdServicio proveedorIdServicio;
    @Autowired
    private AlumnoServicio alumnoServicio;
    @GetMapping("/solicitarCambio")
    public String convalidacionesExepciones(Model model, @AuthenticationPrincipal Alumno alumno){
            model.addAttribute("NuevaConvi",new SituacionExcepcionalFormulario());
            Set<Asignatura> asignaturas=new HashSet<>();
            asignaturas.addAll(alumno.getCurso().getAsignatura());
            model.addAttribute("asignaturas",asignaturas);
        return "Alumno/convalidacion";
    }
    @PostMapping("/nueva/solicitud/cambio")
    public String convalidacionesExepcionesNuevo(@ModelAttribute("NuevaConvi") SituacionExcepcionalFormulario situacionExcepcional
            , @AuthenticationPrincipal Alumno alumno, @RequestParam("file") MultipartFile file){
        if(situacionExcepcionalServicio.buscarExistencia(asignaturaServicio.findById(situacionExcepcional.getIdAsignatura()).get(),alumnoServicio.findById(alumno.getId()).get()).orElse(null)!=null){
            situacionExcepcionalServicio.delete(situacionExcepcionalServicio.buscarExistencia(asignaturaServicio.findById(situacionExcepcional.getIdAsignatura()).get(),alumnoServicio.findById(alumno.getId()).get()).get());
        }
        SituacionExcepcionald id=new SituacionExcepcionald(situacionExcepcional.getIdAsignatura(),alumno.getId());
        ProveedorId proveedorId=new ProveedorId();
        proveedorIdServicio.save(proveedorId);
        SituacionExcepcional situacionExcepcional1=new SituacionExcepcional(id,asignaturaServicio.findById(situacionExcepcional.getIdAsignatura()).get()
                ,alumno, LocalDateTime.now(),proveedorId,situacionExcepcional.isTipo(),false);
        situacionExcepcionalServicio.save(situacionExcepcional1);
        if (!file.isEmpty()) {
            String avatar = storageService.store(file, situacionExcepcional1.getIdAux().getId());
            situacionExcepcional1.setAdjunto(MvcUriComponentsBuilder
                    .fromMethodName(ConvalidacionesExcepcionesController.class, "serveFile", avatar).build().toUriString());
            situacionExcepcionalServicio.edit(situacionExcepcional1);
        }

        return "redirect:/index";
    }
    @GetMapping("/aceptar/solicitudes")
    public String solicitudesAceptacion(Model model){
        model.addAttribute("convalidaciones",situacionExcepcionalServicio.buscarExistenciaNoTerminadas());
        return "JefeEstudios/convalidaciones/AceptacionConvalidacion";
    }
    @GetMapping("/descargar/{idAlumno}/info/{idAsignatura}")
    public String detallesSolicitudes(@PathVariable("idAlumno") Long alumno,@PathVariable("idAsignatura") Long asig, Model model){
        model.addAttribute("solicitudes",situacionExcepcionalServicio.buscarExistencia(asignaturaServicio.findById(asig).get(),alumnoServicio.findById(alumno).get()).get());
        model.addAttribute("informacion",new InformacionRechazoAceptacion());
        return "JefeEstudios/convalidaciones/DetallesConvalidacion";
    }
    @PostMapping("/descargar/completado/final")
    public String EnviarSolicitud(@ModelAttribute("informacion") InformacionRechazoAceptacion info, Model model){
        SituacionExcepcional aux=situacionExcepcionalServicio.buscarExistencia(asignaturaServicio.findById(info.getIdAsignatura()).get(),alumnoServicio.findById(info.getIdUsuario()).get()).get();
        aux.setEstado(true);
        aux.setAceptada(info.isAceptado());
        aux.setFechaResolucion(LocalDateTime.now());
        situacionExcepcionalServicio.edit(aux);
        String tipo;
        if(!aux.isTipo()){
            tipo="convalidacion";
        }else{
            tipo="excepción";
        }
        String resolucion;
        if(aux.isAceptada()){
            resolucion="aceptada";
        }else{
            resolucion="rechazada";
        }
        try {
            Mail m = new Mail("Config/configuracion.properties");

            m.enviarEmail("Desición sobre la solicitud "+tipo+" para la asignatura "+aux.getAsignatura().getNombreAsignatura()
                    ,"Se le comunica que la solicitud de "+ tipo+" ha sido "
                            +resolucion+"\nMensaje del centro: "+info.getMensaje()
                    ,aux.getAlumno().getEmail());

        } catch (InvalidParameterException | MessagingException | IOException ex) {
            System.out.println(ex.getMessage());
        }
        return "redirect:/aceptar/solicitudes";
    }



    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        Resource file = storageService.loadAsResource(filename);
        //return ResponseEntity.ok().body(file);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                " attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}
