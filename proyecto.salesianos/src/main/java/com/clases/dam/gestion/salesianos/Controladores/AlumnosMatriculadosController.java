package com.clases.dam.gestion.salesianos.Controladores;

import com.clases.dam.gestion.salesianos.Alumno.Alumno;
import com.clases.dam.gestion.salesianos.Alumno.AlumnoServicio;
import com.clases.dam.gestion.salesianos.Asignatura.Asignatura;
import com.clases.dam.gestion.salesianos.Asignatura.AsignaturaOrdenar;
import com.clases.dam.gestion.salesianos.Asignatura.AsignaturaServicio;
import com.clases.dam.gestion.salesianos.Curso.Curso;
import com.clases.dam.gestion.salesianos.Curso.CursoServicio;
import com.clases.dam.gestion.salesianos.Horario.HorarioServicio;
import com.clases.dam.gestion.salesianos.SituacionExcepcional.SituacionExcepcionalServicio;
import com.clases.dam.gestion.salesianos.SolicitudAmpliacionMatricula.SolicitudAmpliacionMatriculaServicio;
import com.clases.dam.gestion.salesianos.Titulo.TituloServicio;
import com.clases.dam.gestion.salesianos.Usuario.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.lang.reflect.Array;
import java.util.*;
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
    @Autowired
    private SolicitudAmpliacionMatriculaServicio solicitudAmpliacionMatriculaServicio;
    @GetMapping("/listado/alumnos/curso/{id}")
    public String gestionAlumnosCurso(@PathVariable("id") Long id, Model model){
        //Ordenamos las asignaturas de cada alumno por orden alfabetico, y vamos mirando una a una si existe Convalidada y demás
        //Las vamos metiendo en un map que sea asignatura ordenada y resultado
        //Doble map
        model.addAttribute("listaAsignatura",ordenarListaDeAsignaturas(serviCurso.findById(id).get()));
        model.addAttribute("listadoAlumnos",agregarAlListadoElTipo(serviCurso.findById(id).get()));
        return "JefeEstudios/GestionAlumnos/alumnosCurso";
    }

    private List<Asignatura> ordenarListaDeAsignaturas(Curso curso) {
        List<Asignatura> lista=curso.getAsignatura();
        Collections.sort(lista,new AsignaturaOrdenar());
        return lista;
    }

    public Map <Alumno,List<String>> agregarAlListadoElTipo(Curso curso){
        List<String> resultados=new ArrayList<>();
        int iNuevo=0;
        int contador=0;
        Map<Alumno,List<String>> listadoCompuesto=new HashMap<>();
        List<String> listaAux=new ArrayList<>();
        for (Alumno al:
             curso.getAlumnos()) {
            resultados.clear();
            for (Asignatura asig:
                 ordenarListaDeAsignaturas(curso)) {
                if (situacionExcepcionalServicio.buscarExistenciaTerminadaConvalidacion(asig,al).orElse(null)!=null){
                    resultados.add("Convalidada");
                }else if (situacionExcepcionalServicio.buscarExistenciaTerminadaExcepcion(asig,al).orElse(null)!=null){
                    resultados.add("Excepción");
                }else if(al.getAsignaturas().contains(asig)){
                    resultados.add("Aprob. del curso anterior");
                }else{
                    resultados.add("Matriculado");
                }
            }
            iNuevo=resultados.size();
            for (String componente:
                 resultados) {
                listaAux.add(componente);
            }
        }
        for (Alumno al:
             curso.getAlumnos()) {
            listadoCompuesto.put(al,listaAux.subList(contador,contador+iNuevo));
            contador+=iNuevo;
        }
        return listadoCompuesto;
    }
    @GetMapping("/gestion/alumno/asignaturas/{id}")
    public String gestionDeUnAlumno(@PathVariable("id") Long id,Model model){
        //Hacer mediante enlaces
        Alumno al=alumnoServicio.findById(id).get();
        model.addAttribute("Alumno",al);
        model.addAttribute("asignaturas",recorrerListaConResultados(al));
        return "JefeEstudios/GestionAlumnos/AsignaturasAprobada";
    }
    @GetMapping("/marcar/asignatura/{idAsig}/aprobada/{idAlum}")
    public String gestionDeUnAlumnoAprobarSuspender
            (@PathVariable("idAsig") Long idAsig,@PathVariable("idAlum") Long idAlum){
        Alumno al=alumnoServicio.findById(idAlum).get();
        for (Asignatura asig:
             al.getCurso().getAsignatura()) {
            if(asig.getId()==idAsig){
                if (al.getAsignaturas().contains(asig)){
                    al.removeAsignatura(asig);
                    asignaturaServicio.edit(asig);
                    alumnoServicio.edit(al);
                }else {
                    al.addAsignatura(asig);
                    asignaturaServicio.edit(asig);
                    alumnoServicio.edit(al);
                }
            }
        }
        return "redirect:/gestion/alumno/asignaturas/"+idAlum;
    }

    private Map<Asignatura,String> recorrerListaConResultados(Alumno al){
        Map<Asignatura,String> estado=new HashMap<>();
        for (Asignatura asig:
             al.getCurso().getAsignatura()) {
            if (al.getAsignaturas().contains(asig)){
                estado.put(asig,"Aprobada");
            }else if(situacionExcepcionalServicio.buscarExistenciaTerminadaConvalidacion(asig,al).orElse(null)!=null){
                estado.put(asig,"Convalidada");
            }else if (situacionExcepcionalServicio.buscarExistenciaTerminadaExcepcion(asig,al).orElse(null)!=null){
                estado.put(asig,"Excepción");
            }else{
                estado.put(asig,"Matriculado");
            }
        }
        return estado;
    }
}
