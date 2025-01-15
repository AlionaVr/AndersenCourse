package org.tasks.reservation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CustomerTests {
    private Repository repository;
    private Scanner scannerMock;
    private SpaceManager manager;
    private Customer customer;

    @BeforeEach
    void setUp() {
        repository = new Repository();
        scannerMock = mock(Scanner.class);
        manager = mock(SpaceManager.class);
        customer = new Customer(repository, scannerMock, manager);
    }

    @Test
    void testReserve() {
        // Arrange
        CoworkingSpace space1 = new CoworkingSpace("Space1", "open", 100.0);
        CoworkingSpace space2 = new CoworkingSpace("Space2", "open", 200.0);

        repository.getSpaces().addAll(space1, space2);

        when(scannerMock.nextLine()).thenReturn("Meeting at 10 AM");

        // Act
        customer.reserve(1);

        // Assert
        assertFalse(space1.isAvailable());
        verify(manager).addSpaceToMyReservation(any(CoworkingSpaceBooking.class));
    }

    @Test
    void testCancelReservation() {
        //arrange
        CoworkingSpace space1 = new CoworkingSpace("Space1", "open", 100.0);
        CoworkingSpaceBooking booking1 = new CoworkingSpaceBooking(space1, "Meeting at 10 AM");
        repository.getMyReservations().add(booking1);

        when(manager.removeSpaceFromMyReservation(0)).thenReturn(booking1);

        // act
        customer.cancelReservation(1);
        // assert
        assertTrue(space1.isAvailable());
    }
}


