package org.tasks.reservation;

import jakarta.persistence.EntityManager;
import org.tasks.reservation.entities.CoworkingSpace;

import java.util.Optional;
import java.util.Scanner;

public class Admin {

    private final Scanner scanner;
    ExecutorEntityManager executorEntityManager = new ExecutorEntityManager();

    SpaceManager manager;

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
        executorEntityManager.executeWithEntityManager(
                entityManager -> entityManager.persist(newSpace),
                "Coworking space added successfully!",
                "Error adding coworking space: ");

    }

    protected void removeSpace(int id) {
        executorEntityManager.executeWithEntityManager(
                entityManager -> findAndRemoveSpace(id, entityManager),
                "DELETED!",
                "Error deleting coworking space: ");
    }

    private void findAndRemoveSpace(int id, EntityManager entityManager) {
        CoworkingSpace spaceToDelete = entityManager.find(CoworkingSpace.class, id);
        if (manager.isSpaceExist(spaceToDelete, entityManager))
            entityManager.remove(spaceToDelete);
    }

    protected void updateSpace(int idToUpdate) {
        executorEntityManager.executeWithEntityManager(
                entityManager -> updateCoworkingSpaceBasedOnUserInput(idToUpdate, entityManager),
                "UPDATED!",
                "Error updating coworking space: "
        );
    }

    private void updateCoworkingSpaceBasedOnUserInput(int idToUpdate, EntityManager entityManager) {
        CoworkingSpace spaceToUpdate = entityManager.find(CoworkingSpace.class, idToUpdate);
        if (manager.isSpaceExist(spaceToUpdate, entityManager)) {
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
        }
    }
}




