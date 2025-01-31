package org.tasks.reservation.service;

import org.tasks.reservation.entity.CoworkingSpace;

public interface AdminService {

    void addSpace(CoworkingSpace newSpace);

    void removeSpace(int id);

    void updateSpace(int id, CoworkingSpace updatedSpace);
}
