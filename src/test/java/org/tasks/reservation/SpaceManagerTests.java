package org.tasks.reservation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

    static Stream<Arguments> provideInputForGetValidInputNumber() {
        return Stream.of(
            Arguments.of(new String[]{"abc", "3"}, 5, 3),
            Arguments.of(new String[]{"-1", "5"}, 5, 5),
            Arguments.of(new String[]{"3"}, 5, 3),
            Arguments.of(new String[]{"6", "4"}, 5, 4)
        );
    }

    @ParameterizedTest
    @MethodSource("provideInputForGetValidInputNumber")
    void testGetValidInputNumber(String[] inputs, int maxNumber, int expected) {
        //arrange
        when(scannerMock.nextLine()).thenReturn(inputs[0], Arrays.copyOfRange(inputs, 1, inputs.length));
        //act
        int input = manager.getValidInputNumber(maxNumber);
        //assert
        assertEquals(expected, input);
        verify(scannerMock, times(inputs.length)).nextLine();
    }

    @Test
    void testAskUserToWriteNumberOfSpace() {
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