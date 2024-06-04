/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package instituto.busquedas.añadir;

import instituto.busquedas.Busqueda;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.swing.JOptionPane;
import java.sql.*;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
/**
 *
 * @author icast
 */
public class EditarProfesores extends javax.swing.JFrame {
 
    private Busqueda parentPanel;
    private String correos;
      private String correo;
   private String rutaImagen = "";
        
    public EditarProfesores(Object[] profesorData,Busqueda parentPanel) {
          this.parentPanel = parentPanel;
        initComponents();
         nameTextField.setText(profesorData[0].toString());
         apellidoTextField.setText(profesorData[1].toString());
         correoTextField.setText(profesorData[2].toString());
         correo = profesorData[2].toString();
          int id = obtenerIdProfesor(correo);
         String rutaImagenGuardada = new File("src/imagenes/" + id + ".png").getAbsolutePath();
         File imagenGuardada = new File(rutaImagenGuardada);
            if (imagenGuardada.exists()) {
                rutaImagen = rutaImagenGuardada;
                ImageIcon icon = new ImageIcon(rutaImagen);
                Image image = icon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
                jLabel6.setIcon(new ImageIcon(image));
            }
         correos = correoTextField.getText();
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
private boolean verificarContrasenia(String contraseniaActual) {
    try {
        Class.forName("org.hsqldb.jdbc.JDBCDriver");
        try (Connection connection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/", "SA", "")) {
            String consultaSQL = "SELECT * FROM PUBLIC.INSTITUTO.PROFESORES WHERE CORREOELECTRONICO=? AND CONTRASENIA=?";
            try (PreparedStatement stmt = connection.prepareStatement(consultaSQL)) {
                stmt.setString(1, correos);
                stmt.setString(2, contraseniaActual);
                try (ResultSet rs = stmt.executeQuery()) {
                    return rs.next();
                }
            }
        }
    } catch (ClassNotFoundException | SQLException ex) {
        ex.printStackTrace();
    }
    return false; 
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
        contraseñaLabel = new javax.swing.JLabel();
        nameLabel = new javax.swing.JLabel();
        nameTextField = new javax.swing.JTextField();
        apellidoLabel = new javax.swing.JLabel();
        apellidoTextField = new javax.swing.JTextField();
        correoLabel = new javax.swing.JLabel();
        correoTextField = new javax.swing.JTextField();
        jPasswordField1 = new javax.swing.JPasswordField();
        contraseñaLabel1 = new javax.swing.JLabel();
        jPasswordField2 = new javax.swing.JPasswordField();
        jButton2 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Editar Profesores");
        setResizable(false);

        jButton1.setText("editar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        contraseñaLabel.setText("introduce su contraseña");

        nameLabel.setText("nombre");

        apellidoLabel.setText("apellido");

        correoLabel.setText("correo");

        contraseñaLabel1.setText("nueva contraseña");

        jButton2.setText("cambiar foto");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/profile_picture (1).png"))); // NOI18N
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 5, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jButton2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nameLabel)
                            .addComponent(nameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(apellidoLabel)
                            .addComponent(apellidoTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton1)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(correoTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(correoLabel)
                                .addComponent(contraseñaLabel)
                                .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(contraseñaLabel1)
                    .addComponent(jPasswordField2, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(nameLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(nameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(apellidoLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(apellidoTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(correoLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(correoTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(contraseñaLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(contraseñaLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPasswordField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    String nombre = nameTextField.getText().trim();
String apellido = apellidoTextField.getText().trim();
String correo = correoTextField.getText().trim();
String contraseniaActual = jPasswordField1.getText().trim();
String nuevaContrasenia = jPasswordField2.getText().trim();

if (nombre.isEmpty() || apellido.isEmpty() || correo.isEmpty() || contraseniaActual.isEmpty() || nuevaContrasenia.isEmpty()) {
    JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
} else {
    String correoRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
    Pattern pattern = Pattern.compile(correoRegex);
    Matcher matcher = pattern.matcher(correo);

    if (!matcher.matches()) {
        JOptionPane.showMessageDialog(null, "Por favor, ingrese un correo electrónico válido.", "Correo No Válido", JOptionPane.WARNING_MESSAGE);
        return;
    }
     if (correoExistente(correo)) {
            JOptionPane.showMessageDialog(null, "El correo electrónico ya está en uso", "Correo Duplicado", JOptionPane.WARNING_MESSAGE);
            return;
        }
    String contraseniaHasheadaActual = hashPassword(contraseniaActual);

    if (verificarContrasenia(contraseniaHasheadaActual)) {
        String nuevaContraseniaHasheada = hashPassword(nuevaContrasenia);

        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            try (Connection connection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/", "SA", "")) {
                String consultaSQL = "UPDATE PUBLIC.INSTITUTO.PROFESORES SET NOMBRE=?, APELLIDO=?, CORREOELECTRONICO=?, CONTRASENIA=? WHERE CORREOELECTRONICO=?";
                try (PreparedStatement stmt = connection.prepareStatement(consultaSQL)) {
                    stmt.setString(1, nombre);
                    stmt.setString(2, apellido);
                    stmt.setString(3, correo);
                    stmt.setString(4, nuevaContraseniaHasheada); 
                    stmt.setString(5, correos);

                    int filasAfectadas = stmt.executeUpdate();
                    if (filasAfectadas > 0) {
                        int id = obtenerIdProfesor(correo);
                        
                        JOptionPane.showMessageDialog(null, "Los datos se han actualizado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                        if (this.getParent() != null && this.getParent().isEnabled()) {
                            this.getParent().setEnabled(true); 
                        }
                        if (parentPanel != null) {
                            String selectedTable = "profesores";
                            parentPanel.mostrarDatosEnJTable(selectedTable);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Hubo un error al actualizar los datos", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
    } else {
        JOptionPane.showMessageDialog(null, "La contraseña actual es incorrecta", "Error", JOptionPane.ERROR_MESSAGE);
    }
}
    }//GEN-LAST:event_jButton1ActionPerformed
private boolean correoExistente(String correo) {
    try {
        Class.forName("org.hsqldb.jdbc.JDBCDriver");
        try (Connection connection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/", "SA", "")) {
            String consultaSQL = "SELECT COUNT(*) AS cantidad FROM PUBLIC.INSTITUTO.PROFESORES WHERE CORREOELECTRONICO=?";
            try (PreparedStatement stmt = connection.prepareStatement(consultaSQL)) {
                stmt.setString(1, correo);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        int cantidad = rs.getInt("cantidad");
                        return cantidad > 0;
                    }
                }
            }
        }
    } catch (ClassNotFoundException | SQLException ex) {
        ex.printStackTrace();
    }
    return false;
}
    private int obtenerIdProfesor(String correoProfesor) {
    int idProfesor = -1; 

    try {
        Class.forName("org.hsqldb.jdbc.JDBCDriver");
        try (Connection connection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/", "SA", "")) {
            String consultaSQL = "SELECT \"ID profesor\" FROM PUBLIC.INSTITUTO.PROFESORES WHERE CORREOELECTRONICO = ?";
            try (PreparedStatement stmt = connection.prepareStatement(consultaSQL)) {
                stmt.setString(1, correoProfesor);

                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    idProfesor = rs.getInt("ID profesor");
                }
            }
        }
    } catch (ClassNotFoundException | SQLException ex) {
        ex.printStackTrace();
    }

    return idProfesor;
}
 private void seleccionarImagen(int idProfesor) {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    int returnValue = fileChooser.showOpenDialog(this);

    if (returnValue == JFileChooser.APPROVE_OPTION) {
        File file = fileChooser.getSelectedFile();
        String nombreArchivo = idProfesor + ".png"; 
        String rutaDestino = "src/imagenes/" + nombreArchivo; 

        try {
            InputStream inputStream = new FileInputStream(file);
            OutputStream outputStream = new FileOutputStream(rutaDestino);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            outputStream.close();
            inputStream.close();

            rutaImagen = rutaDestino;
            ImageIcon icon = new ImageIcon(rutaImagen);
            Image image = icon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            jLabel6.setIcon(new ImageIcon(image));
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al guardar la imagen: " + e.getMessage());
        }
    }
}
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int id = obtenerIdProfesor(correo);
         seleccionarImagen(id);
    }//GEN-LAST:event_jButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(EditarProfesores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditarProfesores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditarProfesores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditarProfesores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
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
                 Object[] profesorData = {};
                new EditarProfesores(profesorData,null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel apellidoLabel;
    private javax.swing.JTextField apellidoTextField;
    private javax.swing.JLabel contraseñaLabel;
    private javax.swing.JLabel contraseñaLabel1;
    private javax.swing.JLabel correoLabel;
    private javax.swing.JTextField correoTextField;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JPasswordField jPasswordField2;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JTextField nameTextField;
    // End of variables declaration//GEN-END:variables
}
