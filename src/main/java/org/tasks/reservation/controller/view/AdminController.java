package org.tasks.reservation.controller.view;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.tasks.reservation.dto.StatusResponseDto;
import org.tasks.reservation.entity.CoworkingSpace;
import org.tasks.reservation.helper.TypeOfSpace;
import org.tasks.reservation.repository.CoworkingSpaceRepository;
import org.tasks.reservation.service.AdminService;

import java.security.Principal;
import java.util.List;

@Controller
public class AdminController {
    private final CoworkingSpaceRepository coworkingSpaceRepository;

    private final AdminService adminService;

    public AdminController(CoworkingSpaceRepository coworkingSpaceRepository, AdminService adminService) {
        this.coworkingSpaceRepository = coworkingSpaceRepository;
        this.adminService = adminService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/coworkingSpaces")
    public String viewCoworkingSpaces(Model model, Principal principal) {
        populateSpaces(model);
        return "coworkingSpaces.html";
    }

    private void populateSpaces(Model model) {
        List<CoworkingSpace> spaces = coworkingSpaceRepository.findAll();
        model.addAttribute("coworkingSpaces", spaces);
        model.addAttribute("types", TypeOfSpace.values());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(path = "/coworkingSpace")
    public String addCoworkingSpace(
            @RequestParam(name = "name") String name,
            @RequestParam(name = "type") String type,
            @RequestParam(name = "price") String price
    ) {
        CoworkingSpace addingSpace = adminService.createSpace(name, type, Double.parseDouble(price));
        adminService.addSpace(addingSpace);
        return "redirect:/coworkingSpaces";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/coworkingSpace/delete")
    public String deleteCoworkingSpace(@RequestParam("id") Integer id, Model model) {
        try {
            adminService.removeSpace(id);
            model.addAttribute("deleteResponseDto", new StatusResponseDto());
        } catch (Exception ex) {
            model.addAttribute("deleteResponseDto", new StatusResponseDto(ex.getMessage()));
        }
        populateSpaces(model);
        return "coworkingSpaces.html";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(path = "/coworkingSpace/update")
    public String updateCoworkingSpace(
            @RequestParam(name = "id") int id,
            @RequestParam(name = "name") String name,
            @RequestParam(name = "type") String type,
            @RequestParam(name = "price") double price,
            Model model
    ) {
        CoworkingSpace updatedSpace = adminService.createSpace(name, type, price);
        try {
            adminService.updateSpace(id, updatedSpace);
            model.addAttribute("updateResponseDto", new StatusResponseDto());
        } catch (Exception ex) {
            model.addAttribute("updateResponseDto", new StatusResponseDto(ex.getMessage()));
        }
        populateSpaces(model);
        return "coworkingSpaces.html";
    }
}

