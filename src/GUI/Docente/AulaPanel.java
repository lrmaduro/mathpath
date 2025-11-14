    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI.Docente;

import GUI.*;
import coil.prototipo.logica.Aula;
import database.dbConnections;
import java.util.ArrayList;
import Controller.Controlador;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;

/**
 *
 * @author Luis
 */
public class AulaPanel extends javax.swing.JPanel {

   private Controlador controlador;
    
   public void cargarAulas() {
       controlador.cargarAulasProf(listaAulas);
   }
   
    public AulaPanel(Controlador controlador) {
        initComponents();
        this.controlador = controlador;
    
        listaAulas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Acción del botón "Ver Aula" - envía el aula seleccionada al controlador
        VerAulaBoton.addActionListener(evt -> {
            Aula aulaSeleccionada = listaAulas.getSelectedValue();
            if (aulaSeleccionada == null) {
                JOptionPane.showMessageDialog(this, "Selecciona un aula primero.", "Sin selección", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (controlador != null) {
                controlador.showAula(aulaSeleccionada);
            }
        });

        // Acción del botón "Crear Aula" - abre el panel de creación
        CrearAulaBoton.addActionListener(evt -> {
            if (controlador != null) {
                controlador.cambiarVentana("crearAula");
            }
        });

        // Cuando el panel se muestre, cargamos las aulas del docente logueado (si existe)
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                if (controlador != null && controlador.getDocenteLogueado() != null) {
                    cargarAulas();
                }
            }
        });
        
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        CrearAulaBoton = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        listaAulas = new javax.swing.JList<>();
        VolverBoton1 = new javax.swing.JButton();
        VerAulaBoton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        CrearAulaBoton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/Docente/img/crear aula boton.png"))); // NOI18N
        CrearAulaBoton.setBorderPainted(false);
        CrearAulaBoton.setContentAreaFilled(false);
        CrearAulaBoton.setFocusPainted(false);
        CrearAulaBoton.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/Docente/img/crear aula boton hover.png"))); // NOI18N
        CrearAulaBoton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CrearAulaBotonActionPerformed(evt);
            }
        });
        add(CrearAulaBoton, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 430, 220, -1));

        jLabel3.setFont(new java.awt.Font("Franklin Gothic Medium", 1, 60)); // NOI18N
        jLabel3.setText("AULAS");
        add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 20, 220, 70));

        listaAulas.setBackground(new java.awt.Color(153, 255, 153));
        listaAulas.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listaAulasValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(listaAulas);

        add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 110, 490, 310));

        VolverBoton1.setFont(VolverBoton1.getFont());
        VolverBoton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/Boton volver.png"))); // NOI18N
        VolverBoton1.setBorder(null);
        VolverBoton1.setBorderPainted(false);
        VolverBoton1.setContentAreaFilled(false);
        VolverBoton1.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/Boton volver_hover.png"))); // NOI18N
        VolverBoton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VolverBoton1ActionPerformed(evt);
            }
        });
        add(VolverBoton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 520, 91, 70));

        VerAulaBoton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/Docente/img/ver aula boton.png"))); // NOI18N
        VerAulaBoton.setBorderPainted(false);
        VerAulaBoton.setContentAreaFilled(false);
        VerAulaBoton.setFocusPainted(false);
        VerAulaBoton.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/Docente/img/ver aula boton hover.png"))); // NOI18N
        VerAulaBoton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VerAulaBotonActionPerformed(evt);
            }
        });
        add(VerAulaBoton, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 430, -1, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/12.png"))); // NOI18N
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 620));
    }// </editor-fold>//GEN-END:initComponents

    private void listaAulasValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listaAulasValueChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_listaAulasValueChanged

    private void VolverBoton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VolverBoton1ActionPerformed
        controlador.volverPanelDashboard();
    }//GEN-LAST:event_VolverBoton1ActionPerformed

    private void CrearAulaBotonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CrearAulaBotonActionPerformed
        controlador.cambiarVentana(TOOL_TIP_TEXT_KEY);
    }//GEN-LAST:event_CrearAulaBotonActionPerformed

    private void VerAulaBotonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VerAulaBotonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_VerAulaBotonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CrearAulaBoton;
    private javax.swing.JButton VerAulaBoton;
    private javax.swing.JButton VolverBoton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JList<coil.prototipo.logica.Aula> listaAulas;
    // End of variables declaration//GEN-END:variables
}
