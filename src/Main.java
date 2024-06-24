import java.sql.Connection;
import java.util.Scanner;

public class Main {
    private Menu menu = new Menu();
    private MostrarBD bd = new MostrarBD();
    private ConectarBD conectarBD = new ConectarBD();
    private Connection conn = conectarBD.getConnection();

    public static void main(String[] args) {
        Main main = new Main();
        main.mostrarMenuPrincipal();
        System.out.println("Adios!");
    }

    public void mostrarMenuPrincipal() {
        Scanner scanner = new Scanner(System.in);
        int opcion = 0;
        do {
            this.menu.mostrarMenuPrincipal();
            opcion = this.menu.leerOpcion(scanner);
            switch (opcion) {
                case 1:
                    mostrarMenuCliente(scanner);
                    break;
                case 2:
                    System.out.println("Saliendo de la aplicación...");
                    break;
                default:
                    this.menu.mostrarMensajeError();
            }
        } while (opcion != 2);

        scanner.close();
    }

    public void mostrarMenuCliente(Scanner scanner) {
        int opcion = 0;
        do {
            this.menu.mostrarMenuCliente();
            opcion = this.menu.leerOpcion(scanner);
            switch (opcion) {
                case 1:
                    //mostrar habitaciones junto a su estado de reserva
                    this.bd.mostrarDatosDB("SELECT Habitacion.habitacion_id as id, numero, Reserva.estado as estado_reserva FROM Habitacion LEFT JOIN Reserva ON Habitacion.habitacion_id = Reserva.habitacion_id", conn);
                    mostrarSubMenuHabitaciones(scanner);
                    break;
                case 2:
                    System.out.println("Ingrese su RUT (sin puntos ni digito verificador): ");
                    int rut = this.menu.leerOpcion(scanner);
                    this.bd.mostrarReservas("SELECT Reserva.reserva_id, Habitacion.numero as numero_habitacion, Reserva.usuario_id as rut, fecha_entrada, fecha_salida, estado " 
                    +"FROM Reserva INNER JOIN Habitacion ON Reserva.habitacion_id = Habitacion.habitacion_id WHERE Reserva.usuario_id = " + rut, conn);
                    mostrarSubMenuReservas(scanner);
                    break;
                case 3:
                    mostrarSubMenuNuevaReserva(scanner);
                    break;
                case 4:
                    return;
                default:
                    this.menu.mostrarMensajeError();
            }
        } while (opcion != 4);
    }

    void mostrarSubMenuHabitaciones(Scanner scanner) {
        int opcion = 0;
        do {
            this.menu.mostrarSubMenuHabitaciones();
            opcion = this.menu.leerOpcion(scanner);
            switch (opcion) {
                case 1:
                    System.out.println("Ingrese el id de la habitacion: ");
                    int idHabitacion = this.menu.leerOpcion(scanner);
                    this.bd.mostrarDatosDB("SELECT * FROM Habitacion WHERE habitacion_id = " + idHabitacion, conn);
                    break;
                case 2:
                    return;
                default:
                    this.menu.mostrarMensajeError();
            }
        } while (opcion != 2);
    }

    void mostrarSubMenuReservas(Scanner scanner) {
        int opcion = 0;
        do {
            this.menu.mostrarSubMenuReservas();
            opcion = this.menu.leerOpcion(scanner);
            switch (opcion) {
                case 1:
                    System.out.print("Ingrese el ID de la reserva a editar: ");
                    int idReserva = this.menu.leerOpcion(scanner);
                    InsertarSQL insertar = new InsertarSQL();
                    insertar.actualizarEnBD(scanner, Integer.toString(idReserva));
                    break;
                case 2:
                    System.out.print("Ingrese el ID de la reserva a eliminar: ");
                    int idEliminar = this.menu.leerOpcion(scanner);
                    EliminarDatosSQL eliminar = new EliminarDatosSQL();
                    eliminar.eliminarEnBD(scanner, Integer.toString(idEliminar));
                    break;
                case 3:
                    return;
                default:
                    this.menu.mostrarMensajeError();
            }
        } while (opcion != 3);
    }

    void mostrarSubMenuNuevaReserva(Scanner scanner) {
        int opcion = 0;
        do {
            this.menu.mostrarSubMenuNuevaReserva();
            opcion = this.menu.leerOpcion(scanner);
            switch (opcion) {
                case 1:
                    InsertarSQL insertar = new InsertarSQL();
                    insertar.insertarEnBD(scanner);
                    break;
                case 2:
                    return;
                default:
                    this.menu.mostrarMensajeError();
            }
        } while (opcion != 2);
    }
}