package org.tasks.reservation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.tasks.reservation.entities.CoworkingSpace;

import java.util.Optional;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;


public class AdminTests {

    private EntityManager entityManager;
    private EntityTransaction transaction;
    private Admin admin;
    private Scanner scannerMock;

    @BeforeEach
    void setUp() {
        entityManager = mock(EntityManager.class);
        transaction = mock(EntityTransaction.class);
        scannerMock = mock(Scanner.class);
        when(entityManager.getTransaction()).thenReturn(transaction);
        admin = new Admin(entityManager, scannerMock);
    }

    @Test
    void givenSpaceIsFound_whenRemoveSpace_thenCommitTransactionAndRemoveSpace() {
        //arrange
        admin.removeSpace(1);
        int id = 1;
        CoworkingSpace coworkingSpace = new CoworkingSpace("Space1", "open", 100.0);
        when(entityManager.find(CoworkingSpace.class, id)).thenReturn(coworkingSpace);
        //act
        admin.removeSpace(id);
        //assert
        verify(entityManager).find(CoworkingSpace.class, id);
        verify(entityManager).remove(coworkingSpace);
        verify(transaction).begin();
        verify(transaction).commit();
    }

    @Test
    void givenSpaceNotFound_whenRemoveSpace_thenCommitTransactionAndRemoveSpace() {
        //arrange
        int id = 1;
        CoworkingSpace space = new CoworkingSpace("Space1", "Type1", 100.0);
        when(entityManager.find(CoworkingSpace.class, id)).thenReturn(space);
        //act
        admin.removeSpace(id);
        //assert
        verify(entityManager).find(CoworkingSpace.class, id);
        verify(entityManager).remove(space);
        verify(transaction).begin();
        verify(transaction).commit();
    }

    @Test
    void givenSpace_whenAddToDataBase_thenPersistSpaceAndCommitTransaction() {
        //arrange
        CoworkingSpace newSpace = new CoworkingSpace("AddedSpace", "open", 100.0);
        //act
        admin.addSpace(newSpace);
        //assert
        verify(entityManager).persist(newSpace);
        verify(transaction).begin();
        verify(transaction).commit();
    }

    @Test
    void givenValidCoworkingSpace_whenAskUserToWriteCoworkingSpaceString_thenCoworkingSpaceShouldContainCorrectData() {
        when(scannerMock.nextLine()).thenReturn("Space1,private,100.0");
        //act
        Optional<CoworkingSpace> inputSpace = admin.askUserToWriteCoworkingSpaceString();
        //assert
        verify(scannerMock, times(1)).nextLine();
        assertTrue(inputSpace.isPresent());
        assertTrue(inputSpace.get().toString().contains("Space1"));
    }
}
