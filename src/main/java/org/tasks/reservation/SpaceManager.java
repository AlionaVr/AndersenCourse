package org.tasks.reservation;

import jakarta.persistence.EntityManager;
import org.tasks.reservation.entities.CoworkingSpace;
import org.tasks.reservation.entities.CoworkingSpaceBooking;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Predicate;


public class SpaceManager {
    private final Scanner scanner;
    private final Repository repository;

    public SpaceManager(Repository repository, Scanner scanner) {
        this.repository = repository;
        this.scanner = scanner;
    }

    protected void showSpaces(Predicate<CoworkingSpace> availabilityFilter) {
        List<CoworkingSpace> filteredSpaces = repository.getSpaces().stream()
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

    protected void showMyReservation() {
        List<CoworkingSpaceBooking> myReservations = repository.getMyReservations().stream().toList();

        if (myReservations.isEmpty()) {
            System.out.println("Empty list.");
        } else myReservations.forEach(space -> {
            System.out.println("\n-------------------------------------------------");
            System.out.println(space.toString());
            System.out.println("-------------------------------------------------");
        });
    }

    protected Optional<Integer> askUserToWriteNumberOfSpace() {
        try {
            int inputNumber = Integer.parseInt(scanner.nextLine());
            return Optional.of(inputNumber);
        } catch (NumberFormatException e) {
            System.err.println("Input contains letters or is not a valid number.");
        }
        return Optional.empty();
    }

    protected boolean isSpaceExist(CoworkingSpace space, EntityManager entityManager) {
        if (space == null) {
            System.out.println("Space is not found");
            entityManager.getTransaction().rollback();
            return false;
        } else return true;
    }
}