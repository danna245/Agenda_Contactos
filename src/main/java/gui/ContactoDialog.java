package gui;

import modelo.Contacto;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File; // Necesario para JFileChooser

/**
 * Diálogo para agregar o editar un contacto.
 */
public class ContactoDialog extends JDialog {

    private JTextField txtPrimerNombre;
    private JTextField txtSegundoNombre;
    private JTextField txtPrimerApellido;
    private JTextField txtSegundoApellido;
    private JCheckBox chkEsFavorito;
    private JTextField txtRutaFoto; 
    private JButton btnSeleccionarFoto; 

    private Contacto contacto;
    private boolean saved;

    public ContactoDialog(JFrame parent, Contacto contacto) {
        super(parent, contacto == null ? "Agregar Contacto" : "Editar Contacto", true); // Modal
        this.contacto = contacto;
        this.saved = false;

        setSize(400, 380);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10)); 
        this.getContentPane().setBackground(new Color(40, 40, 40));

        initComponents();
        populateFields();
    }

    private void initComponents() {
        
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(50, 50, 50)); 
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        
        Font labelFont = new Font("Arial", Font.PLAIN, 14);

        
        txtPrimerNombre = new JTextField(20);
        txtSegundoNombre = new JTextField(20);
        txtPrimerApellido = new JTextField(20);
        txtSegundoApellido = new JTextField(20);
        txtRutaFoto = new JTextField(20);
        txtRutaFoto.setEditable(false);

        
        Color txtBg = new Color(60, 60, 60);
        Color txtFg = Color.WHITE;
        Font txtFont = new Font("Arial", Font.PLAIN, 14);
        
        JTextField[] textFields = {txtPrimerNombre, txtSegundoNombre, txtPrimerApellido, txtSegundoApellido, txtRutaFoto};
        for (JTextField tf : textFields) {
            tf.setBackground(txtBg);
            tf.setForeground(txtFg);
            tf.setCaretColor(txtFg);
            tf.setFont(txtFont);
            tf.setBorder(BorderFactory.createLineBorder(new Color(90, 90, 90)));
        }

        
        chkEsFavorito = new JCheckBox("Es Favorito");
        chkEsFavorito.setBackground(new Color(50, 50, 50));
        chkEsFavorito.setForeground(Color.WHITE); 
        chkEsFavorito.setFont(labelFont);

        
        btnSeleccionarFoto = new JButton("Seleccionar Foto...");

        
        int row = 0;
        
        
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0.0; 
        JLabel lblPrimerNombre = new JLabel("Primer Nombre:");
        lblPrimerNombre.setForeground(Color.WHITE); lblPrimerNombre.setFont(labelFont);
        formPanel.add(lblPrimerNombre, gbc);
        gbc.gridx = 1; gbc.gridy = row++; gbc.weightx = 1.0; 
        formPanel.add(txtPrimerNombre, gbc);

        
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0.0; 
        JLabel lblSegundoNombre = new JLabel("Segundo Nombre:");
        lblSegundoNombre.setForeground(Color.WHITE); lblSegundoNombre.setFont(labelFont);
        formPanel.add(lblSegundoNombre, gbc);
        gbc.gridx = 1; gbc.gridy = row++; gbc.weightx = 1.0; 
        formPanel.add(txtSegundoNombre, gbc);

        
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0.0; 
        JLabel lblPrimerApellido = new JLabel("Primer Apellido:");
        lblPrimerApellido.setForeground(Color.WHITE); lblPrimerApellido.setFont(labelFont);
        formPanel.add(lblPrimerApellido, gbc);
        gbc.gridx = 1; gbc.gridy = row++; gbc.weightx = 1.0; 
        formPanel.add(txtPrimerApellido, gbc);

        
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0.0; 
        JLabel lblSegundoApellido = new JLabel("Segundo Apellido:");
        lblSegundoApellido.setForeground(Color.WHITE); lblSegundoApellido.setFont(labelFont);
        formPanel.add(lblSegundoApellido, gbc);
        gbc.gridx = 1; gbc.gridy = row++; gbc.weightx = 1.0; 
        formPanel.add(txtSegundoApellido, gbc);

        
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2; 
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(chkEsFavorito, gbc);
        gbc.gridwidth = 1; 
        gbc.anchor = GridBagConstraints.CENTER;
        row++;

       
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0.0; 
        JLabel lblRutaFoto = new JLabel("Ruta de Foto:");
        lblRutaFoto.setForeground(Color.WHITE); lblRutaFoto.setFont(labelFont);
        formPanel.add(lblRutaFoto, gbc);
        gbc.gridx = 1; gbc.gridy = row; gbc.weightx = 1.0;
        JPanel photoPanel = new JPanel(new BorderLayout(5, 0)); 
        photoPanel.setBackground(new Color(50, 50, 50)); 
        photoPanel.add(txtRutaFoto, BorderLayout.CENTER);
        photoPanel.add(btnSeleccionarFoto, BorderLayout.EAST);
        formPanel.add(photoPanel, gbc);
        row++;

        add(formPanel, BorderLayout.CENTER);

        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        buttonPanel.setBackground(new Color(50, 50, 50)); 
        
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");

        
        Color buttonBg = new Color(70, 70, 70); 
        Color buttonFg = Color.BLACK;
        Font buttonFont = new Font("Arial", Font.BOLD, 14);
        Dimension buttonSize = new Dimension(100, 30); 

        btnGuardar.setBackground(buttonBg); 
        btnGuardar.setForeground(buttonFg);         
        btnGuardar.setFocusPainted(false);             
        btnGuardar.setFont(buttonFont);
        btnGuardar.setPreferredSize(buttonSize);

        btnCancelar.setBackground(buttonBg); 
        btnCancelar.setForeground(buttonFg);         
        btnCancelar.setFocusPainted(false);             
        btnCancelar.setFont(buttonFont);
        btnCancelar.setPreferredSize(buttonSize);

        btnSeleccionarFoto.setBackground(buttonBg); 
        btnSeleccionarFoto.setForeground(buttonFg);         
        btnSeleccionarFoto.setFocusPainted(false);             
        btnSeleccionarFoto.setFont(buttonFont);
        btnSeleccionarFoto.setPreferredSize(new Dimension(150, 30));
        

        buttonPanel.add(btnGuardar);
        buttonPanel.add(btnCancelar);
        add(buttonPanel, BorderLayout.SOUTH);

        
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateFields()) {
                    saveContact();
                    saved = true;
                    dispose(); 
                }
            }
        });

        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saved = false;
                dispose(); 
            }
        });

        btnSeleccionarFoto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seleccionarFoto();
            }
        });
    }

    private void populateFields() {
        if (contacto != null) { 
            txtPrimerNombre.setText(contacto.getPrimerNombre());
            txtSegundoNombre.setText(contacto.getSegundoNombre());
            txtPrimerApellido.setText(contacto.getPrimerApellido());
            txtSegundoApellido.setText(contacto.getSegundoApellido());
            chkEsFavorito.setSelected(contacto.isEsFavorito());
            txtRutaFoto.setText(contacto.getRutaFoto());
        }
    }

    private boolean validateFields() {
        if (txtPrimerNombre.getText().trim().isEmpty() || txtPrimerApellido.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El primer nombre y el primer apellido son obligatorios.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void saveContact() {
        if (contacto == null) { 
            contacto = new Contacto(
                    txtPrimerNombre.getText().trim(),
                    txtSegundoNombre.getText().trim().isEmpty() ? null : txtSegundoNombre.getText().trim(),
                    txtPrimerApellido.getText().trim(),
                    txtSegundoApellido.getText().trim().isEmpty() ? null : txtSegundoApellido.getText().trim(),
                    chkEsFavorito.isSelected(),
                    txtRutaFoto.getText().trim().isEmpty() ? null : txtRutaFoto.getText().trim()
            );
        } else { 
            contacto.setPrimerNombre(txtPrimerNombre.getText().trim());
            contacto.setSegundoNombre(txtSegundoNombre.getText().trim().isEmpty() ? null : txtSegundoNombre.getText().trim());
            contacto.setPrimerApellido(txtPrimerApellido.getText().trim());
            contacto.setSegundoApellido(txtSegundoApellido.getText().trim().isEmpty() ? null : txtSegundoApellido.getText().trim());
            contacto.setEsFavorito(chkEsFavorito.isSelected());
            contacto.setRutaFoto(txtRutaFoto.getText().trim().isEmpty() ? null : txtRutaFoto.getText().trim());
        }
    }

    private void seleccionarFoto() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccionar Foto del Contacto");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        // Opcional: Filtrar por tipos de imagen
        fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
            public boolean accept(File f) {
                return f.isDirectory() || f.getName().toLowerCase().endsWith(".jpg") ||
                       f.getName().toLowerCase().endsWith(".jpeg") ||
                       f.getName().toLowerCase().endsWith(".png") ||
                       f.getName().toLowerCase().endsWith(".gif");
            }
            public String getDescription() {
                return "Archivos de imagen (JPG, JPEG, PNG, GIF)";
            }
        });

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            if (selectedFile != null) {
                txtRutaFoto.setText(selectedFile.getAbsolutePath());
            } else {
                txtRutaFoto.setText("");
            }
        } else {
            txtRutaFoto.setText("");
        }
    }


    public Contacto getContacto() {
        return contacto;
    }

    public boolean isSaved() {
        return saved;
    }
}