-- Crear BD y usarla
CREATE DATABASE IF NOT EXISTS hospitaldb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE hospitaldb;

-- =========================
-- Tablas
-- =========================
CREATE TABLE IF NOT EXISTS usuarios (
  id INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(100) UNIQUE NOT NULL,
  password_hash VARCHAR(256) NOT NULL,
  role ENUM('ADMIN','MEDICO','RECEPCIONISTA') NOT NULL,
  medico_id INT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS medicos (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(150) NOT NULL,
  especialidad VARCHAR(100),
  telefono VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS pacientes (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(150) NOT NULL,
  dni VARCHAR(20) UNIQUE NOT NULL,
  fecha_nacimiento DATE,
  telefono VARCHAR(50),
  historial TEXT,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS citas (
  id INT AUTO_INCREMENT PRIMARY KEY,
  paciente_id INT NOT NULL,
  medico_id INT NOT NULL,
  fecha DATETIME NOT NULL,
  motivo VARCHAR(255),
  estado ENUM('PROGRAMADA','REALIZADA','CANCELADA') DEFAULT 'PROGRAMADA',
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (paciente_id) REFERENCES pacientes(id),
  FOREIGN KEY (medico_id) REFERENCES medicos(id),
  INDEX idx_citas_paciente (paciente_id),
  INDEX idx_citas_medico (medico_id)
);

CREATE TABLE IF NOT EXISTS equipos_autorizados (
  id INT AUTO_INCREMENT PRIMARY KEY,
  mac VARCHAR(50) UNIQUE NOT NULL,
  descripcion VARCHAR(255),
  activo BOOLEAN DEFAULT TRUE
);

-- =========================
-- Datos de ejemplo (Médicos)
-- =========================
INSERT INTO medicos (nombre, especialidad, telefono) VALUES
('Dr. Juan Pérez', 'Cardiología', '999-000-111'),
('Dra. Ana Gómez', 'Pediatría', '999-000-222'),
('Dr. Carlos Ruiz', 'Neurología', '999-000-333'),
('Dra. Laura Medina', 'Dermatología', '999-000-444');

-- =========================
-- Datos de ejemplo (Pacientes)
-- =========================
INSERT INTO pacientes (nombre, dni, fecha_nacimiento, telefono, historial) VALUES
('Luis Ramirez','12345678','1985-04-15','987654321','Sin alergias'),
('María Torres','87654321','1990-09-03','987650123','Alergia a penicilina'),
('Pedro Sánchez','11223344','1978-12-01','987651234','Hipertensión controlada'),
('Julia Mena','44332211','2001-06-21','987659876','Asma leve');

-- =========================
-- Usuarios (login) con hash SHA-256
-- =========================
-- Nota: el código usa HashUtil.sha256, que equivale a SHA2(...,256) en MySQL
INSERT INTO usuarios (username, password_hash, role, medico_id) VALUES
('admin', SHA2('admin123', 256), 'ADMIN', NULL),
('juan',  SHA2('juan123',  256), 'MEDICO', 1),
('ana',   SHA2('ana123',   256), 'MEDICO', 2),
('recep', SHA2('recep123', 256), 'RECEPCIONISTA', NULL);

-- =========================
-- Citas de ejemplo (referencian IDs creados arriba)
-- =========================
INSERT INTO citas (paciente_id, medico_id, fecha, motivo, estado) VALUES
(1, 1, NOW() + INTERVAL 1 DAY, 'Control post-operatorio', 'PROGRAMADA'),
(2, 2, NOW() + INTERVAL 2 DAY, 'Consulta pediátrica',    'PROGRAMADA'),
(3, 3, NOW() - INTERVAL 7 DAY, 'Cefaleas recurrentes',    'REALIZADA'),
(4, 4, NOW() + INTERVAL 3 DAY, 'Revisión de piel',        'PROGRAMADA');

-- Añadir entradas al historial para simular actualizaciones
UPDATE pacientes SET historial = CONCAT(IFNULL(historial,''), '\n[', NOW(), '] Última revisión general sin cambios significativos.') WHERE id IN (1,2,3,4);

-- =========================
-- Autorizar MACs
-- =========================
INSERT INTO equipos_autorizados (mac, descripcion, activo) VALUES
('B4-2E-99-1C-7C-75', 'PC del usuario', TRUE),
('00-14-22-01-23-45', 'PC Recepción', TRUE),
('00-16-36-12-34-56', 'PC Medicina', TRUE)
ON DUPLICATE KEY UPDATE descripcion = VALUES(descripcion), activo = TRUE;

-- =========================
-- Comprobaciones rápidas
-- =========================
SELECT username, role FROM usuarios ORDER BY id;
SELECT id, nombre, especialidad FROM medicos ORDER BY id;
SELECT id, nombre, dni FROM pacientes ORDER BY id;
SELECT id, paciente_id, medico_id, fecha, estado FROM citas ORDER BY fecha DESC;
SELECT mac, activo FROM equipos_autorizados ORDER BY id;