package org.tasks.reservation;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Properties;

public class Main {
    private final static Repository repository = new Repository();
    public static Connection postgresDbConnection;

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Main main = new Main();

        try (Connection connection = getDatabaseConnection()) {
            postgresDbConnection = connection;
            main.tryToLoadMenu();
        }
    }

    private static Connection getDatabaseConnection() throws SQLException, ClassNotFoundException {
        String jdbcUrl = getPropertyValue("jdbcUrl");
        String username = getPropertyValue("username");
        String password = getPropertyValue("password");

        Class.forName("org.postgresql.Driver");

        postgresDbConnection = DriverManager.getConnection(jdbcUrl, username, password);
        System.out.println("Connected to database successfully!");
        return postgresDbConnection;
    }

    private static String getPropertyValue(String propertyKey) {
        Properties properties = new Properties();

        try (FileInputStream fis = new FileInputStream("src\\main\\resources\\database.property")) {
            properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(properties.getProperty(propertyKey))
                .orElseThrow(() -> new IllegalArgumentException("Property not found for key: " + propertyKey));
    }

    private void tryToLoadMenu() {
        try {
            String directoryPath = "target\\classes\\";
            CustomClassLoader classLoader = new CustomClassLoader(directoryPath);
            String className = "org.tasks.reservation.MenuLauncher";
            Class<?> loadedClass = classLoader.loadClass(className);
            Object instance = loadedClass.getDeclaredConstructor(Repository.class).newInstance(repository);
            Method method = loadedClass.getMethod("showMainMenu");
            method.invoke(instance);
        } catch (Exception e) {
            System.out.println("Something went wrong " + e.getMessage());
            e.printStackTrace();
        }
    }
}
