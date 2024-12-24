package org.tasks.reservation;

import java.util.Scanner;

public class Admin {

    private final Scanner scanner = new Scanner(System.in);
    private final SpaceManager manager;
    private final Repository repository;

    public Admin(Repository repository) {
        this.repository = repository;
        this.manager = new SpaceManager(repository);
    }

    //Add a new coworking space
    protected void addSpace() {
        System.out.println("Enter name of space, type and price: \n ***in format: Name,type,price ");
        String input = scanner.nextLine();
        String[] elements = input.split(",");
        if (elements.length != 3) {
            System.out.println("Invalid input. Please enter data in format: Name,type,price");
            return;
        }
        try {
            String name = elements[0];
            String type = elements[1];
            double price = Double.parseDouble(elements[2]);
            CoworkingSpace newSpace = new CoworkingSpace(name, type, price);

            repository.getSpaces().add(newSpace);

            System.out.println("Space added successfully! That's all spaces:");
            manager.showSpaces(space -> true);

        } catch (NumberFormatException e) {
            System.out.println("Invalid price format. Please, enter a valid number for price.");
        }
    }

    //Remove a coworking space
    protected void removeSpace() {
        manager.showSpaces(null);
        if (!repository.getSpaces().isEmpty()) {
            System.out.println("Please, choose the number of space, that you would like to delete.");
            int numberOfSpace = manager.getValidChosenSpace(repository.getSpaces().size());

            repository.getSpaces().remove(numberOfSpace - 1);
            System.out.println("DELETED!");
        }
    }

    protected void updateSpace() {
        manager.showSpaces(null);
        if (!repository.getSpaces().isEmpty()) {
            System.out.println("Please, choose the number of space, that you would like to update.");

            int numberOfSpace = manager.getValidChosenSpace(repository.getSpaces().size());

            repository.getSpaces().remove(numberOfSpace - 1);
            addSpace();
            System.out.println("UPDATED!");
        }
    }
}





