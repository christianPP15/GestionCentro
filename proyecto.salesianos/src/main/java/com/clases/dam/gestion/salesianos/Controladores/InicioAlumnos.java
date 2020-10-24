package com.clases.dam.gestion.salesianos.Controladores;

import com.clases.dam.gestion.salesianos.Alumno.Alumno;
import com.clases.dam.gestion.salesianos.Alumno.AlumnoServicio;
import com.clases.dam.gestion.salesianos.Asignatura.Asignatura;
import com.clases.dam.gestion.salesianos.Curso.Curso;
import com.clases.dam.gestion.salesianos.Curso.CursoServicio;
import com.clases.dam.gestion.salesianos.Formularios.CodigoActivacion;
import com.clases.dam.gestion.salesianos.Horario.Horario;
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
    @GetMapping("/index/alumno")
    public String mostrarHorario(@AuthenticationPrincipal Usuario log, Model model){
        if (!log.isPrimeraVez()){
            model.addAttribute("codigo",new CodigoActivacion());
            return "PrimeraVez";
        }else {
            Alumno al=alumnoServicio.findById(log.getId()).get();
            model.addAttribute("horarios",ordenarFinalmente(listaHorariosDevolver(cursoServicio.findById(al.getCurso().getId()).get())));
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
    public List<Horario> ordenar(List<Horario> lista){
        lista=lista.stream()
                .sorted(Comparator.comparingInt(Horario::getDia))
                .collect(Collectors.toList());
        return lista;
    }
    public List<Horario> tramo(List<Horario> lista,int dia){
        List<Horario> listaF=new ArrayList<>();
        for(Horario h:lista){
            if (h.getTramo()==dia){
                listaF.add(h);
            }
        }
        return listaF;
    }
    public List<List<Horario>> ordenarFinalmente(List<Horario> lista){
        List<List<Horario>> listF=new ArrayList<>();
        for (int i=1;i<7;i++){
            listF.add(this.ordenar(this.tramo(lista,i)));
        }
        return listF;
    }
    public List<Horario> listaHorariosDevolver(Curso curso){
        List<Horario> listaHorarios=new ArrayList<>();
        for (Asignatura asig:
             curso.getAsignatura()) {
            for (Horario hora:
                 asig.getHorario()) {
                listaHorarios.add(hora);
            }
        }
        return listaHorarios;
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
