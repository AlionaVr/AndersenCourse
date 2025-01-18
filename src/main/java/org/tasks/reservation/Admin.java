package org.tasks.reservation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        String query = "INSERT INTO coworkingSpace (name, type, price) VALUES (?, ?, ?)";
        try {
            PreparedStatement preparedStatement = Main.postgresDbConnection.prepareStatement(query);

            preparedStatement.setString(1, newSpace.getName());
            preparedStatement.setString(2, newSpace.getType());
            preparedStatement.setDouble(3, newSpace.getPrice());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void removeSpace(int id) {
        String deleteQuery = "DELETE FROM coworkingSpace WHERE id = ?";
        try (PreparedStatement preparedStatement = Main.postgresDbConnection.prepareStatement(deleteQuery)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            System.out.println("DELETED!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void updateSpace(int idToUpdate) {
        String selectQuery = "SELECT * FROM coworkingSpace WHERE id = ?";
        try (PreparedStatement preparedStatement = Main.postgresDbConnection.prepareStatement(selectQuery)) {
            preparedStatement.setInt(1, idToUpdate);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                System.out.println("No space found with the specified ID: " + idToUpdate);
            }
            String updateQuery = "UPDATE coworkingSpace SET name = ?, type = ?, price = ?, availability = ? WHERE id = ?";
            try (PreparedStatement updateStatement = Main.postgresDbConnection.prepareStatement(updateQuery)) {
                Optional<CoworkingSpace> coworkingSpace = askUserToWriteCoworkingSpaceString();
                while (coworkingSpace.isEmpty()) {
                    System.out.println("Invalid input. Please enter data in format: Name,type,price");
                    coworkingSpace = askUserToWriteCoworkingSpaceString();
                }
                CoworkingSpace space = coworkingSpace.get();

                updateStatement.setString(1, space.getName());
                updateStatement.setString(2, space.getType());
                updateStatement.setDouble(3, space.getPrice());
                updateStatement.setBoolean(4, space.isAvailable());
                updateStatement.setInt(5, idToUpdate);
                updateStatement.executeUpdate();

                System.out.println("UPDATED!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}




