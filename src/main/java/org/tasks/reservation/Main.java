package org.tasks.reservation;

import java.lang.annotation.Documented;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {
    public static LinkedList<CoworkingSpace> spaces = new LinkedList<>();


    public static void main(String[] args) {

        System.out.println("Hello! Welcome to our Coworking! Please log in to the system.");
        showMainMenu();
    }

    public static void showMainMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Choose the option \n" +
                    "1. Admin Login\n" +
                    "2. User Login\n" +
                    "3. Exit\n");
            int request;
            try {
                request = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Проверьте введенные данные.");
                continue;
            }
            switch (request) {
                case 1:
                    showAdminMenu();
                    break;
                case 2:
                    showCustomerMenu();
                    break;
                case 3:
                    System.out.println("Bye, have a nice day!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Incorrect input number");
                    break;
            }
        }
    }

    public static void showAdminMenu() {
        Scanner scanner = new Scanner(System.in);
        Admin admin = new Admin();
        System.out.println("Hello, Admin!");
        while (true) {
            System.out.println("Please, choose the option:\n" +
                    "1.Add a new coworking space\n" +
                    "2.Remove a coworking space\n" +
                    "3.Update a coworking space\n" +
                    "4. View all reservations\n" +
                    "5. Return to MAIN menu\n" +
                    "6. Exit");
            int input;
            try {
                input = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please, check your input data");
                continue;
            }
            switch (input) {
                case 1:
                    admin.addSpace();
                    break;
                case 2:
                    admin.removeSpace();
                    break;
                case 3:
                    admin.updateSpace();
                    break;
                case 4:
                    SpaceManager.showSpaces(space -> !space.isAvailable());
                    break;
                case 5:
                    showMainMenu();
                    break;
                case 6:
                    System.out.println("Bye, have a nice day!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Incorrect input number");
                    break;
            }
        }
    }


    public static void showCustomerMenu() {
        Scanner scanner = new Scanner(System.in);
        Customer customer = new Customer();
        System.out.println("Hello, Customer!");
        while (true) {
            System.out.println("Please, choose the option:\n" +
                    "1.Browse available spaces\n" +
                    "2.Make a reservation\n" +
                    "3.View my reservations\n" +
                    "4.Cancel a reservation\n" +
                    "5. Return to MAIN menu\n" +
                    "6. Exit");
            int input;
            try {
                input = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please, check your input data");
                continue;
            }
            switch (input) {
                case 1:
                    SpaceManager.showSpaces(CoworkingSpace::isAvailable);
                    break;
                case 2:
                    customer.reserve();
                    break;
                case 3:
                    SpaceManager.showMyReservation();
                    break;
                case 4:
                    customer.cancelReservation();
                    break;
                case 5:
                    showMainMenu();
                    break;
                case 6:
                    System.out.println("Bye, have a nice day!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Incorrect input number");
                    break;
            }
        }
    }
}