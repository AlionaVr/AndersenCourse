package org.tasks.reservation.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "coworking_space_booking")
public class CoworkingSpaceBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coworking_space_id")
    private CoworkingSpace coworkingSpace;

    @Column(name = "booking_details")
    private String bookingDetails;

    @Override
    public String toString() {
        return String.format("%s\nBooking details: %s", coworkingSpace.toString(), bookingDetails);
    }
}
