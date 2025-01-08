package org.tasks.reservation;

import java.util.Optional;
import java.util.Scanner;

public class Admin {

    private final Scanner scanner = new Scanner(System.in);
    private final SpaceManager manager;
    private final Repository repository;

    public Admin(Repository repository) {
        this.repository = repository;
        this.manager = new SpaceManager(repository);
    }

    protected void addSpace() {
        System.out.println("Enter name of space, type and price: \n ***in format: Name,type,price ");
        String input = scanner.nextLine();
        Optional.of(input.split(","))
                .filter(elements -> elements.length == 3)
                .ifPresentOrElse(
                        elements -> {// if condition is true
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
                        },
                        () -> { // if condition is false
                            System.out.println("Invalid input. Please enter data in format: Name,type,price");
                        }
                );

    }

    protected void removeSpace() {
        manager.showSpaces(space -> true);
        Optional.of(repository.getSpaces())
                .filter(spaces -> !spaces.isEmpty())
                .ifPresent(spaces -> {
                    System.out.println("Please, choose the number of space, that you would like to delete.");
                    int numberOfSpace = manager.getValidChosenSpace(spaces.size());
                    spaces.remove(numberOfSpace - 1);
                    System.out.println("DELETED!");
                });
    }

    protected void updateSpace() {
        manager.showSpaces(space -> true);

        Optional.of(repository.getSpaces())
                .filter(spaces -> !spaces.isEmpty())
                .ifPresent(spaces -> {
                    System.out.println("Please, choose the number of space, that you would like to update.");
                    int numberOfSpace = manager.getValidChosenSpace(spaces.size());
                    spaces.remove(numberOfSpace - 1);
                    addSpace();
                    System.out.println("UPDATED!");
                });
    }
}





