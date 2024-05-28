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
import instituto.busquedas.añadir.AñadirHorarios;
import instituto.busquedas.añadir.AñadirProfesores;
import instituto.busquedas.añadir.CenterRenderer;
import instituto.busquedas.añadir.EditarAlumnos;
import instituto.busquedas.añadir.EditarAsignaturas;
import instituto.busquedas.añadir.EditarAulas;
import instituto.busquedas.añadir.EditarCiclos;
import instituto.busquedas.añadir.EditarHorarios;
import instituto.busquedas.añadir.EditarProfesores;
import instituto.busquedas.añadir.ImageBooleanRenderer;
import instituto.busquedas.añadir.ImageRenderer;
import instituto.busquedas.añadir.ImageRenderer2;
import instituto.busquedas.añadir.ImageRenderer3;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
/**
 *
 * @author ivan.castellano
 */
public class Busqueda extends javax.swing.JPanel {
    
    private HashMap<String, String> columnMapping;
    private int rol;

    public Busqueda(int Rol) {
        this.rol = Rol;
        initComponents();
         jComboBox3.setVisible(false);
         jComboBox4.setVisible(false);
         jComboBox5.setVisible(false);
         jComboBox6.setVisible(false);
         jComboBox7.setVisible(false);
        if (Rol != 0) {
         jComboBox1.addItem("horarios");
        
        }else{
         addButton.setEnabled(false);
        }
      columnMapping = new HashMap<>();
        columnMapping.put("nombre", "NOMBRE");
        columnMapping.put("apellido", "APELLIDO");
        columnMapping.put("correo", "CORREOELECTRONICO");
        columnMapping.put("capacidad", "CAPACIDAD");
        columnMapping.put("año", "ANIO");
        columnMapping.put("año ciclo", "ANIO_CICLO");
        columnMapping.put("nombre ciclo", "NOMBRE_CICLO");
        columnMapping.put("profesor", "PROFESOR");
        columnMapping.put("asignatura","ASIGNATURA");  
        columnMapping.put("aula","AULA");  
        columnMapping.put("dia de la semana","dia de la semana");  
        columnMapping.put("hora de inicio","hora de inicio");  
        columnMapping.put("hora de fin","hora de fin");  
        columnMapping.put("pizarra","PIZARRA");  
        columnMapping.put("ordenadores","ORDENADORES");   
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
        if (sorter != null) {
            sorter.setRowFilter(null);
        }
        // Agregar elementos  al segundo ComboBox según la selección del primero
     if (selectedValue.equals("profesores")) {
            jComboBox2.addItem("nombre");
            jComboBox2.addItem("apellido");
            jComboBox2.addItem("correo");
            // Desactivar los JComboBox adicionales si estaban activados
            jComboBox3.setVisible(false);
            jComboBox4.setVisible(false);
            jComboBox5.setVisible(false);
            jComboBox6.setVisible(false);
            jComboBox7.setVisible(false);
             jButton4.setEnabled(true);
        } else if (selectedValue.equals("alumnos")) {
            jComboBox2.addItem("nombre");
            jComboBox2.addItem("apellido");
            jComboBox2.addItem("correo");
            jComboBox2.addItem("nombre ciclo");
            jComboBox2.addItem("año ciclo");
            // Desactivar los JComboBox adicionales si estaban activados
            jComboBox3.setVisible(false);
            jComboBox4.setVisible(false);
            jComboBox5.setVisible(false);
            jComboBox6.setVisible(false);
            jComboBox7.setVisible(false);
             jButton4.setEnabled(true);
        } else if (selectedValue.equals("asignaturas")) {
            jComboBox2.addItem("nombre");
            jComboBox2.addItem("nombre ciclo");
            jComboBox2.addItem("año ciclo");
            // Desactivar los JComboBox adicionales si estaban activados
            jComboBox3.setVisible(false);
            jComboBox4.setVisible(false);
            jComboBox5.setVisible(false);
            jComboBox6.setVisible(false);
            jComboBox7.setVisible(false);
             jButton4.setEnabled(true);
        } else if (selectedValue.equals("aulas")) {
            jComboBox2.addItem("nombre");
            jComboBox2.addItem("ubicacion");
            jComboBox2.addItem("capacidad");
            jComboBox2.addItem("pizarra");
            jComboBox2.addItem("ordenadores");
            // Desactivar los JComboBox adicionales si estaban activados
            jComboBox3.setVisible(false);
            jComboBox4.setVisible(false);
            jComboBox5.setVisible(false);
            jComboBox6.setVisible(false);
            jComboBox7.setVisible(false);
             jButton4.setEnabled(true);
        } else if (selectedValue.equals("ciclos")) {
            jComboBox2.addItem("nombre");
            jComboBox2.addItem("año");
            // Desactivar los JComboBox adicionales si estaban activados
            jComboBox3.setVisible(false);
            jComboBox4.setVisible(false);
            jComboBox5.setVisible(false);
            jComboBox6.setVisible(false);
            jComboBox7.setVisible(false);
            jButton4.setEnabled(true);
        } else if (selectedValue.equals("horarios")) {
           jComboBox2.addItem("asignatura");
            jComboBox2.addItem("profesor");
            jComboBox2.addItem("aula");
            jComboBox2.addItem("dia de la semana");
            jComboBox2.addItem("hora de inicio");
            jComboBox2.addItem("hora de fin");
            jComboBox3.setVisible(false);
            jComboBox4.setVisible(false);
            jComboBox5.setVisible(false);
            jComboBox6.setVisible(false);
            jComboBox7.setVisible(false);
            jButton4.setEnabled(true);
        }
        
        // Mostrar datos en JTable
        mostrarDatosEnJTable(selectedValue);
    }
});
  jComboBox2.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        String selectedValue = (String) jComboBox2.getSelectedItem();
        if (selectedValue != null) {
            if (selectedValue.equals("dia de la semana")) {
                jComboBox3.setVisible(true);
                jComboBox4.setVisible(false);
                jComboBox5.setVisible(false);
                txtBuscar.setVisible(false);
                jComboBox6.setVisible(false);
                jComboBox7.setVisible(false);
                jButton4.setEnabled(false);
            } else if (selectedValue.equals("hora de inicio")) {
                jComboBox3.setVisible(false);
                jComboBox4.setVisible(true);
                jComboBox5.setVisible(false);
                jComboBox6.setVisible(false);
                jComboBox7.setVisible(false);
                txtBuscar.setVisible(false);
                 jButton4.setEnabled(false);
            } else if (selectedValue.equals("hora de fin")) {
                jComboBox3.setVisible(false);
                jComboBox4.setVisible(false);
                jComboBox5.setVisible(true);
                jComboBox6.setVisible(false);
                jComboBox7.setVisible(false);
                txtBuscar.setVisible(false);
               jButton4.setEnabled(false);
            } else if (selectedValue.equals("pizarra")) {
                jComboBox3.setVisible(false);
                jComboBox4.setVisible(false);
                jComboBox5.setVisible(false);
                jComboBox6.setVisible(true);
                jComboBox7.setVisible(false);
                txtBuscar.setVisible(false);
               jButton4.setEnabled(false);
            } else if (selectedValue.equals("ordenadores")) {
                jComboBox3.setVisible(false);
                jComboBox4.setVisible(false);
                jComboBox5.setVisible(false);
                jComboBox6.setVisible(false);
                jComboBox7.setVisible(true);
                txtBuscar.setVisible(false);
                jButton4.setEnabled(false);
            } else {
                // Para otras opciones, hacer visible txtBuscar y jButton4, y ocultar jComboBox3, jComboBox4, y jComboBox5
                jComboBox3.setVisible(false);
                jComboBox4.setVisible(false);
                jComboBox5.setVisible(false);
                jComboBox6.setVisible(false);
                jComboBox7.setVisible(false);
                txtBuscar.setVisible(true);
                jButton4.setEnabled(true);
            }
        }
    }
});
    }

public void mostrarDatosEnJTable(String selectedTable) {
    int contador = 0;
    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
    jTable1.setRowSorter(new TableRowSorter<>(model));
    model.setRowCount(0);
    model.setColumnCount(0);
    jTable1.setDefaultEditor(Object.class, null);
    ImageIcon moreinfoIcon = new ImageIcon(getClass().getResource("/imagenes/informacion.png"));
    try {
        Class.forName("org.hsqldb.jdbc.JDBCDriver");

        try (Connection connection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/", "SA", "")) {
            String consultaSQL = getSQLQueryForTable(selectedTable);

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

                    configureTableColumns(selectedTable, model);

                    jTable1.setRowHeight(30); // Establecer el tamaño de fila personalizado

                    while (rs.next()) {
                        Object[] rowData = new Object[columnCount];
                        for (int i = 0; i < columnCount; i++) {
                            rowData[i] = rs.getObject(i + 1);
                        }
                        contador++; // Incrementar el contador
                        model.addRow(rowData);
                    }
                }
            }
        }
    } catch (ClassNotFoundException | SQLException e) {
        e.printStackTrace();
    }
        addTableMouseListener(selectedTable);
}

private String getSQLQueryForTable(String table) {
    switch (table) {
        case "profesores":
            return "SELECT NOMBRE, APELLIDO, CORREOELECTRONICO FROM PUBLIC.INSTITUTO." + table;
        case "alumnos":
            return "SELECT ALUMNOS.NOMBRE, ALUMNOS.APELLIDO, ALUMNOS.CORREOELECTRONICO, CICLOS.NOMBRE AS NOMBRE_CICLO, CICLOS.ANIO AS ANIO_CICLO " +
                   "FROM PUBLIC.INSTITUTO.ALUMNOS " +
                   "INNER JOIN PUBLIC.INSTITUTO.CICLOS ON ALUMNOS.\"ID ciclos\" = CICLOS.\"ID Ciclos\"";
       case "asignaturas":
            return "SELECT " +
                       "ASIGNATURAS.NOMBRE, " +
                       "'-' AS descripcion, " + 
                       "CICLOS.NOMBRE AS NOMBRE_CICLO, " +
                       "CICLOS.ANIO AS ANIO_CICLO " +
                   "FROM " +
                       "PUBLIC.INSTITUTO.ASIGNATURAS " +
                       "INNER JOIN PUBLIC.INSTITUTO.CICLOS " +
                       "ON ASIGNATURAS.ID_CICLOS = CICLOS.\"ID Ciclos\"";
        case "aulas":
            return "SELECT NOMBRE, UBICACION, CAPACIDAD_AULA AS CAPACIDAD, PIZARRA, ORDENADORES FROM PUBLIC.INSTITUTO." + table;
        case "ciclos":
            return "SELECT NOMBRE, ANIO AS año FROM PUBLIC.INSTITUTO." + table;
        case "horarios":
            return "SELECT HORARIOS.\"dia de la semana\", HORARIOS.\"hora de inicio\", HORARIOS.\"hora de fin\", " +
                   "ASIGNATURAS.NOMBRE AS ASIGNATURA, PROFESORES.NOMBRE AS PROFESOR, AULAS.NOMBRE AS AULA " +
                   "FROM PUBLIC.INSTITUTO.HORARIOS " +
                   "INNER JOIN PUBLIC.INSTITUTO.ASIGNATURAS ON HORARIOS.\"ID asignatura\" = ASIGNATURAS.\"ID Asignatura\" " +
                   "INNER JOIN PUBLIC.INSTITUTO.PROFESORES ON HORARIOS.\"ID profesor\" = PROFESORES.\"ID profesor\" " +
                   "INNER JOIN PUBLIC.INSTITUTO.AULAS ON HORARIOS.\"ID aula\" = AULAS.\"ID aula\"";
        default:
            return "";
    }
}

private void configureTableColumns(String table, DefaultTableModel model) {
    ImageIcon editIcon = new ImageIcon(getClass().getResource("/imagenes/editar.png"));
    ImageIcon deleteIcon = new ImageIcon(getClass().getResource("/imagenes/delete.png"));
    ImageIcon descriptionIcon = new ImageIcon(getClass().getResource("/imagenes/informacion.png"));
    switch (table) {
        case "alumnos":
            if (rol != 0) {
                model.addColumn("EDITAR");
                model.addColumn("ELIMINAR");
                setColumnWidths(jTable1, new int[]{150, 150, 270, 350, 100, 80, 80});
                setColumnCenterAlignment(jTable1, new int[]{4, 5});
            } else {
                setColumnWidths(jTable1, new int[]{150, 150, 270, 350,100});
                setColumnCenterAlignment(jTable1, new int[]{4});
            }
            break;
        case "asignaturas":
            if (rol != 0) {
                model.addColumn("EDITAR");
                model.addColumn("ELIMINAR");
                setColumnWidths(jTable1, new int[]{280,80, 270, 90, 90, 80, 80});
                setColumnCenterAlignment(jTable1, new int[]{3, 4});
                int descriptionColumnIndex = model.getColumnCount() - 5;
                jTable1.getColumnModel().getColumn(descriptionColumnIndex).setCellRenderer(new ImageRenderer3(descriptionIcon, 20));
            } else {
                setColumnWidths(jTable1, new int[]{150, 80, 280, 90});
                setColumnCenterAlignment(jTable1, new int[]{2,3});
                int descriptionColumnIndex = model.getColumnCount() - 3;
            jTable1.getColumnModel().getColumn(descriptionColumnIndex).setCellRenderer(new ImageRenderer3(descriptionIcon, 20));
            }
            
            break;
        case "aulas":
            if (rol != 0) {
                model.addColumn("EDITAR");
                model.addColumn("ELIMINAR");
                setColumnWidths(jTable1, new int[]{150, 150, 150, 80, 80, 80, 80});
                setColumnCenterAlignment(jTable1, new int[]{2, 3});
            } else {
                setColumnWidths(jTable1, new int[]{150, 150, 150});
                setColumnCenterAlignment(jTable1, new int[]{2});
            }
            configureAulasTableColumns();
            break;
        case "ciclos":
            if (rol != 0) {
                model.addColumn("DESCRIPCIÓN");
                model.addColumn("EDITAR");
                model.addColumn("ELIMINAR");
                setColumnWidths(jTable1, new int[]{170, 150, 80, 80, 80});
                setColumnCenterAlignment(jTable1, new int[]{1, 2});
                int descriptionColumnIndex = model.getColumnCount() - 3;
                jTable1.getColumnModel().getColumn(descriptionColumnIndex).setCellRenderer(new ImageRenderer(descriptionIcon, 20));
            } else {
                model.addColumn("DESCRIPCIÓN");
                setColumnWidths(jTable1, new int[]{170, 80, 80});
                setColumnCenterAlignment(jTable1, new int[]{1});
                int descriptionColumnIndex = model.getColumnCount() - 1;
                jTable1.getColumnModel().getColumn(descriptionColumnIndex).setCellRenderer(new ImageRenderer(descriptionIcon, 20));
            }
           
            break;
        case "horarios":
            if (rol != 0) {
                model.addColumn("EDITAR");
                model.addColumn("ELIMINAR");
                setColumnWidths(jTable1, new int[]{150, 150, 150, 400, 150, 150, 80, 80});
                setColumnCenterAlignment(jTable1, new int[]{0, 1, 2,3,4,5});
            } else {
                setColumnWidths(jTable1, new int[]{150, 150, 150, 400});
                setColumnCenterAlignment(jTable1, new int[]{2});
            }
            break;
        case "profesores":
        default:
            if (rol != 0) {
                model.addColumn("EDITAR");
                model.addColumn("ELIMINAR");
                setColumnWidths(jTable1, new int[]{150, 150});
                setColumnCenterAlignment(jTable1, new int[]{1});
            } else {
                setColumnWidths(jTable1, new int[]{100});
                setColumnCenterAlignment(jTable1, new int[]{0,1,2});
            }
            break;
    }
    // Establecer el renderizador personalizado para las columnas "editar" y "eliminar"
    if (isEditableAndDeletable(table) && rol !=0) {
        int editColumnIndex = model.getColumnCount() - 2; // Índice de la columna "editar"
        int deleteColumnIndex = model.getColumnCount() - 1; // Índice de la columna "eliminar"
        jTable1.getColumnModel().getColumn(editColumnIndex).setCellRenderer(new ImageRenderer(editIcon, 20));
        jTable1.getColumnModel().getColumn(deleteColumnIndex).setCellRenderer(new ImageRenderer(deleteIcon, 20));
    }
}

private void setColumnWidths(JTable table, int[] widths) {
    for (int i = 0; i < widths.length; i++) {
        if (i < table.getColumnModel().getColumnCount()) {
            table.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);
        }
    }
}

private void setColumnCenterAlignment(JTable table, int[] columns) {
    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    centerRenderer.setHorizontalAlignment(JLabel.CENTER);
    for (int column : columns) {
        if (column < table.getColumnModel().getColumnCount()) {
            table.getColumnModel().getColumn(column).setCellRenderer(centerRenderer);
        }
    }
}

private void configureAulasTableColumns() {

        ImageBooleanRenderer pizarraRenderer = new ImageBooleanRenderer(
            new ImageIcon(getClass().getResource("/imagenes/pizarra_true.png")),
            new ImageIcon(getClass().getResource("/imagenes/pizarra_false.png")),
            20
        );
        jTable1.getColumnModel().getColumn(3).setCellRenderer(pizarraRenderer);

        ImageBooleanRenderer ordenadoresRenderer = new ImageBooleanRenderer(
            new ImageIcon(getClass().getResource("/imagenes/ordenadores_true.png")),
            new ImageIcon(getClass().getResource("/imagenes/ordenadores_false.png")),
            20
        );
        jTable1.getColumnModel().getColumn(4).setCellRenderer(ordenadoresRenderer);
}

private boolean isEditableAndDeletable(String table) {
    return table.equals("profesores") || table.equals("alumnos") || table.equals("asignaturas") || table.equals("aulas") || table.equals("ciclos") || table.equals("horarios");
}

private void addTableMouseListener(String selectedTable) {
    // Eliminar todos los MouseListeners existentes
    for (MouseListener listener : jTable1.getMouseListeners()) {
        jTable1.removeMouseListener(listener);
    }
    
    // Añadir el nuevo MouseListener
    jTable1.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            int column = jTable1.getColumnModel().getColumnIndexAtX(e.getX()); // Obtener el índice de la columna clicada
            int row = e.getY() / jTable1.getRowHeight(); // Obtener el índice de la fila clicada

            if (row < jTable1.getRowCount() && row >= 0 && column < jTable1.getColumnCount() && column >= 0) {
                if (rol != 0) { // Si el usuario tiene el rol diferente de 0
                    if (column == jTable1.getColumnCount() - 2) {
                        System.out.println("Edit icon clicked");
                        System.out.println(row);
                        abrirEditarFrame(selectedTable, row); 
                    } else if (column == jTable1.getColumnCount() - 1) { 
                        System.out.println("Delete icon clicked");
                        System.out.println(row);
                        int opcion = JOptionPane.showConfirmDialog(null, "¿Estás seguro de que deseas eliminar esta fila?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
                        if (opcion == JOptionPane.YES_OPTION) {
                            eliminarFila(selectedTable, row);
                        } else if (opcion == JOptionPane.NO_OPTION) {
                            System.out.println("Operación de eliminación cancelada.");
                        }
                    } else if (column == jTable1.getColumnCount() - 5 && selectedTable.equals("asignaturas")) { // Columna de descripción para la tabla de asignaturas
                        System.out.println("Description icon clicked");
                        abrirPanelDescripcion(selectedTable, row);
                    } else if (column == jTable1.getColumnCount() - 3 && selectedTable.equals("ciclos")) { // Columna de descripción para la tabla de ciclos
                        System.out.println("Description icon clicked");
                        abrirPanelDescripcion(selectedTable, row);
                    }
                } else {
                    if (column == jTable1.getColumnCount() - 3 && selectedTable.equals("asignaturas")) { // Columna de descripción para la tabla de asignaturas
                        System.out.println("Description icon clicked");
                        abrirPanelDescripcion(selectedTable, row);
                    }else if (column == jTable1.getColumnCount() - 1 && selectedTable.equals("ciclos")) { // Columna de descripción para la tabla de ciclos
                        System.out.println("Description icon clicked");
                        abrirPanelDescripcion(selectedTable, row);
                    }
                }
            }
        }
    });
}

private void abrirPanelDescripcion(String selectedTable, int row) {
    String descripcion = "";
    int id = -1;
    
    // Obtener el ID de la asignatura o ciclo
    try {
        if (selectedTable.equals("ciclos")) {
            String nombreCiclo = (String) jTable1.getValueAt(row, 0);
             int anioCiclo = (int) jTable1.getValueAt(row, 1);
            id = buscarIDCiclo(nombreCiclo,anioCiclo);
        } else if (selectedTable.equals("asignaturas")) {
            String nombreAsignatura = (String) jTable1.getValueAt(row, 0);
            String nombreCiclo = (String) jTable1.getValueAt(row, 2);
            int anioCiclo =  (int) jTable1.getValueAt(row, 3);
            id = buscarIDAsignatura(nombreAsignatura,nombreCiclo,anioCiclo);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
    // Realizar la consulta para obtener la descripción
    if (id != -1) {
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");

            try (Connection connection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/", "SA", "")) {
                String consultaSQL = "";
                if (selectedTable.equals("ciclos")) {
                    consultaSQL = "SELECT DESCRIPCION FROM PUBLIC.INSTITUTO.CICLOS WHERE \"ID Ciclos\" = ?";
                } else if (selectedTable.equals("asignaturas")) {
                    consultaSQL = "SELECT DESCRIPCION FROM PUBLIC.INSTITUTO.ASIGNATURAS WHERE \"ID Asignatura\" = ?";
                }

                try (PreparedStatement stmt = connection.prepareStatement(consultaSQL)) {
                    stmt.setInt(1, id);
                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next()) {
                            descripcion = rs.getString("DESCRIPCION");
                        }
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Mostrar la descripción en un cuadro de diálogo
    if (!descripcion.isEmpty()) {
        JOptionPane.showMessageDialog(null, descripcion, "Descripción", JOptionPane.INFORMATION_MESSAGE);
    } else {
        JOptionPane.showMessageDialog(null, "No se encontró descripción para esta entrada.", "Descripción", JOptionPane.INFORMATION_MESSAGE);
    }
}
private void abrirEditarFrame(String selectedTable, int row) {
    switch (selectedTable) {
        case "profesores":
            EditarProfesores editarProfesores = new EditarProfesores(getProfesorData(row),this);
            editarProfesores.setLocationRelativeTo(null);
            editarProfesores.setVisible(true);
            break;
        case "alumnos":
            EditarAlumnos editarAlumnos = new EditarAlumnos(getAlumnoData(row),this);
            editarAlumnos.setLocationRelativeTo(null);
            editarAlumnos.setVisible(true);
            break;
        case "asignaturas":
            EditarAsignaturas editarAsignaturas = new EditarAsignaturas(getAsignaturaData(row),this);
            editarAsignaturas.setLocationRelativeTo(null);
            editarAsignaturas.setVisible(true);
            break;
        case "aulas":
            EditarAulas editarAulas = new EditarAulas(getAulaData(row),this);
            editarAulas.setLocationRelativeTo(null);
            editarAulas.setVisible(true);
            break;
        case "ciclos":
            EditarCiclos editarCiclos = new EditarCiclos(getCicloData(row),this);
            editarCiclos.setLocationRelativeTo(null);
            editarCiclos.setVisible(true);
            break;
        case "horarios":
            EditarHorarios editarHorarios = new EditarHorarios(getHorarioData(row),this);
            editarHorarios.setLocationRelativeTo(null);
            editarHorarios.setVisible(true);
            break;
        default:
            JOptionPane.showMessageDialog(null, "No se puede editar esta tabla.", "Error", JOptionPane.ERROR_MESSAGE);
            break;
    }
}

private Object[] getProfesorData(int row) {
    // Recuperar y devolver los datos del profesor de la fila
    return new Object[] {
        jTable1.getValueAt(row, 0), // Nombre
        jTable1.getValueAt(row, 1), // Apellido
        jTable1.getValueAt(row, 2)  // Correo electrónico
    };
}

private Object[] getAlumnoData(int row) {
    // Recuperar y devolver los datos del alumno de la fila
    return new Object[] {
        jTable1.getValueAt(row, 0), // Nombre
        jTable1.getValueAt(row, 1), // Apellido
        jTable1.getValueAt(row, 2), // Correo electrónico
        jTable1.getValueAt(row, 3), // Ciclo
        jTable1.getValueAt(row, 4)  // Año
    };
}

private Object[] getAsignaturaData(int row) {
    // Recuperar y devolver los datos de la asignatura de la fila
    return new Object[] {
        jTable1.getValueAt(row, 0), // Nombre
        jTable1.getValueAt(row, 2), // Ciclo
        jTable1.getValueAt(row, 3)  // Año
    };
}

private Object[] getAulaData(int row) {
    // Recuperar y devolver los datos del aula de la fila
    return new Object[] {
        jTable1.getValueAt(row, 0), // Nombre
        jTable1.getValueAt(row, 1), // Ubicación
        jTable1.getValueAt(row, 2), // Capacidad
        jTable1.getValueAt(row, 3), // Pizarra
        jTable1.getValueAt(row, 4)  // Ordenadores
    };
}

private Object[] getCicloData(int row) {
    // Recuperar y devolver los datos del ciclo de la fila
    return new Object[] {
        jTable1.getValueAt(row, 0), // Nombre
        jTable1.getValueAt(row, 1)  // Año
    };
}

private Object[] getHorarioData(int row) {
    // Recuperar y devolver los datos del horario de la fila
    return new Object[] {
        jTable1.getValueAt(row, 0), // Día de la semana
        jTable1.getValueAt(row, 1), // Hora de inicio
        jTable1.getValueAt(row, 2), // Hora de fin
        jTable1.getValueAt(row, 3), // Asignatura
        jTable1.getValueAt(row, 4), // Profesor
        jTable1.getValueAt(row, 5)  // Aula
    };
}
private void eliminarFila(String selectedTable, int row) {
    try {
        int selectedRow = jTable1.convertRowIndexToModel(row); // Convertir el índice de la vista a modelo
        int id = -1;

        switch (selectedTable) {
            case "profesores":
                String nombreProf = (String) jTable1.getModel().getValueAt(selectedRow, 0);
                String apellidoProf = (String) jTable1.getModel().getValueAt(selectedRow, 1);
                String correoProf = (String) jTable1.getModel().getValueAt(selectedRow, 2);
                id = buscarIDProfesores(nombreProf, apellidoProf, correoProf);
                if (id != -1) {
                    eliminarProfesor(id);
                }
                break;

            case "alumnos":
                String nombreAlum = (String) jTable1.getModel().getValueAt(selectedRow, 0);
                String apellidoAlum = (String) jTable1.getModel().getValueAt(selectedRow, 1);
                String correoAlum = (String) jTable1.getModel().getValueAt(selectedRow, 2);
                id = buscarIDAlumno(nombreAlum, apellidoAlum, correoAlum);
                if (id != -1) {
                    eliminarAlumno(id);
                }
                break;

            case "asignaturas":
                String nombreAsig = (String) jTable1.getModel().getValueAt(selectedRow, 0);
                String nombreCiclo2 = (String) jTable1.getModel().getValueAt(selectedRow, 2);
                int aniociclo = (int) jTable1.getModel().getValueAt(selectedRow, 3);
                id = buscarIDAsignatura(nombreAsig,nombreCiclo2,aniociclo);
                if (id != -1) {
                    eliminarAsignatura(id);
                }
                break;

            case "aulas":
                String nombreAula = (String) jTable1.getModel().getValueAt(selectedRow, 0);
                String ubicacionAula = (String) jTable1.getModel().getValueAt(selectedRow, 1);
                id = buscarIDAula(nombreAula, ubicacionAula);
                if (id != -1) {
                    eliminarAula(id);
                }
                break;

            case "ciclos":
                String nombreCiclo = (String) jTable1.getModel().getValueAt(selectedRow, 0);
                int anioCiclo = (int) jTable1.getModel().getValueAt(selectedRow, 1);
                id = buscarIDCiclo(nombreCiclo, anioCiclo);
                if (id != -1) {
                    eliminarCiclo(id);
                }
                break;

            case "horarios":
                String diaSemana = (String) jTable1.getModel().getValueAt(selectedRow, 0);
                Time horaInicio = (Time) jTable1.getModel().getValueAt(selectedRow, 1);
                Time horaFin = (Time) jTable1.getModel().getValueAt(selectedRow, 2);
                String nombreAsignatura = (String) jTable1.getModel().getValueAt(selectedRow, 3);
                String nombreProfesor = (String) jTable1.getModel().getValueAt(selectedRow, 4);
                String nombreAula2 = (String) jTable1.getModel().getValueAt(selectedRow, 5);
                id = buscarIDHorario(diaSemana, horaInicio, horaFin, nombreAsignatura, nombreProfesor, nombreAula2);
                if (id != -1) {
                    eliminarHorario(id);
                }
                break;

            default:
                JOptionPane.showMessageDialog(null, "Tabla no manejada para eliminación", "Error", JOptionPane.ERROR_MESSAGE);
                break;
        }

        // Eliminar la fila del JTable si se encontró un ID
        if (id != -1) {
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.removeRow(row);
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo encontrar el ID", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (SQLException ex) {
        Logger.getLogger(Busqueda.class.getName()).log(Level.SEVERE, null, ex);
    }
}

public void eliminarAlumno(int idAlumno) throws SQLException {
    Connection conn = null;
    PreparedStatement stmt = null;

    try {
        conn = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/", "SA", "");
        String query = "DELETE FROM PUBLIC.INSTITUTO.ALUMNOS WHERE \"ID alumno\" = ?";
        stmt = conn.prepareStatement(query);
        stmt.setInt(1, idAlumno);
        stmt.executeUpdate();
    } finally {
        if (stmt != null) {
            stmt.close();
        }
        if (conn != null) {
            conn.close();
        }
    }
}

public void eliminarAsignatura(int idAsignatura) throws SQLException {
    Connection conn = null;
    PreparedStatement stmt = null;

    try {
        conn = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/", "SA", "");
        String query = "DELETE FROM PUBLIC.INSTITUTO.ASIGNATURAS WHERE \"ID Asignatura\" = ?";
        stmt = conn.prepareStatement(query);
        stmt.setInt(1, idAsignatura);
        stmt.executeUpdate();
    } finally {
        if (stmt != null) {
            stmt.close();
        }
        if (conn != null) {
            conn.close();
        }
    }
}

public void eliminarAula(int idAula) throws SQLException {
    Connection conn = null;
    PreparedStatement stmt = null;

    try {
        conn = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/", "SA", "");
        String query = "DELETE FROM PUBLIC.INSTITUTO.AULAS WHERE \"ID aula\" = ?";
        stmt = conn.prepareStatement(query);
        stmt.setInt(1, idAula);
        stmt.executeUpdate();
    } finally {
        if (stmt != null) {
            stmt.close();
        }
        if (conn != null) {
            conn.close();
        }
    }
}

public void eliminarCiclo(int idCiclo) throws SQLException {
    Connection conn = null;
    PreparedStatement stmt = null;

    try {
        conn = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/", "SA", "");
        String query = "DELETE FROM PUBLIC.INSTITUTO.CICLOS WHERE \"ID Ciclos\" = ?";
        stmt = conn.prepareStatement(query);
        stmt.setInt(1, idCiclo);
        stmt.executeUpdate();
    } finally {
        if (stmt != null) {
            stmt.close();
        }
        if (conn != null) {
            conn.close();
        }
    }
}

public void eliminarHorario(int idHorario) throws SQLException {
    Connection conn = null;
    PreparedStatement stmt = null;

    try {
        conn = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/", "SA", "");
        String query = "DELETE FROM PUBLIC.INSTITUTO.HORARIOS WHERE \"ID horario\" = ?";
        stmt = conn.prepareStatement(query);
        stmt.setInt(1, idHorario);
        stmt.executeUpdate();
    } finally {
        if (stmt != null) {
            stmt.close();
        }
        if (conn != null) {
            conn.close();
        }
    }
}

public void eliminarProfesor(int idProfesor) throws SQLException {
    Connection conn = null;
    PreparedStatement stmt = null;

    try {
        // Conectar con la base de datos
        conn = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/", "SA", "");

        // Preparar la sentencia SQL para eliminar el registro del profesor
        String query = "DELETE FROM PUBLIC.INSTITUTO.PROFESORES WHERE \"ID profesor\" = ?";
        stmt = conn.prepareStatement(query);
        stmt.setInt(1, idProfesor);

        // Ejecutar la sentencia SQL para eliminar el registro
        stmt.executeUpdate();
    } finally {
        // Cerrar el PreparedStatement y la conexión
        if (stmt != null) {
            stmt.close();
        }
        if (conn != null) {
            conn.close();
        }
    }
}

public int buscarIDHorario(String diaSemana, Time horaInicio, Time horaFin, String nombreAsignatura, String nombreProfesor, String nombreAula) throws SQLException {
    int idHorario = -1; // Valor predeterminado en caso de que no se encuentre ningún ID

    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    try {
        conn = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/", "SA", "");
        String query = "SELECT \"ID horario\" FROM PUBLIC.INSTITUTO.HORARIOS " +
                       "WHERE \"dia de la semana\" = ? AND \"hora de inicio\" = ? AND \"hora de fin\" = ? " +
                       "AND \"ID asignatura\" = (SELECT \"ID Asignatura\" FROM PUBLIC.INSTITUTO.ASIGNATURAS WHERE NOMBRE = ? LIMIT 1) " +
                       "AND \"ID profesor\" = (SELECT \"ID profesor\" FROM PUBLIC.INSTITUTO.PROFESORES WHERE NOMBRE = ? LIMIT 1) " +
                       "AND \"ID aula\" = (SELECT \"ID aula\" FROM PUBLIC.INSTITUTO.AULAS WHERE NOMBRE = ? LIMIT 1)";
        stmt = conn.prepareStatement(query);
        stmt.setString(1, diaSemana);
        stmt.setTime(2, horaInicio);
        stmt.setTime(3, horaFin);
        stmt.setString(4, nombreAsignatura);
        stmt.setString(5, nombreProfesor);
        stmt.setString(6, nombreAula);
        rs = stmt.executeQuery();

        if (rs.next()) {
            idHorario = rs.getInt("ID horario");
        }
    } finally {
        if (rs != null) rs.close();
        if (stmt != null) stmt.close();
        if (conn != null) conn.close();
    }

    return idHorario;
}

public int buscarIDProfesores(String nombreProf, String apellidoProf, String correoProf) throws SQLException {
    int idProfesor = -1; // Valor predeterminado en caso de que no se encuentre ningún ID

    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    try {
        conn = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/", "SA", "");
        String query = "SELECT \"ID profesor\" FROM PUBLIC.INSTITUTO.PROFESORES WHERE NOMBRE = ? AND APELLIDO = ? AND CORREOELECTRONICO = ?";
        stmt = conn.prepareStatement(query);
        stmt.setString(1, nombreProf);
        stmt.setString(2, apellidoProf);
        stmt.setString(3, correoProf);
        rs = stmt.executeQuery();

        if (rs.next()) {
            idProfesor = rs.getInt("ID profesor");
        }
    } finally {
        if (rs != null) rs.close();
        if (stmt != null) stmt.close();
        if (conn != null) conn.close();
    }

    return idProfesor;
}

public int buscarIDAlumno(String nombreAlum, String apellidoAlum, String correoAlum) throws SQLException {
    int idAlumno = -1; // Valor predeterminado en caso de que no se encuentre ningún ID

    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    try {
        conn = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/", "SA", "");
        String query = "SELECT \"ID alumno\" FROM PUBLIC.INSTITUTO.ALUMNOS WHERE NOMBRE = ? AND APELLIDO = ? AND CORREOELECTRONICO = ?";
        stmt = conn.prepareStatement(query);
        stmt.setString(1, nombreAlum);
        stmt.setString(2, apellidoAlum);
        stmt.setString(3, correoAlum);
        rs = stmt.executeQuery();

        if (rs.next()) {
            idAlumno = rs.getInt("ID alumno");
        }
    } finally {
        if (rs != null) rs.close();
        if (stmt != null) stmt.close();
        if (conn != null) conn.close();
    }

    return idAlumno;
}

public int buscarIDAsignatura(String nombreAsig, String nombreCiclo, int añoCiclo) throws SQLException {
    int idAsignatura = -1; // Valor predeterminado en caso de que no se encuentre ningún ID

    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    try {
        conn = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/", "SA", "");
        String query = "SELECT ASIGNATURAS.\"ID Asignatura\" " +
                       "FROM PUBLIC.INSTITUTO.ASIGNATURAS AS ASIGNATURAS " +
                       "INNER JOIN PUBLIC.INSTITUTO.CICLOS AS CICLOS ON ASIGNATURAS.ID_CICLOS = CICLOS.\"ID Ciclos\" " +
                       "WHERE ASIGNATURAS.NOMBRE = ? AND CICLOS.NOMBRE = ? AND CICLOS.ANIO = ?";
        stmt = conn.prepareStatement(query);
        stmt.setString(1, nombreAsig);
        stmt.setString(2, nombreCiclo);
        stmt.setInt(3, añoCiclo);
        rs = stmt.executeQuery();

        if (rs.next()) {
            idAsignatura = rs.getInt("ID Asignatura");
        }
    } finally {
        if (rs != null) rs.close();
        if (stmt != null) stmt.close();
        if (conn != null) conn.close();
    }

    return idAsignatura;
}

public int buscarIDAula(String nombreAula, String ubicacionAula) throws SQLException {
    int idAula = -1; // Valor predeterminado en caso de que no se encuentre ningún ID

    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    try {
        conn = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/", "SA", "");
        String query = "SELECT \"ID aula\" FROM PUBLIC.INSTITUTO.AULAS WHERE NOMBRE = ? AND UBICACION = ?";
        stmt = conn.prepareStatement(query);
        stmt.setString(1, nombreAula);
        stmt.setString(2, ubicacionAula);
        rs = stmt.executeQuery();

        if (rs.next()) {
            idAula = rs.getInt("ID aula");
        }
    } finally {
        if (rs != null) rs.close();
        if (stmt != null) stmt.close();
        if (conn != null) conn.close();
    }

    return idAula;
}

public int buscarIDCiclo(String nombreCiclo, int anioCiclo) throws SQLException {
    int idCiclo = -1; // Valor predeterminado en caso de que no se encuentre ningún ID

    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    try {
        conn = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/", "SA", "");
        String query = "SELECT \"ID Ciclos\" FROM PUBLIC.INSTITUTO.CICLOS WHERE NOMBRE = ? AND ANIO = ?";
        stmt = conn.prepareStatement(query);
        stmt.setString(1, nombreCiclo);
        stmt.setInt(2, anioCiclo);
        rs = stmt.executeQuery();

        if (rs.next()) {
            idCiclo = rs.getInt("ID Ciclos");
        }
    } finally {
        if (rs != null) rs.close();
        if (stmt != null) stmt.close();
        if (conn != null) conn.close();
    }

    return idCiclo;
}

private void buscarEnTabla(String textoABuscar, String columnaSeleccionada) {
    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
    TableRowSorter<DefaultTableModel> sorter = (TableRowSorter<DefaultTableModel>) jTable1.getRowSorter();

    if (sorter != null) {
        if (textoABuscar == null || textoABuscar.isEmpty() || textoABuscar.equalsIgnoreCase("seleccione una hora") || textoABuscar.equalsIgnoreCase("seleccione un dia") || textoABuscar.equalsIgnoreCase("¿tiene pizarra?") || textoABuscar.equalsIgnoreCase("¿tiene ordenadores?")) {
            sorter.setRowFilter(null);
        } else {
            int columnIndex = jTable1.getColumnModel().getColumnIndex(columnaSeleccionada);
            if (columnaSeleccionada.equals("PIZARRA") || columnaSeleccionada.equals("ORDENADORES")) {
                System.out.println("entro");
                List<Integer> filasCoincidentes = new ArrayList<>();
                for (int i = 0; i < model.getRowCount(); i++) {
                    Object value = model.getValueAt(i, columnIndex);
                    System.out.println("Fila " + i + ", Columna " + columnaSeleccionada + ", Valor: " + value); // Imprime el valor
                    if (value instanceof Boolean) {
                        boolean boolValue = (Boolean) value;
                        if (textoABuscar.equalsIgnoreCase("si") && boolValue) {
                            filasCoincidentes.add(i);
                        } else if (textoABuscar.equalsIgnoreCase("no") && !boolValue) {
                            filasCoincidentes.add(i);
                        }
                        System.out.println("Fila " + i + ", Valor booleano: " + boolValue); // Imprime el valor booleano
                    }
                }
                sorter.setRowFilter(new RowFilter<Object, Integer>() {
                    @Override
                    public boolean include(Entry<? extends Object, ? extends Integer> entry) {
                        int fila = entry.getIdentifier();
                        return filasCoincidentes.contains(fila);
                    }
                });
            } else {
                String regex = "(?i)" + textoABuscar; // Ignorar mayúsculas y minúsculas
                sorter.setRowFilter(RowFilter.regexFilter(regex, columnIndex));
            }
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

        addButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        txtBuscar = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jComboBox2 = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jComboBox3 = new javax.swing.JComboBox<>();
        jComboBox4 = new javax.swing.JComboBox<>();
        jComboBox5 = new javax.swing.JComboBox<>();
        jComboBox6 = new javax.swing.JComboBox<>();
        jComboBox7 = new javax.swing.JComboBox<>();

        setPreferredSize(new java.awt.Dimension(1372, 627));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        addButton.setText("añadir");
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });
        add(addButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 500, -1, -1));

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

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 850, -1));

        txtBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarActionPerformed(evt);
            }
        });
        add(txtBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 20, 490, -1));

        jButton4.setText("buscar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 20, -1, -1));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "profesores", "alumnos", "asignaturas", "aulas", "ciclos" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 100, -1));

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "nombre", "apellido", "correo" }));
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });
        add(jComboBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 20, 150, -1));

        jButton1.setText("reiniciar tabla");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 500, -1, -1));

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione un dia", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes" }));
        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
            }
        });
        add(jComboBox3, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 20, 490, -1));

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione una hora", "8:30:00", "9:30:00", "10:25:00", "11:45:00", "12:40:00", "13:35:00" }));
        jComboBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox4ActionPerformed(evt);
            }
        });
        add(jComboBox4, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 20, 490, -1));

        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione una hora", "09:25:00", "10:20:00", "11:15:00", "12:35:00", "13:30:00", "14:25:00" }));
        jComboBox5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox5ActionPerformed(evt);
            }
        });
        add(jComboBox5, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 20, 490, -1));

        jComboBox6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "¿Tiene pizarra?", "si", "no" }));
        jComboBox6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox6ActionPerformed(evt);
            }
        });
        add(jComboBox6, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 20, 490, -1));

        jComboBox7.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "¿tiene ordenadores?", "si", "no" }));
        jComboBox7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox7ActionPerformed(evt);
            }
        });
        add(jComboBox7, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 20, 490, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void txtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarActionPerformed
         String opcionSeleccionada = (String) jComboBox2.getSelectedItem();
         System.out.println(opcionSeleccionada);
         String columnaSeleccionada = columnMapping.get(opcionSeleccionada);
         buscarEnTabla(txtBuscar.getText(),columnaSeleccionada);
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
               AñadirProfesores ap = new AñadirProfesores(this);
               ap.setLocationRelativeTo(null);
               ap.setVisible(true);
               this.setEnabled(false);
               mostrarDatosEnJTable(selectedTable);
                break;
            case "alumnos":
               AñadirAlumnos aa = new AñadirAlumnos(this);
               aa.setLocationRelativeTo(null);
               aa.setVisible(true);
               this.setEnabled(false);
               mostrarDatosEnJTable(selectedTable);
                break;
            case "asignaturas":
               AñadirAsignaturas aas = new AñadirAsignaturas(this);
               aas.setLocationRelativeTo(null);
               aas.setVisible(true);
               this.setEnabled(false);
               mostrarDatosEnJTable(selectedTable);
                break;
            case "aulas":
               AñadirAulas aau = new AñadirAulas(this);
               aau.setLocationRelativeTo(null);
               aau.setVisible(true);
               this.setEnabled(false);
               mostrarDatosEnJTable(selectedTable);
                break;
            case "ciclos":
                AñadirCiclos ac = new AñadirCiclos(this);
               ac.setLocationRelativeTo(null);
               ac.setVisible(true);
               this.setEnabled(false);
               mostrarDatosEnJTable(selectedTable);
                break;
            case "horarios":
                AñadirHorarios ah = new AñadirHorarios(this);
                ah.setSize(584, 261);
               ah.setLocationRelativeTo(null);
               ah.setVisible(true);
               this.setEnabled(false);
               mostrarDatosEnJTable(selectedTable);
                break;
            default:
              
                break;
        }
    }//GEN-LAST:event_addButtonActionPerformed

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

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jComboBox7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox7ActionPerformed
      String opcionSeleccionada = (String) jComboBox2.getSelectedItem();
    if (opcionSeleccionada != null) {
        String columnaSeleccionada = columnMapping.get(opcionSeleccionada);
        buscarEnTabla((String) jComboBox7.getSelectedItem(), columnaSeleccionada);
        System.out.println(jComboBox7.getSelectedItem() +" " + columnaSeleccionada);
    }
    }//GEN-LAST:event_jComboBox7ActionPerformed

    private void jComboBox6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox6ActionPerformed
        String opcionSeleccionada = (String) jComboBox2.getSelectedItem();
    if (opcionSeleccionada != null) {
        String columnaSeleccionada = columnMapping.get(opcionSeleccionada);
        buscarEnTabla((String) jComboBox6.getSelectedItem(), columnaSeleccionada);
         System.out.println(jComboBox6.getSelectedItem() +" " + columnaSeleccionada);
    }
    }//GEN-LAST:event_jComboBox6ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JComboBox<String> jComboBox6;
    private javax.swing.JComboBox<String> jComboBox7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
