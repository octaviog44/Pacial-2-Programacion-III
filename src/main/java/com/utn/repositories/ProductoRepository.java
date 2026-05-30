package com.utn.repositories;

import com.utn.entities.Producto;
import jakarta.persistence.EntityManager;

import java.util.List;

public class ProductoRepository extends BaseRepository<Producto> {

    public ProductoRepository(EntityManager em) {
        super(Producto.class, em);
    }

    // BUSCAR POR NOMBRE
    public List<Producto> buscarPorNombre(String nombre) {

        return em.createQuery(
                "FROM Producto p WHERE p.nombre LIKE :nombre AND p.eliminado = false",
                Producto.class
        )
                .setParameter("nombre", "%" + nombre + "%")
                .getResultList();
    }

    // BUSCAR PRODUCTOS CON STOCK MENOR A
    public List<Producto> buscarConStockMenorA(int stock) {

        return em.createQuery(
                "FROM Producto p WHERE p.stock < :stock AND p.eliminado = false",
                Producto.class
        )
                .setParameter("stock", stock)
                .getResultList();
    }

    // BUSCAR PRODUCTOS POR CATEGORIA
    public List<Producto> buscarPorCategoria(Long categoriaId) {

        return em.createQuery(
                "FROM Producto p WHERE p.categoria.id = :categoriaId AND p.eliminado = false",
                Producto.class
        )
                .setParameter("categoriaId", categoriaId)
                .getResultList();
    }

    public List<Producto> buscarPorCategoria(String nombreCategoria) {

    return em.createQuery(
            "SELECT p FROM Producto p " +
            "WHERE p.categoria.nombre = :categoria " +
            "AND p.eliminado = false",
            Producto.class)
            .setParameter("categoria", nombreCategoria)
            .getResultList();
        }
    }

