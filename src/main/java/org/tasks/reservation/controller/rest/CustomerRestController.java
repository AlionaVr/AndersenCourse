package org.tasks.reservation.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tasks.reservation.dto.StatusResponseDto;
import org.tasks.reservation.entity.CoworkingSpace;
import org.tasks.reservation.entity.CoworkingSpaceBooking;
import org.tasks.reservation.repository.CoworkingSpaceBookingRepository;
import org.tasks.reservation.repository.CoworkingSpaceRepository;
import org.tasks.reservation.service.CustomerService;

import java.util.List;

@RestController
@RequestMapping("/rest/coworkingBookingSpaces")
@RequiredArgsConstructor
public class CustomerRestController {

    private final CoworkingSpaceBookingRepository coworkingSpaceBookingRepository;
    private final CustomerService customerService;
    private final CoworkingSpaceRepository coworkingSpaceRepository;

    @GetMapping("/available")
    public ResponseEntity<List<CoworkingSpace>> viewAvailableSpaces() {
        List<CoworkingSpace> availableSpaces = coworkingSpaceRepository.findCoworkingSpaceByAvailability(true);
        return ResponseEntity.ok(availableSpaces);
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<CoworkingSpaceBooking>> viewReservations() {
        List<CoworkingSpaceBooking> reservations = coworkingSpaceBookingRepository.findAll();
        return ResponseEntity.ok(reservations);
    }

    @PostMapping("/reserve")
    public ResponseEntity<StatusResponseDto> reserveCoworkingSpace(
            @RequestParam(name = "id") int id,
            @RequestParam(name = "bookingDetails") String bookingDetails,
            @RequestParam(name = "spaceDate") String spaceDate
    ) {
        try {
            customerService.reserve(id, bookingDetails);
            return ResponseEntity.ok(new StatusResponseDto());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new StatusResponseDto(ex.getMessage()));
        }
    }

    @PostMapping("/cancel/{id}")
    public ResponseEntity<?> cancelReservation(@PathVariable("id") Integer id) {
        try {
            customerService.cancelReservation(id);
            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}

