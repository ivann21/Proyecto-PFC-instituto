/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package instituto.aulas;

import java.awt.Component;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;
import java.sql.PreparedStatement;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;


/**
 *
 * @author ivan.castellano
 */
public class A302 extends javax.swing.JFrame {
    
    private static Connection connection = null;
    private static Statement statement = null;
    private static ResultSet resultSet = null;
    
    public A302() {
        initComponents();
        connectToDatabase();
                comboBoxDiaSemana.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Cuando se selecciona un nuevo día de la semana, actualiza los datos en la JTable
                mostrarDatosEnJTable();
            }
        });

        // Añade el JComboBox al contenedor (puede ser un JPanel o un contenedor similar)
        getContentPane().add(comboBoxDiaSemana);

        // Carga los datos inicialmente
        mostrarDatosEnJTable();
        mostrarDatosEnJTable2();
    }

        // Método para mostrar los datos en la JTable según la selección en el JComboBox
    private void mostrarDatosEnJTable() {
        String diaSeleccionado = (String) comboBoxDiaSemana.getSelectedItem();
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Asignatura");
        model.addColumn("Profesor");
        model.addColumn("Apellido");
        model.addColumn("Hora de Inicio");
        model.addColumn("Hora de Fin");

        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");

             try (Connection connection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/", "SA", "")) {
                String consultaSQL = "SELECT A.NOMBRE AS Asignatura, P.NOMBRE AS Profesor, P.APELLIDO AS Profesor, H.\"dia de la semana\" AS Dia_Semana, " +
                                     "H.\"hora de inicio\" AS Hora_Inicio, H.\"hora de fin\" AS Hora_Fin " +
                                     "FROM PUBLIC.INSTITUTO.HORARIOS H " +
                                     "INNER JOIN PUBLIC.INSTITUTO.ASIGNATURAS A ON H.\"ID asignatura\" = A.\"ID Asignatura\" " +
                                     "INNER JOIN PUBLIC.INSTITUTO.PROFESORES P ON H.\"ID profesor\" = P.\"ID profesor\" " +
                                     "WHERE H.\"ID aula\" = 14 AND H.\"dia de la semana\" = ?  ORDER BY H.\"hora de inicio\" ASC";
                try (PreparedStatement stmt = connection.prepareStatement(consultaSQL)) {
                    stmt.setString(1, diaSeleccionado);
                    try (ResultSet rs = stmt.executeQuery()) {
                        while (rs.next()) {
                            model.addRow(new Object[]{
                                    rs.getString("Asignatura"),
                                    rs.getString("Profesor"),
                                    rs.getString("Apellido"),
                                    rs.getString("Hora_Inicio"),
                                    rs.getString("Hora_Fin")
                            });
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            jTable1.setModel(model);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void mostrarDatosEnJTable2() {
    DefaultTableModel model = new DefaultTableModel();
    model.addColumn("Capacidad");
    model.addColumn("Pizarra");
    model.addColumn("Ordenadores"); 

    try {
        Class.forName("org.hsqldb.jdbc.JDBCDriver");

         try (Connection connection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/", "SA", "")) {
            String consultaSQL = "SELECT NOMBRE, CAPACIDAD_AULA, PIZARRA, ORDENADORES " +
                                 "FROM PUBLIC.INSTITUTO.AULAS " +
                                 "WHERE \"ID aula\" = 14";
            try (PreparedStatement stmt = connection.prepareStatement(consultaSQL)) {
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        int capacidad = rs.getInt("CAPACIDAD_AULA");
                        boolean tienePizarra = rs.getBoolean("PIZARRA");
                        boolean tieneOrdenadores = rs.getBoolean("ORDENADORES");
                        ImageIcon pizarraIcon;
                        ImageIcon ordenadoresIcon;
                        if (tienePizarra) {
                            pizarraIcon = new ImageIcon(getClass().getResource("/imagenes/pizarra_true.png"));
                        } else {
                            pizarraIcon = new ImageIcon(getClass().getResource("/imagenes/pizarra_false.png"));
                        }
                        if (tieneOrdenadores) {
                            ordenadoresIcon = new ImageIcon(getClass().getResource("/imagenes/ordenadores_true.png"));
                        } else {
                            ordenadoresIcon = new ImageIcon(getClass().getResource("/imagenes/ordenadores_false.png"));
                        }
                        model.addRow(new Object[]{
                                capacidad,
                                pizarraIcon,
                                ordenadoresIcon
                        });
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        jTable2.setModel(model);
        int rowHeight = 100;
        jTable2.setRowHeight(rowHeight);
        
// Configurar el renderer personalizado para las columnas "Pizarra" y "Ordenadores"
DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        label.setHorizontalAlignment(JLabel.CENTER); // Centrar la imagen en la celda
        if (value instanceof ImageIcon) {
            ImageIcon icon = (ImageIcon) value;
            // Escalar la imagen al tamaño máximo deseado
            int maxSize = 60; // Tamaño máximo en píxeles
            int width = icon.getIconWidth();
            int height = icon.getIconHeight();
            if (width > maxSize || height > maxSize) {
                if (width > height) {
                    height = (int) (((double) height / width) * maxSize);
                    width = maxSize;
                } else {
                    width = (int) (((double) width / height) * maxSize);
                    height = maxSize;
                }
                icon = new ImageIcon(icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
            }
            label.setIcon(icon);
            label.setText(""); // Limpiar el texto para que solo se muestre el icono
        }
        return label;
    }
};

// Aplicar el renderer personalizado a las columnas "Pizarra" y "Ordenadores"
jTable2.getColumnModel().getColumn(1).setCellRenderer(renderer); // Columna "Pizarra"
jTable2.getColumnModel().getColumn(2).setCellRenderer(renderer); // Columna "Ordenadores"

// Deshabilitar la selección de filas en la tabla
jTable2.setRowSelectionAllowed(false);
        
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    }
}
     private static void connectToDatabase() {
        try {
            // Establecer la conexión con la base de datos
            connection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/", "SA", "");
            statement = connection.createStatement();
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
        comboBoxDiaSemana = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

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

        comboBoxDiaSemana.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Lunes", "Martes", "Miercoles ", "Jueves", "Viernes" }));

        jLabel1.setText("Dia de la semana: ");

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(jTable2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboBoxDiaSemana, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 188, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 594, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 594, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboBoxDiaSemana, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
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
            java.util.logging.Logger.getLogger(A302.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(A302.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(A302.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(A302.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new A302().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> comboBoxDiaSemana;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    // End of variables declaration//GEN-END:variables
}
