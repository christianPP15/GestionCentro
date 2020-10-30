package com.clases.dam.gestion.salesianos.Horario;

import com.clases.dam.gestion.salesianos.Asignatura.Asignatura;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Horario implements Comparable<Horario>{
    @Id
    @GeneratedValue
    private Long id;

    private int dia;

    private int tramo;

    @ManyToOne
    private Asignatura asignatura;

    private boolean activo=true;

    public Horario(int dia, int tramo) {
        this.dia = dia;
        this.tramo = tramo;
    }

    @Override
    public int compareTo(Horario o) {
        int numP=1,numN=1;
        if(this.getTramo()<o.getTramo()){
            return numP;
        }else {
            return numN;
        }
    }

    public Horario(int dia) {
        this.dia = dia;
    }
}
