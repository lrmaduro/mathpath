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
        jLabel1 = new javax.swing.JLabel();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        AulasLabel.setFont(new java.awt.Font("Franklin Gothic Medium", 1, 48)); // NOI18N
        AulasLabel.setText("AULAS");
        add(AulasLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 50, -1, 50));

        CrearAulaBoton.setText("Crear Aula");
        CrearAulaBoton.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        CrearAulaBoton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CrearAulaBotonActionPerformed(evt);
            }
        });
        add(CrearAulaBoton, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 450, 280, 100));

        VolverBoton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/Docente/Boton volver.png"))); // NOI18N
        VolverBoton.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        VolverBoton.setBorderPainted(false);
        VolverBoton.setContentAreaFilled(false);
        VolverBoton.setFocusPainted(false);
        VolverBoton.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/Docente/Boton volver_hover.png"))); // NOI18N
        VolverBoton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VolverBotonActionPerformed(evt);
            }
        });
        add(VolverBoton, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 470, 80, 80));

        jScrollPane1.setBorder(null);

        listaAulas.setBackground(new java.awt.Color(255, 102, 102));
        listaAulas.setBorder(null);
        listaAulas.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listaAulasValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(listaAulas);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 120, 570, 280));

        VerAulaBoton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/Docente/Boton VER AULA.png"))); // NOI18N
        VerAulaBoton.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        VerAulaBoton.setBorderPainted(false);
        VerAulaBoton.setContentAreaFilled(false);
        VerAulaBoton.setFocusPainted(false);
        VerAulaBoton.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/Docente/Boton VER AULA_hover.png"))); // NOI18N
        add(VerAulaBoton, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 450, 270, 110));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/Docente/14.png"))); // NOI18N
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1100, 620));
    }// </editor-fold>//GEN-END:initComponents

    private void VolverBotonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VolverBotonActionPerformed
        controlador.volverPanelDashboard();
    }//GEN-LAST:event_VolverBotonActionPerformed

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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList<coil.prototipo.logica.Aula> listaAulas;
    // End of variables declaration//GEN-END:variables

   
    public void cargarAulas(String idDocente) {
        System.out.println(">>> AULAS PANEL DOCENTE: cargando aulas para docente ID: " + idDocente);
        dbConnections db = new dbConnections("jdbc:sqlite:src/database/mathpath.db");
        ArrayList<Aula> aulas = db.listarAulasDocente(idDocente);

        DefaultListModel<Aula> modeloLista = new DefaultListModel<>();
        for (Aula aula : aulas) {
            modeloLista.addElement(aula);
        }

        listaAulas.setModel(modeloLista);
    }
}
