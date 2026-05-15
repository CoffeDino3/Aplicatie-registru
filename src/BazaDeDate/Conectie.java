package BazaDeDate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conectie {
    private static final String URL = "jdbc:postgresql://localhost:5432/Registru";
    private static final String USER = "postgres";
    private static final String PASSWORD = "1234";
    private static Connection instance;

    public static Connection getConnection() throws SQLException {
        if (instance== null || instance.isClosed()) {
            try {
                instance =DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (SQLException e) {
                System.err.println("Eroare la conectarea cu baza de date: " + e.getMessage());
                throw e;
            }
        }
        return instance;
    }
}