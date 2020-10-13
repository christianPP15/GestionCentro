package com.clases.dam.gestion.salesianos.Servicios.Base;

import java.util.List;
import java.util.Optional;

public interface IBaseService <T, ID>{
    T save(T t);

    Optional findById(ID id);

    List<T> findAll();

    T edit(T t);

    void delete(T t);

    void deleteById(ID id);
}
