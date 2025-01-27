package org.tasks.reservation.service.impl;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.tasks.reservation.SpaceManager;
import org.tasks.reservation.entities.CoworkingSpace;
import org.tasks.reservation.helper.ExecutorEntityManagerHelper;
import org.tasks.reservation.service.AdminService;

import java.util.Optional;
import java.util.Scanner;

@Service
@RequiredArgsConstructor
public class ConsoleAdminService implements AdminService {
    private final Scanner scanner;
    private final ExecutorEntityManagerHelper executorEntityManagerHelper;
    private final SpaceManager manager;

    @Override
    public Optional<CoworkingSpace> askUserToWriteCoworkingSpaceString() {
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

    @Override
    public void addSpace(CoworkingSpace newSpace) {
        executorEntityManagerHelper.executeWithEntityManager(
                entityManager -> entityManager.persist(newSpace),
                "Coworking space added successfully!",
                "Error adding coworking space: ");

    }

    @Override
    public void removeSpace(int id) {
        executorEntityManagerHelper.executeWithEntityManager(
                entityManager -> findAndRemoveSpace(id, entityManager),
                "DELETED!",
                "Error deleting coworking space: ");
    }

    private void findAndRemoveSpace(int id, EntityManager entityManager) {
        CoworkingSpace spaceToDelete = entityManager.find(CoworkingSpace.class, id);
        if (manager.isSpaceExist(spaceToDelete, entityManager))
            entityManager.remove(spaceToDelete);
    }

    @Override
    public void updateSpace(int idToUpdate) {
        executorEntityManagerHelper.executeWithEntityManager(
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




