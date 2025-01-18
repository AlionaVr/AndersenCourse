package org.tasks.reservation;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Repository implements Serializable {
    public CustomList<CoworkingSpace> getSpaces() {
        CustomList<CoworkingSpace> spaces = new CustomList<>();
        String query = "SELECT * FROM coworkingSpace";
        try (Statement statement = Main.postgresDbConnection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                spaces.add(new CoworkingSpace(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("type"),
                        resultSet.getDouble("price"),
                        resultSet.getBoolean("availability")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return spaces;
    }

    public CustomList<CoworkingSpaceBooking> getMyReservations() {
        CustomList<CoworkingSpaceBooking> myReservations = new CustomList<>();
        String query = """
                SELECT coworkingSpaceBooking.coworking_space_id,
                coworkingSpaceBooking.booking_date,
                       coworkingSpace.name,
                       coworkingSpace.type,
                       coworkingSpace.price,
                       coworkingSpace.availability,
                   coworkingSpaceBooking.booking_details
                FROM coworkingSpaceBooking
                JOIN coworkingSpace ON coworkingSpaceBooking.coworking_space_id = coworkingSpace.id;
                """;
        try (Statement statement = Main.postgresDbConnection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                CoworkingSpace coworkingSpace = new CoworkingSpace(
                        resultSet.getInt("coworking_space_id"),
                        resultSet.getString("name"),
                        resultSet.getString("type"),
                        resultSet.getDouble("price"),
                        resultSet.getBoolean("availability")
                );
                CoworkingSpaceBooking booking = new CoworkingSpaceBooking(
                        coworkingSpace,
                        resultSet.getString("booking_details")
                );
                myReservations.add(booking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return myReservations;
    }
}
