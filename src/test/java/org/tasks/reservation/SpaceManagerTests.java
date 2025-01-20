package org.tasks.reservation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class SpaceManagerTests {
    private SpaceManager manager;
    private Scanner scannerMock;

    @BeforeEach
    void setUp() {
        scannerMock = mock(Scanner.class);
        Repository repository = new Repository();
        manager = new SpaceManager(repository, scannerMock);
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