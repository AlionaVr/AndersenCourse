package org.tasks.reservation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

public class CustomListTests {
    private CustomList<String> list;

    @BeforeEach
    void setUp() {
        list = new CustomList<>();
    }

    @Test
    void testAdd() {
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
    void testAddAll() {
        //act
        list.addAll("element1", "element2", "element3", "element4");

        //assert
        assertEquals(4, list.size());
        assertEquals("element1", list.get(0));
        assertEquals("element2", list.get(1));
    }

    @Test
    void testCheckIndexAndGetElement() {
        //arrange
        list.addAll("element1", "element2", "element3", "element4");

        //act and assert
        assertEquals("element2", list.get(1));
        assertEquals("element3", list.get(2));
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(4));
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(-4));
    }

    @Test
    void testRemove_InvalidIndex() {
        // arrange
        list.addAll("element1", "element2", "element3", "element4");
        // act
        list.remove(2);
        // act and assert
        assertEquals(3, list.size());
        assertEquals("element4", list.get(2));
        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(3));
    }

    @Test
    void testIterator() {
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
