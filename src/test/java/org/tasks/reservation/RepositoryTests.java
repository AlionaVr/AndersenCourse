package org.tasks.reservation;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class RepositoryTests {
    private final File testFile = new File("save.txt");
    private Repository repository;

    @BeforeEach
    void setUp() {
        repository = new Repository();
    }

    @AfterEach
    void deleteFile() {
        if (testFile.exists()) {
            assertTrue(testFile.delete(), "File saveTest.txt wasn't deleted");
        }
    }

    @Test
    void testSaveObject_AndReadFile() {
        //arrange
        repository.getSpaces().add(new CoworkingSpace("Space1", "open", 100.0));
        repository.getSpaces().add(new CoworkingSpace("Space2", "private", 200.0));

        //act
        repository.saveObject();
        Repository loadedRepository = new Repository();
        loadedRepository.readFile();

        //assert
        assertEquals(2, loadedRepository.getSpaces().size(), "Expected 2 objects in loadedRepository");
        assertTrue(loadedRepository.getSpaces().get(0).toString().contains("Space1"));
        assertTrue(loadedRepository.getSpaces().get(1).toString().contains("Space2"));
    }
}
