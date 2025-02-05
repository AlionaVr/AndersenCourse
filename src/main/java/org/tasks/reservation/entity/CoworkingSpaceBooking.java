package org.tasks.reservation.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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
    private Integer id;

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
