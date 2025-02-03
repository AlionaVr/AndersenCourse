package org.tasks.reservation.helper;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Component
public class ExecutorEntityManagerHelper {
    private final EntityManagerFactory entityManagerFactory;

    public ExecutorEntityManagerHelper(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public void executeWithEntityManager(Consumer<EntityManager> action, String successMessage, String errorMessage) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();

            action.accept(entityManager);

            entityManager.getTransaction().commit();
            System.out.println(successMessage);
        } catch (Exception e) {
            System.err.println(errorMessage + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public <T> List<T> fetchData(String query, Class<T> classOfSpace) {
        List<T> listOfSpaces = new ArrayList<>();
        executeWithEntityManager(
                entityManager -> listOfSpaces.addAll(entityManager.createQuery(query, classOfSpace).getResultList()),
                "Data fetched successfully",
                "Error fetching data:"
        );
        return listOfSpaces;
    }
}
