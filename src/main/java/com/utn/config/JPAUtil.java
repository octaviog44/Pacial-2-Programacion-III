package com.utn.config;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {

    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("miUnidad");

    public static EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }
}