package org.tasks.reservation;


import jakarta.persistence.EntityManager;
import org.tasks.reservation.entities.CoworkingSpace;
import org.tasks.reservation.entities.CoworkingSpaceBooking;


public class Customer {
    ExecutorEntityManager executorEntityManager = new ExecutorEntityManager();
    SpaceManager manager;

    protected void reserve(int id, String bookingDetails) {
        executorEntityManager.executeWithEntityManager(
                entityManager -> findAndReserveSpace(id, bookingDetails, entityManager),
                "RESERVED! Booking successfully added to database.",
                "Error adding Space To My Reservation : ");
    }

    private void findAndReserveSpace(int id, String bookingDetails, EntityManager entityManager) {
        CoworkingSpace spaceToReserve = entityManager.find(CoworkingSpace.class, id);
        if (manager.isSpaceExist(spaceToReserve, entityManager) && spaceToReserve.isAvailable()) {
            spaceToReserve.setAvailability(false);
            CoworkingSpaceBooking booking = new CoworkingSpaceBooking();
            booking.setCoworkingSpace(spaceToReserve);
            booking.setBookingDetails(bookingDetails);

            entityManager.persist(booking);
        } else {
            System.out.println("Space not available or doesn't exist.");
        }
    }


    protected void cancelReservation(int coworkingSpaceId) {
        executorEntityManager.executeWithEntityManager(entityManager -> {
            findAndCancelReservation(coworkingSpaceId, entityManager);
        }, "CANCELED!Booking successfully removed from database.", "Error removing from My Reservation : ");
    }

    private void findAndCancelReservation(int coworkingSpaceId, EntityManager entityManager) {
        CoworkingSpace space = entityManager.find(CoworkingSpace.class, coworkingSpaceId);
        if (manager.isSpaceExist(space, entityManager)) {
            entityManager.createNativeQuery("DELETE FROM coworkingspacebooking WHERE coworking_space_id = :spaceId")
                    .setParameter("spaceId", coworkingSpaceId)
                    .executeUpdate();
            space.setAvailability(true);
        }
    }
}
