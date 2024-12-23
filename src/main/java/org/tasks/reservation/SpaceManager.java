package org.tasks.reservation;

import java.util.LinkedList;
import java.util.Scanner;
import java.util.function.Predicate;

import static org.tasks.reservation.Main.spaces;


public class SpaceManager {
    private final LinkedList<CoworkingSpaceBooking> myReservations = new LinkedList<>();
    private final Scanner scanner = new Scanner(System.in);

    protected void showSpaces(Predicate<CoworkingSpace> availabilityFilter) {
        int counter = 0;
        for (CoworkingSpace space : spaces) {
            if (availabilityFilter == null) {
                showSpaceWithNumber(++counter, space);
            } else if (availabilityFilter.test(space)) {
                showSpaceWithNumber(++counter, space);
            }
        }

        if (counter == 0) {
            System.out.println("Empty list.");
        }
    }

    protected void showSpaceWithNumber(int counter, CoworkingSpace space) {
        System.out.println("\n-------------------------------------------------");
        System.out.printf("%d.\n%s", counter, space.toString());
        System.out.println("-------------------------------------------------");
    }

    protected void showMyReservation() {
        int counter = 0;
        for (CoworkingSpaceBooking mySpace : myReservations) {
            System.out.println("\n-------------------------------------------------");
            System.out.println(++counter + ".\n" + mySpace);
            System.out.println("-------------------------------------------------");
        }

        if (counter == 0) {
            System.out.println("Empty list.");
        }
    }

    protected int getValidInputNumber(int inputNumber, int maxNumber) {
        while (inputNumber <= 0 || inputNumber > maxNumber) {
            System.out.println("Invalid input.Please, check your input data");
            inputNumber = Integer.parseInt(scanner.nextLine());
        }
        return inputNumber;
    }

    protected void addSpaceToMyReservation(CoworkingSpaceBooking coworkingSpaceBooking) {
        myReservations.add(coworkingSpaceBooking);
    }

    protected CoworkingSpaceBooking removeSpaceFromMyReservation(int index) {
        return myReservations.remove(index);
    }
}