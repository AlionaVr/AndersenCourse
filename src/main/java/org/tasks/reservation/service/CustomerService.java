package org.tasks.reservation.service;

public interface CustomerService {
    void reserve(int id, String bookingDetails);

    void cancelReservation(int coworkingSpaceId);
}
