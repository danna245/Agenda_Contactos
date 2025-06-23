package gui;

import modelo.NumeroTelefono; 
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

public class NumeroTelefonoDialog extends JDialog {

    private JComboBox<String> cmbTipo;
    private JTextField txtNumero;
    private boolean saved = false;
    private NumeroTelefono numeroTelefono; 

    
    private static final String[] TIPOS_NUMERO = {"Móvil", "Casa", "Trabajo", "Fax", "Otro"};

    /**
     * Constructor para agregar un nuevo número.
     * @param parent El JFrame padre.
     */
    public NumeroTelefonoDialog(JFrame parent) {
        super(parent, "Agregar Número de Teléfono", true); 
        this.numeroTelefono = null; 
        initComponents();
        populateFields();
    }

    /**
     * Constructor para editar un número existente.
     * @param parent El JFrame padre.
     * @param numeroTelefono El objeto NumeroTelefono a editar.
     */
    public NumeroTelefonoDialog(JFrame parent, NumeroTelefono numeroTelefono) {
        super(parent, "Editar Número de Teléfono", true); 
        this.numeroTelefono = numeroTelefono;
        initComponents();
        populateFields();
    }

    private void initComponents() {
        setSize(350, 200); 
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout(10, 10));
        this.getContentPane().setBackground(new Color(40, 40, 40));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(50, 50, 50)); 
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8); 

        
        Font labelFont = new Font("Arial", Font.PLAIN, 14);

        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        JLabel lblTipo = new JLabel("Tipo:");
        lblTipo.setForeground(Color.WHITE); 
        lblTipo.setFont(labelFont);
        formPanel.add(lblTipo, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        cmbTipo = new JComboBox<>(TIPOS_NUMERO);
        cmbTipo.setBackground(new Color(60, 60, 60));
        cmbTipo.setForeground(Color.WHITE);
        cmbTipo.setFont(new Font("Arial", Font.PLAIN, 14));

        
        cmbTipo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                list.setBackground(new Color(60, 60, 60)); 
                list.setForeground(Color.WHITE); 

                if (isSelected) {
                    setBackground(new Color(100, 100, 100)); 
                    setForeground(Color.CYAN); 
                } else {
                    setBackground(new Color(60, 60, 60)); 
                    setForeground(Color.WHITE); 
                }
                return this;
            }
        });

        formPanel.add(cmbTipo, gbc);

        
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        JLabel lblNumero = new JLabel("Número:");
        lblNumero.setForeground(Color.WHITE); 
        lblNumero.setFont(labelFont);
        formPanel.add(lblNumero, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        txtNumero = new JTextField(20);
        addRealTimeValidation(); 
        txtNumero.setBackground(new Color(60, 60, 60)); 
        txtNumero.setForeground(Color.WHITE);
        txtNumero.setCaretColor(Color.WHITE);
        txtNumero.setFont(new Font("Arial", Font.PLAIN, 14));
        txtNumero.setBorder(BorderFactory.createLineBorder(new Color(90, 90, 90)));
        formPanel.add(txtNumero, gbc);

        add(formPanel, BorderLayout.CENTER);

        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10)); 
        buttonPanel.setBackground(new Color(50, 50, 50)); 
        
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");

        // Estilo común para los botones
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

        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateInput()) {
                    saved = true;
                    
                    if (numeroTelefono != null) {
                        numeroTelefono.setTipo((String) cmbTipo.getSelectedItem());
                        numeroTelefono.setNumero(txtNumero.getText().trim());
                    } else {
                       
                    }
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

        buttonPanel.add(btnGuardar);
        buttonPanel.add(btnCancelar);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void populateFields() {
        if (numeroTelefono != null) {
            cmbTipo.setSelectedItem(numeroTelefono.getTipo());
            txtNumero.setText(numeroTelefono.getNumero());
        } else {
            
            cmbTipo.setSelectedItem("Móvil");
            txtNumero.setText("");
        }
    }

    private boolean validateInput() {
        String numero = txtNumero.getText().trim();
        
        // Verificar que no esté vacío
        if (numero.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "El número de teléfono no puede estar vacío.", 
                "Error de Validación", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        // Verificar longitud mínima
        if (numero.length() < 7) {
            JOptionPane.showMessageDialog(this, 
                "El número de teléfono debe tener al menos 7 caracteres.", 
                "Error de Validación", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        // Contar letras y números
        int contadorLetras = 0;
        int contadorNumeros = 0;
        
        for (char c : numero.toCharArray()) {
            if (Character.isLetter(c)) {
                contadorLetras++;
            } else if (Character.isDigit(c)) {
                contadorNumeros++;
            }
            // Permitir espacios, guiones, paréntesis, signos +
            // No hacer nada para estos caracteres
        }
        
        // Validar que tenga al menos algunos números
        if (contadorNumeros < 5) {
            JOptionPane.showMessageDialog(this, 
                "El número de teléfono debe contener al menos 5 dígitos.", 
                "Error de Validación", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        // Validar que no tenga más de 2 letras
        if (contadorLetras > 2) {
            JOptionPane.showMessageDialog(this, 
                "El número de teléfono puede contener máximo 2 letras.", 
                "Error de Validación", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        // Validar que no sea completamente letras (sin números)
        if (contadorNumeros == 0) {
            JOptionPane.showMessageDialog(this, 
                "El número de teléfono debe contener dígitos numéricos.", 
                "Error de Validación", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        // Validar caracteres permitidos (números, letras, espacios, guiones, paréntesis, +)
        if (!numero.matches("[0-9a-zA-Z\\s\\-\\(\\)\\+\\#\\*]+")) {
            JOptionPane.showMessageDialog(this, 
                "El número de teléfono contiene caracteres no válidos.\n" +
                "Solo se permiten: números, letras (máximo 2), espacios, guiones, paréntesis, + y #", 
                "Error de Validación", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        return true;
    }

    private void addRealTimeValidation() {
        txtNumero.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent evt) {
                char c = evt.getKeyChar();
                String currentText = txtNumero.getText();
                
                // Contar letras actuales
                long currentLetters = currentText.chars()
                    .filter(ch -> Character.isLetter(ch))
                    .count();
                
                // Si ya hay 2 letras y se intenta agregar otra letra, bloquear
                if (Character.isLetter(c) && currentLetters >= 2) {
                    evt.consume(); // Bloquea el carácter
                    
                    // Mostrar tooltip temporal
                    JToolTip tooltip = txtNumero.createToolTip();
                    tooltip.setTipText("Máximo 2 letras permitidas");
                    
                    // Opcional: cambiar color del borde temporalmente
                    txtNumero.setBorder(BorderFactory.createLineBorder(Color.RED));
                    
                    // Restaurar borde original después de 2 segundos
                    Timer timer = new Timer(2000, e -> {
                        txtNumero.setBorder(BorderFactory.createLineBorder(new Color(90, 90, 90)));
                    });
                    timer.setRepeats(false);
                    timer.start();
                }
                
                // Bloquear caracteres no permitidos
                if (!Character.isDigit(c) && !Character.isLetter(c) && 
                    c != ' ' && c != '-' && c != '(' && c != ')' && 
                    c != '+' && c != '#' && c != '*' && 
                    c != KeyEvent.VK_BACK_SPACE && c != KeyEvent.VK_DELETE) {
                    evt.consume();
                }
            }
        });
    }
    /**
	 * Indica si se guardó el número de teléfono.
	 * @return true si se guardó, false si se canceló.
	 */
    public boolean isSaved() {
        return saved;
    }

    public String getSelectedTipo() {
        return (String) cmbTipo.getSelectedItem();
    }

    public String getEnteredNumero() {
        return txtNumero.getText().trim();
    }

    /**
     * Devuelve el objeto NumeroTelefono si se está editando y se guardó, o null si es una nueva inserción
     * (el nuevo objeto se crea en el DAO).
     */
    public NumeroTelefono getNumeroTelefono() {
        return numeroTelefono;
    }
}