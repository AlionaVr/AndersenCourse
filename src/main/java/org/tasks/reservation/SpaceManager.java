package org.tasks.reservation;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.tasks.reservation.entities.CoworkingSpace;
import org.tasks.reservation.entities.CoworkingSpaceBooking;
import org.tasks.reservation.repository.CoworkingSpaceBookingRepository;
import org.tasks.reservation.repository.CoworkingSpaceRepository;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Predicate;

@Component
@RequiredArgsConstructor
public class SpaceManager {
    private final Scanner scanner;
    private final CoworkingSpaceRepository coworkingSpaceRepository;
    private final CoworkingSpaceBookingRepository coworkingSpaceBookingRepository;

    public void showSpaces(Predicate<CoworkingSpace> availabilityFilter) {
        List<CoworkingSpace> filteredSpaces = coworkingSpaceRepository.getSpaces().stream()
                .filter(availabilityFilter)
                .toList();

        if (filteredSpaces.isEmpty()) {
            System.out.println("Empty list.");
        } else filteredSpaces.forEach(space -> {
            System.out.println("\n-------------------------------------------------");
            System.out.println(space.toString());
            System.out.println("-------------------------------------------------");
        });
    }

    public void showMyReservation() {
        List<CoworkingSpaceBooking> myReservations = coworkingSpaceBookingRepository.getMyReservations().stream().toList();

        if (myReservations.isEmpty()) {
            System.out.println("Empty list.");
        } else myReservations.forEach(space -> {
            System.out.println("\n-------------------------------------------------");
            System.out.println(space.toString());
            System.out.println("-------------------------------------------------");
        });
    }

    public Optional<Integer> askUserToWriteNumberOfSpace() {
        try {
            int inputNumber = Integer.parseInt(scanner.nextLine());
            return Optional.of(inputNumber);
        } catch (NumberFormatException e) {
            System.err.println("Input contains letters or is not a valid number.");
        }
        return Optional.empty();
    }

    public boolean isSpaceExist(CoworkingSpace space, EntityManager entityManager) {
        if (space == null) {
            System.out.println("Space is not found");
            entityManager.getTransaction().rollback();
            return false;
        } else return true;
    }
}