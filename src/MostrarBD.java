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
    public void mostrarReservas(String sql, Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            String reservasFuturas = "";
            String reservasAnteriores = "";
            while (rs.next()) {
                if (rs.getDate(4).after(new java.util.Date())) {
                    reservasFuturas += "Reserva: " + rs.getString(1) + " - Habitacion: " + rs.getString(2) + " - RUT: " + rs.getString(3) + " - Fecha de entrada: " + rs.getDate(4) + " - Fecha de salida: " + rs.getDate(5) + " - Estado: " + rs.getString(6) + "\n";
                }
                else {
                    reservasAnteriores += "Reserva: " + rs.getString(1) + " - Habitacion: " + rs.getString(2) + " - RUT: " + rs.getString(3) + " - Fecha de entrada: " + rs.getDate(4) + " - Fecha de salida: " + rs.getDate(5) + "\n";
                }
            }
            System.out.println("\nReservas futuras:\n " + reservasFuturas);
            System.out.println("Reservas anteriores:\n " + reservasAnteriores);
        } catch (SQLException se) {
            System.out.println("Error de JDBC");
            se.printStackTrace();
        }
    }
}
