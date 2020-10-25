package com.clases.dam.gestion.salesianos.Horario;

import com.clases.dam.gestion.salesianos.Alumno.Alumno;
import com.clases.dam.gestion.salesianos.Asignatura.Asignatura;
import com.clases.dam.gestion.salesianos.Asignatura.AsignaturaServicio;
import com.clases.dam.gestion.salesianos.Curso.Curso;
import com.clases.dam.gestion.salesianos.Servicios.BaseServiceImpl;
import com.clases.dam.gestion.salesianos.SituacionExcepcional.SituacionExcepcionalRepository;
import com.clases.dam.gestion.salesianos.SituacionExcepcional.SituacionExcepcionalServicio;
import com.clases.dam.gestion.salesianos.SolicitudAmpliacionMatricula.SolicitudAmpliacionMatricula;
import com.clases.dam.gestion.salesianos.Titulo.Titulo;
import com.clases.dam.gestion.salesianos.Titulo.TituloServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class HorarioServicio extends BaseServiceImpl<Horario,Long,HorarioRepository> {
    public HorarioServicio(HorarioRepository repo) {
        super(repo);
    }
    @Autowired
    public TituloServicio tituloServicio;
    @Autowired
    public AsignaturaServicio asignaturaServicio;
    @Autowired
    public SituacionExcepcionalServicio situacionExcepcionalServicio;

    public List<Horario> listaHorasActivas(Asignatura asignatura) {
        return repositorio.listaHorasActivas(asignatura);
    }


    public Set<Horario> horariosPorAlumno(Alumno alumno, List<SolicitudAmpliacionMatricula> listaAmpliaciones){

        Set<Horario> listaHor = new HashSet<>();
        Set<Asignatura> listaAsig = new HashSet<>();

        for(Asignatura asig : alumno.getCurso().getAsignatura()){
            if(asig.isActivo()){
                listaAsig.add(asig);
            }
        }
        List<Asignatura> listaAsignaturasFinal=new ArrayList<>();
        listaAsignaturasFinal.addAll(listaAsig);
        for(int i = 0; i < listaAsignaturasFinal.size(); i++){
            if (situacionExcepcionalServicio.buscarExistenciaTerminada(listaAsignaturasFinal.get(i), alumno).orElse(null)!=null) {
                listaAsig.remove(listaAsignaturasFinal.get(i));
            }
        }

        if (!alumno.getAsignaturas().isEmpty()){
            for (Asignatura asig:alumno.getAsignaturas()){
                for (int i=0;i<listaAsignaturasFinal.size();i++){
                    if (listaAsignaturasFinal.get(i).equals(asig)){
                        listaAsig.remove(asig);
                    }
                }
            }
        }

        for(SolicitudAmpliacionMatricula ampl : listaAmpliaciones){
            if(ampl.getAlumno().equals(alumno)) {
                if(ampl.isAceptada()){
                    if(ampl.getAsignatura().isActivo()){
                        listaAsig.add(ampl.getAsignatura());
                    }
                }
            }
        }

        for(Asignatura asig : listaAsig){
            for(Horario hor : asig.getHorario()){
                if(hor.isActivo()){
                    listaHor.add(hor);
                }
            }
        }

        return listaHor;
    }

    public List<List<Horario>> ordenarFinal (Set<Horario> lista){

        List<List<Horario>> listaF = new ArrayList<>();
        for(int i=1;i<7;i++){
            listaF.add(this.ordenar(this.listaTramo(lista, i)));
        }

        return listaF;
    }



    public List<Horario> ordenar (List<Horario> lista){
        int plus=0;
        lista = lista.stream()
                .sorted(Comparator.comparingInt(Horario::getDia))
                .collect(Collectors.toList());
        //Buscamos si no están todas las horas ocupadas y contamos cuantas hay
        if(lista.size()<5) {
            plus = 5 - lista.size();
            for (int i = 0; i < plus; i++) {
                lista.add(new Horario(6));
            }

            boolean encontrado;
            for (int dia = 1; dia <= 5; dia++) {
                encontrado = false;
                //Buscamos para cada tramos, si para cada día está su hora
                for (Horario h : lista) {
                    if (h.getDia() == dia) {
                        encontrado = true;
                    }
                }
                //Si no encontramos la hora X del tramo que estamos tratando, tratamos una de relleno (las que tienen el dia=6)
                //y cambiamos su día por el que correspoda para rellenar el vacío dejado
                if (!encontrado) {
                    for (int j = 0; j < lista.size(); j++) {
                        if (lista.get(j).getDia() == 6) {
                            lista.get(j).setDia(dia);
                            break;
                        }
                    }
                }
            }

        }
        lista = lista.stream()
                .sorted(Comparator.comparingInt(Horario::getDia))
                .collect(Collectors.toList());

        return lista;
    }

    public List<Horario> listaTramo(Set<Horario> lista, int dia){
        List<Horario> listaF = new ArrayList<>();
        for(Horario h : lista){
            if(h.getTramo()==dia){
                listaF.add(h);
            }
        }

        return listaF;
    }

}
