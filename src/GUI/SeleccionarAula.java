    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI;

import coil.prototipo.logica.Aula;
import database.dbConnections;
import java.util.ArrayList;
import Controller.Controlador;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.DefaultListModel;

/**
 *
 * @author Luis
 */
public class SeleccionarAula extends javax.swing.JPanel {

   private Controlador controlador;
    
   public void cargarAulas(String idEstudiante) {
        
        System.out.println(">>> SELECCIONAR_AULA: Cargando aulas para ID: " + idEstudiante);
        
        // Crea la conexión
        dbConnections db = new dbConnections("jdbc:sqlite:src/database/mathpath.db");
        
        // ¡Llama al método de la DB!
        ArrayList<Aula> aulas = db.listarAulas(Integer.valueOf(idEstudiante));
        
        System.out.println(">>> SELECCIONAR_AULA: DB devolvió " + aulas.size() + " aulas.");

        // Crea el modelo para la lista
        DefaultListModel<Aula> modeloLista = new DefaultListModel<>();
        
        // Llena el modelo
        for (Aula aula : aulas) {
            modeloLista.addElement(aula);
        }
        
        // Asigna el modelo al JList
        // (Asumo que tu JList se llama jListAulas)
        jListAulas1.setModel(modeloLista);
    }
    
    public SeleccionarAula(Controlador controlador) {
        initComponents();
        this.controlador = controlador;
    
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jListAulas1 = new javax.swing.JList<>();
        VolverBoton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/Boton INGRESAR AULA.png"))); // NOI18N
        jButton1.setBorderPainted(false);
        jButton1.setContentAreaFilled(false);
        jButton1.setFocusPainted(false);
        jButton1.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/Boton INGRESAR AULA_hover.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 460, 290, -1));

        jLabel3.setFont(new java.awt.Font("Franklin Gothic Medium", 1, 60)); // NOI18N
        jLabel3.setText("AULAS");
        add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 20, 220, 70));

        jListAulas1.setBackground(new java.awt.Color(153, 255, 153));
        jListAulas1.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jListAulas1ValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(jListAulas1);

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
        add(VolverBoton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 480, 91, 70));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/12.png"))); // NOI18N
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 620));
    }// </editor-fold>//GEN-END:initComponents

    private void jListAulas1ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jListAulas1ValueChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_jListAulas1ValueChanged

    private void VolverBoton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VolverBoton1ActionPerformed
        controlador.volverPanelDashboard();
    }//GEN-LAST:event_VolverBoton1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        controlador.cambiarVentana(TOOL_TIP_TEXT_KEY);
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton VolverBoton1;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JList<coil.prototipo.logica.Aula> jListAulas1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
