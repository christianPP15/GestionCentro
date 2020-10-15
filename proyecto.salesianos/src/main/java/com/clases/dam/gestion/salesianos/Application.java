package com.clases.dam.gestion.salesianos;

import com.clases.dam.gestion.salesianos.Alumno.Alumno;
import com.clases.dam.gestion.salesianos.Asignatura.Asignatura;
import com.clases.dam.gestion.salesianos.Asignatura.AsignaturaServicio;
import com.clases.dam.gestion.salesianos.Curso.Curso;
import com.clases.dam.gestion.salesianos.Curso.CursoServicio;
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

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	@Bean
	public CommandLineRunner init(UsuarioServicio servicio
			, BCryptPasswordEncoder passwordEncoder, TituloServicio tituloServicio
			, CursoServicio cursoServicio , AsignaturaServicio asignaturaServicio) {
		return args -> {

			Usuario u = new Alumno("Christian","Payo","cpp",passwordEncoder.encode("1234"));


			servicio.save(u);


			Usuario a = new Profesor("Luismi","Lopez",
					"Luismi@gmail.com",passwordEncoder.encode("1234"),true,false);

			servicio.save(a);

			Usuario angel = new Profesor("Angel","Naranjo",
					"admin",passwordEncoder.encode("admin"),true,true);

			servicio.save(angel);
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
			segundoDam.addAsignatura(asig2);
			segundoDam.addAsignatura(asig3);
			primerDam.addAsignatura(asig);
			primerDam.addAsignatura(asig1);
			ti.addCurso(primerDam);
			ti.addCurso(segundoDam);
			asignaturaServicio.edit(asig);
			asignaturaServicio.edit(asig1);
			asignaturaServicio.edit(asig2);
			asignaturaServicio.edit(asig3);
			cursoServicio.edit(primerDam);
			cursoServicio.edit(segundoDam);
			tituloServicio.edit(ti);
		};
	}
}
