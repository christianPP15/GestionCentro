package com.clases.dam.gestion.salesianos.proveedorId;

import com.clases.dam.gestion.salesianos.Servicios.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ProveedorIdServicio extends BaseServiceImpl<ProveedorId,Long,ProveedorIdRepository> {

    public ProveedorIdServicio(ProveedorIdRepository repo) {
        super(repo);
    }
}
