/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package instituto.aulas;

import instituto.Principal;
import java.sql.*;
import java.text.SimpleDateFormat;
import calendar.calendar.Calendar;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import calendar.calendar.model.ModelDate;
import calendar.calendar.utils.CalendarSelectedListener;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.util.Locale;
import javax.swing.SpinnerNumberModel;
/**
 *
 * @author icast
 */
public class SalaDeReuniones extends javax.swing.JFrame {
    
 private static Principal principalRef;
  private static int idProfesor;
  private String fechaString;
  private int idAula = 7;
 
    public SalaDeReuniones(Principal principalRef, int idProfesor) {
        this.principalRef = principalRef;
        this.idProfesor = idProfesor;
        initComponents();
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(0, 0, 3, 1);
        jSpinner1.setModel(spinnerModel);
         addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                principalRef.setEnabled(true);
            }
        });
         calendar1.addCalendarSelectedListener(new CalendarSelectedListener() {
         public void selected(MouseEvent evt,ModelDate date){
              fechaString = date.toDate().toString();
         }
         });
    }
private void insertarDatos(java.sql.Date fechaSQL, int numeroHoras) {
    try {
        if (fechaSQL == null || "Seleccione una hora".equals(jComboBox1.getSelectedItem())) {
            JOptionPane.showMessageDialog(null, "Por favor, seleccione una fecha y hora válidas.", "Campos Incompletos", JOptionPane.WARNING_MESSAGE);
            return; 
        }
        
        Connection conn = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/", "SA", "");
        
        String horaInicio = (String) jComboBox1.getSelectedItem();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        java.util.Date parsedDate = sdf.parse(horaInicio);
        
        for (int i = 0; i < numeroHoras; i++) {
            Time horaReservaTime = new Time(parsedDate.getTime());
            java.util.Calendar cal = java.util.Calendar.getInstance();
            cal.setTime(parsedDate);
            cal.add(java.util.Calendar.MINUTE, 55);
            Time horaFinReservaTime = new Time(cal.getTimeInMillis());

            if (existeReserva(idAula, fechaSQL, horaReservaTime, horaFinReservaTime)) {
                JOptionPane.showMessageDialog(null, "El aula ya está reservada para la fecha y hora seleccionadas.", "Aula Reservada", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String sql = "INSERT INTO PUBLIC.INSTITUTO.RESERVAS (ID_AULA, FECHA_RESERVA, ID_PROFESOR, HORA_RESERVA, HORA_FIN_RESERVA) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);

            statement.setInt(1, idAula);
            statement.setDate(2, fechaSQL); 
            statement.setInt(3, idProfesor);
            statement.setTime(4, horaReservaTime); 
            statement.setTime(5, horaFinReservaTime); 

            statement.executeUpdate();
            statement.close();

            parsedDate = cal.getTime(); // Incrementa la hora para la próxima iteración
        }
        
        conn.close();
        
        JOptionPane.showMessageDialog(null, "El aula ha sido reservada correctamente.", "Reserva Exitosa", JOptionPane.INFORMATION_MESSAGE);
        
        System.out.println("Datos insertados correctamente.");
    } catch (SQLException ex) {
        System.out.println("Error al insertar datos: " + ex.getMessage());
    } catch (ParseException ex) {
        System.out.println("Error al convertir la hora: " + ex.getMessage());
    }
}

private boolean existeReserva(int idAula, java.sql.Date fechaReserva, Time horaInicio, Time horaFin) {
    boolean existe = false;
    try {
        Connection conn = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/", "SA", "");
        
        String sql = "SELECT COUNT(*) AS num_reservas FROM PUBLIC.INSTITUTO.RESERVAS WHERE ID_AULA = ? AND FECHA_RESERVA = ? AND ((HORA_RESERVA >= ? AND HORA_RESERVA < ?) OR (HORA_FIN_RESERVA > ? AND HORA_FIN_RESERVA <= ?) OR (HORA_RESERVA <= ? AND HORA_FIN_RESERVA >= ?))";
        PreparedStatement statement = conn.prepareStatement(sql);
        
        statement.setInt(1, idAula); 
        statement.setDate(2, fechaReserva); 
        statement.setTime(3, horaInicio); 
        statement.setTime(4, horaFin); 
        statement.setTime(5, horaInicio); 
        statement.setTime(6, horaFin); 
        statement.setTime(7, horaInicio); 
        statement.setTime(8, horaFin); 
        
        ResultSet resultSet = statement.executeQuery();
        
        if (resultSet.next()) {
            int numReservas = resultSet.getInt("num_reservas");
            if (numReservas > 0) {
                existe = true;
            }
        }
       
        resultSet.close();
        statement.close();
        conn.close();
    } catch (SQLException ex) {
        System.out.println("Error al verificar la existencia de reserva: " + ex.getMessage());
    }
    return existe;
}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jSpinner1 = new javax.swing.JSpinner();
        jButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        calendar1 = new calendar.calendar.Calendar();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("reservar sala de reuniones");

        jLabel1.setText("hora de reserva");

        jLabel2.setText("numero de horas");

        jButton1.setText("reservar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(204, 204, 204)));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(calendar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(calendar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(10, Short.MAX_VALUE))
        );

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione una hora", "8:30:00", "9:30:00", "10:25:00", "11:45:00", "12:40:00", "13:35:00" }));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/dayperson.png"))); // NOI18N
        jLabel3.setToolTipText("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))))
                .addContainerGap(82, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1)))
                .addGap(0, 82, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
  if (fechaString == null || fechaString.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Por favor, seleccione una fecha válida.", "Fecha Incompleta", JOptionPane.WARNING_MESSAGE);
        return; 
    }
    int numeroHoras = (int) jSpinner1.getValue(); 
    if (numeroHoras < 1 || numeroHoras > 3) {
        JOptionPane.showMessageDialog(null, "Número de horas no válido. Por favor, seleccione un valor entre 1 y 3.", "Número de Horas No Válido", JOptionPane.WARNING_MESSAGE);
        return; 
    }
    SimpleDateFormat formato = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);

    try {
        java.util.Date fechaUtil = formato.parse(fechaString);
        java.sql.Date fechaSQL = new java.sql.Date(fechaUtil.getTime()); 

        String horaInicio = (String) jComboBox1.getSelectedItem();

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        java.util.Date parsedDate = sdf.parse(horaInicio);

        // Calcular la hora final de la reserva
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(parsedDate);
        cal.add(java.util.Calendar.MINUTE, 50 * numeroHoras);
        java.util.Date horaFin = cal.getTime();

        // Obtener la hora máxima permitida
        java.util.Date maxHour = sdf.parse("14:30");
        System.out.println(maxHour);
         System.out.println(horaFin);
        // Validar si la hora final excede la hora máxima permitida
        if (horaFin.after(maxHour) || horaFin.equals(maxHour)) {
            JOptionPane.showMessageDialog(null, "El horario seleccionado excede o coincide con el límite máximo de reserva (2:30 PM). Por favor, seleccione un horario válido.", "Horario No Válido", JOptionPane.WARNING_MESSAGE);
            return;
        }

        for (int i = 0; i < numeroHoras; i++) {
            Time horaReservaTime = new Time(parsedDate.getTime());
            cal.setTime(parsedDate);
            cal.add(java.util.Calendar.MINUTE, 55);
            Time horaFinReservaTime = new Time(cal.getTimeInMillis());
           
            if (existeReserva(idAula, fechaSQL, horaReservaTime, horaFinReservaTime)) {
                JOptionPane.showMessageDialog(null, "El aula ya está reservada para la fecha y hora seleccionadas.", "Aula Reservada", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }

        insertarDatos(fechaSQL, numeroHoras);
    } catch (ParseException ex) {
        JOptionPane.showMessageDialog(null, "Por favor, seleccione una hora válida.", "Campos Incompletos", JOptionPane.WARNING_MESSAGE);
        return;
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
            java.util.logging.Logger.getLogger(SalaDeReuniones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SalaDeReuniones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SalaDeReuniones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SalaDeReuniones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SalaDeReuniones(principalRef,idProfesor).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private calendar.calendar.Calendar calendar1;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSpinner jSpinner1;
    // End of variables declaration//GEN-END:variables
}
