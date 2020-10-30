package com.clases.dam.gestion.salesianos;

import com.clases.dam.gestion.salesianos.Alumno.Alumno;
import com.clases.dam.gestion.salesianos.Asignatura.Asignatura;
import com.clases.dam.gestion.salesianos.Asignatura.AsignaturaServicio;
import com.clases.dam.gestion.salesianos.Curso.Curso;
import com.clases.dam.gestion.salesianos.Curso.CursoServicio;
import com.clases.dam.gestion.salesianos.Horario.Horario;
import com.clases.dam.gestion.salesianos.Horario.HorarioServicio;
import com.clases.dam.gestion.salesianos.Profesor.Profesor;
import com.clases.dam.gestion.salesianos.Servicios.upload.StorageService;
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
			, HorarioServicio horarioServicio, StorageService storageService) {
		return args -> {
			storageService.init();

			/*Usuario a = new Profesor("Luismi","Lopez",
					"Luismi@gmail.com",passwordEncoder.encode("1234"),true,false);

			servicio.save(a);

			Usuario angel = new Profesor("Angel","Naranjo",
					"admin",passwordEncoder.encode("admin"),true,true);

			servicio.save(angel);*/
		};
	}
}
