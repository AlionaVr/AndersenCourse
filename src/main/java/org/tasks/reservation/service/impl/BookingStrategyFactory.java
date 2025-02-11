package org.tasks.reservation.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.tasks.reservation.helper.TypeOfSpace;
import org.tasks.reservation.service.BookingStrategy;

@Component
@RequiredArgsConstructor
public class BookingStrategyFactory {

    private final PrepaidBookingStrategy prepaidBookingStrategy;
    private final StandardBookingStrategy standardBookingStrategy;

    public BookingStrategy getStrategy(TypeOfSpace spaceType) {
        if (spaceType == TypeOfSpace.PRIVATE) {
            return prepaidBookingStrategy;
        }
        return standardBookingStrategy;
    }
}
