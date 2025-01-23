package org.tasks.reservation;

import jakarta.persistence.EntityManager;
import org.tasks.reservation.entities.CoworkingSpace;
import org.tasks.reservation.entities.CoworkingSpaceBooking;

public class Customer {

    protected void reserve(int id, String bookingDetails) {
        try (EntityManager entityManager = Main.getSessionFactory().createEntityManager()) {
            entityManager.getTransaction().begin();
            CoworkingSpace spaceToReserve = entityManager.find(CoworkingSpace.class, id);
            if (spaceToReserve != null && spaceToReserve.isAvailable()) {
                spaceToReserve.setAvailability(false);
                CoworkingSpaceBooking booking = new CoworkingSpaceBooking();
                booking.setCoworkingSpace(spaceToReserve);
                booking.setBookingDetails(bookingDetails);

                entityManager.persist(booking);
                System.out.println("RESERVED! Booking successfully added to database.");
            } else {
                System.out.println("Space not available or doesn't exist.");
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("Error adding Space To My Reservation : " + e.getMessage());
            e.printStackTrace();
        }
    }

    protected void cancelReservation(int coworkingSpaceId) {
        try (EntityManager entityManager = Main.getSessionFactory().createEntityManager()) {
            entityManager.getTransaction().begin();
            CoworkingSpace space = entityManager.find(CoworkingSpace.class, coworkingSpaceId);
            if (space == null) {
                System.out.println("space is not found with ID: " + coworkingSpaceId);
            } else {
                entityManager.createNativeQuery("DELETE FROM coworkingspacebooking WHERE coworking_space_id = :spaceId")
                        .setParameter("spaceId", coworkingSpaceId)
                        .executeUpdate();
                space.setAvailability(true);
                System.out.println("CANCELED!Booking successfully removed from database.");
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("Error removing from My Reservation : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
