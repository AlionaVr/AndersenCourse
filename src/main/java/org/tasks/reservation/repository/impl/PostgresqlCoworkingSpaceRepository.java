package org.tasks.reservation.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.tasks.reservation.entities.CoworkingSpace;
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
}
