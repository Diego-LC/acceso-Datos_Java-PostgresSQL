import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class EliminarDatosSQL {
    ConectarBD conectarBD = new ConectarBD();
    Connection conn = conectarBD.getConnection();

    public void eliminarDatosSQL(Connection conn, String sql){
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("Datos eliminados...");
        } catch (SQLException se) {
            System.out.println("Error al eliminar datos...");
            se.printStackTrace();
            System.out.println(" ");
        }
    }

    void eliminarEnBD(Scanner scanner, String reservaId) {
        try (Statement stmt = conn.createStatement()) {
            String sql = "DELETE FROM Reserva WHERE reserva_id = " + reservaId;
            eliminarDatosSQL(conn, sql);
        } catch (SQLException se) {
            System.out.println("Error al conectar a la base de datos...");
            se.printStackTrace();
        }
    }

    void eliminarTodoEnBD(Scanner scanner) {
        try (Statement stmt = conn.createStatement()) {
            String sql = "DELETE FROM Reserva";
            eliminarDatosSQL(conn, sql);
        } catch (SQLException se) {
            System.out.println("Error al conectar a la base de datos...");
            se.printStackTrace();
        }
    }
}