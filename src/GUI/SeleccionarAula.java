/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI;

import coil.prototipo.logica.Aula;
import database.dbConnections;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.DefaultListModel;

/**
 *
 * @author Luis
 */
public class SeleccionarAula extends javax.swing.JPanel {

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
        jListAulas.setModel(modeloLista);
    }
    
    public SeleccionarAula() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jListAulas = new javax.swing.JList<>();

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setText("Aulas Inscritas");

        jListAulas.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jListAulasValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(jListAulas);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(138, 138, 138)
                        .addComponent(jLabel2))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(105, 105, 105)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(109, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(56, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(90, 90, 90)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(92, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jListAulasValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jListAulasValueChanged
        if (!evt.getValueIsAdjusting()) {
        
        Aula aulaSeleccionada = (Aula) jListAulas.getSelectedValue(); 

        if (aulaSeleccionada != null) {
            
            // 2. Accedes a los datos directamente
            String idAula = aulaSeleccionada.getId_aula();
            String nombreAula = aulaSeleccionada.getNombre();
            
            System.out.println("Aula seleccionada: " + nombreAula + " (ID: " + idAula + ")");
            
            // (Aquí llamas al MainFrame para ir al panel del aula)
            }
        }
    }//GEN-LAST:event_jListAulasValueChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JList<coil.prototipo.logica.Aula> jListAulas;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
