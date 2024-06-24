import java.util.Scanner;

class Menu {

    void mostrarMenuPrincipal() {
        System.out.println("\n** Bienvenido al Sistema de Reserva de Hoteles **");
        System.out.println("1. Ingresar");
        System.out.println("2. Salir");
        System.out.print("Seleccione una opción: ");
    }

    void mostrarMenuCliente() {
        System.out.println("\n** Menú del Cliente **");
        System.out.println("1. Mostrar habitaciones");
        System.out.println("2. Ver mis reservas");
        System.out.println("3. Realizar nueva reserva");
        System.out.println("4. Salir");
        System.out.print("Seleccione una opción: ");
    }

    void mostrarSubMenuHabitaciones() {
        System.out.println("\n** Submenú: Habitaciones **");
        System.out.println("1. Ver detalles de habitación");
        System.out.println("2. Volver al menú del cliente");
        System.out.print("Seleccione una opción: ");
    }

    void mostrarSubMenuReservas() {
        System.out.println("\n** Submenú: Reservas **");
        System.out.println("1. Editar reserva");
        System.out.println("2. Eliminar reserva");
        System.out.println("3. Volver al menú del cliente");
        System.out.print("Seleccione una opción: ");
    }

    void mostrarSubMenuNuevaReserva() {
        System.out.println("\n** Submenú: Nueva Reserva **");
        System.out.println("1. Confirmar reserva");
        System.out.println("2. Cancelar");
        System.out.print("Seleccione una opción: ");
    }

    int leerOpcion(Scanner scanner) {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Por favor, ingrese un número válido.");
            return -1; // Valor inválido para repetir el bucle
        }
    }

    void mostrarMensajeError() {
        System.out.println("Opción no válida. Por favor, seleccione una opción válida.");
    }
}
