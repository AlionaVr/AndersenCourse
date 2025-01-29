package org.tasks.reservation.repository;

import org.tasks.reservation.entities.CoworkingSpace;

import java.util.List;

public interface CoworkingSpaceRepository {
    List<CoworkingSpace> getSpaces();
}
