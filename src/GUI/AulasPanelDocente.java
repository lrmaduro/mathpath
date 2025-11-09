/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI;

import Controller.Controlador;
import coil.prototipo.logica.Aula;
import database.dbConnections;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;

public class AulasPanelDocente extends javax.swing.JPanel {

    private Controlador controlador;
    
    
    
    public AulasPanelDocente(Controlador controlador) {
        initComponents();
        this.controlador = controlador;

        // Hacer que la lista acepte una sola selección
        jList1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Acción del botón "Ver Aula" - envía el aula seleccionada al controlador
        VerAulaBoton.addActionListener(evt -> {
            Aula aulaSeleccionada = jList1.getSelectedValue();
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

        jLabel1 = new javax.swing.JLabel();
        CrearAulaBoton = new javax.swing.JButton();
        VolverBoton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        VerAulaBoton = new javax.swing.JButton();

        jLabel1.setFont(new java.awt.Font("Segoe UI Semibold", 1, 24)); // NOI18N
        jLabel1.setText("Aulas");

        CrearAulaBoton.setText("Crear Aula");

        VolverBoton.setText("Volver");
        VolverBoton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VolverBotonActionPerformed(evt);
            }
        });

        jList1 = new javax.swing.JList<>();
        jScrollPane1.setViewportView(jList1);

        VerAulaBoton.setText("Ver Aula");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(279, 279, 279)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(144, 144, 144)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(VolverBoton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                        .addComponent(VerAulaBoton)
                        .addGap(37, 37, 37)
                        .addComponent(CrearAulaBoton)))
                .addGap(138, 138, 138))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
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
        controlador.volverPanelAnterior();
    }//GEN-LAST:event_VolverBotonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CrearAulaBoton;
    private javax.swing.JButton VerAulaBoton;
    private javax.swing.JButton VolverBoton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JList<coil.prototipo.logica.Aula> jList1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

   
    public void cargarAulas(String idDocente) {
        System.out.println(">>> AULAS PANEL DOCENTE: cargando aulas para docente ID: " + idDocente);
        dbConnections db = new dbConnections("jdbc:sqlite:src/database/mathpath.db");
        ArrayList<Aula> aulas = db.listarAulasDocente(idDocente);

        DefaultListModel<Aula> modeloLista = new DefaultListModel<>();
        for (Aula aula : aulas) {
            modeloLista.addElement(aula);
        }

        jList1.setModel(modeloLista);
    }
}
