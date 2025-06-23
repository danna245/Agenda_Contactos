package gui;

import dao.ContactoDAO;
import modelo.Contacto;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * Panel que muestra una tabla con la lista de contactos.
 */
public class PanelContactos extends JPanel {

    private JTable tablaContactos;
    private DefaultTableModel tableModel;
    private ContactoDAO contactoDAO;

    private JButton btnEditar;
    private JButton btnEliminar;
    private JButton btnVerDetalles;
    private JButton btnAgregar; 

    // --- COMPONENTES PARA LA BÚSQUEDA ---
    private JTextField txtBuscar;
    private JButton btnBuscar;
    // ------------------------------------------

    /**
     * Constructor del PanelContactos.
     * Inicializa los componentes de la interfaz de usuario.
     */
    public PanelContactos() {
        setLayout(new BorderLayout()); 
        this.setBackground(new Color(40, 40, 40)); 

        contactoDAO = new ContactoDAO(); 

        initComponents(); 
        setupListeners(); 
        cargarContactos(); 
    }

    private void initComponents() {
        // --- Panel de Búsqueda ---
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 15)); 
        panelBusqueda.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15)); 
        panelBusqueda.setBackground(new Color(50, 50, 50)); 
        
        JLabel lblBuscar = new JLabel("Buscar por Nombre/Apellido:");
        lblBuscar.setForeground(Color.WHITE); 
        lblBuscar.setFont(new Font("Arial", Font.PLAIN, 18));

        txtBuscar = new JTextField(25); 
        txtBuscar.setBackground(new Color(60, 60, 60)); 
        txtBuscar.setForeground(Color.WHITE);
        txtBuscar.setCaretColor(Color.WHITE); 
        txtBuscar.setFont(new Font("Arial", Font.PLAIN, 18)); 

        btnBuscar = new JButton("Buscar");
        btnBuscar.setBackground(new Color(70, 70, 70)); 
        btnBuscar.setForeground(Color.BLACK);         
        btnBuscar.setFocusPainted(false);             
        btnBuscar.setFont(new Font("Arial", Font.BOLD, 16));
        btnBuscar.setPreferredSize(new Dimension(120, 40));
        try {
            btnBuscar.setIcon(new ImageIcon(getClass().getResource("/iconos/buscar.png"))); 
        } catch (Exception e) {
            System.err.println("No se pudo cargar el icono /iconos/buscar.png: " + e.getMessage());
        }

        panelBusqueda.add(lblBuscar);
        panelBusqueda.add(txtBuscar);
        panelBusqueda.add(btnBuscar);
        
        add(panelBusqueda, BorderLayout.NORTH);

        
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        tableModel.addColumn("ID");
        tableModel.addColumn("Primer Nombre");
        tableModel.addColumn("Segundo Nombre");
        tableModel.addColumn("Primer Apellido");
        tableModel.addColumn("Segundo Apellido");
        

        tablaContactos = new JTable(tableModel);
        tablaContactos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); 
        tablaContactos.getTableHeader().setReorderingAllowed(false); 

       
        tablaContactos.setBackground(new Color(60, 60, 60));      
        tablaContactos.setForeground(Color.WHITE);
        tablaContactos.setGridColor(new Color(90, 90, 90));      
        tablaContactos.setSelectionBackground(new Color(100, 100, 100)); 
        tablaContactos.setSelectionForeground(Color.CYAN);       
        tablaContactos.setFont(new Font("Arial", Font.PLAIN, 16)); 
        tablaContactos.setRowHeight(30); 
        
        // Encabezados de la tabla
        tablaContactos.getTableHeader().setBackground(new Color(80, 80, 80)); 
        tablaContactos.getTableHeader().setForeground(Color.BLACK);             
        tablaContactos.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));

        // Ocultar la columna ID
        tablaContactos.getColumnModel().getColumn(0).setMinWidth(0);
        tablaContactos.getColumnModel().getColumn(0).setMaxWidth(0);
        tablaContactos.getColumnModel().getColumn(0).setWidth(0);
        tablaContactos.getColumnModel().getColumn(0).setPreferredWidth(0);

        JScrollPane scrollPane = new JScrollPane(tablaContactos);
        scrollPane.setBackground(new Color(40, 40, 40)); 
        scrollPane.getViewport().setBackground(new Color(60, 60, 60)); 
        add(scrollPane, BorderLayout.CENTER); 

        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 15)); 
        panelBotones.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15)); 
        panelBotones.setBackground(new Color(50, 50, 50)); 
        
        
        Font buttonFont = new Font("Arial", Font.BOLD, 16);
        Dimension buttonSize = new Dimension(150, 40); 

        btnAgregar = new JButton("Agregar"); 
        btnAgregar.setBackground(new Color(70, 70, 70)); 
        btnAgregar.setForeground(Color.BLACK);         
        btnAgregar.setFocusPainted(false);             
        btnAgregar.setFont(buttonFont);
        btnAgregar.setPreferredSize(buttonSize);
        try {
            btnAgregar.setIcon(new ImageIcon(getClass().getResource("/iconos/add.png")));
        } catch (Exception e) { System.err.println("No se pudo cargar el icono /iconos/add.png"); }

        btnEditar = new JButton("Editar");
        btnEditar.setBackground(new Color(70, 70, 70)); 
        btnEditar.setForeground(Color.BLACK);         
        btnEditar.setFocusPainted(false);             
        btnEditar.setFont(buttonFont);
        btnEditar.setPreferredSize(buttonSize);
        try {
            btnEditar.setIcon(new ImageIcon(getClass().getResource("/iconos/edit.png")));
        } catch (Exception e) { System.err.println("No se pudo cargar el icono /iconos/edit.png"); }

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBackground(new Color(70, 70, 70)); 
        btnEliminar.setForeground(Color.BLACK);         
        btnEliminar.setFocusPainted(false);             
        btnEliminar.setFont(buttonFont);
        btnEliminar.setPreferredSize(buttonSize);
        try {
            btnEliminar.setIcon(new ImageIcon(getClass().getResource("/iconos/delete.png")));
        } catch (Exception e) { System.err.println("No se pudo cargar el icono /iconos/delete.png"); }
        
        btnVerDetalles = new JButton("Ver Detalles");
        btnVerDetalles.setBackground(new Color(70, 70, 70)); 
        btnVerDetalles.setForeground(Color.BLACK);         
        btnVerDetalles.setFocusPainted(false);             
        btnVerDetalles.setFont(buttonFont);
        btnVerDetalles.setPreferredSize(buttonSize);
        try {
            btnVerDetalles.setIcon(new ImageIcon(getClass().getResource("/iconos/details.png")));
        } catch (Exception e) { System.err.println("No se pudo cargar el icono /iconos/details.png"); }
        
        
        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnVerDetalles);

        add(panelBotones, BorderLayout.SOUTH);
    }

    private void setupListeners() {
        btnBuscar.addActionListener(e -> {
            String criterioBusqueda = txtBuscar.getText().trim();
            buscarContactos(criterioBusqueda);
        });
        
        txtBuscar.addActionListener(e -> {
            String criterioBusqueda = txtBuscar.getText().trim();
            buscarContactos(criterioBusqueda);
        });
    }

    public void cargarContactos() {
        buscarContactos(""); 
    }

    public void buscarContactos(String criterio) {
        tableModel.setRowCount(0); 

        List<Contacto> contactos;
        if (criterio == null || criterio.isEmpty()) {
            contactos = contactoDAO.obtenerTodosLosContactos(); 
        } else {
            contactos = contactoDAO.buscarContactos(criterio); 
        }
        
        for (Contacto contacto : contactos) {
            tableModel.addRow(new Object[]{
                contacto.getIdContacto(),
                contacto.getPrimerNombre(),
                contacto.getSegundoNombre(),
                contacto.getPrimerApellido(),
                contacto.getSegundoApellido()
                
            });
        }
    }

    public void cargarContactosFavoritos() {
        tableModel.setRowCount(0); 

        List<Contacto> contactosFavoritos = contactoDAO.obtenerContactosFavoritos(); 
        for (Contacto contacto : contactosFavoritos) {
            tableModel.addRow(new Object[]{
                contacto.getIdContacto(),
                contacto.getPrimerNombre(),
                contacto.getSegundoNombre(),
                contacto.getPrimerApellido(),
                contacto.getSegundoApellido()
                
            });
        }
    }

    public Contacto getContactoSeleccionado() {
        int selectedRow = tablaContactos.getSelectedRow();
        if (selectedRow >= 0) {
            int idContacto = (int) tableModel.getValueAt(selectedRow, 0);
            return contactoDAO.obtenerContactoPorId(idContacto); 
        }
        return null;
    }

    public JButton getBtnEditar() {
        return btnEditar;
    }

    public JButton getBtnEliminar() {
        return btnEliminar;
    }
    
    public JButton getBtnVerDetalles() {
        return btnVerDetalles;
    }
    
    public JButton getBtnAgregar() {
        return btnAgregar;
    }
    
    public JTable getTablaContactos() {
        return tablaContactos;
    }
}