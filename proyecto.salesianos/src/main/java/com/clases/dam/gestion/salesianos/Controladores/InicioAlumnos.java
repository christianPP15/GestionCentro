package com.clases.dam.gestion.salesianos.Controladores;

import com.clases.dam.gestion.salesianos.Alumno.Alumno;
import com.clases.dam.gestion.salesianos.Alumno.AlumnoServicio;
import com.clases.dam.gestion.salesianos.Asignatura.Asignatura;
import com.clases.dam.gestion.salesianos.Curso.Curso;
import com.clases.dam.gestion.salesianos.Curso.CursoServicio;
import com.clases.dam.gestion.salesianos.Formularios.CodigoActivacion;
import com.clases.dam.gestion.salesianos.Horario.Horario;
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

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
@Controller
public class InicioAlumnos {
    @Autowired
    private UsuarioServicio usuarioServicio;
    @Autowired
    private AlumnoServicio alumnoServicio;
    @Autowired
    private CursoServicio cursoServicio;
    @Autowired
    private ServletContext context;
    @Autowired
    private SolicitudAmpliacionMatriculaServicio solicitudAmpliacionMatriculaServicio;
    @Autowired
    private HorarioServicio horarioServicio;

    @GetMapping("/index/alumno")
    public String mostrarHorario(@AuthenticationPrincipal Alumno log, Model model){
        if (!log.isPrimeraVez()){
            model.addAttribute("codigo",new CodigoActivacion());
            return "PrimeraVez";
        }else {
            Alumno al=alumnoServicio.findById(log.getId()).get();
            model.addAttribute("horarios",horarioServicio.ordenarFinal(horarioServicio.horariosPorAlumno(log,solicitudAmpliacionMatriculaServicio.findAll())));
            return "IndexAlumno";
        }
    }
    @PostMapping("/submit/codigo/alumno")
    public String confirmarCódigo(@ModelAttribute("codigo") CodigoActivacion codigo,
                                  @AuthenticationPrincipal Usuario usuarioLog, BCryptPasswordEncoder encode){
        if(usuarioLog.getCodigoSeguridad().contentEquals(codigo.getCodigo())){
            usuarioLog.setPrimeraVez(true);
            usuarioLog.setPassword(encode.encode(codigo.getContra()));
            usuarioLog.setCodigoSeguridad(null);
            usuarioServicio.edit(usuarioLog);
        }
        return "redirect:/index/alumno";
    }
    @GetMapping("/pruebas")
    public void generarHorarioAlumno(@AuthenticationPrincipal Alumno al){
        for (Asignatura asig:
             al.getCurso().getAsignatura()) {
            asig.getHorario().stream().sorted(Comparator.comparingInt(Horario::getDia)).collect(Collectors.toList());
        }
    }

    @GetMapping("/createPdf/alumnos")
    public void generarPdfPeliculas(HttpServletRequest request, HttpServletResponse response,@AuthenticationPrincipal Alumno alumno) {
        boolean isFlag= AlumnoServicio.createPdf(alumno,request, response,context);

        if(isFlag) {
            String fullPath= request.getServletContext().getRealPath("/resources/reports/"+"alumno"+".pdf");
            filedownload(fullPath,response,"alumno.pdf");
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
