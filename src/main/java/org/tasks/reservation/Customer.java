package org.tasks.reservation;

import java.util.Scanner;


public class Customer {
    private final SpaceManager manager;
    private final Scanner scanner;


    public Customer(Scanner scanner, SpaceManager manager) {

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
