/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package instituto.perfil;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.sql.*;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;

/**
 *
 * @author ivan.castellano
 */
public class ClasesAsignadas extends javax.swing.JPanel {

    private int idProfesor;
    private Connection connection;
    private HashMap<String, String> columnMapping;
    
    public ClasesAsignadas(int idProfesor) {
         this.idProfesor = idProfesor;
        initComponents();
        cargarHorariosDelProfesor();
         columnMapping = new HashMap<>();
        columnMapping.put("nombre", "NOMBRE");
        columnMapping.put("apellido", "APELLIDO");
        columnMapping.put("correo", "CORREOELECTRONICO");
        columnMapping.put("capacidad", "CAPACIDAD");
        columnMapping.put("año", "ANIO");
        columnMapping.put("año ciclo", "ANIO_CICLO");
        columnMapping.put("nombre ciclo", "NOMBRE_CICLO");
        columnMapping.put("profesor", "PROFESOR");
        columnMapping.put("asignatura","Asignatura");  
        columnMapping.put("aula","Aula");  
        columnMapping.put("dia de la semana","Día de la semana");  
        columnMapping.put("hora de inicio","Hora de inicio");  
        columnMapping.put("hora de fin","Hora de fin");  
       
  jComboBox2.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        String selectedValue = (String) jComboBox2.getSelectedItem();
        txtBuscar.setText(""); // Limpiar el campo de búsqueda
        
        TableRowSorter<DefaultTableModel> sorter = (TableRowSorter<DefaultTableModel>) jTable1.getRowSorter();
        if (sorter != null) {
            sorter.setRowFilter(null);
        }
        if (selectedValue != null) {
            if (selectedValue.equals("dia de la semana")) {
                jComboBox3.setVisible(true);
                jComboBox4.setVisible(false);
                jComboBox5.setVisible(false);
                txtBuscar.setVisible(false);
                jButton4.setEnabled(false);
            } else if (selectedValue.equals("hora de inicio")) {
                jComboBox3.setVisible(false);
                jComboBox4.setVisible(true);
                jComboBox5.setVisible(false);
                txtBuscar.setVisible(false);
                 jButton4.setEnabled(false);
            } else if (selectedValue.equals("hora de fin")) {
                jComboBox3.setVisible(false);
                jComboBox4.setVisible(false);
                jComboBox5.setVisible(true);
                txtBuscar.setVisible(false);
               jButton4.setEnabled(false);
            } else {
                // Para otras opciones, hacer visible txtBuscar y jButton4, y ocultar jComboBox3, jComboBox4, y jComboBox5
                jComboBox3.setVisible(false);
                jComboBox4.setVisible(false);
                jComboBox5.setVisible(false);
                txtBuscar.setVisible(true);
               jButton4.setEnabled(true);
            }
        }
    }
});
  
    }
 private void cargarHorariosDelProfesor() {
    try {
        Connection connection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/", "SA", "");
        String query = "SELECT a.NOMBRE AS Asignatura, au.NOMBRE AS Aula, "
            + "h.\"dia de la semana\", h.\"hora de inicio\", h.\"hora de fin\" "
            + "FROM PUBLIC.INSTITUTO.HORARIOS h "
            + "INNER JOIN PUBLIC.INSTITUTO.ASIGNATURAS a ON h.\"ID asignatura\" = a.\"ID Asignatura\" "
            + "INNER JOIN PUBLIC.INSTITUTO.AULAS au ON h.\"ID aula\" = au.\"ID aula\" "
            + "WHERE h.\"ID profesor\" = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, idProfesor);
        ResultSet resultSet = statement.executeQuery();

        // Crear el modelo de la tabla sin incluir la columna ID horario
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Asignatura");
        model.addColumn("Aula");
        model.addColumn("Día de la semana");
        model.addColumn("Hora de inicio");
        model.addColumn("Hora de fin");

        // Verificar si el resultado de la consulta está vacío
        if (!resultSet.next()) {
            // Mostrar un mensaje indicando que no hay clases asignadas
            JOptionPane.showMessageDialog(this, "No hay clases asignadas para este profesor.");
            
        } else {
            // Iterar sobre los resultados y agregarlos al modelo de la tabla
            do {
                Object[] row = {
                    resultSet.getString("Asignatura"),
                    resultSet.getString("Aula"),
                    resultSet.getString("dia de la semana"),
                    resultSet.getTime("hora de inicio"),
                    resultSet.getTime("hora de fin")
                };
                model.addRow(row);
            } while (resultSet.next());
        }

        jTable1.setModel(model);
    } catch (SQLException ex) {
        // Manejar cualquier error de SQL
        ex.printStackTrace();
    }
}
private void buscarEnTabla(String textoABuscar, String columnaSeleccionada) {
    DefaultTableModel model = (DefaultTableModel) jTable1.getModel(); 
    TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model); // Inicializa el TableRowSorter
    
    jTable1.setRowSorter(sorter); // Asigna el TableRowSorter al jTable1
    
    if (sorter != null) {
        // Si el texto a buscar es nulo o está vacío, eliminar el filtro y mostrar todos los datos
        if (textoABuscar == null || textoABuscar.isEmpty() || textoABuscar.equalsIgnoreCase("seleccione una hora") || textoABuscar.equalsIgnoreCase("seleccione un dia")) {
            sorter.setRowFilter(null);
        }  else {
            // Ignorar mayúsculas y minúsculas en la búsqueda
            String regex = "(?i)" + textoABuscar;
            // Obtener el índice de la columna en el modelo de la tabla
            int columnIndex = jTable1.getColumn(columnaSeleccionada).getModelIndex();
            // Aplicar el filtro de búsqueda en la columna seleccionada
            sorter.setRowFilter(RowFilter.regexFilter(regex, columnIndex));
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        txtBuscar = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jComboBox2 = new javax.swing.JComboBox<>();
        jComboBox3 = new javax.swing.JComboBox<>();
        jComboBox4 = new javax.swing.JComboBox<>();
        jComboBox5 = new javax.swing.JComboBox<>();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 730, -1));

        txtBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarActionPerformed(evt);
            }
        });
        add(txtBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 30, 490, -1));

        jButton4.setText("buscar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 30, 70, -1));

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "asignatura", "aula", "dia de la semana", "hora de inicio", "hora de fin" }));
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });
        add(jComboBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 150, -1));

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione un dia", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes" }));
        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
            }
        });
        add(jComboBox3, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 30, 490, -1));

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione una hora", "8:30:00", "9:30:00", "10:25:00", "11:45:00", "12:40:00", "13:35:00" }));
        jComboBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox4ActionPerformed(evt);
            }
        });
        add(jComboBox4, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 30, 490, -1));

        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione una hora", "09:25:00", "10:20:00", "11:15:00", "12:35:00", "13:30:00", "14:25:00" }));
        jComboBox5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox5ActionPerformed(evt);
            }
        });
        add(jComboBox5, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 30, 490, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed

        String opcionSeleccionada = (String) jComboBox2.getSelectedItem();
        String columnaSeleccionada = columnMapping.get(opcionSeleccionada);
        buscarEnTabla(txtBuscar.getText(),columnaSeleccionada); // Primero realizar la búsqueda en la tabla

    }//GEN-LAST:event_jButton4ActionPerformed

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        String opcionSeleccionada = (String) jComboBox2.getSelectedItem();
        System.out.println("seleccionado" + opcionSeleccionada);
        if (opcionSeleccionada != null) {
            String columnaSeleccionada = columnMapping.get(opcionSeleccionada);
            buscarEnTabla((String) jComboBox3.getSelectedItem(), columnaSeleccionada);
        }
    }//GEN-LAST:event_jComboBox3ActionPerformed

    private void jComboBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox4ActionPerformed
        String opcionSeleccionada = (String) jComboBox2.getSelectedItem();
        System.out.println("seleccionado" + opcionSeleccionada);
        if (opcionSeleccionada != null) {
            String columnaSeleccionada = columnMapping.get(opcionSeleccionada);
            buscarEnTabla((String) jComboBox4.getSelectedItem(), columnaSeleccionada);
        }
    }//GEN-LAST:event_jComboBox4ActionPerformed

    private void jComboBox5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox5ActionPerformed
        String opcionSeleccionada = (String) jComboBox2.getSelectedItem();
        System.out.println("seleccionado" + opcionSeleccionada);
        if (opcionSeleccionada != null) {
            String columnaSeleccionada = columnMapping.get(opcionSeleccionada);
            buscarEnTabla((String) jComboBox5.getSelectedItem(), columnaSeleccionada);
        }
    }//GEN-LAST:event_jComboBox5ActionPerformed

    private void txtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarActionPerformed
        String opcionSeleccionada = (String) jComboBox2.getSelectedItem();
        System.out.println(opcionSeleccionada);
        String columnaSeleccionada = columnMapping.get(opcionSeleccionada);
        buscarEnTabla(txtBuscar.getText(),columnaSeleccionada);
    }//GEN-LAST:event_txtBuscarActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
