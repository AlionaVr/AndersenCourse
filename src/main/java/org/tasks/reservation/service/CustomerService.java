package org.tasks.reservation.service;

import java.time.LocalDate;

public interface CustomerService {
    void reserve(int id, String bookingDetails, LocalDate date);

    void cancelReservation(int coworkingSpaceId);
}
