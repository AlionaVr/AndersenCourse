package org.tasks.reservation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.tasks.reservation.repository.CoworkingSpaceBookingRepository;
import org.tasks.reservation.repository.CoworkingSpaceRepository;

import java.util.Optional;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class SpaceManagerTests {
    private SpaceManager manager;
    private Scanner scannerMock;
    private CoworkingSpaceRepository coworkingSpaceRepository;
    private CoworkingSpaceBookingRepository coworkingSpaceBookingRepository;

    @BeforeEach
    void setUp() {
        scannerMock = mock(Scanner.class);
        manager = new SpaceManager(scannerMock, coworkingSpaceRepository, coworkingSpaceBookingRepository);
    }

    @Test
    void givenInvalidAndValidInput_whenAskUserToWriteNumberOfSpace_thenReturnValidInput() {
        //arrange
        when(scannerMock.nextLine())
                .thenReturn("abc")
                .thenReturn("3");
        //act
        Optional<Integer> input1 = manager.askUserToWriteNumberOfSpace();
        Optional<Integer> input2 = manager.askUserToWriteNumberOfSpace();
        //assert
        assertTrue(input1.isEmpty());
        assertTrue(input2.isPresent());
        assertEquals(3, input2.get());
        verify(scannerMock, times(2)).nextLine();
    }
}