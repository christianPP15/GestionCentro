package com.clases.dam.gestion.salesianos.Controladores;

import com.clases.dam.gestion.salesianos.Alumno.Alumno;
import com.clases.dam.gestion.salesianos.Alumno.AlumnoServicio;
import com.clases.dam.gestion.salesianos.Asignatura.Asignatura;
import com.clases.dam.gestion.salesianos.Asignatura.AsignaturaServicio;
import com.clases.dam.gestion.salesianos.Curso.CursoServicio;
import com.clases.dam.gestion.salesianos.Horario.HorarioServicio;
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

    @GetMapping("/listado/alumnos/curso/{id}")
    public String gestionAlumnosCurso(@PathVariable("id") Long id, Model model){
        /*Map<Alumno, List<Asignatura>> alumnosMatriculados=new HashMap<>();
        for (Alumno alumno:
             alumnoServicio.findAll()) {
            alumnosMatriculados.put(serviCurso.findById(id).get().getAlumnos(),serviCurso.findById(id).get().getAsignatura())
        }*/
        model.addAttribute("listadoAlumnos",serviCurso.findById(id).get().getAlumnos());
        model.addAttribute("listadoAsignaturas",serviCurso.findById(id).get().getAsignatura());
        return "JefeEstudios/GestionAlumnos/alumnosCurso";
    }
}
