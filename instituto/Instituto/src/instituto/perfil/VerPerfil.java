/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package instituto.perfil;

import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.*;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *
 * @author ivan.castellano
 */
public class VerPerfil extends javax.swing.JPanel {

    private int idProfesor;
    private String rutaImagen = "";
      private static Connection connection = null;
    private static Statement statement = null;
    private static ResultSet resultSet = null;
    
    public VerPerfil(int idProfesor) {
        this.idProfesor = idProfesor;
        initComponents();
         mostrarDatosProfesor();
         connectToDatabase();
        String rol = obtenerNombreRolProfesor(idProfesor);
        jTextField4.setText(rol);
}
private static void connectToDatabase() {
        try {
            connection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/", "SA", "");
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
private String obtenerNombreRolProfesor(int idProfesor) {
    try {
        String query = "SELECT r.NOMBRE_ROL " +
                       "FROM PUBLIC.INSTITUTO.ASIGNACION_ROLES ar " +
                       "INNER JOIN PUBLIC.INSTITUTO.ROLES r ON ar.ID_ROL = r.ID_ROL " +
                       "WHERE ar.ID_PROFESOR = " + idProfesor;
        resultSet = statement.executeQuery(query);
        if (resultSet.next()) {
            return resultSet.getString("NOMBRE_ROL");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null; 
}
  private void mostrarDatosProfesor() {
      try {
        Connection connection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/", "SA", "");
        Statement statement = connection.createStatement();

        String query = "SELECT * FROM instituto.profesores WHERE \"ID profesor\" = " + idProfesor;
        ResultSet resultSet = statement.executeQuery(query);

        if (resultSet.next()) {
            String nombre = resultSet.getString("NOMBRE");
            String apellido = resultSet.getString("APELLIDO");
            String correo = resultSet.getString("CORREOELECTRONICO");
            String rutaImagenGuardada = new File("src/imagenes/" + idProfesor + ".png").getAbsolutePath();

            jTextField1.setText(nombre);
            jTextField2.setText(apellido);
            jTextField3.setText(correo);

            File imagenGuardada = new File(rutaImagenGuardada);
            if (imagenGuardada.exists()) {
                rutaImagen = rutaImagenGuardada;
                ImageIcon icon = new ImageIcon(rutaImagen);
                Image image = icon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
                jLabel5.setIcon(new ImageIcon(image));
            }
        }

        resultSet.close();
        statement.close();
        connection.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
  
       private void guardarCambiosProfesor() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/", "SA", "");

            String updateQuery = "UPDATE instituto.profesores SET NOMBRE = ?, APELLIDO = ?, CORREOELECTRONICO = ? WHERE \"ID profesor\" = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);

            String nombre = jTextField1.getText();
            String apellido = jTextField2.getText();
            String correo = jTextField3.getText();

            preparedStatement.setString(1, nombre);
            preparedStatement.setString(2, apellido);
            preparedStatement.setString(3, correo);
            preparedStatement.setInt(4, idProfesor);

            int rowsAffected = preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Los cambios han sido guardados exitosamente.");
            } else {
                JOptionPane.showMessageDialog(null, "No se han realizado cambios.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al guardar los cambios: " + e.getMessage());
        }
    }
 private void seleccionarImagen() {
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
            jLabel5.setIcon(new ImageIcon(image));
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al guardar la imagen: " + e.getMessage());
        }
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(232, 228, 236));
        jPanel1.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(102, 102, 102)));
        jPanel1.setForeground(new java.awt.Color(232, 228, 236));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setForeground(new java.awt.Color(102, 102, 102));
        jLabel1.setText("nombre");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 20, -1, -1));

        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setText("apellidos");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 80, -1, -1));

        jButton1.setText("añadir foto");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(46, 180, -1, -1));

        jButton2.setText("guardar cambios");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 270, -1, -1));

        jLabel3.setForeground(new java.awt.Color(102, 102, 102));
        jLabel3.setText("correo electronico");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 150, -1, -1));

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/profile_picture (1).png"))); // NOI18N
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 5, -1, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 35, 130, 130));

        jLabel4.setForeground(new java.awt.Color(102, 102, 102));
        jLabel4.setText("rol");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 220, -1, -1));

        jTextField4.setForeground(new java.awt.Color(102, 102, 102));
        jTextField4.setEnabled(false);
        jPanel1.add(jTextField4, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 240, 100, 25));

        jTextField1.setForeground(new java.awt.Color(102, 102, 102));
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jPanel1.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 50, 290, 25));

        jTextField2.setForeground(new java.awt.Color(102, 102, 102));
        jPanel1.add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 110, 290, 25));

        jTextField3.setForeground(new java.awt.Color(102, 102, 102));
        jPanel1.add(jTextField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 180, 290, 25));

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 491, 314));
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
         guardarCambiosProfesor();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        seleccionarImagen();
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    // End of variables declaration//GEN-END:variables
}
