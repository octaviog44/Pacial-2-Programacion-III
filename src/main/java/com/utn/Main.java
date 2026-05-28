package com.utn;

import com.utn.config.JPAUtil;
import com.utn.entities.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        EntityManager em = JPAUtil
                .getEntityManagerFactory()
                .createEntityManager();

        EntityTransaction tx = em.getTransaction();

        try {

            tx.begin();


            // CATEGORIAS
            Categoria bebidas = new Categoria();
            bebidas.setNombre("Bebidas");

            Categoria pizzas = new Categoria();
            pizzas.setNombre("Pizzas");

            Categoria hamburguesas = new Categoria();
            hamburguesas.setNombre("Hamburguesas");

            em.persist(bebidas);
            em.persist(pizzas);
            em.persist(hamburguesas);

            // PRODUCTOS
            Producto p1 = new Producto();
            p1.setNombre("Coca Cola");
            p1.setPrecio(2500.0);
            p1.setStock(20);
            p1.setCategoria(bebidas);

            Producto p2 = new Producto();
            p2.setNombre("Sprite");
            p2.setPrecio(2300.0);
            p2.setStock(15);
            p2.setCategoria(bebidas);

            Producto p3 = new Producto();
            p3.setNombre("Fanta");
            p3.setPrecio(2200.0);
            p3.setStock(10);
            p3.setCategoria(bebidas);

            Producto p4 = new Producto();
            p4.setNombre("Pizza Muzza");
            p4.setPrecio(12000.0);
            p4.setStock(8);
            p4.setCategoria(pizzas);

            Producto p5 = new Producto();
            p5.setNombre("Pizza Napolitana");
            p5.setPrecio(14000.0);
            p5.setStock(5);
            p5.setCategoria(pizzas);

            Producto p6 = new Producto();
            p6.setNombre("Pizza Especial");
            p6.setPrecio(16000.0);
            p6.setStock(6);
            p6.setCategoria(pizzas);

            Producto p7 = new Producto();
            p7.setNombre("Hamburguesa Simple");
            p7.setPrecio(9000.0);
            p7.setStock(10);
            p7.setCategoria(hamburguesas);

            Producto p8 = new Producto();
            p8.setNombre("Hamburguesa Doble");
            p8.setPrecio(12000.0);
            p8.setStock(9);
            p8.setCategoria(hamburguesas);

            Producto p9 = new Producto();
            p9.setNombre("Hamburguesa Triple");
            p9.setPrecio(15000.0);
            p9.setStock(7);
            p9.setCategoria(hamburguesas);

            Producto p10 = new Producto();
            p10.setNombre("Agua");
            p10.setPrecio(1800.0);
            p10.setStock(25);
            p10.setCategoria(bebidas);

            em.persist(p1);
            em.persist(p2);
            em.persist(p3);
            em.persist(p4);
            em.persist(p5);
            em.persist(p6);
            em.persist(p7);
            em.persist(p8);
            em.persist(p9);
            em.persist(p10);

           
            // USUARIOS
            Usuario u1 = new Usuario();
            u1.setNombre("Octavio");
            u1.setEmail("octa@gmail.com");

            Usuario u2 = new Usuario();
            u2.setNombre("Jorge");
            u2.setEmail("jorge@gmail.com");

            em.persist(u1);
            em.persist(u2);

            
            // PEDIDOS
            Pedido pedido1 = new Pedido();
            pedido1.setFecha(LocalDate.now());
            pedido1.setUsuario(u1);

            Pedido pedido2 = new Pedido();
            pedido2.setFecha(LocalDate.now());
            pedido2.setUsuario(u1);

            Pedido pedido3 = new Pedido();
            pedido3.setFecha(LocalDate.now());
            pedido3.setUsuario(u2);

            em.persist(pedido1);
            em.persist(pedido2);
            em.persist(pedido3);

            // DETALLES
            DetallePedido d1 = new DetallePedido();
            d1.setCantidad(2);
            d1.setPedido(pedido1);
            d1.setProducto(p7);

            DetallePedido d2 = new DetallePedido();
            d2.setCantidad(1);
            d2.setPedido(pedido1);
            d2.setProducto(p1);

            DetallePedido d3 = new DetallePedido();
            d3.setCantidad(1);
            d3.setPedido(pedido2);
            d3.setProducto(p4);

            DetallePedido d4 = new DetallePedido();
            d4.setCantidad(2);
            d4.setPedido(pedido2);
            d4.setProducto(p2);

            DetallePedido d5 = new DetallePedido();
            d5.setCantidad(1);
            d5.setPedido(pedido3);
            d5.setProducto(p8);

            DetallePedido d6 = new DetallePedido();
            d6.setCantidad(3);
            d6.setPedido(pedido3);
            d6.setProducto(p10);

            em.persist(d1);
            em.persist(d2);
            em.persist(d3);
            em.persist(d4);
            em.persist(d5);
            em.persist(d6);

  
            // UPDATE
            p1.setPrecio(3000.0);
            p4.setStock(20);

            em.merge(p1);
            em.merge(p4);


            // BUSCAR POR ID
            Usuario usuarioBuscado = em.find(Usuario.class, u1.getId());

            System.out.println("BUSQUEDA POR ID");
            System.out.println(usuarioBuscado);

            // BUSCAR POR MAIL
            try {

                Usuario usuarioMail = em.createQuery(
                "SELECT u FROM Usuario u WHERE u.email = :mail",
              Usuario.class
    )
                            .setParameter("mail", "octa@gmail.com")
                            .getSingleResult();

                System.out.println("BUSQUEDA POR MAIL");
                System.out.println(usuarioMail);

            } catch (Exception e) {

                System.out.println("No se encontró usuario con ese mail");
}


            
            // DELETE

Producto productoEliminar = new Producto();

productoEliminar.setNombre("Producto Temporal");
productoEliminar.setPrecio(1000.0);
productoEliminar.setStock(1);
productoEliminar.setCategoria(bebidas);

em.persist(productoEliminar);

// eliminar producto
em.remove(productoEliminar);

tx.commit();

System.out.println("TRANSACCION REALIZADA CON EXITO");

} catch (Exception e) {

    tx.rollback();
    e.printStackTrace();

} finally {

    em.close();

}
    }
}