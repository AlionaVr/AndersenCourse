package org.tasks.reservation;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Predicate;


public class SpaceManager {

    private final Scanner scanner;
    private final Repository repository;

    public SpaceManager(Repository repository, Scanner scanner) {
        this.repository = repository;
        this.scanner = scanner;
    }

    protected void showSpaces(Predicate<CoworkingSpace> availabilityFilter) {
        List<CoworkingSpace> filteredSpaces = repository.getSpaces().stream()
                .filter(availabilityFilter)
                .toList();

        if (filteredSpaces.isEmpty()) {
            System.out.println("Empty list.");
        } else filteredSpaces.forEach(space -> {
            System.out.println("\n-------------------------------------------------");
            System.out.println(space.toString());
            System.out.println("-------------------------------------------------");
        });
    }

    protected void showMyReservation() {
        List<CoworkingSpaceBooking> myReservations = repository.getMyReservations().stream().toList();

        if (myReservations.isEmpty()) {
            System.out.println("Empty list.");
        } else myReservations.forEach(space -> {
            System.out.println("\n-------------------------------------------------");
            System.out.println(space.toString());
            System.out.println("-------------------------------------------------");
        });
    }

    protected Optional<Integer> askUserToWriteNumberOfSpace() {
        try {
            int inputNumber = Integer.parseInt(scanner.nextLine());
            return Optional.of(inputNumber);
        } catch (NumberFormatException e) {
            System.out.println("Input contains letters or is not a valid number.");
        }
        return Optional.empty();
    }

    protected void changeSpaceAvailability(boolean availability, int id) {
        String updateQuery = "UPDATE coworkingSpace SET availability = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = Main.postgresDbConnection.prepareStatement(updateQuery)) {
            preparedStatement.setBoolean(1, availability);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void addSpaceToMyReservation(int id, String bookingDetails) {
        String insertQuery = "INSERT INTO coworkingSpaceBooking (coworking_space_id, booking_details, booking_date) VALUES (?, ?, NOW())";
        try (PreparedStatement preparedStatement = Main.postgresDbConnection.prepareStatement(insertQuery)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, bookingDetails);
            preparedStatement.executeUpdate();
            System.out.println("Booking successfully added to database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void removeSpaceFromMyReservation(int id) {
        String deleteQuery = "DELETE FROM coworkingSpaceBooking WHERE id = ?";

        try (PreparedStatement preparedStatement = Main.postgresDbConnection.prepareStatement(deleteQuery)) {
            preparedStatement.setInt(1, id);
            if (preparedStatement.executeUpdate() > 0) {
                System.out.println("Booking successfully removed from database.");
            } else {
                System.out.println("No booking found with the specified ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}