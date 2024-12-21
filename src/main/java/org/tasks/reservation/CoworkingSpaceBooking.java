package org.tasks.reservation;

public class CoworkingSpaceBooking {
    private final CoworkingSpace coworkingSpace;
    private final String bookingDetails;

    public CoworkingSpaceBooking(CoworkingSpace coworkingSpace, String bookingDetails) {
        this.coworkingSpace = coworkingSpace;
        this.bookingDetails = bookingDetails;
    }

    public CoworkingSpace getCoworkingSpace() {
        return coworkingSpace;
    }

    @Override
    public String toString() {
        return String.format("%s\nBooking details: %s", coworkingSpace.toString(), bookingDetails);
    }
}
