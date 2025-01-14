package org.tasks.reservation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Optional;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;


public class AdminTests {
    private Repository repository;
    private Admin admin;
    private Scanner scannerMock;

    @BeforeEach
    void setUp() {
        repository = new Repository();
        scannerMock = mock(Scanner.class);
        admin = new Admin(repository, scannerMock);
    }

    @Test
    void testRemoveSpace_WhenListIsNotEmpty() {
        //arrange
        repository.getSpaces().add(new CoworkingSpace("Space1", "open", 100.0));
        repository.getSpaces().add(new CoworkingSpace("Space2", "private", 200.0));

        //act
        admin.removeSpace(1);

        //assert
        assertEquals(1, repository.getSpaces().size());
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 5})
    void testRemoveSpace_WhenListIsEmpty_OrInvalidInput(int numberOfSpaceToDelete) {
        //act
        admin.removeSpace(numberOfSpaceToDelete);
        //assert
        assertEquals(0, repository.getSpaces().size());
    }

    @Test
    void testAddSpace() {
        //arrange
        CoworkingSpace newSpace = new CoworkingSpace("AddedSpace", "open", 100.0);
        CoworkingSpace newSpace2 = new CoworkingSpace("AddedSpace2", "open", 200.0);
        //act
        repository.getSpaces().add(newSpace);
        repository.getSpaces().add(newSpace2);
        //assert
        assertEquals(2, repository.getSpaces().size());
        assertEquals(newSpace, repository.getSpaces().get(0));
        assertEquals(newSpace2, repository.getSpaces().get(1));
    }

    @Test
    void testAskUserToWriteCoworkingSpaceString() {
        when(scannerMock.nextLine()).thenReturn("Space1,private,100.0");
        //act
        Optional<CoworkingSpace> inputSpace = admin.askUserToWriteCoworkingSpaceString();
        //assert
        verify(scannerMock, times(1)).nextLine();
        assertTrue(inputSpace.isPresent());
        assertTrue(inputSpace.get().toString().contains("Space1"));
    }

}
