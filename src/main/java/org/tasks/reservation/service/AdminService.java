package org.tasks.reservation.service;

import org.tasks.reservation.entities.CoworkingSpace;

import java.util.Optional;

public interface AdminService {
    Optional<CoworkingSpace> askUserToWriteCoworkingSpaceString();

    void addSpace(CoworkingSpace newSpace);

    void removeSpace(int id);

    void updateSpace(int idToUpdate);
}
