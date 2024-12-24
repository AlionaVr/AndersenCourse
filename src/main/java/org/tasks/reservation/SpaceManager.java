package org.tasks.reservation;

import java.util.Scanner;
import java.util.function.Predicate;



public class SpaceManager {

    private final Scanner scanner = new Scanner(System.in);
    private final Repository repository;

    public SpaceManager(Repository repository) {
        this.repository = repository;
    }

    protected void showSpaces(Predicate<CoworkingSpace> availabilityFilter) {
        int counter = 0;
        for (CoworkingSpace space : repository.getSpaces()) {
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
        System.out.println("\n-------------------------------------------------");
    }

    protected void showMyReservation() {
        int counter = 0;
        for (CoworkingSpaceBooking mySpace : repository.getMyReservations()) {
            System.out.println("\n-------------------------------------------------");
            System.out.println(++counter + ".\n" + mySpace);
            System.out.println("\n-------------------------------------------------");
        }

        if (counter == 0) {
            System.out.println("Empty list.");
        }
    }

    protected int getValidInputNumber(int inputNumber, int maxNumber) {
        while (inputNumber <= 0 || inputNumber > maxNumber) {
            System.out.println("Invalid input.Please, check your input data");
            try {
                inputNumber = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Input contains letters or is not a valid number.");
            }
        }
        return inputNumber;
    }

    protected int getValidChosenSpace(int maxNumber) throws SpaceIsNotFound {
        int inputNumber = -1;

        while (inputNumber <= 0 || inputNumber > maxNumber) {
            try {
                System.out.println("Please, enter a number:");
                inputNumber = Integer.parseInt(scanner.nextLine());

                if (inputNumber <= 0 || inputNumber > maxNumber) {
                    throw new SpaceIsNotFound("This space is not found. Please check your input data");
                }
            } catch (SpaceIsNotFound e) {
                System.out.println(e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Input contains letters or is not a valid number.");
            }
        }

        return inputNumber;
    }


    protected void addSpaceToMyReservation(CoworkingSpaceBooking coworkingSpaceBooking) {
        repository.getMyReservations().add(coworkingSpaceBooking);
    }

    protected CoworkingSpaceBooking removeSpaceFromMyReservation(int index) {
        return repository.getMyReservations().remove(index);
    }
}