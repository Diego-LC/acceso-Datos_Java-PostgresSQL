import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.Map;

public class MostrarBD {
    
    public void mostrarDatosDB(String sql, Connection conn) {
        try (Statement stmt = conn.createStatement()) {

            System.out.println("\nMostrando datos...");

            // Imprime los nombres de las tablas de la base de datos conectada
            DatabaseMetaData md = conn.getMetaData();
            ResultSet rs = md.getTables(null, null, "%", new String[]{"TABLE"});
            /* while (rs.next()) {
                System.out.println(rs.getString(3)); // Imprime los nombres de las tablas de la base de datos conectada
            } */

            rs = stmt.executeQuery(sql);

            // Extraemos información del ResultSet y lo almacenamos en un diccionario
            Map<String, Integer> map = new LinkedHashMap<>();
            for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                map.put(rs.getMetaData().getColumnName(i), rs.getMetaData().getColumnType(i));
            }

            // Paso 5: Extraer datos del conjunto de resultados
            while (rs.next()) {
                // Recuperar por nombre de columna desde el diccionario
                for (Map.Entry<String, Integer> entry : map.entrySet()) {
                    System.out.print(entry.getKey() + ": ");
                    if (entry.getValue() == 4) { // 4 es el tipo de dato INT
                        System.out.print((rs.getInt(entry.getKey())) + "  ");
                    } else if (entry.getValue() == 12) { // 12 es el tipo de dato VARCHAR
                        System.out.print(rs.getString(entry.getKey()) == null ? "  " : rs.getString(entry.getKey()) + "  ");
                    } else if (entry.getValue() == 91) { // 91 es el tipo de dato DATE
                        System.out.print(rs.getDate(entry.getKey()) == null ? "  " : rs.getDate(entry.getKey()) + "  ");
                    }
                }
                System.out.println();
            }

        } catch (SQLException se) {
            System.out.println("Error de JDBC");
            se.printStackTrace();
        }
    }
    public boolean mostrarReservas(int rut, Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            String sql1 = "SELECT Reserva.reserva_id, Habitacion.numero as numero_habitacion, Reserva.usuario_id as rut, fecha_entrada, fecha_salida, Habitacion.estado " 
            +"FROM Reserva INNER JOIN Habitacion ON Reserva.habitacion_id = Habitacion.habitacion_id WHERE Reserva.usuario_id = " + rut;
            String sql2 = "SELECT Registro_reservas.registro_id, Habitacion.numero as numero_habitacion, Registro_reservas.usuario_id as rut, fecha_entrada, fecha_salida, Habitacion.estado " 
            +"FROM Registro_reservas INNER JOIN Habitacion ON Registro_reservas.habitacion_id = Habitacion.habitacion_id WHERE Registro_reservas.usuario_id = " + rut;
            ResultSet rs = stmt.executeQuery(sql1);
            String reservasActuales = "";
            String reservasAnteriores = "";
            while (rs.next()) {
                reservasActuales += "Id: " + rs.getString(1) + " - Habitacion: " + rs.getString(2) + " - RUT: " + rs.getString(3) + " - Fecha de entrada: " + rs.getDate(4) + " - Fecha de salida: " + rs.getDate(5) + " - Estado: " + rs.getString(6) + "\n";
            }
            ResultSet rs2 = stmt.executeQuery(sql2);
            while (rs2.next()) {
                reservasAnteriores += "Habitacion: " + rs2.getString(2) + " - RUT: " + rs2.getString(3) + " - Fecha de entrada: " + rs2.getDate(4) + " - Fecha de salida: " + rs2.getDate(5) + "\n";
            }
            System.out.println("\nReservas actuales:\n " + reservasActuales);
            System.out.println("Reservas anteriores o eliminadas:\n " + reservasAnteriores);

            if (reservasActuales.equals("")) {
                return false;
            }
            return true;
        } catch (SQLException se) {
            System.out.println("Error de JDBC");
            se.printStackTrace();
        }
        return false;
    }
}
