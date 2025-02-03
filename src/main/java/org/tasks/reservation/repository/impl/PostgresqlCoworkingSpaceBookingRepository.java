package org.tasks.reservation.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.tasks.reservation.entity.CoworkingSpaceBooking;
import org.tasks.reservation.helper.ExecutorEntityManagerHelper;
import org.tasks.reservation.repository.CoworkingSpaceBookingRepository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostgresqlCoworkingSpaceBookingRepository implements CoworkingSpaceBookingRepository {
    private final ExecutorEntityManagerHelper executorEntityManagerHelper;

    public List<CoworkingSpaceBooking> getMyReservations() {
        return executorEntityManagerHelper.fetchData("SELECT b FROM CoworkingSpaceBooking b JOIN FETCH b.coworkingSpace", CoworkingSpaceBooking.class);
    }
}
