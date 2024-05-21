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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
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
        this.rol = rol;
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
        if (sorter != null) {
            sorter.setRowFilter(null);
        }
        
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
            return "SELECT ASIGNATURAS.NOMBRE, CICLOS.NOMBRE AS NOMBRE_CICLO, CICLOS.ANIO AS ANIO_CICLO " +
                   "FROM PUBLIC.INSTITUTO.ASIGNATURAS " +
                   "INNER JOIN PUBLIC.INSTITUTO.CICLOS ON ASIGNATURAS.ID_CICLOS = CICLOS.\"ID Ciclos\"";
        case "aulas":
            return "SELECT NOMBRE, UBICACION, CAPACIDAD_AULA AS CAPACIDAD, PIZARRA, ORDENADORES FROM PUBLIC.INSTITUTO." + table;
        case "ciclos":
            return "SELECT NOMBRE, ANIO AS año FROM PUBLIC.INSTITUTO." + table;
        default:
            return "";
    }
}

private void configureTableColumns(String table, DefaultTableModel model) {
    ImageIcon editIcon = new ImageIcon(getClass().getResource("/imagenes/editar.png"));
    ImageIcon deleteIcon = new ImageIcon(getClass().getResource("/imagenes/delete.png"));
    switch (table) {
        case "alumnos":
            jTable1.getColumnModel().getColumn(2).setPreferredWidth(270);
            jTable1.getColumnModel().getColumn(3).setPreferredWidth(300);
            jTable1.getColumnModel().getColumn(4).setPreferredWidth(100);
            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
            jTable1.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
            model.addColumn("EDITAR");
            model.addColumn("ELIMINAR");
            break;
        case "asignaturas":
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(280);
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(270);
            jTable1.getColumnModel().getColumn(2).setPreferredWidth(90);
            jTable1.getColumnModel().getColumn(3).setPreferredWidth(90);
            DefaultTableCellRenderer centerRenderer2 = new DefaultTableCellRenderer();
            centerRenderer2.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
            jTable1.getColumnModel().getColumn(2).setCellRenderer(centerRenderer2);
            model.addColumn("EDITAR");
            model.addColumn("ELIMINAR");
            break;
        case "aulas":
            DefaultTableCellRenderer centerRenderer3 = new DefaultTableCellRenderer();
            centerRenderer3.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
            jTable1.getColumnModel().getColumn(2).setCellRenderer(centerRenderer3);
            model.addColumn("EDITAR");
            model.addColumn("ELIMINAR");
            break;
        case "ciclos":
            model.addColumn("DESCRIPCIÓN");
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(170);
            DefaultTableCellRenderer centerRenderer4 = new DefaultTableCellRenderer();
            centerRenderer4.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
            jTable1.getColumnModel().getColumn(1).setCellRenderer(centerRenderer4);
            model.addColumn("EDITAR");
            model.addColumn("ELIMINAR");
            break;
        case "profesores":
        default:
            model.addColumn("EDITAR");
            model.addColumn("ELIMINAR");
            break;
    }

    // Establecer el renderizador personalizado para las columnas "editar" y "eliminar"
    if (isEditableAndDeletable(table)) {
        int editColumnIndex = model.getColumnCount() - 2; // Índice de la columna "editar"
        int deleteColumnIndex = model.getColumnCount() - 1; // Índice de la columna "eliminar"
        jTable1.getColumnModel().getColumn(editColumnIndex).setCellRenderer(new ImageRenderer(editIcon, 20));
        jTable1.getColumnModel().getColumn(deleteColumnIndex).setCellRenderer(new ImageRenderer(deleteIcon, 20));
    }
}

private boolean isEditableAndDeletable(String table) {
    return table.equals("profesores") || table.equals("alumnos") || table.equals("asignaturas") || table.equals("aulas") || table.equals("ciclos");
}

private void configureAulasTableColumns() {
    ImageBooleanRenderer pizarraRenderer = new ImageBooleanRenderer(
        new ImageIcon(getClass().getResource("/imagenes/pizarra_true.png")),
        new ImageIcon(getClass().getResource("/imagenes/pizarra_false.png")),
        20
    );    jTable1.getColumnModel().getColumn(3).setCellRenderer(pizarraRenderer);

    ImageBooleanRenderer ordenadoresRenderer = new ImageBooleanRenderer(
        new ImageIcon(getClass().getResource("/imagenes/ordenadores_true.png")),
        new ImageIcon(getClass().getResource("/imagenes/ordenadores_false.png")),
        20
    );
    jTable1.getColumnModel().getColumn(4).setCellRenderer(ordenadoresRenderer);
}

private void addTableMouseListener(String selectedTable) {
    jTable1.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            System.out.println("Mouse clicked on table");
            int column = jTable1.getColumnModel().getColumnIndexAtX(e.getX()); // Obtener el índice de la columna clicada
            int row = e.getY() / jTable1.getRowHeight(); // Obtener el índice de la fila clicada

            if (row < jTable1.getRowCount() && row >= 0 && column < jTable1.getColumnCount() && column >= 0) {
                int id = row;
                System.out.println(id);
                // Verificar si la columna es "Editar" o "Eliminar" basándonos en la posición de la columna
                if (column == jTable1.getColumnCount() - 2) { // Penúltima columna: EDITAR
                    System.out.println("Edit icon clicked");
                    // Lógica para editar
                } else if (column == jTable1.getColumnCount() - 1) { // Última columna: ELIMINAR
                    System.out.println("Delete icon clicked");
                    int opcion = JOptionPane.showConfirmDialog(null, "¿Estás seguro de que deseas eliminar esta fila?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
                    if (opcion == JOptionPane.YES_OPTION) {
                        eliminarFila(selectedTable, row);
                    } else if (opcion == JOptionPane.NO_OPTION) {
                        System.out.println("Operación de eliminación cancelada.");
                    }
                }
            } else {
                System.out.println("se ha ido");
            }
        }
    });
}

private void eliminarFila(String selectedTable, int row) {
    try {
        int selectedRow = jTable1.convertRowIndexToModel(row); // Convertir el índice de la vista a modelo
        String nombre = (String) jTable1.getModel().getValueAt(selectedRow, 0); // Suponiendo que el nombre está en la primera columna
        String apellido = (String) jTable1.getModel().getValueAt(selectedRow, 1); // Suponiendo que el apellido está en la segunda columna
        String correo = (String) jTable1.getModel().getValueAt(selectedRow, 2); // Suponiendo que el correo está en la tercera columna

        int id = -1; // Declarar la variable fuera del bloque try-catch

        if (selectedTable.equals("alumnos")) {
            id = buscarIDProfesores(nombre, apellido, correo);
        }

        if (id != -1) { // Se encontró el ID
            // Eliminar la fila del JTable
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.removeRow(row);
            eliminarProfesor(id);
        } else {
            // Mostrar un mensaje de error si no se encuentra el ID
            JOptionPane.showMessageDialog(null, "No se pudo encontrar el ID", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (SQLException ex) {
        Logger.getLogger(Busqueda.class.getName()).log(Level.SEVERE, null, ex);
    }
}


   private void mostrarDatosEnJTable2(String selectedTable) {
    int contador = 0;
    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
    jTable1.setRowSorter(new TableRowSorter<>(model)); 
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
                        contador++; // Incrementar el contador
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
    System.out.println("Mouse clicked on table");
    int column = jTable1.getColumnModel().getColumnIndexAtX(e.getX()); // Obtener el índice de la columna clicada
    int row = e.getY() / jTable1.getRowHeight(); // Obtener el índice de la fila clicada

    if (row < jTable1.getRowCount() && row >= 0 && column < jTable1.getColumnCount() && column >= 0) {
        int id = row;
        System.out.println(id);
        // Verificar si la columna es "Editar" o "Eliminar" basándonos en la posición de la columna
        if (column == jTable1.getColumnCount() - 2) { // Penúltima columna: EDITAR
            System.out.println("Edit icon clicked");
            // Lógica para editar
        } else if (column == jTable1.getColumnCount() - 1) { // Última columna: ELIMINAR
            System.out.println("Delete icon clicked");
            int opcion = JOptionPane.showConfirmDialog(null, "¿Estás seguro de que deseas eliminar esta fila?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if(selectedTable.equals("alumnos")){
        if (opcion == JOptionPane.YES_OPTION) {
            int selectedRow = jTable1.convertRowIndexToModel(row); // Convertir el índice de la vista a modelo
            String nombreProf = (String) jTable1.getModel().getValueAt(selectedRow, 0); // Suponiendo que el nombre está en la primera columna
            String apellidoProf = (String) jTable1.getModel().getValueAt(selectedRow, 1); // Suponiendo que el apellido está en la segunda columna
            String correoProf = (String) jTable1.getModel().getValueAt(selectedRow, 2); // Suponiendo que el correo está en la tercera columna

            int idProfesor = -1; // Declarar la variable fuera del bloque try-catch

            try {
                // Buscar el ID del profesor
                idProfesor = buscarIDProfesores(nombreProf, apellidoProf, correoProf);
            } catch (SQLException ex) {
                Logger.getLogger(Busqueda.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                // Aquí deberías cerrar cualquier recurso de conexión, si es necesario
            }

            if (idProfesor != -1) { // Se encontró el ID del profesor
                // Eliminar la fila del JTable
                DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                model.removeRow(row);
                try {
                    eliminarProfesor(idProfesor);
                    // Aquí puedes realizar cualquier acción adicional necesaria, como eliminar el registro de la base de datos
                } catch (SQLException ex) {
                    Logger.getLogger(Busqueda.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                // Mostrar un mensaje de error si no se encuentra el ID del profesor
                JOptionPane.showMessageDialog(null, "No se pudo encontrar el ID del profesor", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (opcion == JOptionPane.NO_OPTION) {

            System.out.println("Operación de eliminación cancelada.");
        }
        }
        }
    }else{
        System.out.println("se ha ido");
    }
  }
        });
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

public int buscarIDAsignatura(String nombreAsig) throws SQLException {
    int idAsignatura = -1; // Valor predeterminado en caso de que no se encuentre ningún ID

    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    try {
        conn = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/", "SA", "");
        String query = "SELECT \"ID Asignatura\" FROM PUBLIC.INSTITUTO.ASIGNATURAS WHERE NOMBRE = ?";
        stmt = conn.prepareStatement(query);
        stmt.setString(1, nombreAsig);
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
        // Si el texto a buscar es nulo o está vacío, eliminar el filtro y mostrar todos los datos
        if (textoABuscar == null || textoABuscar.isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            String regex = "(?i)" + textoABuscar; // Ignorar mayúsculas y minúsculas
            int columnIndex = jTable1.getColumnModel().getColumnIndex(columnaSeleccionada);
            sorter.setRowFilter(RowFilter.regexFilter(regex, columnIndex)); // Aplicar el filtro de búsqueda en la columna seleccionada
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
         String opcionSeleccionada = (String) jComboBox2.getSelectedItem();
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
