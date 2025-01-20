package org.tasks.reservation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

public class CustomListTests {
    private CustomList<String> list;

    @BeforeEach
    void setUp() {
        list = new CustomList<>();
    }

    @Test
    void givenElementsAndEmptyList_whenAdd_thenListContainsElements() {
        //arrange
        String element1 = "Element1";
        String element2 = "Element2";

        //act
        list.add(element1);
        list.add(element2);

        //assert
        assertEquals(2, list.size());
        assertEquals("element1", list.get(0));
        assertEquals("element2", list.get(1));
    }

    @Test
    void givenElementsAndEmptyList_whenAddAll_thenListContainsAllElements() {
        //act
        list.addAll("element1", "element2", "element3", "element4");

        //assert
        assertEquals(4, list.size());
        assertEquals("element1", list.get(0));
        assertEquals("element2", list.get(1));
    }

    @Test
    void givenElementsInList_whenGetElement_thenReturnRightElement() {
        //arrange
        list.addAll("element1", "element2", "element3", "element4");
        //act and assert
        assertEquals("element2", list.get(1));
        assertEquals("element3", list.get(2));
    }

    @ParameterizedTest
    @ValueSource(ints = {-4, 4})
    void givenElementsInList_whenGetElementByInvalidIndex_thenReturnException(int invalidIndex) {
        //arrange
        list.addAll("element1", "element2", "element3", "element4");
        //act and assert
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(invalidIndex));
    }

    @Test
    void givenElementsInList_whenRemoveByIndex_thenSizeDecreasesAndReturnCorrectElements() {
        // arrange
        list.addAll("element1", "element2", "element3", "element4");
        // act
        list.remove(2);
        // assert
        assertEquals(3, list.size());
        assertEquals("element4", list.get(2));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 4})
    void givenElementsInList_whenRemoveByInvalidIndex_thenSReturnException(int invalidIndex) {
        // arrange
        list.addAll("element1", "element2", "element3", "element4");
        // act and assert
        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(invalidIndex));
    }

    @Test
    void givenElementsInList_whenIteratorIsUsed_thenIteratesOverAllElements() {
        // arrange
        list.addAll("element1", "element2");
        // act
        Iterator<String> iterator = list.iterator();
        // assert
        assertTrue(iterator.hasNext());
        assertEquals("element1", iterator.next());
        assertEquals("element2", iterator.next());
        assertFalse(iterator.hasNext());
    }
}
