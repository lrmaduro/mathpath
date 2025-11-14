/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI.Docente;

import Controller.Controlador;
import coil.prototipo.logica.Aula;
import database.dbConnections;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;

public class AulasPanelDocente extends javax.swing.JPanel {

    private Controlador controlador;
    
    
    
    public AulasPanelDocente(Controlador controlador) {
        initComponents();
        this.controlador = controlador;

        // Hacer que la lista acepte una sola selección
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
                    cargarAulas(controlador.getDocenteLogueado().getId_docente());
                }
            }
        });
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        AulasLabel = new javax.swing.JLabel();
        CrearAulaBoton = new javax.swing.JButton();
        VolverBoton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        listaAulas = new javax.swing.JList<>();
        VerAulaBoton = new javax.swing.JButton();

        AulasLabel.setFont(new java.awt.Font("Segoe UI Semibold", 1, 24)); // NOI18N
        AulasLabel.setText("Aulas");

        CrearAulaBoton.setText("Crear Aula");
        CrearAulaBoton.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        CrearAulaBoton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CrearAulaBotonActionPerformed(evt);
            }
        });

        VolverBoton.setText("Volver");
        VolverBoton.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        VolverBoton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VolverBotonActionPerformed(evt);
            }
        });

        listaAulas.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listaAulasValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(listaAulas);

        VerAulaBoton.setText("Ver Aula");
        VerAulaBoton.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        VerAulaBoton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VerAulaBotonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(279, 279, 279)
                .addComponent(AulasLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(144, 144, 144)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 339, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(VolverBoton, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(VerAulaBoton, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45)
                        .addComponent(CrearAulaBoton, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(138, 138, 138))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(AulasLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CrearAulaBoton, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(VolverBoton, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(VerAulaBoton, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(38, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void VolverBotonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VolverBotonActionPerformed
        controlador.volverPanelDashboard();
    }//GEN-LAST:event_VolverBotonActionPerformed

    private void VerAulaBotonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VerAulaBotonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_VerAulaBotonActionPerformed

    private void CrearAulaBotonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CrearAulaBotonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CrearAulaBotonActionPerformed

    private void listaAulasValueChanged(ListSelectionEvent evt) {
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel AulasLabel;
    private javax.swing.JButton CrearAulaBoton;
    private javax.swing.JButton VerAulaBoton;
    private javax.swing.JButton VolverBoton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList<coil.prototipo.logica.Aula> listaAulas;
    // End of variables declaration//GEN-END:variables

   
    public void cargarAulas(String idDocente) {
        System.out.println(">>> AULAS PANEL DOCENTE: cargando aulas para docente ID: " + idDocente);
        dbConnections db = new dbConnections("jdbc:sqlite:src/database/mathpath.db");
        ArrayList<Aula> aulas = db.listarAulas(idDocente);

        DefaultListModel<Aula> modeloLista = new DefaultListModel<>();
        for (Aula aula : aulas) {
            modeloLista.addElement(aula);
        }

        listaAulas.setModel(modeloLista);
    }
}
