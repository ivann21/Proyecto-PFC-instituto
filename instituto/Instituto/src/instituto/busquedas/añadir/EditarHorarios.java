/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package instituto.busquedas.añadir;

import instituto.busquedas.Busqueda;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class EditarHorarios extends javax.swing.JFrame {

    private int idHorario;
    private String asignaturaSeleccionada;
    private String profesorSeleccionado;
    private String aulaSeleccionada;
    private String diaSeleccionado;
    private String horaInicioSeleccionada;
    private String horaFinSeleccionada;
    private Busqueda parentPanel;
    
    public EditarHorarios(Object[] horariosData,Busqueda parentPanel) {
    this.parentPanel = parentPanel;
    initComponents();
    populateComboBoxes();
    asignaturaSeleccionada = horariosData[3].toString(); // Asignatura
    profesorSeleccionado = horariosData[4].toString(); // Profesor
    aulaSeleccionada = horariosData[5].toString(); // Aula
    diaSeleccionado = horariosData[0].toString(); // Día de la semana
    horaInicioSeleccionada = horariosData[1].toString(); // Hora de inicio
    horaFinSeleccionada = horariosData[2].toString(); // Hora de fin
    
    System.out.println(asignaturaSeleccionada + " - " + profesorSeleccionado + " - " + aulaSeleccionada + " - " + diaSeleccionado + " - " + horaInicioSeleccionada + " - " + horaFinSeleccionada);
    
    setSelectedItem(jComboBox1, asignaturaSeleccionada); // Asignatura
    setSelectedItem(jComboBox2, profesorSeleccionado); // Profesor
    setSelectedItem(jComboBox3, aulaSeleccionada); // Aula
    jComboBox4.setSelectedItem(diaSeleccionado); // Día de la semana
    jComboBox5.setSelectedItem(horaInicioSeleccionada); // Hora de inicio
    jComboBox6.setSelectedItem(horaFinSeleccionada); // Hora de fin

    String asignatura = asignaturaSeleccionada.split(" ")[0];
    String profesor = profesorSeleccionado.split(" ")[0];
    String aula = aulaSeleccionada.split(" ")[0];
    String dia = diaSeleccionado;
    LocalTime horaInicio = LocalTime.parse(horaInicioSeleccionada);
    LocalTime horaFin = LocalTime.parse(horaFinSeleccionada);
    idHorario = obtenerIdHorario(asignatura, profesor, aula, dia, horaInicio, horaFin);
}

private void setSelectedItem(JComboBox<String> comboBox, String selectedItem) {
    for (int i = 0; i < comboBox.getItemCount(); i++) {
        String item = comboBox.getItemAt(i);
        if (item.endsWith(" - " + selectedItem)) {
            comboBox.setSelectedItem(item);
            break;
        }
    }
}

private void populateComboBoxes() {
    jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(getItems("ASIGNATURAS")));
    jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(getItems("PROFESORES")));
    jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(getItems("AULAS")));
}

private String[] getItems(String tableName) {
    List<String> items = new ArrayList<>();
    try (Connection conn = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/", "SA", "");
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery("SELECT * FROM PUBLIC.INSTITUTO." + tableName)) {

        while (rs.next()) {
            items.add(rs.getString(1) + " - " + rs.getString(2));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return items.toArray(new String[0]);
}

    private String[] getHours() {
        List<String> hours = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime start = LocalTime.of(8, 30);
        for (int i = 0; i < 12; i++) {
            hours.add(start.plusMinutes(i * 55).format(formatter));
        }
        return hours.toArray(new String[0]);
    }
    private boolean isDuplicate(Connection conn, int aulaId, String dia, LocalTime horaInicio, LocalTime horaFin, int horarioId) throws SQLException {
    String query = "SELECT COUNT(*) FROM PUBLIC.INSTITUTO.HORARIOS WHERE \"ID aula\" = ? AND \"dia de la semana\" = ? AND ((\"hora de inicio\" >= ? AND \"hora de inicio\" < ?) OR (\"hora de fin\" > ? AND \"hora de fin\" <= ?)) AND \"ID horario\" <> ?";
    try (PreparedStatement pstmt = conn.prepareStatement(query)) {
        pstmt.setInt(1, aulaId);
        pstmt.setString(2, dia);
        pstmt.setTime(3, Time.valueOf(horaInicio));
        pstmt.setTime(4, Time.valueOf(horaFin));
        pstmt.setTime(5, Time.valueOf(horaInicio));
        pstmt.setTime(6, Time.valueOf(horaFin));
        pstmt.setInt(7, horarioId);
        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
    }
    return false;
}
  private int obtenerIdHorario(String nombreAsignatura, String nombreProfesor, String nombreAula, String dia, LocalTime horaInicio, LocalTime horaFin) {
    int idHorario = -1; // Valor por defecto en caso de que no se encuentre ningún horario con los datos especificados
    try {
        Class.forName("org.hsqldb.jdbc.JDBCDriver");
        try (Connection connection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/", "SA", "")) {
            String consultaSQL = "SELECT \"ID horario\" FROM PUBLIC.INSTITUTO.HORARIOS " +
                                 "WHERE \"ID asignatura\" = (SELECT \"ID Asignatura\" FROM PUBLIC.INSTITUTO.ASIGNATURAS WHERE NOMBRE = ? LIMIT 1) "  +
                                 "AND \"ID profesor\" = (SELECT \"ID profesor\" FROM PUBLIC.INSTITUTO.PROFESORES WHERE NOMBRE = ? LIMIT 1) " +
                                 "AND \"ID aula\" = (SELECT \"ID aula\" FROM PUBLIC.INSTITUTO.AULAS WHERE NOMBRE = ? LIMIT 1)" +
                                 "AND \"dia de la semana\" = ? AND \"hora de inicio\" = ? AND \"hora de fin\" = ?";
            try (PreparedStatement stmt = connection.prepareStatement(consultaSQL)) {
                stmt.setString(1, nombreAsignatura);
                stmt.setString(2, nombreProfesor);
                stmt.setString(3, nombreAula);
                stmt.setString(4, dia);
                stmt.setTime(5, Time.valueOf(horaInicio));
                stmt.setTime(6, Time.valueOf(horaFin));
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    idHorario = rs.getInt("ID horario");
                }
            }
        }
    } catch (ClassNotFoundException | SQLException ex) {
        ex.printStackTrace();
    }
    return idHorario;
}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jComboBox1 = new javax.swing.JComboBox<>();
        jComboBox2 = new javax.swing.JComboBox<>();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jComboBox5 = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jComboBox6 = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jComboBox7 = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Editar Horarios");
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        getContentPane().add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(19, 48, 244, -1));

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        getContentPane().add(jComboBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(19, 102, 244, -1));

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        getContentPane().add(jComboBox3, new org.netbeans.lib.awtextra.AbsoluteConstraints(19, 156, 244, -1));

        jLabel1.setText("Asignatura");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(19, 26, -1, -1));

        jLabel2.setText("Profesor");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(19, 82, -1, -1));

        jLabel3.setText("Aula");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(19, 134, -1, -1));

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Lunes", "Martes", "Miercoles", "Jueves", "Viernes" }));
        getContentPane().add(jComboBox4, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 50, 244, -1));

        jLabel4.setText("Dia de la semana");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 30, -1, -1));

        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "8:30:00", "9:30:00", "10:25:00", "11:45:00", "12:40:00", "13:35:00" }));
        getContentPane().add(jComboBox5, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 110, 244, -1));

        jLabel5.setText("hora de inicio");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 90, -1, -1));

        jLabel6.setText("hora de fin");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 140, -1, -1));

        jComboBox6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "09:25:00", "10:20:00", "11:15:00", "12:35:00", "13:30:00", "14:25:00" }));
        getContentPane().add(jComboBox6, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 160, 244, -1));

        jButton1.setText("editar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 200, -1, -1));

        jLabel7.setText("Año");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 26, -1, -1));

        jComboBox7.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2" }));
        getContentPane().add(jComboBox7, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 48, 40, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       
        int asignaturaId = Integer.parseInt(jComboBox1.getSelectedItem().toString().split(" ")[0]);
        int profesorId = Integer.parseInt(jComboBox2.getSelectedItem().toString().split(" ")[0]);
        int aulaId = Integer.parseInt(jComboBox3.getSelectedItem().toString().split(" ")[0]);
        String dia = jComboBox4.getSelectedItem().toString();
        LocalTime horaInicio = LocalTime.parse(jComboBox5.getSelectedItem().toString());
        LocalTime horaFin = LocalTime.parse(jComboBox6.getSelectedItem().toString());
    try (Connection conn = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/", "SA", "")) {
        while (horaInicio.isBefore(horaFin)) {
            LocalTime horaFinClase = horaInicio.plusMinutes(55);
            if (horaFinClase.isAfter(horaFin)) {
                horaFinClase = horaFin;
            }

            if (isDuplicate(conn, aulaId, dia, horaInicio, horaFinClase, idHorario)) {
                JOptionPane.showMessageDialog(this, "Horario duplicado. No se ha añadido.");
                return;
            }

            String updateSQL = "UPDATE PUBLIC.INSTITUTO.HORARIOS SET \"ID asignatura\"=?, \"ID profesor\"=?, \"ID aula\"=?, \"dia de la semana\"=?, \"hora de inicio\"=?, \"hora de fin\"=? WHERE \"ID horario\"=?";
            try (PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {
                pstmt.setInt(1, asignaturaId);
                pstmt.setInt(2, profesorId);
                pstmt.setInt(3, aulaId);
                pstmt.setString(4, dia);
                pstmt.setTime(5, Time.valueOf(horaInicio));
                pstmt.setTime(6, Time.valueOf(horaFinClase));
                pstmt.setInt(7, idHorario);
                pstmt.executeUpdate();
            }
            horaInicio = horaFinClase;
        }
        JOptionPane.showMessageDialog(this, "Horario editado correctamente.");
         dispose();
        if (parentPanel != null) {
             String selectedTable = "horarios";
           parentPanel. mostrarDatosEnJTable(selectedTable);
       }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(EditarHorarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditarHorarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditarHorarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditarHorarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Object[] horarioData = {};
                new EditarHorarios(horarioData,null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JComboBox<String> jComboBox6;
    private javax.swing.JComboBox<String> jComboBox7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    // End of variables declaration//GEN-END:variables
}
