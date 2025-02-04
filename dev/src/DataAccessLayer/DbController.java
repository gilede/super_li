package DataAccessLayer;



import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public abstract class DbController {
    private static final String DB_NAME = "dev/src/resources/superli.db";
    private static final String CONNECTION_STRING = "jdbc:sqlite:" + DB_NAME;
    protected Connection connection;
    protected String tableName;
    public DbController(String tableName) {
        this.tableName = tableName;
        try {
            // Use ClassLoader to get the resource as an InputStream
            InputStream inputStream = DbController.class.getClassLoader().getResourceAsStream("resources/superli.db");

            if (inputStream != null) {

                String path = DbController.class.getClassLoader().getResource("resources/superli.db").getPath();
                // Define the part of the path that should be replaced
                String replaceFrom = "/out/production/ADDS_Group_AU/";
                String replaceTo = "/dev/src/";

                // Find the index where "/out/production/" occurs
                int index = path.indexOf(replaceFrom);
                if (index != -1) {
                    // Perform the replacement
                    String modifiedPath = path.substring(0, index) + replaceTo + path.substring(index + replaceFrom.length());
                    String path1 = "/C:/Users/gilpl/Desktop/ADDS_Group_AU/dev/src/resources/superli.db";
                    // Create a Path for the target file
                    String connectionString = "jdbc:sqlite:" + modifiedPath;
                    // Load SQLite JDBC driver
                    Class.forName("org.sqlite.JDBC");

                    // Establish connection using the connection string
                    connection = DriverManager.getConnection(connectionString);
                    // Continue with your database operations using modifiedPath
                } else {
                    otherconect();
                }
                if (connection == null) {
                    throw new AssertionError("Database file not found");
                    // Perform any initial database setup here if needed
                }

            }
        } catch (Exception e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
        }
    }
    public void otherconect() {
        try {
            // Use ClassLoader to get the resource as an InputStream
            InputStream inputStream = DbController.class.getClassLoader().getResourceAsStream("resources/superli.db");

            if (inputStream != null) {
                // Get the desktop path
                String desktopPath = System.getProperty("user.home") + "/Desktop";
                File desktopFolder = new File(desktopPath);
                if (!desktopFolder.exists() || !desktopFolder.isDirectory()) {
                    throw new Exception("Desktop folder not found");
                }

                // Create the file on the desktop
                File desktopFile = new File(desktopPath, "superli.db");
                // Schedule the file for deletion on JVM exit
                desktopFile.deleteOnExit();

                // Write the InputStream to the file on the desktop
                try (FileOutputStream outputStream = new FileOutputStream(desktopFile)) {
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                }

                String connectionString = "jdbc:sqlite:" + desktopFile.getAbsolutePath();
                // Load SQLite JDBC driver
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection(connectionString);
                if (connection == null) {
                    throw new AssertionError("Database connection failed");
                    // Perform any initial database setup here if needed
                }

            }
        } catch (Exception e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
        }
    }
    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Error closing the database connection: " + e.getMessage());
        }
    }

    public ArrayList<DTO> selectAll() {
        ArrayList<DTO> results = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            String sql = "SELECT * FROM " + this.tableName;
            var resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                results.add(convertReaderToObject(resultSet));
            }
        } catch (SQLException e) {
            System.err.println("Error executing selectAll: " + e.getMessage());
        }
        return results;
    }
    public ArrayList<DTO> select_by_id(String supplierId, String tableName, String columnName, Function<ResultSet, DTO> converter) {
        ArrayList<DTO> results = new ArrayList<>();
        String sql = "SELECT * FROM " + tableName + " WHERE " + columnName + " = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
             preparedStatement.setString(1, supplierId);
             ResultSet resultSet = preparedStatement.executeQuery();

             while (resultSet.next()) {
                 results.add(converter.apply(resultSet));
             }
        } catch (SQLException e) {
            System.err.println("Error executing selectAllBySupplierId: " + e.getMessage());
        }
        return results;
    }
    public void deleteAll(String tableName) {
        try (Statement statement = connection.createStatement()) {
            String sql = "DELETE FROM " + tableName;
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            System.err.println("Error executing deleteAll: " + e.getMessage());
        }
    }
    protected abstract DTO convertReaderToObject(ResultSet resultSet) throws SQLException;
;
}
