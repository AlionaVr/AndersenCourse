package org.tasks.reservation.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tasks.reservation.entity.CoworkingSpace;
import org.tasks.reservation.helper.TypeOfSpace;
import org.tasks.reservation.repository.CoworkingSpaceRepository;
import org.tasks.reservation.service.AdminService;


@Service
@RequiredArgsConstructor
public class WebAdminService implements AdminService {

    private final CoworkingSpaceRepository coworkingSpaceRepository;

    @Override
    public void addSpace(CoworkingSpace newSpace) {
        try {
            coworkingSpaceRepository.save(newSpace);
            System.out.println("Coworking space added successfully!");
        } catch (IllegalArgumentException e) {
            System.err.println("Error adding coworking space");
        }
    }

    @Override
    public CoworkingSpace createSpace(String name, String type, double price) {
        CoworkingSpace coworkingSpace = CoworkingSpace.builder()
                .name(name)
                .type(TypeOfSpace.valueOf(type))
                .price(price)
                .availability(true)
                .build();
        System.out.println("Coworking space created!");
        return coworkingSpace;
    }

    @Transactional
    @Override
    public void removeSpace(int id) {
        CoworkingSpace spaceToDelete = coworkingSpaceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Space doesn't exist."));
        if (!spaceToDelete.isAvailability()) {
            throw new RuntimeException("You can't delete it. Space is not Available.");
        }
        coworkingSpaceRepository.delete(spaceToDelete);
        System.out.println("Coworking space with ID " + id + " was deleted.");
    }

    @Transactional
    public void updateSpace(int idToUpdate, CoworkingSpace updatedSpace) {
        CoworkingSpace spaceToUpdate = coworkingSpaceRepository.findById(idToUpdate)
                .orElseThrow(() -> new RuntimeException("Space doesn't exist."));
        if (!spaceToUpdate.isAvailability()) {
            throw new RuntimeException("You can't update it. Space is not Available.");
        }
        spaceToUpdate.setName(updatedSpace.getName());
        spaceToUpdate.setType(updatedSpace.getType());
        spaceToUpdate.setPrice(updatedSpace.getPrice());
        spaceToUpdate.setAvailability(updatedSpace.isAvailability());
        System.out.println("Coworking space with ID " + idToUpdate + " updated successfully.");
        coworkingSpaceRepository.save(spaceToUpdate);
    }
}