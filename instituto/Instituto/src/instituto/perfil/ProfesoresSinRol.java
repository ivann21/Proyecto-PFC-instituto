/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package instituto.perfil;

import instituto.busquedas.añadir.ImageRenderer2;
import instituto.busquedas.añadir.TableReloadListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author icast
 */
public class ProfesoresSinRol extends javax.swing.JFrame {

    private Runnable onCloseCallback;
    private AsignarRoles parentPanel;
    
  public ProfesoresSinRol(AsignarRoles parentPanel, Runnable onCloseCallback) {
         this.onCloseCallback = onCloseCallback;
         this.parentPanel = parentPanel;
        initComponents();
        this.setLocationRelativeTo(null);
        jTable1.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Nombre", "Apellido", "Asignar Rol"}
        ));
        jScrollPane1.setViewportView(jTable1);
        cargarProfesoresSinRol();
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                if (onCloseCallback != null) {
                    onCloseCallback.run();
                }
            }
        });
    }

      private void cargarProfesoresSinRol() {
        DefaultTableModel tableModel = (DefaultTableModel) jTable1.getModel();
        ImageIcon addIcon = new ImageIcon(getClass().getResource("/imagenes/add.png"));
        try {
            // Conectar a la base de datos
            Connection con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/", "SA", "");

            // Consulta SQL para obtener los profesores sin rol asignado
            String query = "SELECT P.NOMBRE, P.APELLIDO FROM INSTITUTO.PROFESORES P WHERE P.\"ID profesor\" NOT IN "
                    + "(SELECT ID_PROFESOR FROM INSTITUTO.ASIGNACION_ROLES)";

            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

           

            while (rs.next()) {
                String nombreProfesor = rs.getString("NOMBRE");
                String apellidoProfesor = rs.getString("APELLIDO");
                tableModel.addRow(new Object[]{nombreProfesor, apellidoProfesor, addIcon});
            }

            // Cerrar la conexión y liberar recursos
            rs.close();
            pst.close();
            con.close();

            // Si no hay profesores sin rol, cierra la ventana
            if (tableModel.getRowCount() == 0) {
                dispose();
                if (parentPanel != null) {
                     parentPanel.cargarAsignaciones();
                 }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Agregar el renderizador de imagen
        int addColumnIndex = tableModel.getColumnCount() - 1; // Icono para la columna "Asignar Rol"
        jTable1.getColumnModel().getColumn(addColumnIndex).setCellRenderer(new ImageRenderer2(addIcon, 20));

        // Agregar el listener de mouse para detectar clics en la columna de imagen
        jTable1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int column = jTable1.getColumnModel().getColumnIndexAtX(e.getX());
                int row = e.getY() / jTable1.getRowHeight();

                if (row < jTable1.getRowCount() && row >= 0 && column < jTable1.getColumnCount() && column >= 0) {
                    Object value = jTable1.getValueAt(row, column);
                    if (value instanceof ImageIcon) {
                        String nombreProfesor = (String) jTable1.getValueAt(row, 0);
                        String apellidoProfesor = (String) jTable1.getValueAt(row, 1);
                        mostrarDialogoAgregarRol(nombreProfesor, apellidoProfesor);
                    }
                }
            }
        });
    }

   private void mostrarDialogoAgregarRol(String nombreProfesor, String apellidoProfesor) {
    // Crear y mostrar el diálogo para seleccionar el rol
    SeleccionarRolDialog2 dialog = new SeleccionarRolDialog2(this, true, nombreProfesor, apellidoProfesor);
    dialog.setLocationRelativeTo(null);
    dialog.setVisible(true);

    // Actualizar la tabla si se añadió un rol
    if (dialog.isRolAgregado()) {
        DefaultTableModel tableModel = (DefaultTableModel) jTable1.getModel();
        tableModel.setRowCount(0); // Limpiar la tabla
        cargarProfesoresSinRol(); // Recargar los profesores sin rol
    }
}
     private void onClose() {
        if (onCloseCallback != null) {
            onCloseCallback.run(); // Llama al método run del callback
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Profesores sin rol");
        setResizable(false);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(ProfesoresSinRol.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ProfesoresSinRol.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ProfesoresSinRol.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ProfesoresSinRol.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ProfesoresSinRol(null,null).setVisible(true);
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
