package org.tasks.reservation.service.impl;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.tasks.reservation.SpaceManager;
import org.tasks.reservation.entity.CoworkingSpace;
import org.tasks.reservation.entity.CoworkingSpaceBooking;
import org.tasks.reservation.helper.ExecutorEntityManagerHelper;
import org.tasks.reservation.service.CustomerService;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class WebCustomerService implements CustomerService {

    private final ExecutorEntityManagerHelper executorEntityManagerHelper;
    private final SpaceManager manager;

    @Override
    public void reserve(int id, String bookingDetails, LocalDate date) {
        if (id == 150) { // just for test
            throw new RuntimeException("Id = 150 doesn't exist");
        }

        executorEntityManagerHelper.executeWithEntityManager(
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

    @Override
    public void cancelReservation(int bookingId) {
        executorEntityManagerHelper.executeWithEntityManager(entityManager -> {
            CoworkingSpaceBooking booking = entityManager.find(CoworkingSpaceBooking.class, bookingId);
            CoworkingSpace space = booking.getCoworkingSpace();
            if (manager.isSpaceExist(space, entityManager)) {
                entityManager.createNativeQuery("DELETE FROM coworking_space_booking WHERE id = :spaceId")
                        .setParameter("spaceId", bookingId)
                        .executeUpdate();
                space.setAvailability(true);
            }
        }, "CANCELED!Booking successfully removed from database.", "Error removing from My Reservation : ");
    }
}

