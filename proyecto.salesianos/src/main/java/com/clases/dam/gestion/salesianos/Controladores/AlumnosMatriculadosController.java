package com.clases.dam.gestion.salesianos.Controladores;

import com.clases.dam.gestion.salesianos.Alumno.Alumno;
import com.clases.dam.gestion.salesianos.Alumno.AlumnoServicio;
import com.clases.dam.gestion.salesianos.Asignatura.Asignatura;
import com.clases.dam.gestion.salesianos.Asignatura.AsignaturaServicio;
import com.clases.dam.gestion.salesianos.Curso.Curso;
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
import java.util.stream.Collectors;

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
        //Ordenamos las asignaturas de cada alumno por orden alfabetico, y vamos mirando una a una si existe Convalidada y demás
        //Las vamos metiendo en un map que sea asignatura ordenada y resultado
        //Doble map
        model.addAttribute("listadoAlumnos",agregarAlListadoElTipo(serviCurso.findById(id).get()));

        return "JefeEstudios/GestionAlumnos/alumnosCurso";
    }


    public Map <Map<Asignatura,Alumno>,String> agregarAlListadoElTipo(Curso curso){
        Map<Map<Asignatura,Alumno>,String> listadoCompuesto=new HashMap<>();
        int i=0;
        int bucle =0;
        for (Asignatura asignatura:
             curso.getAsignatura()) {
            int numeroAlumnos=asignatura.getCurso().getAlumnos().size();
            for ( Alumno al:
                  asignatura.getCurso().getAlumnos()) {
                if (numeroAlumnos>i){
                    if (bucle==0){
                        if (situacionExcepcionalServicio.buscarExistenciaTerminada(asignatura,al).orElse(null)!=null){
                            Map <Asignatura,Alumno> listadoSimple=new HashMap<>();
                            listadoSimple.put(asignatura,al);
                            listadoCompuesto.put(listadoSimple,"Convalidado");
                        }else{
                            Map <Asignatura,Alumno> listadoSimple=new HashMap<>();
                            listadoSimple.put(asignatura,al);
                            listadoCompuesto.put(listadoSimple,"Matriculado");
                        }
                    }
                }
                i++;
                bucle++;
            }
        }

        return listadoCompuesto;
    }
}
