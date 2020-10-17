package com.clases.dam.gestion.salesianos.Controladores;

import com.clases.dam.gestion.salesianos.Alumno.Alumno;
import com.clases.dam.gestion.salesianos.Asignatura.Asignatura;
import com.clases.dam.gestion.salesianos.Asignatura.AsignaturaServicio;
import com.clases.dam.gestion.salesianos.Curso.Curso;
import com.clases.dam.gestion.salesianos.Curso.CursoServicio;
import com.clases.dam.gestion.salesianos.Horario.Horario;
import com.clases.dam.gestion.salesianos.Horario.HorarioServicio;
import com.clases.dam.gestion.salesianos.Profesor.Profesor;
import com.clases.dam.gestion.salesianos.Servicios.Mail;
import com.clases.dam.gestion.salesianos.Titulo.Titulo;
import com.clases.dam.gestion.salesianos.Titulo.TituloServicio;
import com.clases.dam.gestion.salesianos.Usuario.Usuario;
import com.clases.dam.gestion.salesianos.Usuario.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.mail.MessagingException;
import javax.swing.*;
import java.io.*;
import java.security.InvalidParameterException;
import java.util.Optional;
import java.util.Random;

@Controller
public class JefeEstudiosGestionController {
    @Autowired
    private UsuarioServicio serviUsuario;
    @Autowired
    private TituloServicio serviTitulo;
    @Autowired
    private CursoServicio serviCurso;
    @Autowired
    private AsignaturaServicio asignaturaServicio;
    @Autowired
    private HorarioServicio horarioServicio;
    @GetMapping("/gestion")
    public String gestionTítulos(Model model){
        model.addAttribute("titulos",serviTitulo.findAll());
        return "JefeEstudios/Gestion/titulos";
    }
    @GetMapping("/jefe/estudios/titulo/eliminar/{id}")
    public String aliminarTitulo(@PathVariable ("id") Long id){
        serviTitulo.delete(serviTitulo.findById(id).orElse(null));
        return "redirect:/gestion";
    }
    @GetMapping("/jefe/estudios/titulo/editar/{id}")
    public String editarTitulo(@PathVariable ("id") Long id,Model model){
        model.addAttribute("tituloAntiguo",serviTitulo.findById(id).get());
        model.addAttribute("tituloNuevo",new Titulo());
        return "JefeEstudios/Edicion/titulo";
    }
    @PostMapping("/submit/editar/titulo/final")
    public String editarTituloFinal(@ModelAttribute("titulo") Titulo titulo){
        Titulo aux=serviTitulo.findById(titulo.getId()).get();
        aux.setNombre(titulo.getNombre());
        serviTitulo.edit(aux);
        return "redirect:/gestion";
    }
    @GetMapping("/jefe/estudios/titulo/nuevo")
    public String agregarTitulo(Model model){
        model.addAttribute("nuevoTitulo",new Titulo());
        return "JefeEstudios/Nuevo/titulo";
    }
    @PostMapping("/submit/nuevo/titulo")
    public String nuevoTitulo(@ModelAttribute("titulo") Titulo titulo){
        serviTitulo.save(titulo);
       return  "redirect:/gestion";
    }
    @GetMapping("/jefe/estudios/cursos/{id}")
    public String gestionCursos(@PathVariable ("id") Long id,Model model){
        Titulo aux=serviTitulo.findById(id).get();
        model.addAttribute("Listacursos",aux.getCursos());
        model.addAttribute("Idtitulo",id);
        return "JefeEstudios/Gestion/cursos";
    }
    @GetMapping("/jefe/estudios/curso/eliminar/{id}")
    public String aliminarCurso(@PathVariable ("id") Long id){
        Titulo idTitulo=serviCurso.findById(id).get().getTitulos();
        serviCurso.delete(serviCurso.findById(id).get());
        return "redirect:/jefe/estudios/cursos/"+idTitulo.getId();
    }
    @GetMapping("/jefe/estudios/curso/editar/{id}")
    public String editarCurso(@PathVariable ("id") Long id,Model model){
        model.addAttribute("cursoAntiguo",serviCurso.findById(id).get());
        model.addAttribute("cursoNuevo",new Curso());
        return "JefeEstudios/Edicion/cursos";
    }
    @PostMapping("/submit/editar/curso/final")
    public String editarCursoFinal(@ModelAttribute("cursoNuevo") Curso curso){
        Curso aux=serviCurso.findById(curso.getId()).get();
        aux.setNombre(curso.getNombre());
        serviCurso.edit(aux);
        Titulo tituloAux=serviTitulo.findById(aux.getTitulos().getId()).get();
        return "redirect:/jefe/estudios/cursos/"+tituloAux.getId();
    }
    @GetMapping("/jefe/estudios/curso/nuevo/{id}")
    public String agregarCurso(@PathVariable ("id") Long id, Model model){
        model.addAttribute("nuevoCurso",new Curso());
        model.addAttribute("idTitulo",id);
        return "JefeEstudios/Nuevo/Curso";
    }
    @PostMapping("/submit/nuevo/curso")
    public String nuevoCurso(@ModelAttribute("nuevoCurso") Curso curso){
        Curso cursoNuevo= new Curso(curso.getNombre());
        serviCurso.save(cursoNuevo);
        cursoNuevo.setTitulos(curso.getTitulos());
        serviCurso.edit(cursoNuevo);
        return  "redirect:/jefe/estudios/cursos/"+curso.getTitulos().getId();
    }

    @GetMapping("/jefe/estudios/asignatura/{id}")
    public String gestionAsignaturas(@PathVariable ("id") Long id,Model model){
        Curso aux=serviCurso.findById(id).get();
        model.addAttribute("ListaAsignaturas",aux.getAsignatura());
        model.addAttribute("IdCurso",aux.getTitulos().getId());
        model.addAttribute("IdCursoNuevo",aux.getId());
        return "JefeEstudios/Gestion/asignaturas";
    }
    @GetMapping("/jefe/estudios/asignatura/eliminar/{id}")
    public String eliminarAsignatura(@PathVariable ("id") Long id){
        Asignatura aux=asignaturaServicio.findById(id).get();
        Curso idCurso=serviCurso.findById(aux.getCurso().getId()).get();
        asignaturaServicio.delete(asignaturaServicio.findById(id).get());
        return "redirect:/jefe/estudios/asignatura/"+idCurso.getId();
    }
    @GetMapping("/jefe/estudios/asignatura/editar/{id}")
    public String editarAsignatura(@PathVariable ("id") Long id,Model model){
        model.addAttribute("asigAntiguo",asignaturaServicio.findById(id).get());
        model.addAttribute("asigNuevo",new Asignatura());;
        return "JefeEstudios/Edicion/asignatura";
    }
    @PostMapping("/submit/editar/asignatura/final")
    public String editarAsignaturaFinal(@ModelAttribute("asigNuevo") Asignatura asignatura){
        Asignatura aux=asignaturaServicio.findById(asignatura.getId()).get();
        aux.setNombreAsignatura(asignatura.getNombreAsignatura());
        asignaturaServicio.edit(aux);
        Curso cursoAux=serviCurso.findById(aux.getCurso().getId()).get();
        return "redirect:/jefe/estudios/asignatura/"+cursoAux.getId();
    }
    @GetMapping("/jefe/estudios/asignatura/nuevo/{id}")
    public String agregarAsignatura(@PathVariable ("id") Long id, Model model){
        model.addAttribute("nuevoAsig",new Asignatura());
        model.addAttribute("idCurso",id);
        return "JefeEstudios/Nuevo/asignatura";
    }
    @PostMapping("/submit/nuevo/asignatura/final")
    public String agregarAsignaturaFinal(@ModelAttribute("nuevoAsig") Asignatura asignatura){
        Asignatura aux=new Asignatura(asignatura.getNombreAsignatura());
        asignaturaServicio.save(aux);
        Curso cursoNuevo=serviCurso.findById(asignatura.getId()).orElse(null);
        cursoNuevo.addAsignatura(aux);
        asignaturaServicio.edit(aux);
        serviCurso.edit(cursoNuevo);
        return "redirect:/jefe/estudios/asignatura/"+cursoNuevo.getId();
    }

    @GetMapping("/jefe/estudios/horas/{id}")
    public String gestionHoras(@PathVariable ("id") Long id,Model model){
        Asignatura aux=asignaturaServicio.findById(id).get();
        model.addAttribute("ListaHoras",aux.getHorario());
        model.addAttribute("IdAsignatura",aux.getCurso().getId());
        model.addAttribute("IdAsignaturaNuevo",aux.getId());
        return "JefeEstudios/Gestion/horarios";
    }
    @GetMapping("/jefe/estudios/horarios/nuevo/{id}")
    public String agregarHorario(@PathVariable ("id") Long id, Model model){
        model.addAttribute("nuevoHorario",new Horario());
        model.addAttribute("idAsig",id);
        return "JefeEstudios/Nuevo/horario";
    }
    @PostMapping("/submit/nuevo/horario/final")
    public String nuevoHorario(@ModelAttribute("nuevoCurso") Horario horario){
        Horario aux=new Horario(horario.getDia(),horario.getHoraComienzo(),horario.getHoraFinalizacion());
        horarioServicio.save(aux);
        Asignatura asignaturaAux=asignaturaServicio.findById(horario.getId()).get();
        asignaturaAux.addHorario(aux);
        horarioServicio.edit(aux);
        asignaturaServicio.edit(asignaturaAux);
        return  "redirect:/jefe/estudios/horas/"+asignaturaAux.getId();
    }
    @GetMapping("/jefe/estudios/hora/eliminar/{id}")
    public String eliminarHorario(@PathVariable ("id") Long id){
        Horario aux=horarioServicio.findById(id).get();
        Asignatura idAsignatura=asignaturaServicio.findById(aux.getAsignatura().getId()).get();
        horarioServicio.delete(aux);
        return "redirect:/jefe/estudios/horas/"+idAsignatura.getId();
    }
    @GetMapping("/jefe/estudios/horario/editar/{id}")
    public String editarHorario(@PathVariable ("id") Long id,Model model){
        model.addAttribute("horaAntiguo",horarioServicio.findById(id).get());
        model.addAttribute("horaNuevo",new Horario());;
        return "JefeEstudios/Edicion/horario";
    }
    @PostMapping("/submit/editar/horario/final")
    public String editarHorarioFinal(@ModelAttribute("horaNuevo") Horario horario){
        Horario aux=horarioServicio.findById(horario.getId()).get();
        aux.setDia(horario.getDia());
        aux.setHoraComienzo(horario.getHoraComienzo());
        aux.setHoraFinalizacion(horario.getHoraFinalizacion());
        horarioServicio.edit(aux);
        Asignatura asigAux=asignaturaServicio.findById(aux.getAsignatura().getId()).get();
        return "redirect:/jefe/estudios/horas/"+asigAux.getId();
    }
}
