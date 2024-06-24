## Getting Started

Welcome to the VS Code Java world. Here is a guideline to help you get started to write Java code in Visual Studio Code.

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

## Dependency Management

The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).

--  

## Script de creacion de la base de datos en postgresql

```sql
CREATE DATABASE IF NOT EXISTS Hotel_reservacion;

\c Hotel_reservacion;

-- Creación de la tabla Hotel
CREATE TABLE Hotel (
    hotel_id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    ubicacion VARCHAR(100) NOT NULL,
    categoria INTEGER
);

-- Creación de la tabla Habitacion
CREATE TABLE Habitacion (
    habitacion_id SERIAL PRIMARY KEY,
    hotel_id INTEGER REFERENCES Hotel(hotel_id),
    numero INTEGER,
    tipo VARCHAR(50),
    capacidad INTEGER,
    estado VARCHAR(50)
);

-- Creación de la tabla Usuario
CREATE TABLE Usuario (
    rut INTEGER PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    email VARCHAR(80) UNIQUE NOT NULL,
    telefono VARCHAR(20)
);

-- Creación de la tabla Reserva
CREATE TABLE Reserva (
    reserva_id SERIAL PRIMARY KEY,
    habitacion_id INTEGER REFERENCES Habitacion(habitacion_id),
    usuario_id INTEGER REFERENCES Usuario(rut),
    fecha_entrada DATE,
    fecha_salida DATE,
    estado VARCHAR(20)
);

-- Creación de la tabla Registro
CREATE TABLE Registro_reservas (
    registro_id SERIAL PRIMARY KEY,
    habitacion_id INTEGER,
    usuario_id INTEGER,
    fecha_entrada DATE,
    fecha_salida DATE,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Función para insertar en la tabla Registro al eliminar una reserva
CREATE OR REPLACE FUNCTION insertar_en_registro() RETURNS TRIGGER AS $$
BEGIN
    INSERT INTO Registro_reservas (habitacion_id, usuario_id, fecha_entrada, fecha_salida)
    VALUES (OLD.habitacion_id, OLD.usuario_id, OLD.fecha_entrada, OLD.fecha_salida);
    RETURN OLD;
END;
$$ LANGUAGE plpgsql;

-- Trigger para llamar a la función al eliminar una reserva
CREATE TRIGGER trigger_insertar_en_registro
AFTER DELETE ON Reserva
FOR EACH ROW
EXECUTE FUNCTION insertar_en_registro();

-- Función para mover reservas cumplidas a la tabla Registro
CREATE OR REPLACE FUNCTION mover_reservas_cumplidas() RETURNS TRIGGER AS $$
BEGIN
    IF OLD.fecha_salida < CURRENT_DATE THEN
        INSERT INTO Registro (habitacion_id, usuario_id, fecha_entrada, fecha_salida)
        VALUES (OLD.habitacion_id, OLD.usuario_id, OLD.fecha_entrada, OLD.fecha_salida);
        DELETE FROM Reserva WHERE reserva_id = OLD.reserva_id;
    END IF;
    RETURN OLD;
END;
$$ LANGUAGE plpgsql;

-- Trigger para llamar a la función diariamente
CREATE TRIGGER trigger_mover_reservas_cumplidas
AFTER UPDATE ON Reserva
FOR EACH ROW
WHEN (OLD.fecha_salida < CURRENT_DATE)
EXECUTE FUNCTION mover_reservas_cumplidas();

-- Inserción de datos en la tabla Hotel
INSERT INTO Hotel (nombre, ubicacion, categoria) VALUES
('Hotel Central', 'Ciudad A', 4),
('Hotel Playa', 'Ciudad B', 5),
('Hotel Montaña', 'Ciudad C', 3);

-- Inserción de datos en la tabla Habitacion
INSERT INTO Habitacion (hotel_id, numero, tipo, capacidad, estado) VALUES
(1, 101, 'Simple', 1, 'Disponible'),
(1, 102, 'Doble', 2, 'Disponible'),
(2, 201, 'Suite', 4, 'Disponible'),
(2, 202, 'Doble', 2, 'Disponible'),
(3, 301, 'Simple', 1, 'Disponible'),
(3, 302, 'Doble', 2, 'Disponible');

-- Inserción de datos en la tabla Usuario
INSERT INTO Usuario (rut, nombre, apellido, email, telefono) VALUES
(12345678, 'Juan', 'Pérez', 'juan.perez@example.com', '123456789'),
(87654321, 'María', 'Gómez', 'maria.gomez@example.com', '987654321'),
(45678912, 'Carlos', 'López', 'carlos.lopez@example.com', '456123789');

-- Inserción de datos en la tabla Reserva
INSERT INTO Reserva (habitacion_id, usuario_id, fecha_entrada, fecha_salida) VALUES
(1, 12345678, '2024-05-01', '2024-05-05'),
(2, 87654321, '2024-05-10', '2024-05-15'),
(3, 45678912, '2024-05-01', '2024-05-07'),
(4, 12345678, '2023-12-01', '2023-12-10');

INSERT INTO Registro_reservas (habitacion_id, usuario_id, fecha_entrada, fecha_salida) VALUES
(1, 12345678, '2023-05-01', '2023-05-05'),
(2, 87654321, '2023-05-10', '2023-05-15'),
(3, 45678912, '2023-05-01', '2023-05-07'),
(4, 12345678, '2023-12-01', '2023-12-10');

```
