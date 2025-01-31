package org.tasks.reservation.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.tasks.reservation.entity.CoworkingSpace;
import org.tasks.reservation.helper.ExecutorEntityManagerHelper;
import org.tasks.reservation.repository.CoworkingSpaceRepository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostgresqlCoworkingSpaceRepository implements CoworkingSpaceRepository {
    private final ExecutorEntityManagerHelper executorEntityManagerHelper;

    public List<CoworkingSpace> getSpaces() {
        return executorEntityManagerHelper.fetchData("FROM CoworkingSpace", CoworkingSpace.class);
    }

    public List<CoworkingSpace> getAvailableSpaces() {
        return executorEntityManagerHelper.fetchData("SELECT c FROM CoworkingSpace c WHERE c.availability = true", CoworkingSpace.class);
    }

    public List<CoworkingSpace> getNotAvailableSpaces() {
        return executorEntityManagerHelper.fetchData("SELECT c FROM CoworkingSpace c WHERE c.availability = false", CoworkingSpace.class);
    }
}
