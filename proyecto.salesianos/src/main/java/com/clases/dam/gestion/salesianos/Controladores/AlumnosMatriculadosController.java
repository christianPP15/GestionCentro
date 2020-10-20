package com.clases.dam.gestion.salesianos.Controladores;

import com.clases.dam.gestion.salesianos.Alumno.Alumno;
import com.clases.dam.gestion.salesianos.Alumno.AlumnoServicio;
import com.clases.dam.gestion.salesianos.Asignatura.Asignatura;
import com.clases.dam.gestion.salesianos.Asignatura.AsignaturaServicio;
import com.clases.dam.gestion.salesianos.Curso.CursoServicio;
import com.clases.dam.gestion.salesianos.Horario.HorarioServicio;
import com.clases.dam.gestion.salesianos.SituacionExcepcional.SituacionExcepcionalServicio;
import com.clases.dam.gestion.salesianos.Titulo.TituloServicio;
import com.clases.dam.gestion.salesianos.Usuario.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Controller
public class AlumnosMatriculadosController {
    @Autowired
    private UsuarioServicio serviUsuario;
    @Autowired
    private TituloServicio serviTitulo;
    @Autowired
    private CursoServicio serviCurso;
    @Autowired
    private AsignaturaServicio asignaturaServicio;
    @Autowired
    private AlumnoServicio alumnoServicio;
    @Autowired
    private HorarioServicio horarioServicio;
    @Autowired
    private SituacionExcepcionalServicio situacionExcepcionalServicio;
    @GetMapping("/listado/alumnos/curso/{id}")
    public String gestionAlumnosCurso(@PathVariable("id") Long id, Model model){
        Map<Alumno,String> listaAlumnosCursos=new HashMap<>();
        for (Alumno al:
           serviCurso.findById(id).get().getAlumnos()) {
            for (Asignatura asig:
                 al.getCurso().getAsignatura()) {
                if (situacionExcepcionalServicio.buscarExistencia(asig,al).orElse(null)!=null){
                    listaAlumnosCursos.put(al,situacionExcepcionalServicio.buscarExistencia(asig,al).get().isTipo() ? "Excepción" : "Convalidacion");
                }else{
                    listaAlumnosCursos.put(al,"Matriculado");
                }
            }
        }
        model.addAttribute("listadoAlumnos",listaAlumnosCursos);
        model.addAttribute("listadoAsignaturas",serviCurso.findById(id).get().getAsignatura());
        return "JefeEstudios/GestionAlumnos/alumnosCurso";
    }
}
