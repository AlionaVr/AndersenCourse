package org.tasks.reservation.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.tasks.reservation.entity.CoworkingSpace;
import org.tasks.reservation.repository.CoworkingSpaceRepository;
import org.tasks.reservation.service.AdminService;

import java.util.List;

@Controller
public class AdminController {
    private final CoworkingSpaceRepository coworkingSpaceRepository;
    @Qualifier("webAdminService")
    private final AdminService adminService;

    public AdminController(CoworkingSpaceRepository coworkingSpaceRepository, AdminService adminService) {
        this.coworkingSpaceRepository = coworkingSpaceRepository;
        this.adminService = adminService;
    }

    @GetMapping("/coworkingSpaces")
    public String viewCoworkingSpaces(Model model) {
        List<CoworkingSpace> spaces = coworkingSpaceRepository.getSpaces();
        model.addAttribute("coworkingSpaces", spaces);
        return "coworkingSpaces.html";
    }

    @PostMapping(path = "/coworkingSpace")
    public String addCoworkingSpace(
            @RequestParam(name = "name") String name,
            @RequestParam(name = "type") String type,
            @RequestParam(name = "price") String price
    ) {
        CoworkingSpace coworkingSpace = new CoworkingSpace(name, type, Double.parseDouble(price));
        adminService.addSpace(coworkingSpace);
        return "redirect:/coworkingSpaces";
    }

    @GetMapping("/coworkingSpace/delete")
    public String deleteCoworkingSpace(@RequestParam("id") Integer id) {
        adminService.removeSpace(id);
        return "redirect:/coworkingSpaces";
    }

    @PostMapping(path = "/coworkingSpace/update")
    public String updateCoworkingSpace(
            @RequestParam(name = "id") int id,
            @RequestParam(name = "name") String name,
            @RequestParam(name = "type") String type,
            @RequestParam(name = "price") double price
    ) {
        CoworkingSpace updatedSpace = new CoworkingSpace(name, type, price);
        adminService.updateSpace(id, updatedSpace);
        return "redirect:/coworkingSpaces";
    }

}

