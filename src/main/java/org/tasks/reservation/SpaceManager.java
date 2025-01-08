package org.tasks.reservation;

import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.stream.IntStream;


public class SpaceManager {

    private final Scanner scanner = new Scanner(System.in);
    private final Repository repository;

    public SpaceManager(Repository repository) {
        this.repository = repository;
    }

    protected void showSpaces(Predicate<CoworkingSpace> availabilityFilter) {
        List<CoworkingSpace> filteredSpaces = repository.getSpaces().stream()
                .filter(availabilityFilter)
                .toList();

        if (filteredSpaces.isEmpty()) {
            System.out.println("Empty list.");
        } else {
            IntStream.range(0, filteredSpaces.size())
                    .forEach(i -> showSpaceWithNumber(i + 1, filteredSpaces.get(i)));
        }
    }

    protected void showSpaceWithNumber(int counter, CoworkingSpace space) {
        System.out.println("\n-------------------------------------------------");
        System.out.printf("%d.\n%s", counter, space.toString());
        System.out.println("\n-------------------------------------------------");
    }

    protected void showMyReservation() {
        List<CoworkingSpaceBooking> myReservations = repository.getMyReservations().stream().toList();

        if (myReservations.isEmpty()) {
            System.out.println("Empty list.");
        } else {
            IntStream.range(0, myReservations.size())
                    .forEach(i -> {
                        System.out.println("\n-------------------------------------------------");
                        System.out.println((i + 1) + ".\n" + myReservations.get(i));
                        System.out.println("\n-------------------------------------------------");
                    });
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