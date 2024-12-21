package org.tasks.reservation;

import java.util.LinkedList;
import java.util.Scanner;

import static org.tasks.reservation.Main.spaces;
import static org.tasks.reservation.SpaceManager.myReservations;
import static org.tasks.reservation.SpaceManager.showMyReservation;

public class Customer {
    public Scanner scanner = new Scanner(System.in);

    public void reserve() {
        SpaceManager.showSpaces(CoworkingSpace::isAvailable);
        System.out.println("Please, choose one available coworking spaces");

        int numberChosenSpace = Integer.parseInt(scanner.nextLine());
        while (numberChosenSpace <= 0 || numberChosenSpace > spaces.size()) {
            System.out.println("Invalid input. Please, enter your input data");
            numberChosenSpace = Integer.parseInt(scanner.nextLine());
        }
        CoworkingSpace coworkingSpaceToBeBooked = spaces.get(numberChosenSpace - 1);

        System.out.println("Please, enter booking details: ");
        String bookingDetails = scanner.nextLine();

        CoworkingSpaceBooking coworkingSpaceBooking = new CoworkingSpaceBooking(coworkingSpaceToBeBooked, bookingDetails);
        coworkingSpaceToBeBooked.setAvailability(false);

        myReservations.add(coworkingSpaceBooking); // probably move to SpaceManager

        System.out.println("RESERVED!");
    }

    public void cancelReservation() {

        System.out.println("It's list of your reservations: ");
        showMyReservation();
        System.out.println("Please, choose the number of space, that you would like to cancel. ");

        int numberChosenSpace = Integer.parseInt(scanner.nextLine());
        while (numberChosenSpace <= 0 || numberChosenSpace > spaces.size()) {
            System.out.println("Invalid input.Please, check your input data");
            numberChosenSpace = Integer.parseInt(scanner.nextLine());
        }

        CoworkingSpaceBooking canceledSpaceBooking = myReservations.remove(numberChosenSpace - 1);
        canceledSpaceBooking.getCoworkingSpace().setAvailability(true);

        System.out.println("CANCELED!");
    }

    private int getValidInputNumber(int maxNumber) {
        try {
            int number = Integer.parseInt(scanner.nextLine());
            if (number <= 0 || number > maxNumber) {
                System.out.printf("Invalid input. Please choose a valid number between 1 and %d.", maxNumber);
                return -1;
            }
            return number;
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
            return -1;
        }
    }
}
