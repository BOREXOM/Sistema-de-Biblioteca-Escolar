CREATE DATABASE biblioteca_escolar;
USE biblioteca_escolar;

CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100),
    email VARCHAR(100) UNIQUE,
    password VARCHAR(255),
    rol ENUM('Administrador', 'Bibliotecario', 'Alumno') NOT NULL,
    mac_address VARCHAR(17) -- Para validación de hardware
);

CREATE TABLE alumnos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100),
    matricula VARCHAR(20) UNIQUE,
    usuario_id INT,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

CREATE TABLE libros (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(200),
    autor VARCHAR(100),
    isbn VARCHAR(13) UNIQUE,
    disponible BOOLEAN DEFAULT TRUE
);

CREATE TABLE prestamos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    alumno_id INT,
    libro_id INT,
    fecha_prestamo DATE,
    fecha_devolucion DATE,
    multa DECIMAL(5,2) DEFAULT 0,
    FOREIGN KEY (alumno_id) REFERENCES alumnos(id),
    FOREIGN KEY (libro_id) REFERENCES libros(id)
);

-- Insertar usuarios de ejemplo (incluyendo MACs autorizados)
INSERT INTO usuarios (nombre, email, password, rol, mac_address) VALUES
('Admin', 'admin@escuela.com', 'admin123', 'Administrador', '00-11-22-33-44-55'),
('Biblio', 'biblio@escuela.com', 'biblio123', 'Bibliotecario', 'AA-BB-CC-DD-EE-FF'),
('Alumno1', 'alumno1@escuela.com', 'alumno123', 'Alumno', '11-22-33-44-55-66');