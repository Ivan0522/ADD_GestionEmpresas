-- Borrar la base de datos si es necesario.
-- drop database gestion_empresas;

-- Crear la base de datos
CREATE DATABASE IF NOT EXISTS gestion_empresas;
USE gestion_empresas;

-- Crear la tabla de empresas
CREATE TABLE Empresas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    industria VARCHAR(255) NOT NULL
);

-- Crear la tabla de departamentos
CREATE TABLE Departamentos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    empresa_id INT NOT NULL,
    FOREIGN KEY (empresa_id) REFERENCES Empresas(id) ON DELETE CASCADE
);

-- Crear la tabla de empleados
CREATE TABLE Empleados (
    dni VARCHAR(20) PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    apellido VARCHAR(255) NOT NULL,
    puesto VARCHAR(255) NOT NULL,
    departamento_id INT NOT NULL,
    FOREIGN KEY (departamento_id) REFERENCES Departamentos(id) ON DELETE CASCADE
);

-- Insertar datos en la tabla de empresas
INSERT INTO Empresas (nombre, industria) VALUES
('TechCorp', 'Tecnología'),
('Foodies Inc', 'Alimentación');

-- Insertar datos en la tabla de departamentos
INSERT INTO Departamentos (nombre, empresa_id) VALUES
('Desarrollo', 1),
('Soporte Técnico', 1),
('Producción', 2);

-- Insertar datos en la tabla de empleados
INSERT INTO Empleados (dni, nombre, apellido, puesto, departamento_id) VALUES
('12345678A', 'Juan', 'Pérez', 'Desarrollador', 1),
('87654321B', 'María', 'Gómez', 'Soporte Técnico', 2),
('11223344C', 'Luis', 'Martínez', 'Soporte Técnico', 2),
('22334455D', 'Ana', 'López', 'Operario', 3),
('33445566E', 'Carlos', 'Hernández', 'Supervisor', 3);
