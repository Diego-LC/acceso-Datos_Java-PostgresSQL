import java.sql.Connection;
import java.util.Scanner;

public class Main {
    private Menu menu = new Menu();
    private MostrarBD bd = new MostrarBD();
    private ConectarBD conectarBD = new ConectarBD();
    private Connection conn = conectarBD.getConnection();
    private int rut = 0;

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
                    this.bd.mostrarDatosDB("SELECT habitacion_id as id, numero, estado FROM Habitacion", conn);
                    mostrarSubMenuHabitaciones(scanner);
                    break;
                case 2:
                    System.out.println("Ingrese su RUT (sin puntos ni digito verificador): ");
                    rut = this.menu.leerOpcion(scanner);
                    boolean reservas = this.bd.mostrarReservas(rut, conn);
                    if (reservas) {
                        mostrarSubMenuReservas(scanner);
                    }
                    break;
                case 3:
                    InsertarSQL insertar = new InsertarSQL();
                    insertar.insertarEnBD(scanner);
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
                    this.bd.mostrarDatosDB("SELECT Habitacion.habitacion_id as id, Habitacion.numero, Habitacion.tipo, Habitacion.capacidad, Habitacion.estado, Hotel.nombre as Hotel, Hotel.ubicacion FROM Habitacion "+
                    "INNER JOIN Hotel ON Habitacion.hotel_id = Hotel.hotel_id WHERE habitacion_id = " + idHabitacion, conn);
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
                    if (rut != 0) {
                        this.bd.mostrarReservas(rut, conn);
                    }
                    break;
                case 3:
                    return;
                default:
                    this.menu.mostrarMensajeError();
            }
        } while (opcion != 3);
    }

    void mostrarSubMenuNuevaReserva(Scanner scanner) {
    }
}