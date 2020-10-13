package com.clases.dam.gestion.salesianos.Servicios;

import com.clases.dam.gestion.salesianos.Curso.CursoRepository;
import com.clases.dam.gestion.salesianos.Servicios.Base.IBaseService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public abstract class   BaseServiceImpl <T, ID, R extends JpaRepository<T, ID>> implements IBaseService<T, ID> {


    protected R repositorio;

    public BaseServiceImpl(R repo) {
        this.repositorio = repo;
    }

    @Override
    public T save(T t) {
        return repositorio.save(t);
    }

    @Override
    public Optional<T> findById(ID id) {
        return repositorio.findById(id);
    }

    @Override
    public List<T> findAll() {
        return repositorio.findAll();
    }

    @Override
    public T edit(T t) {
        return repositorio.save(t);
    }

    @Override
    public void delete(T t) {
        repositorio.delete(t);
    }

    @Override
    public void deleteById(ID id) {
        repositorio.deleteById(id);
    }

}
