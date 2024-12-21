package org.tasks.reservation;

import java.util.Scanner;

import static org.tasks.reservation.Main.spaces;

public class Customer {
    private final SpaceManager manager = new SpaceManager();
    private final Scanner scanner = new Scanner(System.in);

    protected void reserve() {
        manager.showSpaces(CoworkingSpace::isAvailable);
        System.out.println("Please, choose one available coworking spaces");

        int numberChosenSpace = manager.getValidInputNumber(Integer.parseInt(scanner.nextLine()), spaces.size());

        CoworkingSpace coworkingSpaceToBeBooked = spaces.get(numberChosenSpace - 1);

        System.out.println("Please, enter booking details: ");
        String bookingDetails = scanner.nextLine();

        CoworkingSpaceBooking coworkingSpaceBooking = new CoworkingSpaceBooking(coworkingSpaceToBeBooked, bookingDetails);

        coworkingSpaceToBeBooked.setAvailability(false);

        manager.addSpaceToMyReservation(coworkingSpaceBooking);

        System.out.println("RESERVED!");
    }

    protected void cancelReservation() {
        System.out.println("It's list of your reservations: ");
        manager.showMyReservation();
        System.out.println("Please, choose the number of space, that you would like to cancel. ");

        int numberChosenSpace = manager.getValidInputNumber(Integer.parseInt(scanner.nextLine()), spaces.size());

        CoworkingSpaceBooking canceledSpaceBooking = manager.removeSpaceFromMyReservation(numberChosenSpace - 1);
        canceledSpaceBooking.getCoworkingSpace().setAvailability(true);

        System.out.println("CANCELED!");
    }


}
