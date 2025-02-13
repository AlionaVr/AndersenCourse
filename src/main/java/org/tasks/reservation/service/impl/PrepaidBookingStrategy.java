package org.tasks.reservation.service.impl;

import org.springframework.stereotype.Component;
import org.tasks.reservation.entity.CoworkingSpace;
import org.tasks.reservation.entity.CoworkingSpaceBooking;
import org.tasks.reservation.service.BookingStrategy;

@Component
public class PrepaidBookingStrategy extends BookingStrategy {
    public CoworkingSpaceBooking bookSpace(CoworkingSpace space, String bookingDetails) {
        CoworkingSpaceBooking booking = new CoworkingSpaceBooking();
        booking.setBookingDetails(bookingDetails + " | Prepaid booking");
        return booking;
    }
}
