package com.utn.repositories;

import com.utn.config.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;
import java.util.Optional;

public abstract class BaseRepository<T> {

    protected Class<T> entityClass;
    protected EntityManager em;

    public BaseRepository(Class<T> entityClass, EntityManager em) {
        this.entityClass = entityClass;
        this.em = em;
    }

    // GUARDAR O ACTUALIZAR
    public T guardar(T entity) {

        EntityTransaction tx = em.getTransaction();

        try {

            tx.begin();

            entity = em.merge(entity);

            tx.commit();

            return entity;

        } catch (Exception e) {

            if (tx.isActive()) {
                tx.rollback();
            }

            throw e;
        }
    }

    // BUSCAR POR ID
    public Optional<T> buscarPorId(Long id) {

        T entity = em.find(entityClass, id);

        return Optional.ofNullable(entity);
    }

    // LISTAR ACTIVOS
    public List<T> listarActivos() {

        String jpql = "FROM " +
                entityClass.getSimpleName() +
                " e WHERE e.eliminado = false";

        return em.createQuery(jpql, entityClass)
                .getResultList();
    }

    // ELIMINAR LOGICO
    public boolean eliminarLogico(Long id) {

        EntityTransaction tx = em.getTransaction();

        try {

            T entity = em.find(entityClass, id);

            if (entity == null) {
                return false;
            }

            tx.begin();

            entityClass
                    .getMethod("setEliminado", Boolean.class)
                    .invoke(entity, true);

            em.merge(entity);

            tx.commit();

            return true;

        } catch (Exception e) {

            if (tx.isActive()) {
                tx.rollback();
            }

            throw new RuntimeException(e);
        }
    }
}