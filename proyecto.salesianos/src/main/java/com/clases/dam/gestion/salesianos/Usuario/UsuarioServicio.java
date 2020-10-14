package com.clases.dam.gestion.salesianos.Usuario;

import com.clases.dam.gestion.salesianos.Servicios.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioServicio extends BaseServiceImpl<Usuario,Long,UsuarioRepository> {

    public UsuarioServicio(UsuarioRepository repo) {
        super(repo);
    }
    public Optional<Usuario> buscarPorEmail(String email) {
        return repositorio.findFirstByEmail(email);
    }
}
