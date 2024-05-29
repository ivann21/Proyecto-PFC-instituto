/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package instituto.perfil;

import instituto.busquedas.añadir.ImageRenderer;
import instituto.busquedas.añadir.ImageRenderer2;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.regex.Pattern;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;
/**
 *
 * @author ivan.castellano
 */
public class AsignarRoles extends javax.swing.JPanel {
 private int rol;
    private DefaultTableModel tableModel;
    private String nombreProfesorSeleccionado;
    private String apellidoProfesorSeleccionado;

    public AsignarRoles(int rol) {
        this.rol = rol;

        initComponents();

        cargarAsignaciones();
        verificarProfesoresSinRol();
        jTable1.setDefaultEditor(Object.class, null);
        jTable1.setRowHeight(30);
        jTable1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int column = jTable1.columnAtPoint(e.getPoint());
                int row = jTable1.rowAtPoint(e.getPoint());
                if (row >= 0) {
                    TableRowSorter<DefaultTableModel> sorter = (TableRowSorter<DefaultTableModel>) jTable1.getRowSorter();
                    int convertedRow = jTable1.convertRowIndexToModel(row);
                    if (column == jTable1.getColumnCount() - 1) { 
                        int opcion = JOptionPane.showConfirmDialog(null, "¿Estás seguro de que deseas eliminar esta asignación?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
                        if (opcion == JOptionPane.YES_OPTION) {
                            String nombreProfesor = (String) tableModel.getValueAt(convertedRow, 0);
                            String apellidoProfesor = (String) tableModel.getValueAt(convertedRow, 1);
                            String nombreRol = (String) tableModel.getValueAt(convertedRow, 2);
                            System.out.println(nombreProfesor + " " + apellidoProfesor + " " + nombreRol);
                            int idProfesor = obtenerIdProfesor(nombreProfesor, apellidoProfesor);
                            int idRol = obtenerIdRol(nombreRol);
                            System.out.print(idProfesor + " | " + idRol);
                            if (idProfesor != -1 && idRol != -1) {
                                eliminarAsignacion(idProfesor, idRol);
                                tableModel.removeRow(convertedRow);
                            } else {
                                JOptionPane.showMessageDialog(null, "Error al obtener el ID del profesor o del rol.");
                            }
                        }
                    }

                   if (column == jTable1.getColumnCount() - 2) {
                    nombreProfesorSeleccionado = (String) tableModel.getValueAt(convertedRow, 0);
                    apellidoProfesorSeleccionado = (String) tableModel.getValueAt(convertedRow, 1);

                    SeleccionarRolDialog2 dialog = new SeleccionarRolDialog2(null, true, nombreProfesorSeleccionado, apellidoProfesorSeleccionado);
                    dialog.setLocationRelativeTo(null);
                    dialog.setVisible(true);
                    if (dialog.isRolAgregado()) {
                       reiniciarBusqueda();
                       cargarAsignaciones();
                    }
                    }
                }
            }
        });
    }

   private DefaultTableModel buscarEnTabla(String textoBusqueda) {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);

        int columnIndex;
        switch (jComboBox1.getSelectedIndex()) {
            case 0:
                columnIndex = 0;
                break;
            case 1:
                columnIndex = 1;
                break;
            case 2:
                columnIndex = 2;
                break;
            default:
                columnIndex = 0;
                break;
        }

        RowFilter<DefaultTableModel, Object> rowFilter = RowFilter.regexFilter("(?i)" + Pattern.quote(textoBusqueda), columnIndex);
        sorter.setRowFilter(rowFilter);

        jTable1.setRowSorter(sorter);

        return model;
    }

private void reiniciarBusqueda() {
    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
    TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
    jTable1.setRowSorter(sorter);
}
    
       void cargarAsignaciones() {
        tableModel = new DefaultTableModel();
        jTable1.setModel(tableModel);
        tableModel.addColumn("Nombre");
        tableModel.addColumn("Apellido");
        tableModel.addColumn("Nombre Rol");
        tableModel.addColumn("Editar");
        tableModel.addColumn("Eliminar");

        try (Connection con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/", "SA", "");
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT P.NOMBRE, P.APELLIDO, R.NOMBRE_ROL FROM INSTITUTO.ASIGNACION_ROLES AR "
                     + "JOIN INSTITUTO.PROFESORES P ON AR.ID_PROFESOR = P.\"ID profesor\" "
                     + "JOIN INSTITUTO.ROLES R ON AR.ID_ROL = R.ID_ROL")) {

            ImageIcon deleteIcon = new ImageIcon(getClass().getResource("/imagenes/delete.png"));
            ImageIcon editIcon = new ImageIcon(getClass().getResource("/imagenes/editar.png"));

            while (rs.next()) {
                String nombreProfesor = rs.getString("NOMBRE");
                String apellidoProfesor = rs.getString("APELLIDO");
                String nombreRol = rs.getString("NOMBRE_ROL");
                tableModel.addRow(new Object[]{nombreProfesor, apellidoProfesor, nombreRol, editIcon, deleteIcon});
            }

            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(JLabel.CENTER);
            for (int i = 0; i < jTable1.getColumnCount(); i++) {
                jTable1.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }

            int deleteColumnIndex = tableModel.getColumnCount() - 1;
            jTable1.getColumnModel().getColumn(deleteColumnIndex).setCellRenderer(new ImageRenderer2(deleteIcon, 20));
            int editColumnIndex = tableModel.getColumnCount() - 2;
            jTable1.getColumnModel().getColumn(editColumnIndex).setCellRenderer(new ImageRenderer2(editIcon, 20));
            jTable1.setRowHeight(30);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void verificarProfesoresSinRol() {
        try (Connection con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/", "SA", "");
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT P.NOMBRE FROM INSTITUTO.PROFESORES P WHERE P.\"ID profesor\" NOT IN "
                     + "(SELECT ID_PROFESOR FROM INSTITUTO.ASIGNACION_ROLES)")) {

            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "¡Hay profesores sin rol asignado!", "Aviso", JOptionPane.WARNING_MESSAGE);
                ProfesoresSinRol frame = new ProfesoresSinRol(this, this::reloadTable);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setVisible(true);
                frame.setLocationRelativeTo(null);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void reloadTable() {
        cargarAsignaciones();
    }

    private int obtenerIdProfesor(String nombreProfesor, String apellidoProfesor) {
        int idProfesor = -1;
        try (Connection con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/", "SA", "");
             PreparedStatement pst = con.prepareStatement("SELECT \"ID profesor\" FROM INSTITUTO.PROFESORES WHERE NOMBRE = ? AND APELLIDO = ?")) {

            pst.setString(1, nombreProfesor);
            pst.setString(2, apellidoProfesor);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    idProfesor = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idProfesor;
    }

    private int obtenerIdRol(String nombreRol) {
        int idRol = -1;
        try (Connection con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/", "SA", "");
             PreparedStatement pst = con.prepareStatement("SELECT ID_ROL FROM INSTITUTO.ROLES WHERE NOMBRE_ROL = ?")) {

            pst.setString(1, nombreRol);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    idRol = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idRol;
    }

    private void eliminarAsignacion(int idProfesor, int idRol) {
        try (Connection con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/", "SA", "");
             PreparedStatement pst = con.prepareStatement("DELETE FROM INSTITUTO.ASIGNACION_ROLES WHERE ID_PROFESOR = ? AND ID_ROL = ?")) {

            pst.setInt(1, idProfesor);
            pst.setInt(2, idRol);
            pst.executeUpdate();

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
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();

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

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jButton1.setText("buscar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nombre", "apellido", "rol" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        String textoBusqueda = jTextField1.getText().trim();
    if (textoBusqueda.isEmpty()) {
        reiniciarBusqueda();
    } else {
        buscarEnTabla(textoBusqueda);
    }
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
      String textoBusqueda = jTextField1.getText().trim();
    if (textoBusqueda.isEmpty()) {
        reiniciarBusqueda();
    } else {
        buscarEnTabla(textoBusqueda);
    }
    }//GEN-LAST:event_jButton1ActionPerformed



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
