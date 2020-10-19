package com.clases.dam.gestion.salesianos.Controladores;

import com.clases.dam.gestion.salesianos.Alumno.Alumno;
import com.clases.dam.gestion.salesianos.Alumno.AlumnoServicio;
import com.clases.dam.gestion.salesianos.Asignatura.Asignatura;
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
import com.clases.dam.gestion.salesianos.proveedorId.ProveedorId;
import com.clases.dam.gestion.salesianos.proveedorId.ProveedorIdServicio;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;    // Apache commons IO
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Path;
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
    @Autowired private ServletContext context;
    @Autowired
    private AsignaturaServicio asignaturaServicio;
    @Autowired
    private HorarioServicio horarioServicio;
    @Autowired
    private ProveedorIdServicio proveedorIdServicio;
    @Autowired
    private AlumnoServicio alumnoServicio;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;
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
        model.addAttribute("convalidaciones",situacionExcepcionalServicio.findAll());
        return "JefeEstudios/convalidaciones/AceptacionConvalidacion";
    }
    @GetMapping("/descargar/{idAlumno}/info/{idAsignatura}")
    public String detallesSolicitudes(@PathVariable("idAlumno") Long alumno,@PathVariable("idAsignatura") Long asig, Model model){
        model.addAttribute("solicitudes",situacionExcepcionalServicio.buscarExistencia(asignaturaServicio.findById(asig).get(),alumnoServicio.findById(alumno).get()).get());
        return "JefeEstudios/convalidaciones/DetallesConvalidacion";
    }
    @GetMapping("/descargar/{idAlumno}/solicitud/{idAsignatura}")
    public String DescargasSolicitudes(@PathVariable("idAlumno") Long alumno,@PathVariable("idAsignatura") Long asig, Model model) throws Exception {
        handleRequest(request,response,situacionExcepcionalServicio.buscarExistencia(asignaturaServicio.findById(asig).get(),alumnoServicio.findById(alumno).get()).get());
        return "redirect:/descargar/"+alumno+"/info/"+asig;
    }
    public ModelAndView handleRequest(HttpServletRequest request,
                                      HttpServletResponse response,SituacionExcepcional situacionExcepcional) throws Exception {
        try {
            // Aqui se hace a piñón fijo, pero podría obtenerse el fichero
            // pedido por el usuario a partir de algún parámetro del request
            // o de la URL con la que nos han llamado.
            String nombreFichero = "informacion.pdf";
            String unPath = storageService.load(situacionExcepcional.getAdjunto()).toUri().toString();

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=\""
                    + nombreFichero+ "\"");

            InputStream is = new FileInputStream(unPath+nombreFichero);

            IOUtils.copy(is, response.getOutputStream());

            response.flushBuffer();

        } catch (IOException ex) {
            // Sacar log de error.
            throw ex;
        }

        return null;
    }
    /*@GetMapping("/descargar/info/{id}")
    public void descargaPdf(HttpServletRequest request, HttpServletResponse response,@PathVariable("id") Long id){
        String ruta = storageService.load(id+".pdf").toString();
        System.out.println(ruta);
        String fullPath= request.getServletContext().getRealPath(ruta);
        filedownload(fullPath,response,"peticion.pdf");
    }*/
    /*public void filedownload(String fullPath, HttpServletResponse response, String fileName){
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
    }*/

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().body(file);
    }
}
