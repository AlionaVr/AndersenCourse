
package org.tasks.reservation;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.tasks.reservation.entity.CoworkingSpace;
import org.tasks.reservation.entity.CoworkingSpaceBooking;
import org.tasks.reservation.repository.CoworkingSpaceBookingRepository;
import org.tasks.reservation.repository.CoworkingSpaceRepository;

import java.util.List;
import java.util.function.Predicate;

@Component
@RequiredArgsConstructor
public class SpaceManager {

    private final CoworkingSpaceRepository coworkingSpaceRepository;
    private final CoworkingSpaceBookingRepository coworkingSpaceBookingRepository;

    public void showSpaces(Predicate<CoworkingSpace> availabilityFilter) {
        List<CoworkingSpace> filteredSpaces = coworkingSpaceRepository.getSpaces().stream()
                .filter(availabilityFilter)
                .toList();

        if (filteredSpaces.isEmpty()) {
            System.out.println("Empty list.");
        } else filteredSpaces.forEach(space -> {
            System.out.println("\n-------------------------------------------------");
            System.out.println(space.toString());
            System.out.println("-------------------------------------------------");
        });
    }

    public void showMyReservation() {
        List<CoworkingSpaceBooking> myReservations = coworkingSpaceBookingRepository.getMyReservations().stream().toList();

        if (myReservations.isEmpty()) {
            System.out.println("Empty list.");
        } else myReservations.forEach(space -> {
            System.out.println("\n-------------------------------------------------");
            System.out.println(space.toString());
            System.out.println("-------------------------------------------------");
        });
    }


    public boolean isSpaceExist(CoworkingSpace space, EntityManager entityManager) {
        if (space == null) {
            System.out.println("Space is not found");
            entityManager.getTransaction().rollback();
            return false;
        } else return true;
    }
}
