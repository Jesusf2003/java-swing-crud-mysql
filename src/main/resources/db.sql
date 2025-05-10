-- Creación de base de datos
CREATE DATABASE studentdb;
-- Poner en uso base de datos
USE studentdb;

-- Creación de tabla
CREATE TABLE estudiantes
(
    id        INT AUTO_INCREMENT PRIMARY KEY,
    nombre    VARCHAR(50)         NOT NULL,
    apellidos VARCHAR(50)         NOT NULL,
    edad      VARCHAR(3)          NOT NULL,
    pais      VARCHAR(50)         NOT NULL,
    correo    VARCHAR(100) UNIQUE NOT NULL,
    celular   VARCHAR(20) UNIQUE  NOT NULL
);

-- Listado de registros
SELECT *
FROM estudiantes;

-- Inserción de registros
INSERT INTO estudiantes
    (nombre, apellidos, edad, pais, correo, celular)
values ('Jesús', 'Canales Guando', '20', 'Perú', 'jesuscanales@outlook.com', '987456321');

INSERT INTO estudiantes
    (nombre, apellidos, edad, pais, correo, celular)
values ('Fabiola', 'Canales Rodríguez', '20', 'Perú', 'fabiolacanales@outlook.com', '998877412');

INSERT INTO estudiantes
    (nombre, apellidos, edad, pais, correo, celular)
values ('Maryssa', 'Canales Rodríguez', '16', 'Perú', 'maryssacanales@outlook.com', '978877412');

INSERT INTO estudiantes
    (nombre, apellidos, edad, pais, correo, celular)
values ('María Fe', 'Canales Rodríguez', '15', 'Perú', 'mariafecanales@outlook.com', '978877444');