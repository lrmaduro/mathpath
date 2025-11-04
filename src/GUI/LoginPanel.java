/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI;

import database.dbConnections;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;


public class LoginPanel extends javax.swing.JPanel {

    public LoginPanel() {
        initComponents();
    }

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(LoginPanel.class.getName());
    int type = 1;
  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        label3 = new java.awt.Label();
        label4 = new java.awt.Label();
        usernameField1 = new javax.swing.JTextField();
        passwordField1 = new javax.swing.JPasswordField();
        jButton1 = new javax.swing.JButton();
        botonModoLogin1 = new javax.swing.JButton();

        label3.setAlignment(java.awt.Label.CENTER);
        label3.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        label3.setName(""); // NOI18N
        label3.setText("Contraseña");

        label4.setAlignment(java.awt.Label.CENTER);
        label4.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        label4.setName(""); // NOI18N
        label4.setText("Usuario");

        usernameField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usernameField1ActionPerformed(evt);
            }
        });

        jButton1.setText("LOGIN");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        botonModoLogin1.setText("Login Docente");
        botonModoLogin1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonModoLogin1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(87, 87, 87)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(usernameField1)
                        .addComponent(passwordField1)
                        .addComponent(label3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(label4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(botonModoLogin1)))
                .addContainerGap(95, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(label4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(usernameField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(label3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(passwordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53)
                .addComponent(jButton1)
                .addGap(18, 18, 18)
                .addComponent(botonModoLogin1)
                .addContainerGap(50, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(149, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(130, 130, 130))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void usernameField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usernameField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_usernameField1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String username = usernameField1.getText();
        String password = new String(passwordField1.getPassword());

        System.out.println("Botón LOGIN presionado. Tipo actual: " + type); // <-- DEBUG

        try {
            dbConnections db = new dbConnections("jdbc:sqlite:src/database/mathpath.db");

            if (db.login(username, password, type)){

                System.out.println("¡Login EXITOSO en la DB!"); // <-- DEBUG
                JOptionPane.showMessageDialog(this, "Login exitoso"); // ¿Ves este mensaje?

                java.awt.Window parent = SwingUtilities.getWindowAncestor(this);

                if (parent instanceof MainFrame) {
                    System.out.println("Parent es MainFrame. Correcto."); // <-- DEBUG
                    MainFrame frame = (MainFrame) parent;

                    if (type == 2) { // Alumno
                        System.out.println("Tipo es 2 (Alumno). Intentando mostrar 'gesAul'"); // <-- DEBUG
                        frame.showPanel("gesAul"); 
                    } else if (type == 1) { // Docente
                        System.out.println("Tipo es 1 (Docente). Intentando mostrar 'menuDocente'"); // <-- DEBUG
                        frame.showPanel("menuDocente");
                    } else {
                        System.out.println("ERROR: Login exitoso pero tipo no es 1 ni 2. Tipo es: " + type); // <-- DEBUG
                    }

                } else {
                    System.out.println("ERROR: El 'parent' NO es una instancia de MainFrame."); // <-- DEBUG
                }

            } else {
                System.out.println("Login FALLIDO en la DB."); // <-- DEBUG
                JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrecta");
            }
        } catch (Exception e) {
            System.out.println("ERROR en la DB: " + e.getMessage()); // <-- DEBUG
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void botonModoLogin1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonModoLogin1ActionPerformed
        // TODO add your handling code here:
        if (type == 2) {
            type = 1;
            this.botonModoLogin1.setText("Login Docente");
        }
        else {
            type = 2;
            this.botonModoLogin1.setText("Login Estudiante");
        }
    }//GEN-LAST:event_botonModoLogin1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonModoLogin1;
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private java.awt.Label label3;
    private java.awt.Label label4;
    private javax.swing.JPasswordField passwordField1;
    private javax.swing.JTextField usernameField1;
    // End of variables declaration//GEN-END:variables
}
