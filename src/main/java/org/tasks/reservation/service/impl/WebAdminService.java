package org.tasks.reservation.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.tasks.reservation.entity.CoworkingSpace;
import org.tasks.reservation.helper.ExecutorEntityManagerHelper;
import org.tasks.reservation.service.AdminService;

import java.util.Optional;


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
                    CoworkingSpace spaceToDelete = Optional.ofNullable(entityManager.find(CoworkingSpace.class, id))
                            .orElseThrow(() -> new RuntimeException("Space doesn't exist."));
                    if (!spaceToDelete.isAvailability()) {
                        throw new RuntimeException("You can't delete it. Space is not Available.");
                    }
                    entityManager.remove(spaceToDelete);
                    System.out.println("Coworking space with ID " + id + " was deleted.");
                },
                "DELETED!",
                "Error deleting coworking space: "
        );
    }

    public void updateSpace(int idToUpdate, CoworkingSpace updatedSpace) {
        executorEntityManagerHelper.executeWithEntityManager(
                entityManager -> {
                    CoworkingSpace spaceToUpdate = Optional.ofNullable(entityManager.find(CoworkingSpace.class, idToUpdate))
                            .orElseThrow(() -> new RuntimeException("Space doesn't exist."));
                    if (!spaceToUpdate.isAvailability()) {
                        throw new RuntimeException("You can't update it. Space is not Available.");
                    }
                    spaceToUpdate.setName(updatedSpace.getName());
                    spaceToUpdate.setType(updatedSpace.getType());
                    spaceToUpdate.setPrice(updatedSpace.getPrice());
                    spaceToUpdate.setAvailability(updatedSpace.isAvailable());
                    System.out.println("Coworking space with ID " + idToUpdate + " updated successfully.");
                },
                "UPDATED!",
                "Error updating coworking space: "
        );
    }
}
