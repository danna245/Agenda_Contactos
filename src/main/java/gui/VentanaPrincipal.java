package gui;

import modelo.Contacto;
import dao.ContactoDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class VentanaPrincipal extends JFrame {

    private JLabel welcomeLabel;
    private JPanel contentPanel;
    private PanelContactos panelContactos;
    private PanelDetalleContacto panelDetalleContacto;
    private ContactoDAO contactoDAO;

    public VentanaPrincipal() {
        setTitle("Agenda Personal");
        setSize(1000, 750); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        contactoDAO = new ContactoDAO();

        
        panelContactos = new PanelContactos();
        panelDetalleContacto = new PanelDetalleContacto();

       
        contentPanel = new JPanel(new CardLayout());
        contentPanel.setBackground(new Color(40, 40, 40)); 

        contentPanel.add(panelContactos, "CONTACTOS_LISTA");
        contentPanel.add(panelDetalleContacto, "DETALLE_CONTACTO");

        
        welcomeLabel = new JLabel("¡Bienvenido a tu Agenda Personal!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 48));
        welcomeLabel.setForeground(Color.WHITE); 
       
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(30, 30, 30)); 

        JMenuItem verContactosItem = new JMenuItem("Ver Todos los Contactos");
        JMenuItem agregarContactoMenuItem = new JMenuItem("Agregar Nuevo Contacto"); 
        JMenuItem verFavoritosItem = new JMenuItem("Ver Favoritos");
        JMenuItem salirItem = new JMenuItem("Salir");

        
        Font menuItemFont = new Font("Arial", Font.PLAIN, 16);

        verContactosItem.setBackground(new Color(50, 50, 50));
        verContactosItem.setForeground(Color.BLACK); 
        verContactosItem.setFont(menuItemFont);

        agregarContactoMenuItem.setBackground(new Color(50, 50, 50));
        agregarContactoMenuItem.setForeground(Color.BLACK); 
        agregarContactoMenuItem.setFont(menuItemFont);

        verFavoritosItem.setBackground(new Color(50, 50, 50));
        verFavoritosItem.setForeground(Color.BLACK); 
        verFavoritosItem.setFont(menuItemFont);

        salirItem.setBackground(new Color(50, 50, 50));
        salirItem.setForeground(Color.BLACK); 
        salirItem.setFont(menuItemFont);

        
        menuBar.add(verContactosItem);
        menuBar.add(verFavoritosItem);
        menuBar.add(new JSeparator(SwingConstants.VERTICAL)); 
        menuBar.add(salirItem);

        setJMenuBar(menuBar);

        
        verContactosItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout)(contentPanel.getLayout());
                cl.show(contentPanel, "CONTACTOS_LISTA");
                panelContactos.cargarContactos(); 
            }
        });

        agregarContactoMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleAgregarContactoAction();
            }
        });
        
        verFavoritosItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout)(contentPanel.getLayout());
                cl.show(contentPanel, "CONTACTOS_LISTA");
                panelContactos.cargarContactosFavoritos(); 
            }
        });

        salirItem.addActionListener(e -> System.exit(0));

        panelContactos.getBtnAgregar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleAgregarContactoAction(); 
            }
        });

        panelContactos.getBtnEditar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Contacto contactoSeleccionado = panelContactos.getContactoSeleccionado();
                if (contactoSeleccionado != null) {
                    
                    ContactoDialog dialog = new ContactoDialog(VentanaPrincipal.this, contactoSeleccionado);
                    dialog.setVisible(true);

                    if (dialog.isSaved()) {
                        Contacto contactoActualizado = dialog.getContacto();
                        if (contactoDAO.actualizarContacto(contactoActualizado)) {
                            JOptionPane.showMessageDialog(VentanaPrincipal.this, "Contacto actualizado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                            panelContactos.cargarContactos(); 
                            CardLayout cl = (CardLayout)(contentPanel.getLayout());
                            cl.show(contentPanel, "CONTACTOS_LISTA");
                        } else {
                            JOptionPane.showMessageDialog(VentanaPrincipal.this, "Error al actualizar el contacto.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(VentanaPrincipal.this, "Por favor, selecciona un contacto para editar.", "Ningún Contacto Seleccionado", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        panelContactos.getBtnEliminar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Contacto contactoSeleccionado = panelContactos.getContactoSeleccionado();
                if (contactoSeleccionado != null) {
                    int confirm = JOptionPane.showConfirmDialog(VentanaPrincipal.this, 
                                "<html>¿Estás seguro de que quieres eliminar a <b>" + 
                                contactoSeleccionado.getPrimerNombre() + " " + 
                                contactoSeleccionado.getPrimerApellido() + "</b>?</html>",
                                "Confirmar Eliminación", 
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.WARNING_MESSAGE);
                    
                    if (confirm == JOptionPane.YES_OPTION) {
                        if (contactoDAO.eliminarContacto(contactoSeleccionado.getIdContacto())) {
                            JOptionPane.showMessageDialog(VentanaPrincipal.this, "Contacto eliminado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                            panelContactos.cargarContactos(); 
                            CardLayout cl = (CardLayout)(contentPanel.getLayout());
                            cl.show(contentPanel, "CONTACTOS_LISTA");
                        } else {
                            JOptionPane.showMessageDialog(VentanaPrincipal.this, "Error al eliminar el contacto.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(VentanaPrincipal.this, "Por favor, selecciona un contacto para eliminar.", "Ningún Contacto Seleccionado", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });


        panelContactos.getBtnVerDetalles().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Contacto contactoSeleccionado = panelContactos.getContactoSeleccionado();
                if (contactoSeleccionado != null) {
                    panelDetalleContacto.mostrarContacto(contactoSeleccionado);
                    CardLayout cl = (CardLayout)(contentPanel.getLayout());
                    cl.show(contentPanel, "DETALLE_CONTACTO"); 
                } else {
                    JOptionPane.showMessageDialog(VentanaPrincipal.this, "Por favor, selecciona un contacto para ver los detalles.", "Ningún Contacto Seleccionado", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        panelContactos.getTablaContactos().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { 
                    Contacto contactoSeleccionado = panelContactos.getContactoSeleccionado();
                    if (contactoSeleccionado != null) {
                        panelDetalleContacto.mostrarContacto(contactoSeleccionado);
                        CardLayout cl = (CardLayout)(contentPanel.getLayout());
                        cl.show(contentPanel, "DETALLE_CONTACTO"); 
                    }
                }
            }
        });

        this.add(welcomeLabel, BorderLayout.CENTER); 
        SwingUtilities.invokeLater(() -> {
            try {
                Thread.sleep(1500); 
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                VentanaPrincipal.this.remove(welcomeLabel);
                VentanaPrincipal.this.add(contentPanel, BorderLayout.CENTER);
                VentanaPrincipal.this.revalidate();
                VentanaPrincipal.this.repaint();
                CardLayout cl = (CardLayout)(contentPanel.getLayout());
                cl.show(contentPanel, "CONTACTOS_LISTA");
                panelContactos.cargarContactos(); 
            }
        });
    }

    private void handleAgregarContactoAction() {
        ContactoDialog dialog = new ContactoDialog(VentanaPrincipal.this, null); 
        dialog.setVisible(true);

        if (dialog.isSaved()) {
            Contacto nuevoContacto = dialog.getContacto();
            if (contactoDAO.insertarContacto(nuevoContacto)) {
                JOptionPane.showMessageDialog(VentanaPrincipal.this, "Contacto registrado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                panelContactos.cargarContactos(); 
                CardLayout cl = (CardLayout)(contentPanel.getLayout());
                cl.show(contentPanel, "CONTACTOS_LISTA");
            } else {
                JOptionPane.showMessageDialog(VentanaPrincipal.this, "Error al registrar el contacto.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    public static void main(String[] args) {
        try {
            
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            
        } catch (UnsupportedLookAndFeelException e) {
            System.err.println("Error al establecer el Look and Feel: " + e.getMessage());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
             System.err.println("Error al cargar el L&F del sistema: " + e.getMessage());
        }

        SwingUtilities.invokeLater(() -> {
            new VentanaPrincipal().setVisible(true);
        });
    }
}