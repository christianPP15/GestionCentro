package com.clases.dam.gestion.salesianos;

import com.clases.dam.gestion.salesianos.Alumno.Alumno;
import com.clases.dam.gestion.salesianos.Asignatura.Asignatura;
import com.clases.dam.gestion.salesianos.Asignatura.AsignaturaServicio;
import com.clases.dam.gestion.salesianos.Curso.Curso;
import com.clases.dam.gestion.salesianos.Curso.CursoServicio;
import com.clases.dam.gestion.salesianos.Horario.Horario;
import com.clases.dam.gestion.salesianos.Horario.HorarioServicio;
import com.clases.dam.gestion.salesianos.Profesor.Profesor;
import com.clases.dam.gestion.salesianos.Titulo.Titulo;
import com.clases.dam.gestion.salesianos.Titulo.TituloServicio;
import com.clases.dam.gestion.salesianos.Usuario.Usuario;
import com.clases.dam.gestion.salesianos.Usuario.UsuarioServicio;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.time.LocalTime;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	@Bean
	public CommandLineRunner init(UsuarioServicio servicio
			, BCryptPasswordEncoder passwordEncoder, TituloServicio tituloServicio
			, CursoServicio cursoServicio , AsignaturaServicio asignaturaServicio
			, HorarioServicio horarioServicio) {
		return args -> {

			Usuario u = new Alumno("Christian","Payo","cpp",passwordEncoder.encode("1234"),true);


			servicio.save(u);


			Usuario a = new Profesor("Luismi","Lopez",
					"Luismi@gmail.com",passwordEncoder.encode("1234"),true,false);

			servicio.save(a);

			Usuario angel = new Profesor("Angel","Naranjo",
					"admin",passwordEncoder.encode("admin"),true,true);

			servicio.save(angel);
			Horario hora1 =new Horario("LUN", LocalTime.of(8,0),LocalTime.of(9,0));
			Horario hora2 =new Horario("MIE",LocalTime.of(9,0),LocalTime.of(10,0));
			Horario hora3 =new Horario("VIE",LocalTime.of(8,0),LocalTime.of(9,0));
			Horario hora4 =new Horario("MAR",LocalTime.of(9,0),LocalTime.of(10,0));
			Horario hora5 =new Horario("MAR",LocalTime.of(10,0),LocalTime.of(11,0));
			Horario hora6 =new Horario("JUE",LocalTime.of(10,0),LocalTime.of(11,0));
			Horario hora7 =new Horario("LUN",LocalTime.of(13,30),LocalTime.of(14,30));
			Horario hora8 =new Horario("MAR",LocalTime.of(8,0),LocalTime.of(9,0));
			Horario hora9 =new Horario("VIE",LocalTime.of(13,30),LocalTime.of(14,30));
			horarioServicio.save(hora1);horarioServicio.save(hora2);horarioServicio.save(hora3);horarioServicio.save(hora4);
			horarioServicio.save(hora5);horarioServicio.save(hora6);horarioServicio.save(hora7);horarioServicio.save(hora8);
			horarioServicio.save(hora9);
			Asignatura asig=new Asignatura("PM y DM");
			Asignatura asig1=new Asignatura("AD");
			Asignatura asig2=new Asignatura("Base de datos");
			Asignatura asig3=new Asignatura("Programacion");
			Curso primerDam=new Curso("Primero de Dam");
			Curso segundoDam=new Curso("Segundo de Dam");
			Titulo ti=new Titulo("Desarrollo aplicaciones multiplataformas");
			asignaturaServicio.save(asig);
			asignaturaServicio.save(asig1);
			asignaturaServicio.save(asig2);
			asignaturaServicio.save(asig3);
			cursoServicio.save(primerDam);
			cursoServicio.save(segundoDam);
			tituloServicio.save(ti);
			asig.addHorario(hora1);asig.addHorario(hora2);asig.addHorario(hora3);
			asig1.addHorario(hora4);asig1.addHorario(hora5);asig1.addHorario(hora6);
			asig2.addHorario(hora7);asig2.addHorario(hora8);asig2.addHorario(hora9);
			segundoDam.addAsignatura(asig2);
			segundoDam.addAsignatura(asig3);
			primerDam.addAsignatura(asig);
			primerDam.addAsignatura(asig1);
			ti.addCurso(primerDam);
			ti.addCurso(segundoDam);
			horarioServicio.edit(hora1);horarioServicio.edit(hora2);horarioServicio.edit(hora3);horarioServicio.edit(hora4);
			horarioServicio.edit(hora5);horarioServicio.edit(hora6);horarioServicio.edit(hora7);horarioServicio.edit(hora8);
			horarioServicio.edit(hora9);
			asignaturaServicio.edit(asig);
			asignaturaServicio.edit(asig1);
			asignaturaServicio.edit(asig2);
			asignaturaServicio.edit(asig3);
			cursoServicio.edit(primerDam);
			cursoServicio.edit(segundoDam);
			tituloServicio.edit(ti);
			primerDam.addAlumno((Alumno) u);
			cursoServicio.edit(primerDam);
			servicio.edit(u);
		};
	}
}
