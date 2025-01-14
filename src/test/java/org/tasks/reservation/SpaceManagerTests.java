package org.tasks.reservation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class SpaceManagerTests {
    private SpaceManager manager;
    private Scanner scannerMock;
    private Repository repository;

    @BeforeEach
    void setUp() {
        scannerMock = mock(Scanner.class);
        repository = new Repository();
        manager = new SpaceManager(repository, scannerMock);
    }

    @Test
    void testGetValidInputNumber() {
        //arrange
        int maxNumber = 5;
        when(scannerMock.nextLine()).thenReturn("5");
        //act
        Optional<CoworkingSpace> inputSpace = manager.getValidInputNumber();
        //assert
        verify(scannerMock, times(1)).nextLine();
        assertTrue(inputSpace.isPresent());
        assertTrue(inputSpace.get().toString().contains("Space1"));
    }
}
