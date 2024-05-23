//package instituto.perfil;
//
//import javax.swing.*;
//import javax.swing.table.DefaultTableModel;
//import java.awt.*;
//import java.sql.*;
//
//public class ProfesoresSinRol extends JFrame {
//
//    public ProfesoresSinRol() {
//        initComponents();
//        cargarProfesoresSinRol();
//    }
//
//    private void initComponents() {
//        JScrollPane jScrollPane1 = new JScrollPane();
//        jTable1 = new JTable();
//        JButton btnAddRole = new JButton("Añadir Rol");
//
//        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
//
//        jTable1.setModel(new DefaultTableModel(
//                new Object[][]{},
//                new String[]{"Nombre Profesor"}
//        ));
//        jScrollPane1.setViewportView(jTable1);
//
//        btnAddRole.addActionListener(e -> mostrarDialogoAgregarRol());
//
//        GroupLayout layout = new GroupLayout(getContentPane());
//        getContentPane().setLayout(layout);
//        layout.setHorizontalGroup(
//                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
//                        .addGroup(layout.createSequentialGroup()
//                                .addContainerGap()
//                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
//                                        .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
//                                        .addGroup(layout.createSequentialGroup()
//                                                .addComponent(btnAddRole)
//                                                .addGap(0, 0, Short.MAX_VALUE)))
//                                .addContainerGap())
//        );
//        layout.setVerticalGroup(
//                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
//                        .addGroup(layout.createSequentialGroup()
//                                .addContainerGap()
//                                .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
//                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
//                                .addComponent(btnAddRole)
//                                .addContainerGap())
//        );
//
//        pack();
//    }
//
//    private void cargarProfesoresSinRol() {
//        DefaultTableModel tableModel = (DefaultTableModel) jTable1.getModel();
//
//        try {
//            // Conectar a la base de datos
//            Connection con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/", "SA", "");
//
//            // Consulta SQL para obtener los profesores sin rol asignado
//            String query = "SELECT P.NOMBRE FROM INSTITUTO.PROFESORES P WHERE P.\"ID profesor\" NOT IN "
//                    + "(SELECT ID_PROFESOR FROM INSTITUTO.ASIGNACION_ROLES)";
//
//            PreparedStatement pst = con.prepareStatement(query);
//
//            ResultSet rs = pst.executeQuery();
//
//            while (rs.next()) {
//                String nombreProfesor = rs.getString("NOMBRE");
//                tableModel.addRow(new Object[]{nombreProfesor});
//            }
//
//            // Cerrar la conexión y liberar recursos
//            rs.close();
//            pst.close();
//            con.close();
//            
//            // Si no hay profesores sin rol, cierra la ventana
//            if (tableModel.getRowCount() == 0) {
//                dispose();
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void mostrarDialogoAgregarRol() {
//        // Crear y mostrar el diálogo para seleccionar el rol
//        SeleccionRolDialog dialog = new SeleccionRolDialog(this, true);
//        dialog.setVisible(true);
//
//        // Actualizar la tabla si se añadió un rol
//        if (dialog.isRolAgregado()) {
//            DefaultTableModel tableModel = (DefaultTableModel) jTable1.getModel();
//            tableModel.setRowCount(0); // Limpiar la tabla
//            cargarProfesoresSinRol(); // Recargar los profesores sin rol
//        }
//    }
//
//    public static void main(String[] args) {
//        EventQueue.invokeLater(() -> {
//            try {
//                ProfesoresSinRol frame = new ProfesoresSinRol();
//                frame.setVisible(true);
//                frame.setLocationRelativeTo(null); // Centrar en la pantalla
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
//    }
//
//    private javax.swing.JTable jTable1;
//}
