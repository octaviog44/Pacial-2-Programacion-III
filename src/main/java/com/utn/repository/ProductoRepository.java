package com.utn.repository;

import com.utn.config.JPAUtil;
import com.utn.entities.Producto;
import jakarta.persistence.EntityManager;

import java.util.List;

public class ProductoRepository
        extends BaseRepository<Producto> {

    public ProductoRepository() {
        super(Producto.class);
    }

    // BUSCAR POR NOMBRE
    public List<Producto> buscarPorNombre(String nombre) {

        EntityManager em = JPAUtil
                .getEntityManagerFactory()
                .createEntityManager();

        try {

            return em.createQuery(
                    "FROM Producto p WHERE p.nombre LIKE :nombre AND p.eliminado = false",
                    Producto.class
            )
                    .setParameter("nombre", "%" + nombre + "%")
                    .getResultList();

        } finally {

            em.close();
        }
    }

    // BUSCAR PRODUCTOS CON STOCK MENOR A
    public List<Producto> buscarConStockMenorA(int stock) {

        EntityManager em = JPAUtil
                .getEntityManagerFactory()
                .createEntityManager();

        try {

            return em.createQuery(
                    "FROM Producto p WHERE p.stock < :stock AND p.eliminado = false",
                    Producto.class
            )
                    .setParameter("stock", stock)
                    .getResultList();

        } finally {

            em.close();
        }
    }
}