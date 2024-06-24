import java.sql.*;
import java.util.Scanner;

public class InsertarSQL {
    public void insertarDatosSQL(Connection conn, String sql) {
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("Datos insertados...");
            MostrarBD mostrar = new MostrarBD();
            mostrar.mostrarDatosDB("SELECT * FROM Reserva", conn);
            System.out.println(" ");
        } catch (SQLException se) {
            System.out.println("Error al insertar datos...");
            se.printStackTrace();
            System.out.println(" ");
        }
    }

    void insertarEnBD(Scanner scanner) {
        ConectarBD conectarBD = new ConectarBD();
        Connection conn = conectarBD.getConnection();
        try (Statement stmt = conn.createStatement()) {
            System.out.println(" ");
            System.out.print("Ingrese el ID de la habitación: ");
            int habitacionId = Integer.parseInt(scanner.nextLine());
            System.out.print("Ingrese el ID del usuario: ");
            int usuarioId = Integer.parseInt(scanner.nextLine());
            System.out.print("Ingrese la fecha de entrada (YYYY-MM-DD): ");
            String fechaEntrada = scanner.nextLine();
            System.out.print("Ingrese la fecha de salida (YYYY-MM-DD): ");
            String fechaSalida = scanner.nextLine();
            System.out.print("Ingrese el estado de la reserva: ");
            String estado = scanner.nextLine();

            String sql = "INSERT INTO Reserva (habitacion_id, usuario_id, fecha_entrada, fecha_salida, estado) "+
            "VALUES (" + habitacionId + ", " + usuarioId + ", '" + fechaEntrada + "', '" + fechaSalida + "', '" + estado + "')";
            System.out.println(sql);
            insertarDatosSQL(conn, sql);
        } catch (SQLException se) {
            System.out.println("Error al conectar a la base de datos...");
            se.printStackTrace();
        }
    }

    void actualizarEnBD(Scanner scanner, String reservaId) {
        ConectarBD conectarBD = new ConectarBD();
        Connection conn = conectarBD.getConnection();
        try (Statement stmt = conn.createStatement()) {
            System.out.print("Ingrese su RUT (sin puntos ni digito verificador): ");
            int usuarioId = Integer.parseInt(scanner.nextLine());
            System.out.print("Ingrese la nueva fecha de entrada (YYYY-MM-DD): ");
            String fechaEntrada = scanner.nextLine();
            System.out.print("Ingrese la nueva fecha de salida (YYYY-MM-DD): ");
            String fechaSalida = scanner.nextLine();
            System.out.print("Ingrese el nuevo estado de la reserva: ");
            String estado = scanner.nextLine();

            String sql = "UPDATE Reserva "+
            "SET rut = " + usuarioId + ", fecha_entrada = '" + fechaEntrada + "', fecha_salida = '" + fechaSalida + "', estado = '" + estado +
            "' WHERE reserva_id = " + reservaId;
            insertarDatosSQL(conn, sql);
        } catch (SQLException se) {
            System.out.println("Error al conectar a la base de datos...");
            se.printStackTrace();
        }
    }
}
