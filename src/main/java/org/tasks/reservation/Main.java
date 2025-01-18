package org.tasks.reservation;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
        String jdbcUrl = "jdbc:postgresql://localhost:5432/postgres";
        String username = "postgres";
        String password = "dbpassword";

        Class.forName("org.postgresql.Driver");

        postgresDbConnection = DriverManager.getConnection(jdbcUrl, username, password);
        System.out.println("Connected to database successfully!");
        return postgresDbConnection;
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
            e.printStackTrace();
        }
    }
}
