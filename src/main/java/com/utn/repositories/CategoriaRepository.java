package com.utn.repositories;

import com.utn.entities.Categoria;
import jakarta.persistence.EntityManager;

public class CategoriaRepository extends BaseRepository<Categoria> {

    public CategoriaRepository(EntityManager em) {
        super(Categoria.class, em);
    }
}