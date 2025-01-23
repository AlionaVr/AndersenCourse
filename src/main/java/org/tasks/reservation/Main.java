package org.tasks.reservation;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {
    private static EntityManagerFactory entityManagerFactory;

    public static void main(String[] args) {
        MenuLauncher menuLauncher = new MenuLauncher(new Repository());
        menuLauncher.showMainMenu();
    }

    public static EntityManagerFactory getSessionFactory() {
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
}
