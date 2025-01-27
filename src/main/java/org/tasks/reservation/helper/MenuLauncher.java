package org.tasks.reservation.helper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.tasks.reservation.SpaceManager;
import org.tasks.reservation.entities.CoworkingSpace;
import org.tasks.reservation.repository.CoworkingSpaceBookingRepository;
import org.tasks.reservation.repository.CoworkingSpaceRepository;
import org.tasks.reservation.service.impl.ConsoleAdminService;
import org.tasks.reservation.service.impl.ConsoleCustomerService;

import java.util.Optional;
import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class MenuLauncher {
    private final SpaceManager manager;
    private final ConsoleAdminService consoleAdminService;
    private final ConsoleCustomerService consoleCustomerService;
    private final CoworkingSpaceBookingRepository coworkingSpaceBookingRepository;
    private final CoworkingSpaceRepository coworkingSpaceRepository;
    private final Scanner scanner;
    private boolean programRunning = true;


    public void showMainMenu() {
        System.out.println("Hello! Welcome to our Coworking! Please log in to the system.");

        while (programRunning) {
            System.out.println("""
                    Please, choose the option:
                    1.Admin Login
                    2.User Login
                    3.Exit""");

            Optional<Integer> optionalInput = manager.askUserToWriteNumberOfSpace();
            int input = optionalInput.orElse(-1);

            switch (input) {
                case 1 -> showAdminMenu();
                case 2 -> showCustomerMenu();
                case 3 -> exitProgram();
                default -> System.out.println("Incorrect input number");
            }
        }
    }

    private void showAdminMenu() {
        System.out.println("Hello, Admin!");

        while (programRunning) {
            System.out.println("""
                    Please, choose the option:
                    1.Add a new coworking space
                    2.Remove a coworking space
                    3.Update a coworking space
                    4.View all reservations
                    5.Return to MAIN menu
                    6.Exit""");

            Optional<Integer> optionalInput = manager.askUserToWriteNumberOfSpace();
            int input = optionalInput.orElse(-1);

            switch (input) {
                case 1 -> {
                    consoleAdminService.addSpace(enterCoworkingSpaceByUser());
                    manager.showSpaces(space -> true);
                }
                case 2 -> {
                    manager.showSpaces(space -> true);
                    int chosenSpaceID = getChosenSpaceID();
                    consoleAdminService.removeSpace(chosenSpaceID);
                }
                case 3 -> {
                    manager.showSpaces(space -> true);
                    int chosenSpaceID = getChosenSpaceID();
                    consoleAdminService.updateSpace(chosenSpaceID);
                }
                case 4 -> manager.showSpaces(space -> !space.isAvailable());
                case 5 -> showMainMenu();
                case 6 -> exitProgram();
                default -> System.out.println("Incorrect input number");
            }
        }
    }

    private int getChosenSpaceID() {
        System.out.println("Please, choose the id of space");
        return Integer.parseInt(scanner.nextLine());
    }

    private CoworkingSpace enterCoworkingSpaceByUser() {
        Optional<CoworkingSpace> coworkingSpace = consoleAdminService.askUserToWriteCoworkingSpaceString();
        while (coworkingSpace.isEmpty()) {
            System.out.println("Invalid input. Please enter data in format: Name,type,price");
            coworkingSpace = consoleAdminService.askUserToWriteCoworkingSpaceString();
        }
        return coworkingSpace.get();
    }


    private void showCustomerMenu() {
        System.out.println("Hello, Customer!");

        while (programRunning) {
            System.out.println("""
                    1.Browse available spaces
                    2.Make a reservation
                    3.View my reservations
                    4.Cancel a reservation
                    5.Return to MAIN menu
                    6.Exit""");

            Optional<Integer> optionalInput = manager.askUserToWriteNumberOfSpace();
            int input = optionalInput.orElse(-1);

            switch (input) {
                case 1 -> manager.showSpaces(CoworkingSpace::isAvailable);
                case 2 -> showAndReserveSpace();
                case 3 -> manager.showMyReservation();
                case 4 -> showAndCancelReservation();
                case 5 -> showMainMenu();
                case 6 -> exitProgram();
                default -> System.out.println("Incorrect input number");
            }
        }
    }

    private void showAndReserveSpace() {
        if (coworkingSpaceRepository.getSpaces().isEmpty()) {
            System.out.println("Sorry, no available spaces");
            return;
        }

        System.out.println("Choose id of one available coworking spaces");
        manager.showSpaces(CoworkingSpace::isAvailable);
        int chosenSpaceID = getChosenSpaceID();
        System.out.println("Please, enter booking details: ");
        String bookingDetails = scanner.nextLine().trim();
        consoleCustomerService.reserve(chosenSpaceID, bookingDetails);
    }

    private void showAndCancelReservation() {
        if (coworkingSpaceBookingRepository.getMyReservations().isEmpty()) {
            System.out.println("Sorry, your list is Empty ");
            return;
        }
        System.out.println("Choose the number of space, that you would like to cancel. ");
        System.out.println("It's list of your reservations: ");
        manager.showMyReservation();
        int chosenSpaceID = getChosenSpaceID();
        consoleCustomerService.cancelReservation(chosenSpaceID);
    }

    private void exitProgram() {
        System.out.println("Bye, have a nice day!");
        programRunning = false;
    }
}
