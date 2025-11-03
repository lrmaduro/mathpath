/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
    package GUI;

    import java.awt.CardLayout;
    import javax.swing.JFrame;
    import javax.swing.JPanel;

/**
 *
 * @author Luis
 */
public class MainFrame extends javax.swing.JFrame {
    
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public MainFrame() {
        initComponents();

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Instancias de paneles
        LoginPanel login = new LoginPanel();
        // MenuPanel menu = new MenuPanel();
        // RegistroPanel registro = new RegistroPanel();

        // Agregar al contenedor principal
        mainPanel.add(login, "login");
        // mainPanel.add(menu, "menu");
        // mainPanel.add(registro, "registro");

        // Añadir al frame
        this.setContentPane(mainPanel);
        this.setSize(600, 400);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void showPanel(String name) {
        cardLayout.show(mainPanel, name);
    }

    // Si quieres arrancar desde aquí:
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            MainFrame mf = new MainFrame();
            mf.setVisible(true);
        });
    }

    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(MainFrame.class.getName());

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
