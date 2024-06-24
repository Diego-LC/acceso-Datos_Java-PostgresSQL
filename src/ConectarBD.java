import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConectarBD {
    private static final String JDBC_DRIVER = "org.postgresql.Driver";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/hotel_reservacion";
    private static final String USER = "admin";
    private static final String PASS = "root1234";
    private static Connection conn = null;

    // Constructor privado para evitar instanciación
    ConectarBD() {}

    // Método para obtener la conexión
    public Connection getConnection() {
        if (conn == null) {
            try {
                // Cargar driver JDBC
                Class.forName(JDBC_DRIVER);

                // Abrir una conexión
                System.out.println("Conectando a la base de datos...");
                conn = DriverManager.getConnection(DB_URL, USER, PASS);
            } catch (SQLException se) {
                // Errores de JDBC
                System.out.println("Error de JDBC: " + se.getMessage());
                se.printStackTrace();
            } catch (ClassNotFoundException e) {
                // Errores de Class.forName
                System.out.println("Error al cargar el driver JDBC: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return conn;
    }

    // Método para cerrar la conexión
    public static void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Conexión cerrada.");
            } catch (SQLException se) {
                System.out.println("Error al cerrar la conexión: " + se.getMessage());
                se.printStackTrace();
            }
        }
    }
}
