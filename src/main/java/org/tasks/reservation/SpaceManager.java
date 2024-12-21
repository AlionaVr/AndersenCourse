package org.tasks.reservation;

import java.util.LinkedList;
import java.util.function.Predicate;

import static org.tasks.reservation.Main.spaces;


public class SpaceManager {
    public static LinkedList<CoworkingSpaceBooking> myReservations = new LinkedList<>();

    public static void showSpaces(Predicate<CoworkingSpace> availabilityFilter) {
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

    public static void showSpaceWithNumber(int counter, CoworkingSpace space) {
        System.out.println("\n-------------------------------------------------");
        System.out.printf("%d.\n%s", counter, space.toString());
        System.out.println("\n-------------------------------------------------");
    }

    public static void showMyReservation() {
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
}