package presentacion;

import negocio.Usuario;
import negocio.ServicioBiblioteca;
import negocio.Alumno;
import negocio.Libro;
import negocio.Prestamo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class VentanaPrincipal extends JFrame {
    private final Usuario usuario;
    private final ServicioBiblioteca servicio = new ServicioBiblioteca();

    private JTabbedPane panelTabs;
    private JTable tablaAlumnos, tablaLibros, tablaPrestamos;
    private DefaultTableModel modeloAlumnos, modeloLibros, modeloPrestamos;

    public VentanaPrincipal(Usuario usuario) {
        this.usuario = usuario;
        setTitle("Biblioteca Escolar - Dashboard (" + usuario.getRol() + ")");
        setSize(950, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 247, 250));

        Color azulPrincipal = new Color(41, 128, 185);
        Color azulClaro = new Color(174, 214, 241);
        Color fondo = new Color(245, 247, 250);
        Color texto = new Color(44, 62, 80);

        JPanel panelLateral = new JPanel();
        panelLateral.setBackground(azulPrincipal);
        panelLateral.setLayout(new BoxLayout(panelLateral, BoxLayout.Y_AXIS));
        panelLateral.setPreferredSize(new Dimension(220, getHeight()));

        JLabel lblTitulo = new JLabel("Biblioteca Escolar", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI Semibold", Font.BOLD, 20));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        panelLateral.add(lblTitulo);
        panelLateral.add(Box.createVerticalStrut(10));

        JButton btnAlumnos = crearBotonMenu("Alumnos");
        JButton btnLibros = crearBotonMenu("Libros");
        JButton btnPrestamos = crearBotonMenu("Préstamos");
        JButton btnSalir = crearBotonMenu("Cerrar sesión");

        panelLateral.add(btnAlumnos);
        panelLateral.add(btnLibros);
        panelLateral.add(btnPrestamos);
        panelLateral.add(Box.createVerticalGlue());
        panelLateral.add(btnSalir);

        add(panelLateral, BorderLayout.WEST);

        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(azulClaro);
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel lblBienvenida = new JLabel("Bienvenido, " + usuario.getNombre() + " (" + usuario.getRol() + ")");
        lblBienvenida.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblBienvenida.setForeground(texto);
        panelSuperior.add(lblBienvenida, BorderLayout.WEST);

        // Título principal centrado en el encabezado
        JLabel lblTituloApp = new JLabel("Biblioteca Escolar", SwingConstants.CENTER);
        lblTituloApp.setFont(new Font("Segoe UI Semibold", Font.BOLD, 22));
        lblTituloApp.setForeground(texto);
        panelSuperior.add(lblTituloApp, BorderLayout.CENTER);

        add(panelSuperior, BorderLayout.NORTH);

        // Pestañas
        panelTabs = new JTabbedPane();
        panelTabs.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panelTabs.setBackground(fondo);

        // Alumnos
        modeloAlumnos = new DefaultTableModel(new String[]{"ID", "Nombre", "Matrícula", "Usuario ID"}, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tablaAlumnos = crearTabla(modeloAlumnos);
        JPanel panelAlumnos = crearPanelTabla("Alumnos", tablaAlumnos, crearBarraInferiorAlumnos());
        panelTabs.addTab("Alumnos", panelAlumnos);

        // Libros
        modeloLibros = new DefaultTableModel(new String[]{"ID", "Título", "Autor", "ISBN", "Disponible"}, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tablaLibros = crearTabla(modeloLibros);
        JPanel panelLibros = crearPanelTabla("Libros", tablaLibros, crearBarraInferiorLibros());
        panelTabs.addTab("Libros", panelLibros);

        // Préstamos (solo para Alumno)
        if ("Alumno".equalsIgnoreCase(usuario.getRol())) {
            modeloPrestamos = new DefaultTableModel(new String[]{"ID", "Libro ID", "Fecha Préstamo", "Fecha Devolución", "Multa"}, 0) {
                public boolean isCellEditable(int row, int column) { return false; }
            };
            tablaPrestamos = crearTabla(modeloPrestamos);
            JPanel panelPrestamos = crearPanelTabla("Mis Préstamos", tablaPrestamos, crearBarraInferiorPrestamos());
            panelTabs.addTab("Préstamos", panelPrestamos);
        }

        add(panelTabs, BorderLayout.CENTER);

        // Acciones del menú
        btnAlumnos.addActionListener(e -> panelTabs.setSelectedIndex(0));
        btnLibros.addActionListener(e -> panelTabs.setSelectedIndex(1));
        btnPrestamos.addActionListener(e -> {
            int idx = panelTabs.indexOfTab("Préstamos");
            if (idx >= 0) panelTabs.setSelectedIndex(idx);
        });
        btnSalir.addActionListener(e -> {
            // Cerrar sesión: cerrar esta ventana y volver al Login
            dispose();
            new VentanaLogin().setVisible(true);
        });

        // Carga inicial desde BD
        cargarAlumnos();
        cargarLibros();
        cargarPrestamosSiAlumno();
    }

    private JButton crearBotonMenu(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        boton.setForeground(Color.WHITE);
        boton.setBackground(new Color(41, 128, 185));
        boton.setBorderPainted(false);
        boton.setFocusPainted(false);
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        boton.setMaximumSize(new Dimension(200, 45));
        boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) { boton.setBackground(new Color(31, 97, 141)); }
            public void mouseExited(java.awt.event.MouseEvent evt) { boton.setBackground(new Color(41, 128, 185)); }
        });
        return boton;
    }

    private JTable crearTabla(DefaultTableModel modelo) {
        JTable tabla = new JTable(modelo);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabla.setRowHeight(25);
        tabla.setBackground(new Color(250, 250, 250));
        tabla.setGridColor(new Color(220, 220, 220));
        return tabla;
    }

    private JPanel crearPanelTabla(String titulo, JTable tabla, JComponent barraInferior) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        JLabel lblTitulo = new JLabel(titulo, SwingConstants.LEFT);
        lblTitulo.setFont(new Font("Segoe UI Semibold", Font.BOLD, 18));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.getViewport().setBackground(Color.WHITE);

        panel.add(lblTitulo, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        if (barraInferior != null) panel.add(barraInferior, BorderLayout.SOUTH);
        return panel;
    }

    private JComponent crearBarraInferiorAlumnos() {
        JPanel barra = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.addActionListener(e -> cargarAlumnos());
        barra.add(btnActualizar);
        if (!"Alumno".equalsIgnoreCase(usuario.getRol())) {
            JButton btnRegistrar = new JButton("Registrar Alumno");
            btnRegistrar.addActionListener(e -> new VentanaRegistroAlumno().setVisible(true));
            barra.add(btnRegistrar);
        }
        return barra;
    }

    private JComponent crearBarraInferiorLibros() {
        JPanel barra = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.addActionListener(e -> cargarLibros());
        barra.add(btnActualizar);

        if ("Alumno".equalsIgnoreCase(usuario.getRol())) {
            JButton btnPrestar = new JButton("Prestar seleccionado");
            btnPrestar.addActionListener(e -> {
                int row = tablaLibros.getSelectedRow();
                if (row < 0) {
                    JOptionPane.showMessageDialog(this, "Selecciona un libro disponible.");
                    return;
                }
                int libroId = (int) modeloLibros.getValueAt(row, 0);
                // Abre la ventana de préstamo (prefill vendrá en la siguiente tarea)
                new VentanaPrestamo(usuario).setVisible(true);
                JOptionPane.showMessageDialog(this, "Abre 'Gestionar Préstamo' y usa el ID de libro: " + libroId);
            });
            barra.add(btnPrestar);
        } else {
            JButton btnRegistrar = new JButton("Registrar Libro");
            btnRegistrar.addActionListener(e -> new VentanaRegistroLibro().setVisible(true));
            barra.add(btnRegistrar);
        }
        return barra;
    }

    private JComponent crearBarraInferiorPrestamos() {
        JPanel barra = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.addActionListener(e -> cargarPrestamosSiAlumno());
        barra.add(btnActualizar);
        JButton btnGestionar = new JButton("Gestionar Préstamo");
        btnGestionar.addActionListener(e -> new VentanaPrestamo(usuario).setVisible(true));
        barra.add(btnGestionar);
        return barra;
    }

    private void cargarAlumnos() {
        try {
            limpiarModelo(modeloAlumnos);
            List<Alumno> alumnos = servicio.listarAlumnos();
            for (Alumno a : alumnos) {
                modeloAlumnos.addRow(new Object[]{a.getId(), a.getNombre(), a.getMatricula(), a.getUsuarioId()});
            }
        } catch (SQLException ex) {
            mostrarError("Error al cargar alumnos: " + ex.getMessage());
        }
    }

    private void cargarLibros() {
        try {
            limpiarModelo(modeloLibros);
            List<Libro> libros = servicio.listarLibrosDisponibles();
            for (Libro l : libros) {
                modeloLibros.addRow(new Object[]{l.getId(), l.getTitulo(), l.getAutor(), l.getIsbn(), l.isDisponible() ? "Sí" : "No"});
            }
        } catch (SQLException ex) {
            mostrarError("Error al cargar libros: " + ex.getMessage());
        }
    }

    private void cargarPrestamosSiAlumno() {
        if (modeloPrestamos == null) return;
        try {
            limpiarModelo(modeloPrestamos);
            Alumno alumno = servicio.obtenerAlumnoPorUsuarioId(usuario.getId());
            if (alumno == null) {
                return;
            }
            List<Prestamo> prestamos = servicio.listarPrestamosPorAlumno(alumno.getId());
            for (Prestamo p : prestamos) {
                modeloPrestamos.addRow(new Object[]{p.getId(), p.getLibroId(), p.getFechaPrestamo(), p.getFechaDevolucion(), p.getMulta()});
            }
        } catch (SQLException ex) {
            mostrarError("Error al cargar préstamos: " + ex.getMessage());
        }
    }

    private void limpiarModelo(DefaultTableModel modelo) {
        if (modelo == null) return;
        while (modelo.getRowCount() > 0) {
            modelo.removeRow(0);
        }
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}

