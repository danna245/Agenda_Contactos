package gui;

import modelo.Contacto;
import modelo.NumeroTelefono;
import dao.NumeroTelefonoDAO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import gui.NumeroTelefonoDialog; 


public class PanelDetalleContacto extends JPanel {

    private JLabel lblFoto;
    private JLabel lblNombreCompleto;
    private JLabel lblFavorito;
    private JList<NumeroTelefono> listNumeros;
    private DefaultListModel<NumeroTelefono> listModelNumeros;

    private JButton btnAgregarNumero;
    private JButton btnEditarNumero;
    private JButton btnEliminarNumero;

    private Contacto contactoActual; // El contacto que se está visualizando
    private NumeroTelefonoDAO numeroTelefonoDAO; // DAO para manejar los números de teléfono

    public PanelDetalleContacto() {
        setLayout(new BorderLayout(10, 10)); // Un layout principal con espaciado
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Margen alrededor del panel
        this.setBackground(new Color(40, 40, 40)); // **Fondo principal del panel**

        numeroTelefonoDAO = new NumeroTelefonoDAO(); // Inicializa el DAO de números

        // --- Panel Superior para Foto y Nombre/Favorito ---
        JPanel panelSuperior = new JPanel(new BorderLayout(10, 0)); // Espacio entre foto y texto
        panelSuperior.setBackground(new Color(50, 50, 50)); // **Fondo del panel superior**

        lblFoto = new JLabel();
        lblFoto.setPreferredSize(new Dimension(100, 100)); // Tamaño fijo para la foto
        lblFoto.setBorder(BorderFactory.createLineBorder(new Color(90, 90, 90))); // **Borde gris para la foto**
        lblFoto.setHorizontalAlignment(SwingConstants.CENTER);
        lblFoto.setVerticalAlignment(SwingConstants.CENTER);
        lblFoto.setText("No hay foto"); // Texto por defecto
        lblFoto.setForeground(Color.LIGHT_GRAY); // **Color del texto "No hay foto"**
        lblFoto.setFont(new Font("Arial", Font.PLAIN, 12)); // **Fuente más pequeña para el texto de la foto**
        panelSuperior.add(lblFoto, BorderLayout.WEST);

        JPanel panelInfoContacto = new JPanel(new GridLayout(2, 1)); // Para nombre y favorito
        panelInfoContacto.setBackground(new Color(50, 50, 50)); // **Fondo del panel de información de contacto**
        
        lblNombreCompleto = new JLabel("Nombre Completo: ");
        lblNombreCompleto.setFont(new Font("Arial", Font.BOLD, 18));
        lblNombreCompleto.setForeground(Color.WHITE); // **Color del texto del nombre**
        
        lblFavorito = new JLabel("Favorito: No");
        lblFavorito.setFont(new Font("Arial", Font.PLAIN, 14));
        lblFavorito.setForeground(Color.LIGHT_GRAY); // **Color del texto de favorito**

        panelInfoContacto.add(lblNombreCompleto);
        panelInfoContacto.add(lblFavorito);
        panelSuperior.add(panelInfoContacto, BorderLayout.CENTER);

        add(panelSuperior, BorderLayout.NORTH);

        // --- Panel Central para Números de Teléfono ---
        JPanel panelNumeros = new JPanel(new BorderLayout());
        // Establecer un TitledBorder para el panel de números
        // Ajustar color y fuente del título
        panelNumeros.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(90, 90, 90)), // **Borde del título**
            "Números de Teléfono", // **Texto del título**
            javax.swing.border.TitledBorder.LEFT,
            javax.swing.border.TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 16), // **Fuente del título**
            Color.WHITE // **Color del texto del título**
        ));
        panelNumeros.setBackground(new Color(50, 50, 50)); // **Fondo del panel de números**

        listModelNumeros = new DefaultListModel<>();
        listNumeros = new JList<>(listModelNumeros);
        listNumeros.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Solo un número a la vez
        listNumeros.setBackground(new Color(60, 60, 60)); // **Fondo de la lista de números**
        listNumeros.setForeground(Color.WHITE); // **Color del texto de la lista de números**
        listNumeros.setSelectionBackground(new Color(100, 100, 100)); // **Fondo de selección de la lista**
        listNumeros.setSelectionForeground(Color.CYAN); // **Color del texto seleccionado**
        listNumeros.setFont(new Font("Arial", Font.PLAIN, 16)); // **Fuente de los elementos de la lista**

        JScrollPane scrollNumeros = new JScrollPane(listNumeros);
        scrollNumeros.setBackground(new Color(40, 40, 40)); 
        scrollNumeros.getViewport().setBackground(new Color(60, 60, 60)); // **Fondo del viewport del scroll**
        panelNumeros.add(scrollNumeros, BorderLayout.CENTER);

        // Panel de botones para números
        JPanel panelBotonesNumeros = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10)); // Espaciado entre botones
        panelBotonesNumeros.setBackground(new Color(50, 50, 50)); // **Fondo del panel de botones de números**

        // Estilo común para los botones
        Color buttonBg = new Color(70, 70, 70); // Fondo del botón
        Color buttonFg = Color.BLACK;         // Texto del botón
        Font buttonFont = new Font("Arial", Font.BOLD, 14); // Fuente negrita y adecuada
        Dimension buttonSize = new Dimension(140, 35); // Tamaño fijo para uniformidad

        btnAgregarNumero = new JButton("Agregar Número");
        btnAgregarNumero.setBackground(buttonBg); 
        btnAgregarNumero.setForeground(buttonFg);         
        btnAgregarNumero.setFocusPainted(false);             
        btnAgregarNumero.setFont(buttonFont);
        btnAgregarNumero.setPreferredSize(buttonSize);

        btnEditarNumero = new JButton("Editar Número");
        btnEditarNumero.setBackground(buttonBg); 
        btnEditarNumero.setForeground(buttonFg);         
        btnEditarNumero.setFocusPainted(false);             
        btnEditarNumero.setFont(buttonFont);
        btnEditarNumero.setPreferredSize(buttonSize);

        btnEliminarNumero = new JButton("Eliminar Número");
        btnEliminarNumero.setBackground(buttonBg); 
        btnEliminarNumero.setForeground(buttonFg);         
        btnEliminarNumero.setFocusPainted(false);             
        btnEliminarNumero.setFont(buttonFont);
        btnEliminarNumero.setPreferredSize(buttonSize);

        panelBotonesNumeros.add(btnAgregarNumero);
        panelBotonesNumeros.add(btnEditarNumero);
        panelBotonesNumeros.add(btnEliminarNumero);
        panelNumeros.add(panelBotonesNumeros, BorderLayout.SOUTH);

        add(panelNumeros, BorderLayout.CENTER);

        // --- Acciones de los botones de números ---
        btnAgregarNumero.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (contactoActual != null) {
                    // Crear y mostrar el nuevo diálogo para agregar número
                    NumeroTelefonoDialog dialog = new NumeroTelefonoDialog((JFrame) SwingUtilities.getWindowAncestor(PanelDetalleContacto.this));
                    dialog.setVisible(true); // Mostrar el diálogo y esperar a que se cierre

                    if (dialog.isSaved()) { // Si el usuario hizo clic en "Guardar"
                        String tipo = dialog.getSelectedTipo();
                        String numero = dialog.getEnteredNumero();

                        NumeroTelefono nuevoNum = new NumeroTelefono(contactoActual.getIdContacto(), tipo, numero);
                        if (numeroTelefonoDAO.insertarNumeroTelefono(nuevoNum)) {
                            JOptionPane.showMessageDialog(PanelDetalleContacto.this, "Número agregado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                            cargarNumerosTelefono(contactoActual.getIdContacto());
                        } else {
                            JOptionPane.showMessageDialog(PanelDetalleContacto.this, "Error al agregar el número.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(PanelDetalleContacto.this, "Seleccione un contacto primero.", "Error", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        btnEditarNumero.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = listNumeros.getSelectedIndex();
                if (selectedIndex != -1) {
                    NumeroTelefono numeroSeleccionado = listModelNumeros.getElementAt(selectedIndex);

                    
                    NumeroTelefonoDialog dialog = new NumeroTelefonoDialog((JFrame) SwingUtilities.getWindowAncestor(PanelDetalleContacto.this), numeroSeleccionado);
                    dialog.setVisible(true); 

                    if (dialog.isSaved()) { 
                        if (numeroTelefonoDAO.actualizarNumeroTelefono(numeroSeleccionado)) {
                            JOptionPane.showMessageDialog(PanelDetalleContacto.this, "Número actualizado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                            cargarNumerosTelefono(contactoActual.getIdContacto());
                        } else {
                            JOptionPane.showMessageDialog(PanelDetalleContacto.this, "Error al actualizar el número.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(PanelDetalleContacto.this, "Seleccione un número para editar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        btnEliminarNumero.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NumeroTelefono numeroSeleccionado = listNumeros.getSelectedValue();
                if (numeroSeleccionado != null) {
                    int confirm = JOptionPane.showConfirmDialog(PanelDetalleContacto.this,
                            "¿Estás seguro de que quieres eliminar el número " + numeroSeleccionado.getNumero() + " (" + numeroSeleccionado.getTipo() + ")?",
                            "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);

                    if (confirm == JOptionPane.YES_OPTION) {
                        if (numeroTelefonoDAO.eliminarNumeroTelefono(numeroSeleccionado.getIdNumero())) {
                            JOptionPane.showMessageDialog(PanelDetalleContacto.this, "Número eliminado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                            cargarNumerosTelefono(contactoActual.getIdContacto()); // Recargar la lista
                        } else {
                            JOptionPane.showMessageDialog(PanelDetalleContacto.this, "Error al eliminar el número.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(PanelDetalleContacto.this,
                            "Por favor, selecciona un número para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }

    /**
     * Muestra los detalles de un contacto en el panel.
     * @param contacto El objeto Contacto a mostrar.
     */
    public void mostrarContacto(Contacto contacto) {
        this.contactoActual = contacto;
        if (contactoActual != null) {
            lblNombreCompleto.setText("Nombre: " + contactoActual.getPrimerNombre() + " " +
                                       (contactoActual.getSegundoNombre() != null && !contactoActual.getSegundoNombre().isEmpty() ? contactoActual.getSegundoNombre() + " " : "") +
                                       contactoActual.getPrimerApellido() +
                                       (contactoActual.getSegundoApellido() != null && !contactoActual.getSegundoApellido().isEmpty() ? " " + contactoActual.getSegundoApellido() : ""));
            lblFavorito.setText("Favorito: " + (contactoActual.isEsFavorito() ? "Sí" : "No"));

            // Cargar y mostrar la foto
            if (contactoActual.getRutaFoto() != null && !contactoActual.getRutaFoto().isEmpty()) {
                ImageIcon icon = new ImageIcon(contactoActual.getRutaFoto());
                
                if (icon.getImageLoadStatus() == MediaTracker.COMPLETE) { 
                    Image img = icon.getImage().getScaledInstance(lblFoto.getPreferredSize().width, lblFoto.getPreferredSize().height, Image.SCALE_SMOOTH);
                    lblFoto.setIcon(new ImageIcon(img));
                    lblFoto.setText(""); 
                } else {
                    lblFoto.setIcon(null);
                    lblFoto.setText("Error al cargar foto");
                    lblFoto.setForeground(Color.RED); 
                }
            } else {
                lblFoto.setIcon(null);
                lblFoto.setText("No hay foto");
                lblFoto.setForeground(Color.LIGHT_GRAY);
            }

            cargarNumerosTelefono(contactoActual.getIdContacto()); 
        } else {
            limpiarPanel();
        }
    }

    /**
     * Carga los números de teléfono para un contacto dado y los muestra en la JList.
     * @param idContacto El ID del contacto.
     */
    private void cargarNumerosTelefono(int idContacto) {
        listModelNumeros.clear(); 
        List<NumeroTelefono> numeros = numeroTelefonoDAO.obtenerNumerosPorIdContacto(idContacto);
        for (NumeroTelefono num : numeros) {
            listModelNumeros.addElement(num);
        }
    }

    /**
     * Abre un diálogo para agregar o editar un número de teléfono.
     * @param numeroTelefono Si es null, es un nuevo número; de lo contrario, es para editar.
     * NOTA: Este método 'agregarEditarNumero' ya no se usa directamente desde los action listeners
     * de los botones en este panel, ya que ahora se llama a NumeroTelefonoDialog.
     * Sin embargo, lo mantengo aquí por si acaso lo necesitas para alguna otra lógica.
     */
    private void agregarEditarNumero(NumeroTelefono numeroTelefono) {
        if (contactoActual == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un contacto primero.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        
        JTextField txtTipo = new JTextField(10);
        JTextField txtNumero = new JTextField(15);

        if (numeroTelefono != null) { // Modo edición
            txtTipo.setText(numeroTelefono.getTipo());
            txtNumero.setText(numeroTelefono.getNumero());
        }

        JPanel panel = new JPanel(new GridLayout(0, 2));
        
        panel.setBackground(new Color(60, 60, 60)); 
        JLabel lblTipo = new JLabel("Tipo:");
        lblTipo.setForeground(Color.WHITE);
        panel.add(lblTipo);
        txtTipo.setBackground(new Color(70, 70, 70));
        txtTipo.setForeground(Color.WHITE);
        txtTipo.setCaretColor(Color.WHITE); 
        panel.add(txtTipo);

        JLabel lblNumero = new JLabel("Número:");
        lblNumero.setForeground(Color.WHITE);
        panel.add(lblNumero);
        txtNumero.setBackground(new Color(70, 70, 70));
        txtNumero.setForeground(Color.WHITE);
        txtNumero.setCaretColor(Color.WHITE); 
        panel.add(txtNumero);

        int result = JOptionPane.showConfirmDialog(this, panel,
                (numeroTelefono == null ? "Agregar Nuevo Número" : "Editar Número"),
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String tipo = txtTipo.getText().trim();
            String numero = txtNumero.getText().trim();

            if (tipo.isEmpty() || numero.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tipo y número no pueden estar vacíos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (numeroTelefono == null) { 
                NumeroTelefono nuevoNum = new NumeroTelefono(contactoActual.getIdContacto(), tipo, numero);
                if (numeroTelefonoDAO.insertarNumeroTelefono(nuevoNum)) {
                    JOptionPane.showMessageDialog(this, "Número agregado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    cargarNumerosTelefono(contactoActual.getIdContacto());
                } else {
                    JOptionPane.showMessageDialog(this, "Error al agregar el número.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else { 
                numeroTelefono.setTipo(tipo);
                numeroTelefono.setNumero(numero);
                if (numeroTelefonoDAO.actualizarNumeroTelefono(numeroTelefono)) {
                    JOptionPane.showMessageDialog(this, "Número actualizado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    cargarNumerosTelefono(contactoActual.getIdContacto());
                } else {
                    JOptionPane.showMessageDialog(this, "Error al actualizar el número.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    /**
     * Limpia el contenido del panel.
     */
    public void limpiarPanel() {
        lblNombreCompleto.setText("Nombre Completo: ");
        lblFavorito.setText("Favorito: No");
        lblFoto.setIcon(null);
        lblFoto.setText("No hay foto");
        lblFoto.setForeground(Color.LIGHT_GRAY); 
        listModelNumeros.clear();
        contactoActual = null;
    }
}