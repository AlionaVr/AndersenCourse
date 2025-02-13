package org.tasks.reservation.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.tasks.reservation.entity.CoworkingSpace;
import org.tasks.reservation.entity.CoworkingSpaceBooking;
import org.tasks.reservation.repository.CoworkingSpaceBookingRepository;
import org.tasks.reservation.repository.CoworkingSpaceRepository;
import org.tasks.reservation.service.BookingStrategy;
import org.tasks.reservation.service.CustomerService;

@Service
@RequiredArgsConstructor
public class WebCustomerService implements CustomerService {
    private final CoworkingSpaceBookingRepository coworkingSpaceBookingRepository;
    private final CoworkingSpaceRepository coworkingSpaceRepository;
    private final BookingStrategyFactory bookingStrategyFactory;
    @Override
    @Transactional
    public void reserve(int id, String bookingDetails) {
        CoworkingSpace spaceToReserve = coworkingSpaceRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Space doesn't exist."));
        if (!spaceToReserve.isAvailability()) {
            throw new RuntimeException("Space is not Available .");
        }
        BookingStrategy strategy = bookingStrategyFactory.getStrategy(spaceToReserve.getType());
        CoworkingSpaceBooking booking = strategy.bookSpace(spaceToReserve, bookingDetails);

        coworkingSpaceBookingRepository.save(booking);
    }

    @Override
    @Transactional
    public void cancelReservation(int bookingId) {
        CoworkingSpaceBooking booking = coworkingSpaceBookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking doesn't exist."));
        CoworkingSpace space = booking.getCoworkingSpace();
        coworkingSpaceBookingRepository.delete(booking);
        space.setAvailability(true);
        coworkingSpaceRepository.save(space);
    }
}

