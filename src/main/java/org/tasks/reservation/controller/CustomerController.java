package org.tasks.reservation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.tasks.reservation.dto.StatusResponseDto;
import org.tasks.reservation.entity.CoworkingSpace;
import org.tasks.reservation.entity.CoworkingSpaceBooking;
import org.tasks.reservation.repository.CoworkingSpaceBookingRepository;
import org.tasks.reservation.repository.CoworkingSpaceRepository;
import org.tasks.reservation.service.CustomerService;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CustomerController {
    private final CoworkingSpaceBookingRepository coworkingSpaceBookingRepository;
    private final CustomerService customerService;
    private final CoworkingSpaceRepository coworkingSpaceRepository;

    @GetMapping("/coworkingBookingSpaces")
    public String viewAvailableSpaces(Model model) {
        populateSpacesAndReservations(model);
        return "templates/reservations.html";
    }

    private void populateSpacesAndReservations(Model model) {
        List<CoworkingSpace> availableSpaces = coworkingSpaceRepository.getAvailableSpaces();
        List<CoworkingSpaceBooking> reservations = coworkingSpaceBookingRepository.getMyReservations();

        model.addAttribute("AvailableSpaces", availableSpaces);
        model.addAttribute("MyReservation", reservations);
    }

    @PostMapping(path = "/reservations")
    public String reserveCoworkingSpace(
            @RequestParam(name = "id") int id,
            @RequestParam(name = "bookingDetails") String bookingDetails,
            @RequestParam(name = "spaceDate") String spaceDate,
            Model model
    ) {
        StatusResponseDto response;
        try {
            customerService.reserve(id, bookingDetails, LocalDate.parse(spaceDate));
            response = new StatusResponseDto();
        } catch (Exception ex) {
            response = new StatusResponseDto(ex.getMessage());
        }
        model.addAttribute("reserveResponseDto", response);
        populateSpacesAndReservations(model);
        return "templates/reservations.html";
    }
    @PostMapping("/reservations/cancel")
    public String cancelReservation(@RequestParam("id") Integer id) {
        customerService.cancelReservation(id);
        return "redirect:/coworkingBookingSpaces";
    }
}
