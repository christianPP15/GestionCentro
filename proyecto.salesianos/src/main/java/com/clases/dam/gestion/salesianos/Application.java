package com.clases.dam.gestion.salesianos;

import com.clases.dam.gestion.salesianos.Alumno.Alumno;
import com.clases.dam.gestion.salesianos.Profesor.Profesor;
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
	public CommandLineRunner init(UsuarioServicio servicio, BCryptPasswordEncoder passwordEncoder) {
		return args -> {

			Usuario u = new Alumno("Christian","Payo","cpp",passwordEncoder.encode("1234"));


			servicio.save(u);


			Usuario a = new Profesor("Luismi","Lopez",
					"Luismi@gmail.com",passwordEncoder.encode("1234"),false);

			servicio.save(a);

			Usuario angel = new Profesor("Angel","Naranjo",
					"admin",passwordEncoder.encode("admin"),true);

			servicio.save(angel);

		};
	}
}
