package org.tasks.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tasks.reservation.entity.CoworkingSpace;

import java.util.List;

public interface CoworkingSpaceRepository extends JpaRepository<CoworkingSpace, Integer> {
    List<CoworkingSpace> findCoworkingSpaceByAvailability(boolean availability);
}
