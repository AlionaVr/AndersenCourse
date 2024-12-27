package org.tasks.reservation;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

public class Main {
    private final static Repository repository = new Repository();

    public static void main(String[] args) throws IOException {
        Main main = new Main();

        File file = new File("save.txt");
        if (file.exists()) {
            repository.readFile();
        } else {
            System.out.println("No saved file found, starting with an empty repository.");
        }
      /*
        try (FileInputStream fileInputStream = new FileInputStream("save.txt")) {
            repository.readFile();
            System.out.println("data are restored\n");
        } catch (FileNotFoundException e) {
            System.out.println("No saved file found, starting with an empty repository.");
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file: " + e.getMessage());
        }*/
      
        main.tryToLoadMenu();

        repository.saveObject();
    }

    protected void tryToLoadMenu() {
        try {
            String directoryPath = "D:\\Projects\\AndersenCourse\\target\\classes\\";
            CustomClassLoader classLoader = new CustomClassLoader(directoryPath);
            String className = "org.tasks.reservation.MenuLauncher";
            Class<?> loadedClass = classLoader.loadClass(className);
            Object instance = loadedClass.getDeclaredConstructor(Repository.class).newInstance(repository);
            Method method = loadedClass.getMethod("showMainMenu");
            method.invoke(instance);
        } catch (Exception e) {
            System.out.println("Something went wrong " + e.getMessage());
        }
    }
}
