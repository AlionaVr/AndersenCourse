package org.tasks.reservation;

import jakarta.persistence.EntityManager;
import org.tasks.reservation.entities.CoworkingSpace;

import java.util.Optional;
import java.util.Scanner;

public class Admin {

    private final Scanner scanner;

    public Admin(Scanner scanner) {
        this.scanner = scanner;
    }

    public Admin(EntityManager entityManager, Scanner scanner) {
        this.scanner = scanner;
    }

    protected Optional<CoworkingSpace> askUserToWriteCoworkingSpaceString() {
        System.out.println("Enter name of space, type and price: \n ***in format: Name,type,price ");
        try {
            String spaceStringSeparatedByComma = scanner.nextLine();
            return Optional.of(spaceStringSeparatedByComma.split(","))
                    .filter(elements -> elements.length == 3)
                    .map(elements -> new CoworkingSpace(elements[0], elements[1], Double.parseDouble(elements[2])));
        } catch (NumberFormatException e) {
            System.out.println("Invalid price format. Please, enter a valid number for price.");
        }
        return Optional.empty();
    }

    protected void addSpace(CoworkingSpace newSpace) {
        try (EntityManager entityManager = Main.getSessionFactory().createEntityManager()) {
            entityManager.getTransaction().begin();
            entityManager.persist(newSpace);
            entityManager.getTransaction().commit();
            System.out.println("Coworking space added successfully!");
        } catch (Exception e) {
            System.err.println("Error adding coworking space: " + e.getMessage());
            e.printStackTrace();
        }
    }

    protected void removeSpace(int id) {
        try (EntityManager entityManager = Main.getSessionFactory().createEntityManager()) {
            entityManager.getTransaction().begin();
            CoworkingSpace spaceToDelete = entityManager.find(CoworkingSpace.class, id);
            if (spaceToDelete == null) {
                System.out.println("No space found with ID: " + id);
                entityManager.getTransaction().rollback();
            } else entityManager.remove(spaceToDelete);
            System.out.println("DELETED!");
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("Error deleting coworking space: " + e.getMessage());
            e.printStackTrace();
        }
    }

    protected void updateSpace(int idToUpdate) {

        try (EntityManager entityManager = Main.getSessionFactory().createEntityManager()) {
            entityManager.getTransaction().begin();
            CoworkingSpace spaceToUpdate = entityManager.find(CoworkingSpace.class, idToUpdate);
            if (spaceToUpdate == null) {
                System.out.println("No space found with ID: " + idToUpdate);
                entityManager.getTransaction().rollback();
            } else {
                Optional<CoworkingSpace> coworkingSpace = askUserToWriteCoworkingSpaceString();
                while (coworkingSpace.isEmpty()) {
                    System.out.println("Invalid input. Please enter data in format: Name,type,price");
                    coworkingSpace = askUserToWriteCoworkingSpaceString();
                }
                CoworkingSpace newSpaceData = coworkingSpace.get();

                spaceToUpdate.setName(newSpaceData.getName());
                spaceToUpdate.setType(newSpaceData.getType());
                spaceToUpdate.setPrice(newSpaceData.getPrice());
                spaceToUpdate.setAvailability(newSpaceData.isAvailable());

                entityManager.getTransaction().commit();
                System.out.println("UPDATED!");
            }
        } catch (Exception e) {
            System.err.println("Error updating coworking space: " + e.getMessage());
            e.printStackTrace();
        }
    }
}




