package org.tasks.reservation;

import java.util.Scanner;

public class MenuLauncher {
    private final SpaceManager manager;
    private final Admin admin;
    private final Customer customer;
    private final Scanner scanner = new Scanner(System.in);
    private boolean flag = true;

    public MenuLauncher(Repository repository) {
        this.manager = new SpaceManager(repository);
        this.admin = new Admin(repository);
        this.customer = new Customer(repository);
    }

    public void showMainMenu() {

        System.out.println("Hello! Welcome to our Coworking! Please log in to the system.");


        while (flag) {
            System.out.println("""
                Please, choose the option:
                1.Admin Login
                2.User Login
                3.Exit""");

            int input = manager.getValidInputNumber(Integer.parseInt(scanner.nextLine()), 3);

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

        while (flag) {
            System.out.println("""
                Please, choose the option:
                1.Add a new coworking space
                2.Remove a coworking space
                3.Update a coworking space
                4.View all reservations
                5.Return to MAIN menu
                6.Exit""");

            int input = manager.getValidInputNumber(Integer.parseInt(scanner.nextLine()), 6);

            switch (input) {
                case 1 -> admin.addSpace();
                case 2 -> admin.removeSpace();
                case 3 -> admin.updateSpace();
                case 4 -> manager.showSpaces(space -> !space.isAvailable());
                case 5 -> showMainMenu();
                case 6 -> exitProgram();
                default -> System.out.println("Incorrect input number");
            }
        }
    }


    private void showCustomerMenu() {
        System.out.println("Hello, Customer!");

        while (flag) {
            System.out.println("""
                1.Browse available spaces
                2.Make a reservation
                3.View my reservations
                4.Cancel a reservation
                5.Return to MAIN menu
                6.Exit""");

            int input = manager.getValidInputNumber(Integer.parseInt(scanner.nextLine()), 6);

            switch (input) {
                case 1 -> manager.showSpaces(CoworkingSpace::isAvailable);
                case 2 -> customer.reserve();
                case 3 -> manager.showMyReservation();
                case 4 -> customer.cancelReservation();
                case 5 -> showMainMenu();
                case 6 -> exitProgram();
                default -> System.out.println("Incorrect input number");
            }
        }
    }

    private void exitProgram() {
        System.out.println("Bye, have a nice day!");
        flag = false;
    }
}
