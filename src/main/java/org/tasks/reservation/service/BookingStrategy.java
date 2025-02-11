package org.tasks.reservation.service;

import org.tasks.reservation.entity.CoworkingSpace;
import org.tasks.reservation.entity.CoworkingSpaceBooking;

public abstract class BookingStrategy {
    public CoworkingSpaceBooking bookSpace(CoworkingSpace space, String bookingDetails) {
        space.setAvailability(false);
        CoworkingSpaceBooking booking = new CoworkingSpaceBooking();
        booking.setCoworkingSpace(space);
        booking.setBookingDetails(bookingDetails);
        return booking;
    }
}
