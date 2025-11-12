package GUI;

import Personalizacion.PanelConFondo;
import database.dbConnections;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import Personalizacion.PanelConFondo;
import Controller.Controlador;


public class LoginPanel extends javax.swing.JPanel {
    
    int tipo;
    private Controlador controlador;
    
    public LoginPanel(Controlador controlador) {
        initComponents();
        this.controlador = controlador;
        tipo = 1;
    }
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(LoginPanel.class.getName());
    
  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToggleButton1 = new javax.swing.JToggleButton();
        jPanel1 = new javax.swing.JPanel();
        label3 = new java.awt.Label();
        label4 = new java.awt.Label();
        usernameField1 = new javax.swing.JTextField();
        passwordField1 = new javax.swing.JPasswordField();
        jButton1 = new javax.swing.JButton();
        botonModoLogin1 = new javax.swing.JButton();

        jToggleButton1.setText("jToggleButton1");

        setPreferredSize(new java.awt.Dimension(1100, 618));

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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(botonModoLogin1, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(214, 214, 214)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(passwordField1, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label3, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(usernameField1, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label4, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(247, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(botonModoLogin1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(label4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(usernameField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addComponent(label3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19)
                .addComponent(passwordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49)
                .addComponent(jButton1)
                .addContainerGap(53, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void usernameField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usernameField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_usernameField1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String username = usernameField1.getText();
        String password = new String(passwordField1.getPassword());
        
        // ¡La única línea de lógica!
        controlador.Login(username, password, tipo);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void botonModoLogin1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonModoLogin1ActionPerformed
        // TODO add your handling code here:
        if (tipo == 2) {
            tipo = 1;
            this.botonModoLogin1.setText("Login Docente");
        }
        else {
            tipo = 2;
            this.botonModoLogin1.setText("Login Estudiante");
        }
    }//GEN-LAST:event_botonModoLogin1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonModoLogin1;
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JToggleButton jToggleButton1;
    private java.awt.Label label3;
    private java.awt.Label label4;
    private javax.swing.JPasswordField passwordField1;
    private javax.swing.JTextField usernameField1;
    // End of variables declaration//GEN-END:variables
}
