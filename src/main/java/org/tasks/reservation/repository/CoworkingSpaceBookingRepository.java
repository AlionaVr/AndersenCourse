package org.tasks.reservation.repository;

import org.tasks.reservation.entities.CoworkingSpaceBooking;

import java.util.List;

public interface CoworkingSpaceBookingRepository {
    List<CoworkingSpaceBooking> getMyReservations();
}
