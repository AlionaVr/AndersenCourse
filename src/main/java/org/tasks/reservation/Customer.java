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

    protected void reserve(int numberChosenSpace) {

        CoworkingSpace coworkingSpaceToBeBooked = repository.getSpaces().get(numberChosenSpace - 1);

        System.out.println("Please, enter booking details: ");
        String bookingDetails = scanner.nextLine().trim();

        CoworkingSpaceBooking coworkingSpaceBooking = new CoworkingSpaceBooking(coworkingSpaceToBeBooked, bookingDetails);
        coworkingSpaceToBeBooked.setAvailability(false);
        manager.addSpaceToMyReservation(coworkingSpaceBooking);
        System.out.println("RESERVED!");
    }

    protected void cancelReservation(int numberChosenSpace) {
        CoworkingSpaceBooking canceledSpaceBooking = manager.removeSpaceFromMyReservation(numberChosenSpace - 1);
        canceledSpaceBooking.getCoworkingSpace().setAvailability(true);
        System.out.println("CANCELED!");
    }
}
