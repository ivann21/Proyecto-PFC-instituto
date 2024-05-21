/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package instituto.perfil;

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

/**
 *
 * @author ivan.castellano
 */
public class VerPerfil extends javax.swing.JPanel {

    private int idProfesor;
    private String rutaImagen = "";
    public VerPerfil(int idProfesor) {
        this.idProfesor = idProfesor;
        initComponents();
         mostrarDatosProfesor();
    }
  private void mostrarDatosProfesor() {
      try {
        // Conectar a la base de datos
        Connection connection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/", "SA", "");
        Statement statement = connection.createStatement();

        // Consultar los datos del profesor
        String query = "SELECT * FROM instituto.profesores WHERE \"ID profesor\" = " + idProfesor;
        ResultSet resultSet = statement.executeQuery(query);

        if (resultSet.next()) {
            String nombre = resultSet.getString("NOMBRE");
            String apellido = resultSet.getString("APELLIDO");
            String correo = resultSet.getString("CORREOELECTRONICO");
            String rutaImagenGuardada = new File("src/imagenes/" + idProfesor + ".png").getAbsolutePath(); // Ruta absoluta de la imagen guardada

            // Actualizar los JTextField con los datos del profesor
            jTextField1.setText(nombre);
            jTextField2.setText(apellido);
            jTextField3.setText(correo);

            // Cargar la imagen si existe
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
            // Conectar a la base de datos
            Connection connection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/", "SA", "");

            // Crear la consulta de actualización
            String updateQuery = "UPDATE instituto.profesores SET NOMBRE = ?, APELLIDO = ?, CORREOELECTRONICO = ? WHERE \"ID profesor\" = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);

            // Obtener los valores de los JTextField
            String nombre = jTextField1.getText();
            String apellido = jTextField2.getText();
            String correo = jTextField3.getText();

            // Configurar los parámetros de la consulta
            preparedStatement.setString(1, nombre);
            preparedStatement.setString(2, apellido);
            preparedStatement.setString(3, correo);
            preparedStatement.setInt(4, idProfesor);

            // Ejecutar la consulta de actualización
            int rowsAffected = preparedStatement.executeUpdate();

            // Cerrar recursos
            preparedStatement.close();
            connection.close();

            // Mostrar mensaje de confirmación
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
        String nombreArchivo = idProfesor + ".png"; // Usamos el ID del profesor como nombre de archivo
        String rutaDestino = "src/imagenes/" + nombreArchivo; // Carpeta de imágenes en el proyecto

        try {
            // Copiar la imagen al directorio de imágenes del proyecto
            InputStream inputStream = new FileInputStream(file);
            OutputStream outputStream = new FileOutputStream(rutaDestino);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            outputStream.close();
            inputStream.close();

            // Mostrar la imagen en el JLabel
            rutaImagen = rutaDestino; // Guardamos la ruta relativa en lugar de la absoluta
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
        jTextField3 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jTextField2 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel1.add(jTextField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 180, 290, -1));

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("nombre");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 20, -1, -1));
        jPanel1.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 40, 290, -1));

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
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
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 220, -1, -1));
        jPanel1.add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 110, 290, -1));

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("correo electronico");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 150, -1, -1));

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/profile_picture (1).png"))); // NOI18N
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 5, -1, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 35, 130, 130));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 486, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
         guardarCambiosProfesor();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
      seleccionarImagen();
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    // End of variables declaration//GEN-END:variables
}
