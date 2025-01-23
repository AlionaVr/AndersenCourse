package org.tasks.reservation.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "coworking_space_booking")
public class CoworkingSpaceBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "coworking_space_id")
    private CoworkingSpace coworkingSpace;

    @Column(name = "booking_details")
    private String bookingDetails;

    public CoworkingSpaceBooking() {
    }

    @Override
    public String toString() {
        return String.format("%s\nBooking details: %s", coworkingSpace.toString(), bookingDetails);
    }

    public void setCoworkingSpace(CoworkingSpace coworkingSpace) {
        this.coworkingSpace = coworkingSpace;
    }

    public void setBookingDetails(String bookingDetails) {
        this.bookingDetails = bookingDetails;
    }
}
