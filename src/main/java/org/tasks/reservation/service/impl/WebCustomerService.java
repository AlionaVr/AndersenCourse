package org.tasks.reservation.service.impl;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.tasks.reservation.entity.CoworkingSpace;
import org.tasks.reservation.entity.CoworkingSpaceBooking;
import org.tasks.reservation.helper.ExecutorEntityManagerHelper;
import org.tasks.reservation.service.CustomerService;

import java.time.LocalDate;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class WebCustomerService implements CustomerService {

    private final ExecutorEntityManagerHelper executorEntityManagerHelper;

    @Override
    public void reserve(int id, String bookingDetails, LocalDate date) {
        executorEntityManagerHelper.executeWithEntityManager(
                entityManager -> findAndReserveSpace(id, bookingDetails, entityManager),
                "RESERVED! Booking successfully added to database.",
                "Error adding Space To My Reservation : ");
    }

    private void findAndReserveSpace(int id, String bookingDetails, EntityManager entityManager) {
        CoworkingSpace spaceToReserve = Optional.ofNullable(entityManager.find(CoworkingSpace.class, id))
                .orElseThrow(() -> new RuntimeException("Space doesn't exist."));
        if (!spaceToReserve.isAvailability()) {
            throw new RuntimeException("Space is not Available .");
        }
        spaceToReserve.setAvailability(false);
        CoworkingSpaceBooking booking = new CoworkingSpaceBooking();
        booking.setCoworkingSpace(spaceToReserve);
        booking.setBookingDetails(bookingDetails);

        entityManager.persist(booking);
    }

    @Override
    public void cancelReservation(int bookingId) {
        executorEntityManagerHelper.executeWithEntityManager(
                entityManager -> findAndCancelReservation(bookingId, entityManager),
                "CANCELED!Booking successfully removed from database.",
                "Error removing from My Reservation : ");
    }

    private void findAndCancelReservation(int bookingId, EntityManager entityManager) {
        CoworkingSpaceBooking booking = entityManager.find(CoworkingSpaceBooking.class, bookingId);
        CoworkingSpace space = booking.getCoworkingSpace();
        entityManager.createNativeQuery("DELETE FROM coworking_space_booking WHERE id = :spaceId")
                .setParameter("spaceId", bookingId)
                .executeUpdate();
        space.setAvailability(true);
    }
}

