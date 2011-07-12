/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * MainWindow.java
 *
 * Created on Jul 12, 2011, 3:09:18 PM
 */
package views;

/**
 *
 * @author ajohnson
 */
public class MainWindow extends javax.swing.JFrame {

    /** Creates new form MainWindow */
    public MainWindow() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        tab1Panel = new javax.swing.JPanel();
        connectionInformationLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        connectionInformationTextArea = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        fileSettingsMenuItem = new javax.swing.JMenuItem();
        fileExitMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Secure Transfer Over SSL Application");

        jTabbedPane1.setName("jTabbedPane1"); // NOI18N

        tab1Panel.setName("tab1Panel"); // NOI18N

        connectionInformationLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        connectionInformationLabel.setText("Connection Information");
        connectionInformationLabel.setName("connectionInformationLabel"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        connectionInformationTextArea.setColumns(20);
        connectionInformationTextArea.setEditable(false);
        connectionInformationTextArea.setRows(5);
        connectionInformationTextArea.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        connectionInformationTextArea.setName("connectionInformationTextArea"); // NOI18N
        jScrollPane1.setViewportView(connectionInformationTextArea);

        javax.swing.GroupLayout tab1PanelLayout = new javax.swing.GroupLayout(tab1Panel);
        tab1Panel.setLayout(tab1PanelLayout);
        tab1PanelLayout.setHorizontalGroup(
            tab1PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tab1PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tab1PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 710, Short.MAX_VALUE)
                    .addComponent(connectionInformationLabel))
                .addContainerGap())
        );
        tab1PanelLayout.setVerticalGroup(
            tab1PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tab1PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(connectionInformationLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 368, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Connections", tab1Panel);

        jPanel1.setName("jPanel1"); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 730, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 410, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Transfers", jPanel1);

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText("File");
        fileMenu.setName("fileMenu"); // NOI18N

        fileSettingsMenuItem.setText("Settings");
        fileSettingsMenuItem.setName("fileSettingsMenuItem"); // NOI18N
        fileMenu.add(fileSettingsMenuItem);

        fileExitMenuItem.setText("Exit");
        fileExitMenuItem.setName("fileExitMenuItem"); // NOI18N
        fileMenu.add(fileExitMenuItem);

        menuBar.add(fileMenu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 735, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 438, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new MainWindow().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel connectionInformationLabel;
    private javax.swing.JTextArea connectionInformationTextArea;
    private javax.swing.JMenuItem fileExitMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenuItem fileSettingsMenuItem;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JPanel tab1Panel;
    // End of variables declaration//GEN-END:variables
}
