package org.tasks.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tasks.reservation.entity.CoworkingSpaceBooking;

public interface CoworkingSpaceBookingRepository extends JpaRepository<CoworkingSpaceBooking, Integer> {
}
