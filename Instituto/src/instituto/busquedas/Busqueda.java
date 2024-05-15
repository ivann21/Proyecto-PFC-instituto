/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package instituto.busquedas;

import instituto.Principal;
import instituto.busquedas.añadir.AñadirAlumnos;
import instituto.busquedas.añadir.AñadirAsignaturas;
import instituto.busquedas.añadir.AñadirAulas;
import instituto.busquedas.añadir.AñadirCiclos;
import instituto.busquedas.añadir.AñadirProfesores;
import instituto.busquedas.añadir.CenterRenderer;
import instituto.busquedas.añadir.ImageBooleanRenderer;
import instituto.busquedas.añadir.ImageRenderer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;
/**
 *
 * @author ivan.castellano
 */
public class Busqueda extends javax.swing.JPanel {
    
      private static Principal principalRef;
      private HashMap<String, String> columnMapping;

    public Busqueda(Principal principalRef) {
        initComponents();
        
      columnMapping = new HashMap<>();
        columnMapping.put("nombre", "NOMBRE");
        columnMapping.put("apellido", "APELLIDO");
        columnMapping.put("correo", "CORREOELECTRONICO");
        columnMapping.put("capacidad", "CAPACIDAD");
        columnMapping.put("año", "ANIO");
        columnMapping.put("año ciclo", "ANIO_CICLO");
        columnMapping.put("nombre ciclo", "NOMBRE_CICLO");
        
        
        String selectedTable = "profesores";
        mostrarDatosEnJTable(selectedTable);
     
      jComboBox1.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        String selectedValue = (String) jComboBox1.getSelectedItem();
        // Limpiar el segundo ComboBox
        jComboBox2.removeAllItems();
        txtBuscar.setText(""); // Limpiar el campo de búsqueda
         TableRowSorter<DefaultTableModel> sorter = (TableRowSorter<DefaultTableModel>) jTable1.getRowSorter();
        sorter.setRowFilter(null);
        // Agregar elementos al segundo ComboBox según la selección del primero
        if (selectedValue.equals("profesores")) {
            jComboBox2.addItem("nombre");
            jComboBox2.addItem("apellido");
            jComboBox2.addItem("correo");
        } else if (selectedValue.equals("alumnos")) {
            jComboBox2.addItem("nombre");
            jComboBox2.addItem("apellido");
            jComboBox2.addItem("correo");
            jComboBox2.addItem("nombre ciclo");
            jComboBox2.addItem("año ciclo");
        } else if (selectedValue.equals("asignaturas")) {
            jComboBox2.addItem("nombre");
            jComboBox2.addItem("nombre ciclo");
            jComboBox2.addItem("año ciclo");
        } else if (selectedValue.equals("aulas")) {
            jComboBox2.addItem("nombre");
            jComboBox2.addItem("ubicacion");
            jComboBox2.addItem("capacidad");
            jComboBox2.addItem("pizarra");
            jComboBox2.addItem("ordenadores");
        } else if (selectedValue.equals("ciclos")) {
            jComboBox2.addItem("nombre");
            jComboBox2.addItem("año");
            jComboBox2.addItem("descripcion");
        }
        
        // Mostrar datos en JTable
        mostrarDatosEnJTable(selectedValue);
    }
});
    }
   private void mostrarDatosEnJTable(String selectedTable) {
    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
    model.setRowCount(0); 
    model.setColumnCount(0);
    jTable1.setDefaultEditor(Object.class, null); 
    ImageIcon editIcon = new ImageIcon(getClass().getResource("/imagenes/editar.png"));
    ImageIcon deleteIcon = new ImageIcon(getClass().getResource("/imagenes/delete.png"));
    ImageIcon moreinfoIcon = new ImageIcon(getClass().getResource("/imagenes/informacion.png"));
    try {
        Class.forName("org.hsqldb.jdbc.JDBCDriver");

        try (Connection connection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/", "SA", "")) {
            String consultaSQL = "";
            switch (selectedTable) {
                case "profesores":
                    consultaSQL = "SELECT NOMBRE, APELLIDO, CORREOELECTRONICO FROM PUBLIC.INSTITUTO." + selectedTable;
                    break;
                case "alumnos":
                consultaSQL = "SELECT ALUMNOS.NOMBRE, ALUMNOS.APELLIDO, ALUMNOS.CORREOELECTRONICO, CICLOS.NOMBRE AS NOMBRE_CICLO, CICLOS.ANIO AS ANIO_CICLO " +
              "FROM PUBLIC.INSTITUTO.ALUMNOS " +
              "INNER JOIN PUBLIC.INSTITUTO.CICLOS ON ALUMNOS.\"ID ciclos\" = CICLOS.\"ID Ciclos\"";
                break;
                case "asignaturas":
                   consultaSQL = "SELECT ASIGNATURAS.NOMBRE, CICLOS.NOMBRE AS NOMBRE_CICLO, CICLOS.ANIO AS ANIO_CICLO " +
                                    "FROM PUBLIC.INSTITUTO.ASIGNATURAS " +
                                    "INNER JOIN PUBLIC.INSTITUTO.CICLOS ON ASIGNATURAS.ID_CICLOS = CICLOS.\"ID Ciclos\"";
                    break;
                case "aulas":
                    consultaSQL = "SELECT NOMBRE, UBICACION, CAPACIDAD_AULA AS CAPACIDAD, PIZARRA, ORDENADORES FROM PUBLIC.INSTITUTO." + selectedTable;
                    break;
                case "ciclos":
                    consultaSQL = "SELECT NOMBRE, ANIO AS año FROM PUBLIC.INSTITUTO." + selectedTable;
                    break;
            }
            
            try (PreparedStatement stmt = connection.prepareStatement(consultaSQL)) {
                try (ResultSet rs = stmt.executeQuery()) {
                    ResultSetMetaData metaData = rs.getMetaData();
                    int columnCount = metaData.getColumnCount();
                    
                    // Agregar las columnas obtenidas de la consulta SQL
                    for (int i = 1; i <= columnCount; i++) {
                        String columnName = metaData.getColumnLabel(i);
                        if (!columnName.toLowerCase().startsWith("id")) {
                            model.addColumn(columnName);
                        }
                    }
                    
                  switch (selectedTable) {
                    case "profesores":
                        model.addColumn("EDITAR");
                        model.addColumn("ELIMINAR");
                        break;
                    case "alumnos":
                        model.addColumn("EDITAR");
                        model.addColumn("ELIMINAR");
                         jTable1.getColumnModel().getColumn(2).setPreferredWidth(270); 
                         jTable1.getColumnModel().getColumn(3).setPreferredWidth(300); 
                         jTable1.getColumnModel().getColumn(4).setPreferredWidth(100); 
                         DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
                         centerRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
                         jTable1.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
                        break;
                    case "asignaturas":
                        model.addColumn("DESCRIPCION");
                        model.addColumn("EDITAR");
                        model.addColumn("ELIMINAR");
                        jTable1.getColumnModel().getColumn(0).setPreferredWidth(280); 
                         jTable1.getColumnModel().getColumn(1).setPreferredWidth(270); 
                         jTable1.getColumnModel().getColumn(2).setPreferredWidth(90); 
                         jTable1.getColumnModel().getColumn(3).setPreferredWidth(90); 
                         DefaultTableCellRenderer centerRenderer2 = new DefaultTableCellRenderer();
                         centerRenderer2.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
                         jTable1.getColumnModel().getColumn(2).setCellRenderer(centerRenderer2);
                         centerRenderer2.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
                         jTable1.getColumnModel().getColumn(2).setCellRenderer(centerRenderer2);
                        break;
                    case "aulas":
                        model.addColumn("EDITAR");
                        model.addColumn("ELIMINAR");
                        DefaultTableCellRenderer centerRenderer3 = new DefaultTableCellRenderer();
                         centerRenderer3.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
                         jTable1.getColumnModel().getColumn(2).setCellRenderer(centerRenderer3);
                         centerRenderer3.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
                         jTable1.getColumnModel().getColumn(2).setCellRenderer(centerRenderer3);
                        break;
                    case "ciclos":
                       
                        model.addColumn("DESCRIPCION");
                        model.addColumn("EDITAR");
                        model.addColumn("ELIMINAR");
                        jTable1.getColumnModel().getColumn(0).setPreferredWidth(170); 
                        DefaultTableCellRenderer centerRenderer4 = new DefaultTableCellRenderer();
                         centerRenderer4.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
                         jTable1.getColumnModel().getColumn(1).setCellRenderer(centerRenderer4);
                         centerRenderer4.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
                         jTable1.getColumnModel().getColumn(1).setCellRenderer(centerRenderer4);
                        break;
                }

                // Establecer el renderizador personalizado para las columnas "editar" y "eliminar"
                if (selectedTable.equals("profesores") || selectedTable.equals("alumnos") || selectedTable.equals("asignaturas") || selectedTable.equals("aulas") || selectedTable.equals("ciclos")) {
                    int editColumnIndex = model.getColumnCount() - 2; // Índice de la columna "editar"
                    int deleteColumnIndex = model.getColumnCount() - 1; // Índice de la columna "eliminar"
                    jTable1.getColumnModel().getColumn(editColumnIndex).setCellRenderer(new ImageRenderer(editIcon,20));
                    jTable1.getColumnModel().getColumn(deleteColumnIndex).setCellRenderer(new ImageRenderer(deleteIcon,20));
                }
               // Establecer el TableCellRenderer personalizado para la columna de descripción
               if (selectedTable.equals("asignaturas")) {
                int descriptionColumnIndex = model.getColumnCount() - 3; // Índice de la columna de descripción
                jTable1.getColumnModel().getColumn(descriptionColumnIndex).setCellRenderer(new ImageRenderer(moreinfoIcon, 20)); // Usar el icono correspondiente y el tamaño de imagen: 20
            } else if (selectedTable.equals("ciclos")) {
                int descriptionColumnIndex = model.getColumnCount() - 3; // Índice de la columna de descripción
                jTable1.getColumnModel().getColumn(descriptionColumnIndex).setCellRenderer(new ImageRenderer(moreinfoIcon, 20)); // Usar el icono correspondiente y el tamaño de imagen: 20
            }else if (selectedTable.equals("aulas")) {
            // Crear una instancia del ImageRenderer para la columna de PIZARRA
            ImageBooleanRenderer pizarraRenderer = new ImageBooleanRenderer(new ImageIcon(getClass().getResource("/imagenes/pizarra_true.png")), new ImageIcon(getClass().getResource("/imagenes/pizarra_false.png")), 20); // Ajusta el tamaño según necesites
            jTable1.getColumnModel().getColumn(3).setCellRenderer(pizarraRenderer);

            // Crear una instancia del ImageRenderer para la columna de ORDENADORES
            ImageBooleanRenderer ordenadoresRenderer = new ImageBooleanRenderer(new ImageIcon(getClass().getResource("/imagenes/ordenadores_true.png")), new ImageIcon(getClass().getResource("/imagenes/ordenadores_false.png")), 20); // Ajusta el tamaño según necesites
            jTable1.getColumnModel().getColumn(4).setCellRenderer(ordenadoresRenderer);
            }
                jTable1.setRowHeight(30); // Establecer el tamaño de fila personalizado
                    while (rs.next()) {
                        Object[] rowData = new Object[columnCount];
                        for (int i = 0; i < columnCount; i++) {
                            rowData[i] = rs.getObject(i + 1);
                        }
                        model.addRow(rowData);
                    }
                }
            }
        }
    } catch (ClassNotFoundException | SQLException e) {
        e.printStackTrace();
    }
    jTable1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int column = jTable1.getColumnModel().getColumnIndexAtX(e.getX()); // Obtener el índice de la columna clicada
                int row = e.getY() / jTable1.getRowHeight(); // Obtener el índice de la fila clicada

                if (row < jTable1.getRowCount() && row >= 0 && column < jTable1.getColumnCount() && column >= 0) {
                    Object value = jTable1.getValueAt(row, column); // Obtener el valor de la celda clicada

                    // Verificar si la columna es "Editar" o "Eliminar" y abrir el JFrame correspondiente
                    if (value instanceof ImageIcon) {
                        if (value.equals(editIcon)) {
                            
                        } else if (value.equals(deleteIcon)) {
                            // Abrir el JFrame de eliminación
                           mostrarDialogoEliminar(row);
                        }
                    }
                }
            }
        });
}
  private void mostrarDialogoEliminar(int row) {
    int opcion = JOptionPane.showConfirmDialog(null, "¿Estás seguro de que deseas eliminar esta fila?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
    if (opcion == JOptionPane.YES_OPTION) {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.removeRow(row);
        // Aquí puedes añadir la lógica adicional para eliminar la fila de la base de datos si es necesario
    }
} 
private void buscarEnTabla(String textoABuscar, String columnaSeleccionada) {
    DefaultTableModel model = (DefaultTableModel) jTable1.getModel(); // Obtener el modelo de la tabla
    TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
    jTable1.setRowSorter(sorter);
    
    // Si el texto a buscar es nulo o está vacío, eliminar el filtro y mostrar todos los datos
    if (textoABuscar == null || textoABuscar.isEmpty()) {
        sorter.setRowFilter(null);
    } else {
        // Crear la expresión regular para buscar en la columna seleccionada
        String regex = "(?i)" + textoABuscar; // Ignorar mayúsculas y minúsculas
        int columnIndex = jTable1.getColumnModel().getColumnIndex(columnaSeleccionada);
        sorter.setRowFilter(RowFilter.regexFilter(regex, columnIndex)); // Aplicar el filtro de búsqueda en la columna seleccionada
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

        addButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        txtBuscar = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jComboBox2 = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(1372, 627));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        addButton.setText("añadir");
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });
        add(addButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 510, -1, -1));

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

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 850, -1));

        txtBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarActionPerformed(evt);
            }
        });
        add(txtBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 30, 530, -1));

        jButton4.setText("buscar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 30, -1, -1));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "profesores", "alumnos", "asignaturas", "aulas", "ciclos" }));
        add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 100, -1));

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "nombre", "apellido", "correo" }));
        add(jComboBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 30, 110, -1));

        jButton1.setText("reiniciar tabla");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 510, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void txtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
    
    String opcionSeleccionada = (String) jComboBox2.getSelectedItem();
    String columnaSeleccionada = columnMapping.get(opcionSeleccionada);
      buscarEnTabla(txtBuscar.getText(),columnaSeleccionada); // Primero realizar la búsqueda en la tabla
   
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
     String selectedTable = (String) jComboBox1.getSelectedItem();
        mostrarDatosEnJTable(selectedTable);
        txtBuscar.setText(""); // Limpiar el campo de búsqueda
    TableRowSorter<DefaultTableModel> sorter = (TableRowSorter<DefaultTableModel>) jTable1.getRowSorter();
    sorter.setRowFilter(null);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
         String selectedTable = (String) jComboBox1.getSelectedItem(); 
         
        switch(selectedTable) {
            case "profesores":
               AñadirProfesores ap = new AñadirProfesores();
               ap.setVisible(true);
               this.setEnabled(false);
               mostrarDatosEnJTable(selectedTable);
                break;
            case "alumnos":
               AñadirAlumnos aa = new AñadirAlumnos();
               aa.setVisible(true);
               this.setEnabled(false);
               mostrarDatosEnJTable(selectedTable);
                break;
            case "asignaturas":
               AñadirAsignaturas aas = new AñadirAsignaturas();
               aas.setVisible(true);
               this.setEnabled(false);
               mostrarDatosEnJTable(selectedTable);
                break;
            case "aulas":
               AñadirAulas aau = new AñadirAulas();
               aau.setVisible(true);
               this.setEnabled(false);
               mostrarDatosEnJTable(selectedTable);
                break;
            case "ciclos":
                AñadirCiclos ac = new AñadirCiclos();
                 ac.setVisible(true);
               this.setEnabled(false);
               mostrarDatosEnJTable(selectedTable);
                break;
            default:
              
                break;
        }
    }//GEN-LAST:event_addButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
