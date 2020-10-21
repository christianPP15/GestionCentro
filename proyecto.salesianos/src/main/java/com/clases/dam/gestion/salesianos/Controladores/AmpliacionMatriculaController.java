package com.clases.dam.gestion.salesianos.Controladores;

import com.clases.dam.gestion.salesianos.Alumno.Alumno;
import com.clases.dam.gestion.salesianos.Alumno.AlumnoServicio;
import com.clases.dam.gestion.salesianos.Asignatura.Asignatura;
import com.clases.dam.gestion.salesianos.Asignatura.AsignaturaServicio;
import com.clases.dam.gestion.salesianos.Curso.Curso;
import com.clases.dam.gestion.salesianos.SolicitudAmpliacionMatricula.SolicitudAmpliacionMatricula;
import com.clases.dam.gestion.salesianos.SolicitudAmpliacionMatricula.SolicitudAmpliacionMatriculaId;
import com.clases.dam.gestion.salesianos.SolicitudAmpliacionMatricula.SolicitudAmpliacionMatriculaServicio;
import com.clases.dam.gestion.salesianos.Titulo.Titulo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Controller
public class AmpliacionMatriculaController {
    @Autowired
    private AlumnoServicio alumnoServicio;
    @Autowired
    private AsignaturaServicio asignaturaServicio;
    @Autowired
    private SolicitudAmpliacionMatriculaServicio solicitudAmpliacionMatriculaServicio;

    @GetMapping("/solicitarAmpliacionMatricula")
    public String elegirAmpliacionMatricula(@AuthenticationPrincipal Alumno alumno, Model model){
        if (alumno.getCurso().getNombre().contains("1")){
            for (Curso curso : alumno.getCurso().getTitulos().getCursos()){
                if (curso.getNombre().contains("2")){
                    model.addAttribute("asignaturasSegundo",crearMapaConInformacion(alumno));
                }
            }
                model.addAttribute("alumno",alumno);
            return "JefeEstudios/AmpliacionMatricula/AmpliacionMatricula";
        }else{
            return "JefeEstudios/AmpliacionMatricula/Error";
        }
    }
    @GetMapping("/convalidar/{idAsig}/completo/{idAlum}")
    public String completarAmpliacionMatricula(@PathVariable("idAsig") Long idAsig,@PathVariable("idAlum") Long idAlum){
        Asignatura asignatura=asignaturaServicio.findById(idAsig).get();
        Alumno alumno=alumnoServicio.findById(idAlum).get();
        if (solicitudAmpliacionMatriculaServicio.buscarExistencia(asignatura,alumno).orElse(null)==null){
            SolicitudAmpliacionMatriculaId id=new SolicitudAmpliacionMatriculaId(alumno.getId(),asignatura.getId());
            SolicitudAmpliacionMatricula solicitudAmpliacionMatricula=new SolicitudAmpliacionMatricula(id,asignatura,alumno, LocalDate.now());
            solicitudAmpliacionMatriculaServicio.save(solicitudAmpliacionMatricula);
        }else{
            SolicitudAmpliacionMatricula solicitudAmpliacionMatricula=solicitudAmpliacionMatriculaServicio.buscarExistencia(asignatura,alumno).get();
            solicitudAmpliacionMatriculaServicio.delete(solicitudAmpliacionMatricula);
        }
        return "redirect:/solicitarAmpliacionMatricula";
    }

    public Map<Asignatura,String> crearMapaConInformacion(Alumno alumno){
        Map<Asignatura,String> info=new HashMap<>();
        for (Curso curso : alumno.getCurso().getTitulos().getCursos()) {
            if (curso.getNombre().contains("2")) {
                for (Asignatura asignatura:curso.getAsignatura()){
                    if (solicitudAmpliacionMatriculaServicio.buscarExistenciaPendientes(asignatura,alumno).orElse(null)!=null){
                        info.put(asignatura,"Cancelar");
                    }else if (solicitudAmpliacionMatriculaServicio.buscarExistenciaConfirmadas(asignatura,alumno).orElse(null)!=null){
                        info.put(asignatura,"Matriculado");
                    }else{
                        info.put(asignatura,"Matricular");
                    }
                }
            }
        }
        return info;
    }
}
