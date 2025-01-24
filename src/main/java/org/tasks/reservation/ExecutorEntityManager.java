package org.tasks.reservation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.function.Consumer;

public class ExecutorEntityManager {
    private EntityManagerFactory entityManagerFactory;

    protected EntityManagerFactory getSessionFactory() {
        if (entityManagerFactory == null) {
            try {
                entityManagerFactory = Persistence.createEntityManagerFactory("my-persistence-unit");
            } catch (Throwable ex) {
                System.err.println("SessionFactory creation failed: " + ex);
                ex.printStackTrace();
            }
        }
        return entityManagerFactory;
    }

    protected void executeWithEntityManager(Consumer<EntityManager> action, String successMessage, String errorMessage) {
        try (EntityManager entityManager = getSessionFactory().createEntityManager()) {
            entityManager.getTransaction().begin();

            action.accept(entityManager);

            entityManager.getTransaction().commit();
            System.out.println(successMessage);
        } catch (Exception e) {
            System.err.println(errorMessage + e.getMessage());
            e.printStackTrace();
        }
    }
}
