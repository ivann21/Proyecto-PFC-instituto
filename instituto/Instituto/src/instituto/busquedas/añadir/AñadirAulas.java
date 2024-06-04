/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package instituto.busquedas.añadir;

import instituto.busquedas.Busqueda;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.swing.JOptionPane;
import java.sql.*;
import java.util.Base64;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
/**
 *
 * @author icast
 */
public class AñadirAulas extends javax.swing.JFrame {

     private Busqueda parentPanel;
      
    public AñadirAulas(Busqueda parentPanel) {
         this.parentPanel = parentPanel;
         initComponents();
       SpinnerModel spinnerModel = new SpinnerNumberModel(0, 0, 50, 1); 
       capacidadSpinner.setModel(spinnerModel);

    }
public  String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            byte[] hashedBytes = md.digest(password.getBytes());

            String hashedPassword = Base64.getEncoder().encodeToString(hashedBytes);

            return hashedPassword;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        nameLabel = new javax.swing.JLabel();
        nameTextField = new javax.swing.JTextField();
        apellidoLabel = new javax.swing.JLabel();
        correoLabel = new javax.swing.JLabel();
        ubicacionComboBox = new javax.swing.JComboBox<>();
        capacidadSpinner = new javax.swing.JSpinner();
        pizarraCheckBox = new javax.swing.JCheckBox();
        ordenadoresCheckBox = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Añadir Profesores");

        jButton1.setText("añadir");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        nameLabel.setText("nombre");

        apellidoLabel.setText("ubicacion");

        correoLabel.setText("capacidad del aula");

        ubicacionComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Edificio Izquierdo", "Edificio central", "Edificio Derecho" }));

        pizarraCheckBox.setText("pizarra");

        ordenadoresCheckBox.setText("ordenadores");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ubicacionComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jButton1)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nameLabel)
                            .addComponent(nameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(apellidoLabel)
                            .addComponent(correoLabel)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(capacidadSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pizarraCheckBox)
                        .addGap(18, 18, 18)
                        .addComponent(ordenadoresCheckBox)))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(nameLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(apellidoLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ubicacionComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(correoLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(capacidadSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pizarraCheckBox)
                    .addComponent(ordenadoresCheckBox))
                .addGap(18, 18, 18)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    String nombre = nameTextField.getText().trim();
    String ubicacion = ubicacionComboBox.getSelectedItem().toString();
    int capacidad = (int) capacidadSpinner.getValue();
    boolean tienePizarra = pizarraCheckBox.isSelected();
    boolean tieneOrdenadores = ordenadoresCheckBox.isSelected();

    if (nombre.isEmpty() || ubicacion.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
    } else if (capacidad < 1 || capacidad > 200) { 
        JOptionPane.showMessageDialog(null, "La capacidad debe estar entre 1 y 200", "Error", JOptionPane.ERROR_MESSAGE);
    } else {
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            try (Connection connection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/", "SA", "")) {
                String consultaVerificacion = "SELECT COUNT(*) FROM PUBLIC.INSTITUTO.AULAS WHERE NOMBRE = ?";
                try (PreparedStatement stmtVerificacion = connection.prepareStatement(consultaVerificacion)) {
                    stmtVerificacion.setString(1, nombre);
                    ResultSet rs = stmtVerificacion.executeQuery();
                    if (rs.next() && rs.getInt(1) > 0) {
                        JOptionPane.showMessageDialog(null, "El nombre del aula ya existe en la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        String consultaSQL = "INSERT INTO PUBLIC.INSTITUTO.AULAS (NOMBRE, UBICACION, CAPACIDAD_AULA, PIZARRA, ORDENADORES) VALUES (?, ?, ?, ?, ?)";
                        try (PreparedStatement stmt = connection.prepareStatement(consultaSQL)) {
                            stmt.setString(1, nombre);
                            stmt.setString(2, ubicacion);
                            stmt.setInt(3, capacidad);
                            stmt.setBoolean(4, tienePizarra);
                            stmt.setBoolean(5, tieneOrdenadores);

                            int filasAfectadas = stmt.executeUpdate();
                            if (filasAfectadas > 0) {
                                JOptionPane.showMessageDialog(null, "Los datos se han guardado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                                dispose();
                                if (parentPanel != null) {
                                    String selectedTable = "aulas";
                                    parentPanel.mostrarDatosEnJTable(selectedTable);
                                }
                                if (this.getParent() != null && this.getParent().isEnabled()) {
                                    this.getParent().setEnabled(true); 
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "Hubo un error al guardar los datos", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Hubo un error con la base de datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AñadirAulas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AñadirAulas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AñadirAulas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AñadirAulas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AñadirAulas(null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel apellidoLabel;
    private javax.swing.JSpinner capacidadSpinner;
    private javax.swing.JLabel correoLabel;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JTextField nameTextField;
    private javax.swing.JCheckBox ordenadoresCheckBox;
    private javax.swing.JCheckBox pizarraCheckBox;
    private javax.swing.JComboBox<String> ubicacionComboBox;
    // End of variables declaration//GEN-END:variables
}
