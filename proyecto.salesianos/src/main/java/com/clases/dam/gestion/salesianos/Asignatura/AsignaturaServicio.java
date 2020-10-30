package com.clases.dam.gestion.salesianos.Asignatura;

import com.clases.dam.gestion.salesianos.Alumno.Alumno;
import com.clases.dam.gestion.salesianos.Curso.Curso;
import com.clases.dam.gestion.salesianos.Servicios.BaseServiceImpl;
import com.clases.dam.gestion.salesianos.SituacionExcepcional.SituacionExcepcionalServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AsignaturaServicio extends BaseServiceImpl<Asignatura,Long,AsignaturaRepository> {
    @Autowired
    private SituacionExcepcionalServicio situacionExcepcionalServicio;
    public AsignaturaServicio(AsignaturaRepository repo) {
        super(repo);
    }

    public Optional<Asignatura> findFirstBynombre(String nombre, Curso curso) {
        return repositorio.findFirstBynombre(nombre,curso);
    }

    public List<Asignatura> encontrarListaAsignaturasActiva(Curso curso) {
        return repositorio.encontrarListaAsignaturasActiva(curso);
    }
    public List<Asignatura> ordenarListaDeAsignaturas(Curso curso) {
        Set<Asignatura> lista=new HashSet<>();
        lista.addAll(curso.getAsignatura());
        List<Asignatura> asignaturas=new ArrayList<>();
        asignaturas.addAll(lista);
        Collections.sort(asignaturas,new AsignaturaOrdenar());
        return asignaturas;
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
                    resultados.add("Exento");
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
}
