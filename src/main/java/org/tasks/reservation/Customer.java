package org.tasks.reservation;

import java.util.Scanner;


public class Customer {
    private final SpaceManager manager;
    private final Scanner scanner = new Scanner(System.in);
    private final Repository repository;

    public Customer(Repository repository) {
        this.repository = repository;
        this.manager = new SpaceManager(repository, scanner);
    }

    protected void reserve() {
        if (repository.getSpaces().isNotEmpty()) {
            System.out.println("Choose one available coworking spaces");

            int numberChosenSpace = manager.getValidChosenSpace(repository.getSpaces().size());

            CoworkingSpace coworkingSpaceToBeBooked = repository.getSpaces().get(numberChosenSpace - 1);

            System.out.println("Please, enter booking details: ");
            String bookingDetails = scanner.nextLine().trim();

            CoworkingSpaceBooking coworkingSpaceBooking = new CoworkingSpaceBooking(coworkingSpaceToBeBooked, bookingDetails);

            coworkingSpaceToBeBooked.setAvailability(false);

            manager.addSpaceToMyReservation(coworkingSpaceBooking);

            System.out.println("RESERVED!");
        }

    }

    protected void cancelReservation() {
        if (repository.getMyReservations().isNotEmpty()) {
            System.out.println("Choose the number of space, that you would like to cancel. ");

            int numberChosenSpace = manager.getValidChosenSpace(repository.getMyReservations().size());

            CoworkingSpaceBooking canceledSpaceBooking = manager.removeSpaceFromMyReservation(numberChosenSpace - 1);
            canceledSpaceBooking.getCoworkingSpace().setAvailability(true);

            System.out.println("CANCELED!");
        }
    }
}
