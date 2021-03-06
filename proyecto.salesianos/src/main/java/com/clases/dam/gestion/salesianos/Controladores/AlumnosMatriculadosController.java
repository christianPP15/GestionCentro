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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
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
    private ServletContext context;
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
        model.addAttribute("listaAsignatura",asignaturaServicio.ordenarListaDeAsignaturas(serviCurso.findById(id).get()));
        model.addAttribute("listadoAlumnos",asignaturaServicio.agregarAlListadoElTipo(serviCurso.findById(id).get()));
        return "JefeEstudios/GestionAlumnos/alumnosCurso";
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
        Set <Asignatura> listaAsignaturas=new HashSet<>();
        listaAsignaturas.addAll(al.getCurso().getAsignatura());
        for (Asignatura asig:
                listaAsignaturas) {
            if(asig.getId()==idAsig){
                if(al.getAsignaturas().contains(asig)){
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
        Set<Asignatura> asignaturas=new HashSet<>();
        asignaturas.addAll(al.getCurso().getAsignatura());
        for (Asignatura asig:
             asignaturas) {
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

    @GetMapping("/createPdf/alumnos/{idAlum}")
    public void generarPdfPeliculas(@PathVariable("idAlum") Long id,  HttpServletRequest request, HttpServletResponse response) {
        Alumno alumno=alumnoServicio.findById(id).get();
        boolean isFlag= AlumnoServicio.createPdf(alumno,horarioServicio,solicitudAmpliacionMatriculaServicio,request, response,context);
        if(isFlag) {
            String fullPath= request.getServletContext().getRealPath("/resources/reports/"+alumno.getNombre()+"_"+alumno.getApellidos()+".pdf");
            filedownload(fullPath,response,alumno.getNombre()+"_"+alumno.getApellidos()+".pdf");
        }
    }
    private void filedownload(String fullPath, HttpServletResponse response, String fileName) {
        File file= new File(fullPath);
        final int BUFFER_SIZE =4096;
        if(file.exists()) {
            try {
                FileInputStream inputStream = new FileInputStream(file);
                String mimeType= context.getMimeType(fullPath);
                response.setContentType(mimeType);
                response.setHeader("content-disposition", "attachment; filename="+ fileName);
                OutputStream outputStream = response.getOutputStream();
                byte [] buffer = new byte [BUFFER_SIZE];
                int bytesRead=-1;
                while((bytesRead = inputStream.read(buffer)) != -1 ){
                    outputStream.write(buffer,0,bytesRead);
                }
                inputStream.close();
                outputStream.close();
                file.delete();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
