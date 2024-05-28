/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package instituto.busquedas.añadir;

import instituto.Principal;
import instituto.busquedas.Busqueda;
import javax.swing.JOptionPane;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.swing.JOptionPane;
import java.sql.*;
import java.util.Base64;
/**
 *
 * @author icast
 */
public class EditarAsignaturas extends javax.swing.JFrame {

    private Busqueda parentPanel;
    
    public EditarAsignaturas(Object[] asignaturasData,Busqueda parentPanel) {
          this.parentPanel = parentPanel;
        initComponents();
           nameTextField.setText(asignaturasData[0].toString());
         anioComboBox.setSelectedItem(asignaturasData[1].toString());
         String descripcion = obtenerDescripcionAsignatura(asignaturasData[0].toString(), asignaturasData[1].toString());
        descripcionTextArea.setText(descripcion);
        cargarCiclos();
    }
    private String obtenerDescripcionAsignatura(String nombreAsignatura, String nombreCiclo) {
    String descripcion = null; // Valor por defecto en caso de que no se encuentre ninguna asignatura con los datos especificados
    try {
        Class.forName("org.hsqldb.jdbc.JDBCDriver");
        try (Connection connection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/", "SA", "")) {
            String consultaSQL = "SELECT a.DESCRIPCION FROM PUBLIC.INSTITUTO.ASIGNATURAS a INNER JOIN PUBLIC.INSTITUTO.CICLOS c ON a.ID_CICLOS = c.\"ID Ciclos\" WHERE a.NOMBRE=? AND c.NOMBRE=?";
            try (PreparedStatement stmt = connection.prepareStatement(consultaSQL)) {
                stmt.setString(1, nombreAsignatura);
                stmt.setString(2, nombreCiclo);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    descripcion = rs.getString("DESCRIPCION");
                }
            }
        }
    } catch (ClassNotFoundException | SQLException ex) {
        ex.printStackTrace();
    }
    return descripcion;
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
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        descripcionTextArea = new javax.swing.JTextArea();
        nameLabel1 = new javax.swing.JLabel();
        cicloComboBox = new javax.swing.JComboBox<>();
        correoLabel2 = new javax.swing.JLabel();
        anioComboBox = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Editar Profesores");

        jButton1.setText("editar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        nameLabel.setText("nombre");

        jLabel1.setText("Descripcion");

        descripcionTextArea.setColumns(20);
        descripcionTextArea.setRows(5);
        jScrollPane1.setViewportView(descripcionTextArea);

        nameLabel1.setText("ciclo");

        cicloComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        correoLabel2.setText("año del ciclo");

        anioComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nameLabel1)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(nameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nameLabel)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(cicloComboBox, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(anioComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(correoLabel2))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton1))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(nameLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nameLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cicloComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(correoLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(anioComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       String nombre = nameTextField.getText().trim();
    String descripcion = descripcionTextArea.getText().trim();
    String nombreCiclo = cicloComboBox.getSelectedItem().toString();
    int anioCiclo = Integer.parseInt(anioComboBox.getSelectedItem().toString());

    // Verificar si alguno de los campos está vacío
    if (nombre.isEmpty() || descripcion.isEmpty()) {
        // Mostrar un mensaje de error al usuario
        JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
    } else {
        int idCicloSeleccionado = obtenerIdCiclo(nombreCiclo, anioCiclo);
        if (idCicloSeleccionado != -1) {
            // Todos los campos están llenos, guardar los datos en la base de datos
            try {
                Class.forName("org.hsqldb.jdbc.JDBCDriver");
                try (Connection connection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/", "SA", "")) {
                    String consultaSQL = "INSERT INTO PUBLIC.INSTITUTO.ASIGNATURAS (NOMBRE, DESCRIPCION, ID_CICLOS) VALUES (?, ?, ?)";
                    try (PreparedStatement stmt = connection.prepareStatement(consultaSQL)) {
                        stmt.setString(1, nombre);
                        stmt.setString(2, descripcion);
                        stmt.setInt(3, idCicloSeleccionado);

                        int filasAfectadas = stmt.executeUpdate();
                        if (filasAfectadas > 0) {
                            // Mostrar un mensaje de éxito al usuario
                            JOptionPane.showMessageDialog(null, "Los datos se han guardado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                            dispose();
                            if (this.getParent() != null && this.getParent().isEnabled()) {
                                this.getParent().setEnabled(true); // Si el frame principal existe y está habilitado
                            }
                             if (parentPanel != null) {
                              String selectedTable = "asignaturas";
                              parentPanel. mostrarDatosEnJTable(selectedTable);
                          }
                        } else {
                            // Mostrar un mensaje de error al usuario si no se guardaron los datos correctamente
                            JOptionPane.showMessageDialog(null, "Hubo un error al guardar los datos", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            } catch (ClassNotFoundException | SQLException ex) {
                ex.printStackTrace();
            }
        } else {
            // Mostrar un mensaje de error si no se encontró el ciclo seleccionado
            JOptionPane.showMessageDialog(null, "No se encontró el ciclo seleccionado en la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } 
    }//GEN-LAST:event_jButton1ActionPerformed
       private int obtenerIdCiclo(String nombreCiclo, int anioCiclo) {
    int idCiclo = -1; // Valor por defecto en caso de que no se encuentre ningún ciclo con el nombre y año especificados
    try {
        Class.forName("org.hsqldb.jdbc.JDBCDriver");
        try (Connection connection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/", "SA", "")) {
            String consultaSQL = "SELECT \"ID Ciclos\" FROM PUBLIC.INSTITUTO.CICLOS WHERE NOMBRE = ? AND ANIO = ?";
            try (PreparedStatement stmt = connection.prepareStatement(consultaSQL)) {
                stmt.setString(1, nombreCiclo);
                stmt.setInt(2, anioCiclo);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    idCiclo = rs.getInt("ID Ciclos");
                }
            }
        }
    } catch (ClassNotFoundException | SQLException ex) {
        ex.printStackTrace();
    }
    return idCiclo;
}
        private void cargarCiclos() {
    cicloComboBox.removeAllItems(); // Limpiar el JComboBox antes de cargar los nuevos ciclos
    try {
        Class.forName("org.hsqldb.jdbc.JDBCDriver");
        try (Connection connection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/", "SA", "")) {
            String consultaSQL = "SELECT DISTINCT NOMBRE FROM PUBLIC.INSTITUTO.CICLOS";
            try (PreparedStatement stmt = connection.prepareStatement(consultaSQL)) {
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    String nombreCiclo = rs.getString("NOMBRE");
                    cicloComboBox.addItem(nombreCiclo);
                }
            }
        }
    } catch (ClassNotFoundException | SQLException ex) {
        ex.printStackTrace();
    }
     }
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
            java.util.logging.Logger.getLogger(EditarAsignaturas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditarAsignaturas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditarAsignaturas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditarAsignaturas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                 Object[] asignaturaData = {};
                new EditarAsignaturas(asignaturaData,null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> anioComboBox;
    private javax.swing.JComboBox<String> cicloComboBox;
    private javax.swing.JLabel correoLabel2;
    private javax.swing.JTextArea descripcionTextArea;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JLabel nameLabel1;
    private javax.swing.JTextField nameTextField;
    // End of variables declaration//GEN-END:variables
}
