/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel; // Ahora es un JPanel
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginView extends JPanel { // <- CAMBIO IMPORTANTE

    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private JButton btnLogin;

    public LoginView() {
        // Ahora el panel MISMO es el contenedor
        this.setLayout(new GridBagLayout()); 
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        this.add(new JLabel("Usuario:"), gbc); // Se añade directamente al 'this'

        gbc.gridx = 1; gbc.gridy = 0;
        txtUsuario = new JTextField(20);
        this.add(txtUsuario, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        this.add(new JLabel("Contraseña:"), gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        txtPassword = new JPasswordField(20);
        this.add(txtPassword, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        btnLogin = new JButton("Ingresar");
        this.add(btnLogin, gbc);
        
        // Ya no hay setSize, setLocation, setDefaultCloseOperation, etc.
    }

    // --- Los métodos para el controlador siguen igual ---
    public String getUsuario() { return txtUsuario.getText(); }
    public String getPassword() { return new String(txtPassword.getPassword()); }
    public void addLoginListener(ActionListener listener) {
        btnLogin.addActionListener(listener);
    }
    public void mostrarError(String mensaje) {
        // Le decimos que el 'padre' de este diálogo es el panel mismo
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
