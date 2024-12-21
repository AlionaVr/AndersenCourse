package org.tasks.reservation;

import java.util.Scanner;

import static org.tasks.reservation.Main.spaces;

public class Admin {

    Scanner scanner = new Scanner(System.in);
    SpaceManager manager = new SpaceManager();

    //Add a new coworking space
    protected void addSpace() {
        System.out.println("Enter name of space, type and price: \n ***in format: Name, type, price ");
        String input = scanner.nextLine();
        String[] elements = input.split(",\\s*");
        if (elements.length != 3) {
            System.out.println("Invalid input. Please enter data in format: Name, type, price");
            return;
        }
        try {
            String name = elements[0];
            String type = elements[1];
            double price = Double.parseDouble(elements[2]);
            CoworkingSpace newSpace = new CoworkingSpace(name, type, price);
            spaces.add(newSpace);

            System.out.println("Space added successfully! That's all spaces");
            SpaceManager.showSpaces(space -> true);

        } catch (NumberFormatException e) {
            System.out.println("Invalid price format. Please enter a valid number for price.");
        }
    }

    //Remove a coworking space
    protected void removeSpace() {
        SpaceManager.showSpaces(null);
        System.out.println("Please, choose the number of space, that you would like to delete. ");
        int numberOfSpace = Integer.parseInt(scanner.nextLine());
        while (numberOfSpace <= 0 || numberOfSpace > spaces.size()) {
            System.out.println("Invalid input. Please, check your input data");
            numberOfSpace = Integer.parseInt(scanner.nextLine());
        }
        spaces.remove(numberOfSpace - 1);
        System.out.println("DELETED!");
    }

    protected void updateSpace() {
        SpaceManager.showSpaces(null);
        System.out.println("Please, choose the number of space, that you would like to update.");
        int numberOfSpace = Integer.parseInt(scanner.nextLine());
        while (numberOfSpace <= 0 || numberOfSpace > spaces.size()) {
            System.out.println("Invalid input. Please, check your input data");
            numberOfSpace = Integer.parseInt(scanner.nextLine());
        }
        spaces.remove(numberOfSpace - 1);
        addSpace();
        System.out.println("UPDATED!");
    }
}





