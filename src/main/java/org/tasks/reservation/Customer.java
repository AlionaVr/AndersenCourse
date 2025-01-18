package org.tasks.reservation;

import java.util.Scanner;


public class Customer {
    private final SpaceManager manager;
    private final Scanner scanner;
    private final Repository repository;

    public Customer(Repository repository, Scanner scanner, SpaceManager manager) {
        this.repository = repository;
        this.scanner = scanner;
        this.manager = manager;
    }

    protected void reserve(int id) {

        System.out.println("Please, enter booking details: ");
        String bookingDetails = scanner.nextLine().trim();

        manager.changeSpaceAvailability(false, id);
        manager.addSpaceToMyReservation(id, bookingDetails);
        System.out.println("RESERVED!");
    }

    protected void cancelReservation(int id) {
        manager.removeSpaceFromMyReservation(id);
        manager.changeSpaceAvailability(true, id);
        System.out.println("CANCELED!");
    }
}
