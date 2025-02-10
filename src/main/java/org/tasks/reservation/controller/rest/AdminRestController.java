package org.tasks.reservation.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.tasks.reservation.entity.CoworkingSpace;
import org.tasks.reservation.repository.CoworkingSpaceRepository;
import org.tasks.reservation.service.AdminService;

import java.util.List;

@RestController
@RequestMapping("/rest/coworkingSpaces")

public class AdminRestController {

    private final CoworkingSpaceRepository coworkingSpaceRepository;

    private final AdminService adminService;

    @Autowired
    public AdminRestController(CoworkingSpaceRepository coworkingSpaceRepository, AdminService adminService) {
        this.coworkingSpaceRepository = coworkingSpaceRepository;
        this.adminService = adminService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public ResponseEntity<List<CoworkingSpace>> viewCoworkingSpaces() {
        System.out.println("Контроллер вызван!");
        List<CoworkingSpace> spaces = coworkingSpaceRepository.findAll();
        return ResponseEntity.ok(spaces);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<?> addCoworkingSpace(@RequestBody CoworkingSpace coworkingSpace) {
        try {
            adminService.addSpace(coworkingSpace);
            return ResponseEntity.status(HttpStatus.CREATED).body(coworkingSpace);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/delete/{id}")
    public ResponseEntity<?> deleteCoworkingSpace(@PathVariable(name = "id") int id) {
        try {
            adminService.removeSpace(id);
            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/update/{id}")
    public ResponseEntity<?> updateCoworkingSpace(@PathVariable(name = "id") int id,
                                                  @RequestBody CoworkingSpace updatedSpace) {
        try {
            adminService.updateSpace(id, updatedSpace);
            CoworkingSpace result = coworkingSpaceRepository.findById(id).get();
            return ResponseEntity.ok(result);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
