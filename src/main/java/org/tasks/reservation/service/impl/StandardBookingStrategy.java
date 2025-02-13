package org.tasks.reservation.service.impl;

import org.springframework.stereotype.Component;
import org.tasks.reservation.entity.CoworkingSpace;
import org.tasks.reservation.entity.CoworkingSpaceBooking;
import org.tasks.reservation.service.BookingStrategy;

@Component
public class StandardBookingStrategy extends BookingStrategy {
    public CoworkingSpaceBooking bookSpace(CoworkingSpace space, String bookingDetails) {
        space.setAvailability(false);
        CoworkingSpaceBooking booking = new CoworkingSpaceBooking();
        booking.setCoworkingSpace(space);
        booking.setBookingDetails(bookingDetails + " | Standard booking");
        return booking;
    }
}

