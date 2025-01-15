package org.tasks.reservation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        when(scannerMock.nextLine()).thenReturn("3");
        when(scannerMock.nextLine()).thenReturn("6");
        //act
        int inputSpace = manager.getValidInputNumber(maxNumber);
        //assert
        verify(scannerMock, times(1)).nextLine();
        assertEquals(3, inputSpace);
    }
}
