/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package instituto.perfil;
import instituto.busquedas.añadir.ImageRenderer;
import instituto.busquedas.añadir.ImageRenderer2;
import java.sql.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
/**
 *
 * @author ivan.castellano
 */
public class AulasReservadas extends javax.swing.JPanel {

    private int idProfesor;
    private DefaultTableModel tableModel;
    private DefaultComboBoxModel comboBoxModel;
    
    public AulasReservadas(int idProfesor) {
        this.idProfesor = idProfesor;
        initComponents();
        cargarSitiosReservados();
        cargarReservas();
         // Agregar un ActionListener al JComboBox
        jComboBox1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cargarReservas(); // Llamar al método cargarReservas() cuando se cambie el valor en el JComboBox
            }
        });
            jTable1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int column = jTable1.columnAtPoint(e.getPoint());
                int row = jTable1.rowAtPoint(e.getPoint());
                if (column == jTable1.getColumnCount() - 1) { // Columna de eliminación
                    int opcion = JOptionPane.showConfirmDialog(null, "¿Estás seguro de que deseas eliminar esta reserva?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
                    if (opcion == JOptionPane.YES_OPTION) {
                        // Obtener valores de la fila seleccionada
                        Date fechaReserva = (Date) tableModel.getValueAt(row, 0);
                        Time horaReserva = (Time) tableModel.getValueAt(row, 1);
                        // Obtener el tipo de aula seleccionado del JComboBox
                        String tipoAula = (String) jComboBox1.getSelectedItem();
                        // Obtener el id del aula
                        int idAula = obtenerIdAula(tipoAula);
                        if (idAula != -1) {
                            // Obtener el ID de la reserva
                            int idReserva = obtenerIdReserva(idProfesor, idAula, fechaReserva, horaReserva);
                            if (idReserva != -1) {
                                // Eliminar de la base de datos
                                eliminarReserva
                                  (idReserva);
                                // Eliminar de la tabla
                                tableModel.removeRow(row);
                            } else {
                                JOptionPane.showMessageDialog(null, "Error al obtener el ID de la reserva.");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Error al obtener el ID del aula.");
                        }
                    }
                }
            }
        });
    }
     private void cargarSitiosReservados() {
       comboBoxModel = new DefaultComboBoxModel();
       
        try {
            // Conectar a la base de datos
            Connection con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/", "SA", "");

            // Consulta SQL para obtener los sitios reservados del profesor
            String query = "SELECT DISTINCT NOMBRE FROM INSTITUTO.AULAS "
                    + "WHERE \"ID aula\" IN (SELECT ID_AULA FROM INSTITUTO.RESERVAS WHERE ID_PROFESOR = ?)";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, idProfesor);
            
            // Ejecutar la consulta
            ResultSet rs = pst.executeQuery();
            
            // Iterar sobre los resultados y agregarlos al modelo del JComboBox
            while (rs.next()) {
                String nombreAula = rs.getString("NOMBRE");
                comboBoxModel.addElement(nombreAula);
            }
            
            // Cerrar la conexión y liberar recursos
            rs.close();
            pst.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        // Establecer el modelo del JComboBox
        jComboBox1.setModel(comboBoxModel);
    }
     private int obtenerIdAula(String nombreAula) {
        int idAula = -1;
        try {
            // Conectar a la base de datos
            Connection con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/", "SA", "");

            // Consulta SQL para obtener el ID del aula
            String query = "SELECT \"ID aula\" FROM INSTITUTO.AULAS WHERE NOMBRE = ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, nombreAula);

            // Ejecutar la consulta
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                idAula = rs.getInt(1);
            }

            // Cerrar la conexión y liberar recursos
            rs.close();
            pst.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idAula;
    }

    private int obtenerIdReserva(int idProfesor, int idAula, Date fechaReserva, Time horaReserva) {
        int idReserva = -1;
        try {
            // Conectar a la base de datos
            Connection con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/", "SA", "");

            // Consulta SQL para obtener el ID de la reserva
            String query = "SELECT ID_RESERVA FROM INSTITUTO.RESERVAS WHERE ID_PROFESOR = ? AND ID_AULA = ? AND FECHA_RESERVA = ? AND HORA_RESERVA = ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, idProfesor);
            pst.setInt(2, idAula);
            pst.setDate(3, new java.sql.Date(fechaReserva.getTime()));
            pst.setTime(4, horaReserva);

            // Ejecutar la consulta
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                idReserva = rs.getInt(1);
            }

            // Cerrar la conexión y liberar recursos
            rs.close();
            pst.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idReserva;
    }
          private void eliminarReserva(int idReserva) {
        try {
            // Conectar a la base de datos
            Connection con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/", "SA", "");

            // Consulta SQL para eliminar la reserva
            String query = "DELETE FROM INSTITUTO.RESERVAS WHERE ID_RESERVA = ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, idReserva);

            // Ejecutar la eliminación
            pst.executeUpdate();

            // Cerrar la conexión y liberar recursos
            pst.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
private void cargarReservas() {
    // Obtener el tipo de aula seleccionado del JComboBox
    String tipoAula = (String) jComboBox1.getSelectedItem();
    ImageIcon deleteIcon = new ImageIcon(getClass().getResource("/imagenes/delete.png"));
    
    // Limpiar la tabla antes de cargar nuevas reservas
    tableModel = new DefaultTableModel();
    jTable1.setModel(tableModel);
    tableModel.addColumn("Fecha");
    tableModel.addColumn("Hora Inicio");
    tableModel.addColumn("Hora Fin");
    tableModel.addColumn("Eliminar"); // Agregar columna de "Eliminar" con imagen
    
    try {
        // Conectar a la base de datos
        Connection con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/", "SA", "");

        // Consulta SQL para obtener las reservas del profesor en el tipo de aula seleccionado
        String query = "SELECT FECHA_RESERVA, HORA_RESERVA, HORA_FIN_RESERVA FROM INSTITUTO.RESERVAS "
                + "WHERE ID_PROFESOR = ? AND ID_AULA IN (SELECT \"ID aula\" FROM INSTITUTO.AULAS WHERE NOMBRE = ?)";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setInt(1, idProfesor);
        pst.setString(2, tipoAula);
        
        // Ejecutar la consulta
        ResultSet rs = pst.executeQuery();
        
        // Iterar sobre los resultados y agregarlos a la tabla
        while (rs.next()) {
            Date fecha = rs.getDate("FECHA_RESERVA");
            Time horaInicio = rs.getTime("HORA_RESERVA");
            Time horaFin = rs.getTime("HORA_FIN_RESERVA");
            tableModel.addRow(new Object[]{fecha, horaInicio, horaFin, deleteIcon}); // Añadir fila con datos y icono de eliminación
        }
        
        // Centrar las celdas de todas las columnas
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < jTable1.getColumnCount(); i++) {
            jTable1.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Configurar la columna de eliminación para mostrar el icono correctamente
        int deleteColumnIndex = tableModel.getColumnCount() - 1;
        jTable1.getColumnModel().getColumn(deleteColumnIndex).setCellRenderer(new ImageRenderer2(deleteIcon, 20));
        
        // Cerrar la conexión y liberar recursos
        rs.close();
        pst.close();
        con.close();
    } catch (SQLException e) {
        e.printStackTrace();
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
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();

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

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 60, 587, 170));

        add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 30, -1, -1));

        jLabel1.setText("Sitio : ");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 32, -1, -1));
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
