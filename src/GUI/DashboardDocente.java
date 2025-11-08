/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI;

import Controller.Controlador;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class DashboardDocente extends javax.swing.JPanel {

    private Controlador controlador;
    
    public DashboardDocente(Controlador controlador) {
        initComponents();
        this.controlador = controlador;
         this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                // This code will execute when the JPanel becomes visible
                BienvenidaTexto.setText("¡Bienvenido, " + controlador.getDocenteLogueado().getNombre_completo() + "!");

                // Perform actions here that should happen when the panel is displayed
            }
        });
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        BienvenidaTexto = new javax.swing.JLabel();
        AulasBoton = new javax.swing.JButton();
        ActividadesBoton = new javax.swing.JButton();
        ReportesBoton = new javax.swing.JButton();

        jPanel1.setBackground(new java.awt.Color(204, 0, 0));

        BienvenidaTexto.setBackground(new java.awt.Color(255, 255, 255));
        BienvenidaTexto.setFont(BienvenidaTexto.getFont().deriveFont(BienvenidaTexto.getFont().getSize()+7f));
        BienvenidaTexto.setForeground(new java.awt.Color(255, 255, 255));
        BienvenidaTexto.setText("¡Bienvenido, Docente!");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(141, 141, 141)
                .addComponent(BienvenidaTexto)
                .addGap(142, 142, 142))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(BienvenidaTexto)
                .addContainerGap())
        );

        AulasBoton.setText("Aulas");
        AulasBoton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AulasBotonActionPerformed(evt);
            }
        });

        ActividadesBoton.setText("Actividades");
        ActividadesBoton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ActividadesBotonActionPerformed(evt);
            }
        });

        ReportesBoton.setText("Reportes");
        ReportesBoton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReportesBotonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ActividadesBoton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ReportesBoton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(AulasBoton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(126, 126, 126)
                .addComponent(AulasBoton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ActividadesBoton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ReportesBoton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(157, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void AulasBotonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AulasBotonActionPerformed
        controlador.cambiarVentana("aulDoc");
    }//GEN-LAST:event_AulasBotonActionPerformed

    private void ActividadesBotonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ActividadesBotonActionPerformed
        controlador.cambiarVentana("actDoc");
    }//GEN-LAST:event_ActividadesBotonActionPerformed

    private void ReportesBotonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReportesBotonActionPerformed
        controlador.cambiarVentana("repDoc");
    }//GEN-LAST:event_ReportesBotonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ActividadesBoton;
    private javax.swing.JButton AulasBoton;
    private javax.swing.JLabel BienvenidaTexto;
    private javax.swing.JButton ReportesBoton;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
