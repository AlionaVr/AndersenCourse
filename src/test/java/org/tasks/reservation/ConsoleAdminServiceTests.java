/*
package org.tasks.reservation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.tasks.reservation.entity.CoworkingSpace;
import org.tasks.reservation.helper.ExecutorEntityManagerHelper;
import org.tasks.reservation.service.impl.ConsoleAdminService;

import java.util.Optional;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;


public class ConsoleAdminServiceTests {

    private ExecutorEntityManagerHelper executorEntityManagerHelperMock;
    private ConsoleAdminService consoleAdminService;
    private Scanner scannerMock;
    private SpaceManager manager;

    @BeforeEach
    void setUp() {
        executorEntityManagerHelperMock = mock(ExecutorEntityManagerHelper.class);
        scannerMock = mock(Scanner.class);
        manager = mock(SpaceManager.class);
        this.consoleAdminService = new ConsoleAdminService(scannerMock, executorEntityManagerHelperMock, manager);
    }

    @Test
    void givenSpace_whenRemoveSpace_thenExecuteWithEntityManager() {
        //arrange
        int id = 1;
        CoworkingSpace coworkingSpace = new CoworkingSpace("Space1", "open", 100.0);
        //act
        consoleAdminService.removeSpace(id);
        //assert
        verify(executorEntityManagerHelperMock).executeWithEntityManager(
                any(),
                eq("DELETED!"),
                eq("Error deleting coworking space: "));
    }

    @Test
    void givenSpace_whenAddToDataBase_thenPersistSpaceAndCommitTransaction() {
        //arrange
        CoworkingSpace newSpace = new CoworkingSpace("AddedSpace", "open", 100.0);
        //act
        consoleAdminService.addSpace(newSpace);
        //assert
        verify(executorEntityManagerHelperMock).executeWithEntityManager(
                any(),
                eq("Coworking space added successfully!"),
                eq("Error adding coworking space: ")
        );
    }

    @Test
    void givenValidCoworkingSpace_whenAskUserToWriteCoworkingSpaceString_thenCoworkingSpaceShouldContainCorrectData() {
        when(scannerMock.nextLine()).thenReturn("Space1,private,100.0");
        //act
        Optional<CoworkingSpace> inputSpace = consoleAdminService.askUserToWriteCoworkingSpaceString();
        //assert
        verify(scannerMock, times(1)).nextLine();
        assertTrue(inputSpace.isPresent());
        assertTrue(inputSpace.get().toString().contains("Space1"));
    }
}
*/
