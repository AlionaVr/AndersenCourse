package org.tasks.reservation;

import java.io.Serializable;

public class CoworkingSpaceBooking implements Serializable {
    private final CoworkingSpace coworkingSpace;
    private final String bookingDetails;

    protected CoworkingSpaceBooking(CoworkingSpace coworkingSpace, String bookingDetails) {
        this.coworkingSpace = coworkingSpace;
        this.bookingDetails = bookingDetails;
    }

    protected CoworkingSpace getCoworkingSpace() {
        return coworkingSpace;
    }

    @Override
    public String toString() {
        return String.format("%s\nBooking details: %s", coworkingSpace.toString(), bookingDetails);
    }
}
