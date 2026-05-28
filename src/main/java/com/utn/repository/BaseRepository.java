package com.utn.repository;

import com.utn.config.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;
import java.util.Optional;

public abstract class BaseRepository<T> {

protected Class<T> entityClass;

public BaseRepository(Class<T> entityClass) {
    this.entityClass = entityClass;
}

// GUARDAR O ACTUALIZAR
public T guardar(T entity) {

    EntityManager em = JPAUtil
            .getEntityManagerFactory()
            .createEntityManager();

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

    } finally {

        em.close();

    }
}

// BUSCAR POR ID
public Optional<T> buscarPorId(Long id) {

    EntityManager em = JPAUtil
            .getEntityManagerFactory()
            .createEntityManager();

    try {

        T entity = em.find(entityClass, id);

        return Optional.ofNullable(entity);

    } finally {

        em.close();

    }
}

// LISTAR ACTIVOS
public List<T> listarActivos() {

    EntityManager em = JPAUtil
            .getEntityManagerFactory()
            .createEntityManager();

    try {

        String jpql = "FROM " +
                entityClass.getSimpleName() +
                " e WHERE e.eliminado = false";

        return em.createQuery(jpql, entityClass)
                .getResultList();

    } finally {

        em.close();

    }
}

// ELIMINAR LOGICO
public boolean eliminarLogico(Long id) {

    EntityManager em = JPAUtil
            .getEntityManagerFactory()
            .createEntityManager();

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

    } finally {

        em.close();

    }
}


}
