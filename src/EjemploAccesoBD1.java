import java.sql.*;

public class EjemploAccesoBD1 {
    public static void main(String[] args) {
        Connection conexion = null;
        try {
            // Cargar el driver
            Class.forName("org.postgresql.Driver");
            System.out.println("Driver cargado" + "\n" + "Conectando con la base de datos...");

            // Se obtiene una conexión con la base de datos.
            // En este caso nos conectamos a la base de datos comida
            // con el usuario root y contraseña 1daw
            conexion = DriverManager.getConnection("jdbc:postgresql://localhost:5432/hotel_reservacion", "postgres", "root1234");
            System.out.println("Conexión realizada");

            // Se crea un Statement, para realizar la consulta
            Statement s = conexion.createStatement();

            // Se realiza la consulta. Los resultados se guardan en el ResultSet rs
            ResultSet rs = s.executeQuery("select * from habitacion");

            // Se recorre el ResultSet, mostrando por pantalla los resultados.
            while (rs.next()) {
                System.out.println(rs.getInt("habitacion_id") + " " + rs.getString(2) + " " + rs.getString(3));
            }
        } catch (SQLException e) {
            System.out.println("Error en la conexión con la base de datos");
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("No se ha encontrado la clase");
            System.out.println(e.getMessage());
        } finally {
            // Se cierra la conexión con la base de datos.
            try {
                if (conexion != null) {
                    conexion.close();
                    System.out.println("Conexión cerrada");
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}