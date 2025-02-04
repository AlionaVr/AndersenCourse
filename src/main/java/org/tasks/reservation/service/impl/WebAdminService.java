package org.tasks.reservation.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tasks.reservation.entity.CoworkingSpace;
import org.tasks.reservation.repository.CoworkingSpaceRepository;
import org.tasks.reservation.service.AdminService;


@Service("webAdminService")
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
        spaceToUpdate.setAvailability(updatedSpace.isAvailable());
        System.out.println("Coworking space with ID " + idToUpdate + " updated successfully.");
        coworkingSpaceRepository.save(spaceToUpdate);
    }
}

// POST /orders
// "{
//     "name": "Order 1",
//     "type": "Type 1",
//     "price": 100.0
// }"

// @PostMapping(path = "/orders")
// public StatusResponseDto addOrder(OrderDto order)

// public class OrderDto {
//     private String name;
//     private String type;
//     private double price;
// }

// public class StatusResponseDto {
//     private boolean success;
// }

// {
//     "success": true
// }