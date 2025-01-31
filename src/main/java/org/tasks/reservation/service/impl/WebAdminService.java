package org.tasks.reservation.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.tasks.reservation.entity.CoworkingSpace;
import org.tasks.reservation.helper.ExecutorEntityManagerHelper;
import org.tasks.reservation.service.AdminService;


@Service("webAdminService")
@RequiredArgsConstructor
public class WebAdminService implements AdminService {
    private final ExecutorEntityManagerHelper executorEntityManagerHelper;

    @Override
    public void addSpace(CoworkingSpace newSpace) {
        executorEntityManagerHelper.executeWithEntityManager(
                entityManager -> entityManager.persist(newSpace),
                "Coworking space added successfully!",
                "Error adding coworking space: "
        );
    }

    @Override
    public void removeSpace(int id) {
        executorEntityManagerHelper.executeWithEntityManager(
                entityManager -> {
                    CoworkingSpace spaceToDelete = entityManager.find(CoworkingSpace.class, id);
                    if (spaceToDelete != null) {
                        entityManager.remove(spaceToDelete);
                        System.out.println("Coworking space with ID " + id + " was deleted.");
                    } else {
                        System.out.println("Coworking space with ID " + id + " does not exist.");
                    }
                },
                "DELETED!",
                "Error deleting coworking space: "
        );
    }

    public void updateSpace(int idToUpdate, CoworkingSpace updatedSpace) {
        executorEntityManagerHelper.executeWithEntityManager(
                entityManager -> {
                    CoworkingSpace spaceToUpdate = entityManager.find(CoworkingSpace.class, idToUpdate);
                    if (spaceToUpdate != null) {
                        spaceToUpdate.setName(updatedSpace.getName());
                        spaceToUpdate.setType(updatedSpace.getType());
                        spaceToUpdate.setPrice(updatedSpace.getPrice());
                        spaceToUpdate.setAvailability(updatedSpace.isAvailable());
                        System.out.println("Coworking space with ID " + idToUpdate + " updated successfully.");
                    } else {
                        System.out.println("Coworking space with ID " + idToUpdate + " does not exist.");
                    }
                },
                "UPDATED!",
                "Error updating coworking space: "
        );
    }
}
