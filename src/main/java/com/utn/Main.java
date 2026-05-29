package com.utn;

import com.utn.config.JPAUtil;
import com.utn.entities.*;
import com.utn.repositories.CategoriaRepository;
import com.utn.repositories.ProductoRepository;
import jakarta.persistence.EntityManager;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        EntityManager em = JPAUtil
                .getEntityManagerFactory()
                .createEntityManager();

        CategoriaRepository categoriaRepository =
                new CategoriaRepository(em);

        ProductoRepository productoRepository =
                new ProductoRepository(em);

        Scanner sc = new Scanner(System.in);

        try {

            // =========================
            // CATEGORIAS
            // =========================

            Categoria bebidas = new Categoria();
            bebidas.setNombre("Bebidas");

            Categoria pizzas = new Categoria();
            pizzas.setNombre("Pizzas");

            Categoria hamburguesas = new Categoria();
            hamburguesas.setNombre("Hamburguesas");

            bebidas = categoriaRepository.guardar(bebidas);
            pizzas = categoriaRepository.guardar(pizzas);
            hamburguesas = categoriaRepository.guardar(hamburguesas);

            // =========================
            // PRODUCTOS
            // =========================

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

            p1 = productoRepository.guardar(p1);
            p2 = productoRepository.guardar(p2);
            p3 = productoRepository.guardar(p3);
            p4 = productoRepository.guardar(p4);
            p5 = productoRepository.guardar(p5);
            p6 = productoRepository.guardar(p6);
            p7 = productoRepository.guardar(p7);
            p8 = productoRepository.guardar(p8);
            p9 = productoRepository.guardar(p9);
            p10 = productoRepository.guardar(p10);

            // =========================
            // USUARIOS
            // =========================

            Usuario u1 = new Usuario();
            u1.setNombre("Octavio");
            u1.setEmail("octa@gmail.com");

            Usuario u2 = new Usuario();
            u2.setNombre("Jorge");
            u2.setEmail("jorge@gmail.com");

            em.getTransaction().begin();

            em.persist(u1);
            em.persist(u2);

            // =========================
            // PEDIDOS
            // =========================

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

            // =========================
            // DETALLES
            // =========================

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

            em.getTransaction().commit();

            // =========================
            // UPDATE
            // =========================

            p1.setPrecio(3000.0);
            p4.setStock(20);

            productoRepository.guardar(p1);
            productoRepository.guardar(p4);

            // =========================
            // BUSQUEDA POR ID
            // =========================

            Usuario usuarioBuscado = em.find(Usuario.class, u1.getId());

            System.out.println("\nBUSQUEDA POR ID");
            System.out.println(usuarioBuscado);

            // =========================
            // BUSQUEDA POR MAIL
            // =========================

            try {

                Usuario usuarioMail = em.createQuery(
                        "SELECT u FROM Usuario u WHERE u.email = :mail",
                        Usuario.class
                )
                        .setParameter("mail", "octa@gmail.com")
                        .getSingleResult();

                System.out.println("\nBUSQUEDA POR MAIL");
                System.out.println(usuarioMail);

            } catch (Exception e) {

                System.out.println("No se encontro usuario con ese mail");
            }

            // =========================
            // DELETE LOGICO
            // =========================

            Producto productoEliminar = new Producto();

            productoEliminar.setNombre("Producto Temporal");
            productoEliminar.setPrecio(1000.0);
            productoEliminar.setStock(1);
            productoEliminar.setCategoria(bebidas);

            productoEliminar = productoRepository.guardar(productoEliminar);

            productoRepository.eliminarLogico(productoEliminar.getId());

            System.out.println("\nTRANSACCION REALIZADA CON EXITO");

            // =========================
            // MENU
            // =========================

            int opcion = -1;

            do {

                System.out.println("\n===== MENU =====");
                System.out.println("1 - Listar productos");
                System.out.println("2 - Buscar producto por nombre");
                System.out.println("3 - Buscar productos con stock menor a");
                System.out.println("4 - Eliminar producto logicamente");
                System.out.println("0 - Salir");
                System.out.print("Ingrese una opcion: ");

                if (sc.hasNextInt()) {

                    opcion = sc.nextInt();
                    sc.nextLine();

                } else {

                    System.out.println("Ingrese un numero valido");
                    sc.nextLine();
                    opcion = -1;
                }

                switch (opcion) {

                    case 1:

                        List<Producto> productos =
                                productoRepository.listarActivos();

                        System.out.println("\nPRODUCTOS ACTIVOS:");

                        for (Producto p : productos) {

                            System.out.println(
                                    p.getId() + " - " +
                                            p.getNombre() + " - $" +
                                            p.getPrecio()
                            );
                        }

                        break;

                    case 2:

                        System.out.print("Ingrese nombre a buscar: ");
                        String nombre = sc.nextLine();

                        List<Producto> productosNombre =
                                productoRepository.buscarPorNombre(nombre);

                        System.out.println("\nRESULTADOS:");

                        for (Producto p : productosNombre) {

                            System.out.println(
                                    p.getId() + " - " +
                                            p.getNombre()
                            );
                        }

                        break;

                    case 3:

                        System.out.print("Ingrese stock maximo: ");

                        int stock = sc.nextInt();
                        sc.nextLine();

                        List<Producto> productosStock =
                                productoRepository.buscarConStockMenorA(stock);

                        System.out.println("\nPRODUCTOS EN BAJO STOCK:");

                        for (Producto p : productosStock) {

                            System.out.println(
                                    p.getNombre() +
                                            " - Stock: " +
                                            p.getStock()
                            );
                        }

                        break;

                    case 4:

                        System.out.print("Ingrese ID del producto a eliminar: ");

                        Long idEliminar = sc.nextLong();
                        sc.nextLine();

                        boolean eliminado =
                                productoRepository.eliminarLogico(idEliminar);

                        if (eliminado) {

                            System.out.println("Producto eliminado logicamente");

                        } else {

                            System.out.println("Producto no encontrado");
                        }

                        break;

                    case 0:

                        System.out.println("Programa finalizado");
                        break;

                    default:

                        System.out.println("Opcion invalida");
                }

            } while (opcion != 0);

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            em.close();
            sc.close();
        }
    }
}