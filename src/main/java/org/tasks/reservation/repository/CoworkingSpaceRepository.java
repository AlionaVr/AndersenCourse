package org.tasks.reservation.repository;

import org.tasks.reservation.entity.CoworkingSpace;

import java.util.List;

public interface CoworkingSpaceRepository {
    List<CoworkingSpace> getSpaces();

    List<CoworkingSpace> getAvailableSpaces();

    List<CoworkingSpace> getNotAvailableSpaces();
}
