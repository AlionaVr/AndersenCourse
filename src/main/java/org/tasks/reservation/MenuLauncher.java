package org.tasks.reservation;

import java.util.Optional;
import java.util.Scanner;

public class MenuLauncher {
    private final SpaceManager manager;
    private final Admin admin;
    private final Customer customer;
    private final Repository repository;
    private final Scanner scanner;
    private boolean programRunning = true;

    public MenuLauncher(Repository repository) {
        this.scanner = new Scanner(System.in);
        this.manager = new SpaceManager(repository, scanner);
        this.admin = new Admin(repository, scanner);
        this.customer = new Customer(scanner, manager);
        this.repository = repository;
    }

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
                    admin.addSpace(enterCoworkingSpaceByUser());
                    manager.showSpaces(space -> true);
                }
                case 2 -> {
                    manager.showSpaces(space -> true);
                    int chosenSpaceID = getChosenSpaceID();
                    admin.removeSpace(chosenSpaceID);
                }
                case 3 -> {
                    manager.showSpaces(space -> true);
                    int chosenSpaceID = getChosenSpaceID();
                    admin.updateSpace(chosenSpaceID);
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
        Optional<CoworkingSpace> coworkingSpace = admin.askUserToWriteCoworkingSpaceString();
        while (coworkingSpace.isEmpty()) {
            System.out.println("Invalid input. Please enter data in format: Name,type,price");
            coworkingSpace = admin.askUserToWriteCoworkingSpaceString();
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
        if (repository.getSpaces().isNotEmpty()) {
            System.out.println("Choose id of one available coworking spaces");
            manager.showSpaces(CoworkingSpace::isAvailable);
            int chosenSpaceID = getChosenSpaceID();
            customer.reserve(chosenSpaceID);
        } else System.out.println("Sorry, no available spaces");
    }

    private void showAndCancelReservation() {
        if (repository.getMyReservations().isNotEmpty()) {
            System.out.println("Choose the number of space, that you would like to cancel. ");
            System.out.println("It's list of your reservations: ");
            manager.showMyReservation();
            int chosenSpaceID = getChosenSpaceID();
            customer.cancelReservation(chosenSpaceID);
        } else System.out.println("Sorry, your list is Empty ");
    }

    private void exitProgram() {
        System.out.println("Bye, have a nice day!");
        programRunning = false;
    }
}
