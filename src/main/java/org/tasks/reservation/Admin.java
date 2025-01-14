package org.tasks.reservation;

import java.util.Optional;
import java.util.Scanner;

public class Admin {

    private final Scanner scanner;
    private final Repository repository;

    public Admin(Repository repository, Scanner scanner) {
        this.scanner = scanner;
        this.repository = repository;
    }

    protected Optional<CoworkingSpace> askUserToWriteCoworkingSpaceString() {
        System.out.println("Enter name of space, type and price: \n ***in format: Name,type,price ");
        try {
            String spaceStringSeparatedByComma = scanner.nextLine();
            return Optional.of(spaceStringSeparatedByComma.split(","))
                    .filter(elements -> elements.length == 3)
                    .map(elements -> new CoworkingSpace(elements[0], elements[1], Double.parseDouble(elements[2])));
        } catch (NumberFormatException e) {
            System.out.println("Invalid price format. Please, enter a valid number for price.");
        }
        return Optional.empty();
    }

    protected void addSpace(CoworkingSpace newSpace) {
        repository.getSpaces().add(newSpace);
        System.out.println("Space added successfully! That's all spaces:");
    }

    protected void removeSpace(int numberOfSpaceToDelete) {
        if (repository.getSpaces().size() >= numberOfSpaceToDelete) {
            repository.getSpaces().remove(numberOfSpaceToDelete - 1);
            System.out.println("DELETED!");
        }
    }

    protected void updateSpace(int numberOfSpaceToUpdate) {
        repository.getSpaces().remove(numberOfSpaceToUpdate - 1);
        Optional<CoworkingSpace> coworkingSpace = askUserToWriteCoworkingSpaceString();
        while (coworkingSpace.isEmpty()) {
            System.out.println("Invalid input. Please enter data in format: Name,type,price");
            coworkingSpace = askUserToWriteCoworkingSpaceString();
        }
        addSpace(coworkingSpace.get());
        System.out.println("UPDATED!");
    }
}





