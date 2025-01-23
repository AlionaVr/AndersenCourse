package org.tasks.reservation;

import jakarta.persistence.EntityManager;
import org.tasks.reservation.entities.CoworkingSpace;
import org.tasks.reservation.entities.CoworkingSpaceBooking;

import java.util.ArrayList;
import java.util.List;


public class Repository {
    public List<CoworkingSpace> getSpaces() {
        return fetchData("FROM CoworkingSpace", CoworkingSpace.class);
    }

    public List<CoworkingSpaceBooking> getMyReservations() {
        return fetchData("SELECT b FROM CoworkingSpaceBooking b JOIN FETCH b.coworkingSpace", CoworkingSpaceBooking.class);
    }

    private <T> List<T> fetchData(String query, Class<T> classOfSpace) {
        List<T> listOfSpaces = new ArrayList<>();
        try (EntityManager session = Main.getSessionFactory().createEntityManager()) {
            session.getTransaction().begin();
            List<T> reservations = session.createQuery(query, classOfSpace).getResultList();
            listOfSpaces.addAll(reservations);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("Error fetching data: " + e.getMessage());
        }
        return listOfSpaces;
    }
}
