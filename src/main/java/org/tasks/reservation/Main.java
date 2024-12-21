package org.tasks.reservation;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static LinkedList<CoworkingSpace> spaces = new LinkedList<>();
    private final SpaceManager manager = new SpaceManager();
    private final Scanner scanner = new Scanner(System.in);
    private final Admin admin = new Admin();
    private final Customer customer = new Customer();

    public static void main(String[] args) {
        Main main = new Main();
        main.showMainMenu();
    }

    public void showMainMenu() {
        System.out.println("Hello! Welcome to our Coworking! Please log in to the system.");
        List<String> options = Arrays.asList(
                "Admin Login",
                "User Login",
                "Exit");
        while (true) {
            showOptions(options);

            int input = manager.getValidInputNumber(Integer.parseInt(scanner.nextLine()), options.size());

            switch (input) {
                case 1 -> showAdminMenu();
                case 2 -> showCustomerMenu();
                case 3 -> exitProgram();
                default -> System.out.println("Incorrect input number");
            }
        }
    }

    public void showAdminMenu() {
        List<String> options = Arrays.asList(
                "Add a new coworking space",
                "Remove a coworking space",
                "Update a coworking space",
                "View all reservations",
                "Return to MAIN menu",
                "Exit");
        System.out.println("Hello, Admin!");

        while (true) {
            showOptions(options);
            int input = manager.getValidInputNumber(Integer.parseInt(scanner.nextLine()), options.size());

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


    public void showCustomerMenu() {
        List<String> options = Arrays.asList(
                "Browse available spaces",
                "Make a reservation",
                "View my reservations",
                "Cancel a reservation",
                "Return to MAIN menu",
                "Exit"
        );
        System.out.println("Hello, Customer!");

        while (true) {
            showOptions(options);

            int input = manager.getValidInputNumber(Integer.parseInt(scanner.nextLine()), options.size());

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

    protected void showOptions(List<String> options) {
        System.out.println("Please, choose the option:");
        for (int i = 0; i < options.size(); i++) {
            System.out.println((i + 1) + ". " + options.get(i));
        }
    }

    private void exitProgram() {
        System.out.println("Bye, have a nice day!");
        System.exit(0);
    }
}
