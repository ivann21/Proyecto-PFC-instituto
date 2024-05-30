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
import java.text.SimpleDateFormat;
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


public class Busqueda extends javax.swing.JPanel {
    
    private HashMap<String, String> columnMapping;
    private List<Integer> idList = new ArrayList<>();
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
        columnMapping.put("dia de la semana","DIA DE LA SEMANA");  
        columnMapping.put("hora de inicio","HORA DE INICIO");  
        columnMapping.put("hora de fin","HORA DE FIN");  
        columnMapping.put("pizarra","PIZARRA");  
        columnMapping.put("ordenadores","ORDENADORES");   
        columnMapping.put("horarios","HORARIOS");   
        
        String selectedTable = "profesores";
        mostrarDatosEnJTable(selectedTable);
        
        
      jComboBox1.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        String selectedValue = (String) jComboBox1.getSelectedItem();
        jComboBox2.removeAllItems();
        txtBuscar.setText("");
        
        TableRowSorter<DefaultTableModel> sorter = (TableRowSorter<DefaultTableModel>) jTable1.getRowSorter();
        if (sorter != null) {
            sorter.setRowFilter(null);
        }
     if (selectedValue.equals("profesores")) {
            jComboBox2.addItem("nombre");
            jComboBox2.addItem("apellido");
            jComboBox2.addItem("correo");
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
            jComboBox3.setVisible(false);
            jComboBox4.setVisible(false);
            jComboBox5.setVisible(false);
            jComboBox6.setVisible(false);
            jComboBox7.setVisible(false);
             jButton4.setEnabled(true);
        } else if (selectedValue.equals("ciclos")) {
            jComboBox2.addItem("nombre");
            jComboBox2.addItem("año");
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

                    for (int i = 1; i <= columnCount; i++) {
                        String columnName = metaData.getColumnLabel(i);
                        if (!columnName.toLowerCase().startsWith("id")) {
                            model.addColumn(columnName);
                        }
                    }

                    configureTableColumns(selectedTable, model);

                    jTable1.setRowHeight(30); 

                   if (selectedTable.equals("horarios")) {
                        while (rs.next()) {
                            Object[] rowData = new Object[columnCount];
                            for (int i = 0; i < columnCount; i++) {
                                if (metaData.getColumnName(i + 1).startsWith("ID")) {
                                    idList.add(rs.getInt(i + 1));
                                } else {
                                    rowData[i] = rs.getObject(i + 1);
                                }
                            }
                            contador++; 
                            model.addRow(rowData);
                        }
                    } else {
                        while (rs.next()) {
                            Object[] rowData = new Object[columnCount];
                            for (int i = 0; i < columnCount; i++) {
                                rowData[i] = rs.getObject(i + 1);
                            }
                            contador++; 
                            model.addRow(rowData);
                        }
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
        return "SELECT NOMBRE AS nombre, APELLIDO AS apellido, \"CORREOELECTRONICO\" AS\"CORREO ELECTRONICO\" FROM PUBLIC.INSTITUTO." + table;
    case "alumnos":
        return "SELECT ALUMNOS.NOMBRE AS nombre, ALUMNOS.APELLIDO AS apellido, ALUMNOS.CORREOELECTRONICO AS \"CORREO ELECTRONICO\", CICLOS.NOMBRE AS \"NOMBRE DEL CICLO\", CICLOS.ANIO AS \"AÑO DEL CICLO\" " +
               "FROM PUBLIC.INSTITUTO.ALUMNOS " +
               "INNER JOIN PUBLIC.INSTITUTO.CICLOS ON ALUMNOS.\"ID ciclos\" = CICLOS.\"ID Ciclos\"";
    case "asignaturas":
        return "SELECT " +
                   "ASIGNATURAS.NOMBRE AS nombre, " +
                   "'-' AS descripcion, " + 
                   "CICLOS.NOMBRE AS \"NOMBRE DEL CICLO\", " +
                   "CICLOS.ANIO AS año " +
               "FROM " +
                   "PUBLIC.INSTITUTO.ASIGNATURAS " +
                   "INNER JOIN PUBLIC.INSTITUTO.CICLOS " +
                   "ON ASIGNATURAS.ID_CICLOS = CICLOS.\"ID Ciclos\"";
    case "aulas":
        return "SELECT NOMBRE AS nombre, UBICACION AS ubicacion, CAPACIDAD_AULA AS capacidad, PIZARRA AS pizarra, ORDENADORES AS ordenadores FROM PUBLIC.INSTITUTO." + table;
    case "ciclos":
        return "SELECT NOMBRE AS nombre, ANIO AS año FROM PUBLIC.INSTITUTO." + table;
    case "horarios":
        return "SELECT HORARIOS.\"dia de la semana\" AS \"DIA DE LA SEMANA\", HORARIOS.\"hora de inicio\" AS \"HORA DE INICIO\", HORARIOS.\"hora de fin\" AS\"HORA DE FIN\", " +
               "ASIGNATURAS.NOMBRE AS asignatura, PROFESORES.NOMBRE AS profesor, AULAS.NOMBRE AS aula " +
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
                setColumnWidths(jTable1, new int[]{150, 150, 270, 350, 142, 80, 80});
                setColumnCenterAlignment(jTable1, new int[]{4, 5});
            } else {
                setColumnWidths(jTable1, new int[]{150, 150, 270, 350,142});
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
                setColumnWidths(jTable1, new int[]{190, 150, 80, 80, 80});
                setColumnCenterAlignment(jTable1, new int[]{1, 2});
                int descriptionColumnIndex = model.getColumnCount() - 3;
                jTable1.getColumnModel().getColumn(descriptionColumnIndex).setCellRenderer(new ImageRenderer(descriptionIcon, 20));
            } else {
                model.addColumn("DESCRIPCIÓN");
                setColumnWidths(jTable1, new int[]{190, 80, 80});
                setColumnCenterAlignment(jTable1, new int[]{1});
                int descriptionColumnIndex = model.getColumnCount() - 1;
                jTable1.getColumnModel().getColumn(descriptionColumnIndex).setCellRenderer(new ImageRenderer(descriptionIcon, 20));
            }
           
            break;
        case "horarios":
            if (rol != 0) {
                model.addColumn("EDITAR");
                model.addColumn("ELIMINAR");
                setColumnWidths(jTable1, new int[]{190, 150, 150, 400, 150, 150, 80, 80});
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
                setColumnWidths(jTable1, new int[]{100, 150,150});
                setColumnCenterAlignment(jTable1, new int[]{0,1,2});
            } else {
                setColumnWidths(jTable1, new int[]{100});
                setColumnCenterAlignment(jTable1, new int[]{0,1,2});
            }
            break;
    }
    if (isEditableAndDeletable(table) && rol !=0) {
        int editColumnIndex = model.getColumnCount() - 2;
        int deleteColumnIndex = model.getColumnCount() - 1; 
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
    for (MouseListener listener : jTable1.getMouseListeners()) {
        jTable1.removeMouseListener(listener);
    }
    
    jTable1.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            int row = jTable1.rowAtPoint(e.getPoint()); // Obtener la fila del punto de clic
            int column = jTable1.columnAtPoint(e.getPoint()); // Obtener la columna del punto de clic

            if (row < jTable1.getRowCount() && row >= 0 && column < jTable1.getColumnCount() && column >= 0) {
                if (rol != 0) { 
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
                    } else if (column == jTable1.getColumnCount() - 5 && selectedTable.equals("asignaturas")) {
                        System.out.println("Description icon clicked");
                        abrirPanelDescripcion(selectedTable, row);
                    } else if (column == jTable1.getColumnCount() - 3 && selectedTable.equals("ciclos")) {
                        System.out.println("Description icon clicked");
                        abrirPanelDescripcion(selectedTable, row);
                    }
                } else {
                    if (column == jTable1.getColumnCount() - 3 && selectedTable.equals("asignaturas")) {
                        System.out.println("Description icon clicked");
                        abrirPanelDescripcion(selectedTable, row);
                    }else if (column == jTable1.getColumnCount() - 1 && selectedTable.equals("ciclos")) {
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
   
    return new Object[] {
        jTable1.getValueAt(row, 0), // Nombre
        jTable1.getValueAt(row, 1), // Apellido
        jTable1.getValueAt(row, 2)  // Correo electrónico
    };
}

private Object[] getAlumnoData(int row) {
    
    return new Object[] {
        jTable1.getValueAt(row, 0), // Nombre
        jTable1.getValueAt(row, 1), // Apellido
        jTable1.getValueAt(row, 2), // Correo electrónico
        jTable1.getValueAt(row, 3), // Ciclo
        jTable1.getValueAt(row, 4)  // Año
    };
}

private Object[] getAsignaturaData(int row) {
    
    return new Object[] {
        jTable1.getValueAt(row, 0), // Nombre
        jTable1.getValueAt(row, 2), // Ciclo
        jTable1.getValueAt(row, 3)  // Año
    };
}

private Object[] getAulaData(int row) {
    
    return new Object[] {
        jTable1.getValueAt(row, 0), // Nombre
        jTable1.getValueAt(row, 1), // Ubicación
        jTable1.getValueAt(row, 2), // Capacidad
        jTable1.getValueAt(row, 3), // Pizarra
        jTable1.getValueAt(row, 4)  // Ordenadores
    };
}

private Object[] getCicloData(int row) {
    
    return new Object[] {
        jTable1.getValueAt(row, 0), // Nombre
        jTable1.getValueAt(row, 1)  // Año
    };
}

private Object[] getHorarioData(int row) {
    
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
      DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        TableRowSorter<DefaultTableModel> sorter = (TableRowSorter<DefaultTableModel>) jTable1.getRowSorter();
        
        int selectedRowInView = row;
        int selectedRowInModel = jTable1.convertRowIndexToModel(selectedRowInView);
        System.out.println(selectedRowInModel);
        int id = -1;

        switch (selectedTable) {
            case "profesores":
                String nombreProf = (String) jTable1.getModel().getValueAt(selectedRowInModel, 0);
                String apellidoProf = (String) jTable1.getModel().getValueAt(selectedRowInModel, 1);
                String correoProf = (String) jTable1.getModel().getValueAt(selectedRowInModel, 2);
                id = buscarIDProfesores(nombreProf, apellidoProf, correoProf);
                if (id != -1) {
                    eliminarProfesor(id);
                }
                break;

            case "alumnos":
                String nombreAlum = (String) jTable1.getModel().getValueAt(selectedRowInModel, 0);
                String apellidoAlum = (String) jTable1.getModel().getValueAt(selectedRowInModel, 1);
                String correoAlum = (String) jTable1.getModel().getValueAt(selectedRowInModel, 2);
                id = buscarIDAlumno(nombreAlum, apellidoAlum, correoAlum);
                if (id != -1) {
                    eliminarAlumno(id);
                }
                break;

            case "asignaturas":
                String nombreAsig = (String) jTable1.getModel().getValueAt(selectedRowInModel, 0);
                String nombreCiclo2 = (String) jTable1.getModel().getValueAt(selectedRowInModel, 2);
                int aniociclo = (int) jTable1.getModel().getValueAt(selectedRowInModel, 3);
                id = buscarIDAsignatura(nombreAsig,nombreCiclo2,aniociclo);
                if (id != -1) {
                    eliminarAsignatura(id);
                }
                break;

            case "aulas":
                String nombreAula = (String) jTable1.getModel().getValueAt(selectedRowInModel, 0);
                String ubicacionAula = (String) jTable1.getModel().getValueAt(selectedRowInModel, 1);
                id = buscarIDAula(nombreAula, ubicacionAula);
                if (id != -1) {
                    eliminarAula(id);
                }
                break;

            case "ciclos":
                String nombreCiclo = (String) jTable1.getModel().getValueAt(selectedRowInModel, 0);
                int anioCiclo = (int) jTable1.getModel().getValueAt(selectedRowInModel, 1);
                id = buscarIDCiclo(nombreCiclo, anioCiclo);
                if (id != -1) {
                    eliminarCiclo(id);
                }
                break;

            case "horarios":
                 id = idList.get(row);
                if (id != -1) {
                    eliminarHorario(id);
                }
                break;

            default:
                JOptionPane.showMessageDialog(null, "Tabla no manejada para eliminación", "Error", JOptionPane.ERROR_MESSAGE);
                break;
        }

        if (id != -1) {
           model.removeRow(selectedRowInView);
           mostrarDatosEnJTable(selectedTable);
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo encontrar el ID", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (SQLException ex) {
        Logger.getLogger(Busqueda.class.getName()).log(Level.SEVERE, null, ex);
    }
}

public void eliminarRegistro(String tabla, String columnaId, int id) throws SQLException {
    try (Connection conn = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/", "SA", "");
         PreparedStatement stmt = conn.prepareStatement("DELETE FROM PUBLIC.INSTITUTO." + tabla + " WHERE \"" + columnaId + "\" = ?")) {
        stmt.setInt(1, id);
        stmt.executeUpdate();
    }
}
public void eliminarAlumno(int idAlumno) throws SQLException {
    eliminarRegistro("ALUMNOS", "ID alumno", idAlumno);
}

public void eliminarAsignatura(int idAsignatura) throws SQLException {
    eliminarRegistro("ASIGNATURAS", "ID Asignatura", idAsignatura);
}

public void eliminarAula(int idAula) throws SQLException {
    eliminarRegistro("AULAS", "ID aula", idAula);
}

public void eliminarCiclo(int idCiclo) throws SQLException {
    eliminarRegistro("CICLOS", "ID Ciclos", idCiclo);
}

private void eliminarHorario(int idHorario) {
    try {
        Class.forName("org.hsqldb.jdbc.JDBCDriver");

        try (Connection connection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/", "SA", "")) {
            String sql = "DELETE FROM PUBLIC.INSTITUTO.HORARIOS WHERE ID_HORARIO = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, idHorario);
                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Horario eliminado correctamente");
                } else {
                    System.out.println("No se encontró ningún horario con el ID proporcionado");
                }
            }
        }
    } catch (ClassNotFoundException | SQLException e) {
        e.printStackTrace();
    }
}

public void eliminarProfesor(int idProfesor) throws SQLException {
    eliminarRegistro("PROFESORES", "ID profesor", idProfesor);
}
public int buscarID(String tabla, String columnaId, String[] columnas, String[] valores) throws SQLException {
    String sqlQuery = "SELECT " + columnaId + " FROM PUBLIC.INSTITUTO." + tabla + " WHERE " + String.join(" = ? AND ", columnas) + " = ?";
    System.out.println("Consulta SQL: " + sqlQuery); // Imprimir la consulta SQL
    
    try (Connection conn = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/", "SA", "");
         PreparedStatement stmt = conn.prepareStatement(sqlQuery)) {
        for (int i = 0; i < columnas.length; i++) {
            stmt.setString(i + 1, valores[i]);
        }
        
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(columnaId);
            }
        } catch (java.sql.SQLDataException ex) {
            ex.printStackTrace();
        }
    }
    return -1;
}

public int buscarIDHorario(String diaSemana, Time horaInicio, Time horaFin, String nombreAsignatura, String nombreCiclo, int añoCiclo, String nombreProfesor, String apellidoProfesor, String correoProfesor, String nombreAula, String ubicacionAula) throws SQLException {

    int idAsignatura = buscarIDAsignatura(nombreAsignatura, nombreCiclo, añoCiclo);
    if (idAsignatura == -1) {
        throw new SQLException("Asignatura no encontrada: " + nombreAsignatura);
    }
    
    int idProfesor = buscarIDProfesores(nombreProfesor, apellidoProfesor, correoProfesor);
    if (idProfesor == -1) {
        throw new SQLException("Profesor no encontrado: " + nombreProfesor);
    }

    int idAula = buscarIDAula(nombreAula, ubicacionAula);
    if (idAula == -1) {
        throw new SQLException("Aula no encontrada: " + nombreAula);
    }

    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    String horaInicioStr = sdf.format(horaInicio);
    String horaFinStr = sdf.format(horaFin);

    String[] columnas = {"\"dia de la semana\"", "\"hora de inicio\"", "\"hora de fin\"", "\"ID asignatura\"", "\"ID profesor\"", "\"ID aula\""};
    String[] valores = {diaSemana, horaInicioStr, horaFinStr, String.valueOf(idAsignatura), String.valueOf(idProfesor), String.valueOf(idAula)};
    
    System.out.println("Hora de inicio: " + horaInicioStr);
    System.out.println("Hora de fin: " + horaFinStr);
    System.out.println("Parámetro 1 (diaSemana): " + diaSemana);
    System.out.println("Parámetro 4 (idAsignatura): " + idAsignatura);
    System.out.println("Parámetro 5 (idProfesor): " + idProfesor);
    System.out.println("Parámetro 6 (idAula): " + idAula);
    
    return buscarID("HORARIOS", "ID horario", columnas, valores);
}

public int buscarIDProfesores(String nombreProf, String apellidoProf, String correoProf) throws SQLException {
    String[] columnas = {"NOMBRE", "APELLIDO", "CORREOELECTRONICO"};
    String[] valores = {nombreProf, apellidoProf, correoProf};
    return buscarID("PROFESORES", "ID profesor", columnas, valores);
}

public int buscarIDAlumno(String nombreAlum, String apellidoAlum, String correoAlum) throws SQLException {
    String[] columnas = {"NOMBRE", "APELLIDO", "CORREOELECTRONICO"};
    String[] valores = {nombreAlum, apellidoAlum, correoAlum};
    return buscarID("ALUMNOS", "ID alumno", columnas, valores);
}

public int buscarIDAsignatura(String nombreAsig, String nombreCiclo, int añoCiclo) throws SQLException {
    String sqlQuery = "SELECT ASIGNATURAS.\"ID Asignatura\" " +
                      "FROM PUBLIC.INSTITUTO.ASIGNATURAS " +
                      "INNER JOIN PUBLIC.INSTITUTO.CICLOS " +
                      "ON ASIGNATURAS.ID_CICLOS = CICLOS.\"ID Ciclos\" " +
                      "WHERE ASIGNATURAS.NOMBRE = ? AND CICLOS.NOMBRE = ? AND CICLOS.ANIO = ?";
    
    System.out.println("Consulta SQL: " + sqlQuery); // Imprimir la consulta SQL

    try (Connection conn = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/", "SA", "");
         PreparedStatement stmt = conn.prepareStatement(sqlQuery)) {
        
        stmt.setString(1, nombreAsig);
        stmt.setString(2, nombreCiclo);
        stmt.setInt(3, añoCiclo);
        
        System.out.println("Parámetro 1 (nombreAsig): " + nombreAsig);
        System.out.println("Parámetro 2 (nombreCiclo): " + nombreCiclo);
        System.out.println("Parámetro 3 (añoCiclo): " + añoCiclo);
        
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1); // Obtener el valor de la primera columna en el resultado
            }
        } catch (java.sql.SQLDataException ex) {
            ex.printStackTrace();
        }
    }
    return -1;
}


public int buscarIDAula(String nombreAula, String ubicacionAula) throws SQLException {
    String sqlQuery = "SELECT \"ID aula\" FROM PUBLIC.INSTITUTO.AULAS WHERE NOMBRE = ? AND UBICACION = ?";
    
    System.out.println("Consulta SQL: " + sqlQuery); // Imprimir la consulta SQL

    try (Connection conn = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/", "SA", "");
         PreparedStatement stmt = conn.prepareStatement(sqlQuery)) {
        
        stmt.setString(1, nombreAula);
        stmt.setString(2, ubicacionAula);
        
        System.out.println("Parámetro 1 (nombreAula): " + nombreAula);
        System.out.println("Parámetro 2 (ubicacionAula): " + ubicacionAula);
        
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1); // Obtener el valor de la primera columna en el resultado
            }
        } catch (java.sql.SQLDataException ex) {
            ex.printStackTrace();
        }
    }
    return -1;
}

public int buscarIDCiclo(String nombreCiclo, int anioCiclo) throws SQLException {
    String[] columnas = {"NOMBRE", "ANIO"};
    String[] valores = {nombreCiclo, String.valueOf(anioCiclo)};
    return buscarID("CICLOS", "ID Ciclos", columnas, valores);
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
                String regex = "(?i)" + textoABuscar; 
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
