/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package instituto.perfil;

import instituto.busquedas.añadir.ImageRenderer;
import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.regex.Pattern;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author ivan.castellano
 */
public class VerArchivos extends javax.swing.JPanel {

  private int idProfesor;
  private static final String CARPETA_ARCHIVOS = "src/archivos/";
  private GestorArchivos gestorArchivos;

   
   public VerArchivos(int idProfesor) {
        System.out.println("Constructor VerArchivos llamado con idProfesor: " + idProfesor);
    this.idProfesor = idProfesor;
    this.gestorArchivos = new GestorArchivos();
    initComponents();
    mostrarArchivos();
    addTableMouseListener(); // Llamar al método para agregar el MouseListener a la tabla
}

private void addTableMouseListener() {
    jTable1.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2) { // Detectar doble clic
                JTable table = (JTable) e.getSource();
                int row = table.getSelectedRow();
                int column = table.getSelectedColumn();
                if (column == 3) { // Si se hace clic en la columna "Descargar"
                    int response = JOptionPane.showConfirmDialog(null, "¿Desea descargar el archivo?", "Confirmar descarga", JOptionPane.YES_NO_OPTION);
                    if (response == JOptionPane.YES_OPTION) {
                        System.out.println("Descargando archivo...");
                        String nombreArchivo = (String) table.getValueAt(row, 1); // Obtener el nombre del archivo real
                        descargarArchivo(nombreArchivo); // Llamar al método para descargar el archivo
                    }
                }
            }
        }
    });
}

private void descargarArchivo(String nombreArchivo) {
    String rutaArchivo = CARPETA_ARCHIVOS + idProfesor + "/" + nombreArchivo;
    File archivo = new File(rutaArchivo);
    if (archivo.exists()) {
        try {
            Desktop.getDesktop().open(archivo); // Abrir el archivo con la aplicación predeterminada
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al abrir el archivo: " + ex.getMessage());
        }
    } else {
        JOptionPane.showMessageDialog(this, "El archivo no existe");
    }
}
 private void mostrarArchivos() {
    ImageIcon downloadIcon = new ImageIcon(getClass().getResource("/imagenes/download.png"));
    ImageIcon editIcon = new ImageIcon(getClass().getResource("/imagenes/editar.png"));
    ImageIcon deleteIcon = new ImageIcon(getClass().getResource("/imagenes/delete.png"));
    
    // Obtener la lista de archivos del usuario
    List<?> archivosUsuario = gestorArchivos.obtenerArchivosUsuario(idProfesor, true); // Devuelve objetos Archivo

    // Crear modelo de tabla con cuatro columnas
    DefaultTableModel model = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            // Hacer que todas las celdas sean no editables
            return false;
        }
    };
    model.addColumn("Nombre del Archivo Personalizado");
    model.addColumn("Nombre del Archivo Real");
    model.addColumn("Extensión");
    model.addColumn("Descargar");
    model.addColumn("EDITAR");
    model.addColumn("ELIMINAR");

    // Agregar archivos al modelo de tabla
    for (Object archivo : archivosUsuario) {
        if (archivo instanceof Archivo) {
            Archivo archivoObj = (Archivo) archivo;
            model.addRow(new Object[]{archivoObj.getNombrePersonalizado(), archivoObj.getNombreReal(), archivoObj.getExtension(), downloadIcon, editIcon, deleteIcon}); // true indica que hay un archivo descargable
        }
    }

    // Establecer modelo de tabla
    jTable1.setModel(model);

    // Configurar ImageRenderer para la columna "Descargar"
//    int columnIndexDescargar = 3; // Índice de la columna "Descargar"
//    TableColumn columnDescargar = jTable1.getColumnModel().getColumn(columnIndexDescargar);
//    columnDescargar.setCellRenderer(new ImageRenderer(downloadIcon, 20));
//
//    // Configurar ImageRenderer para la columna "EDITAR" y "ELIMINAR"
//    int columnIndexEditar = 4; // Índice de la columna "EDITAR"
//    TableColumn columnEditar = jTable1.getColumnModel().getColumn(columnIndexEditar);
//    columnEditar.setCellRenderer(new ImageRenderer(editIcon, 20));
//
//    int columnIndexEliminar = 5; // Índice de la columna "ELIMINAR"
//    TableColumn columnEliminar = jTable1.getColumnModel().getColumn(columnIndexEliminar);
//    columnEliminar.setCellRenderer(new ImageRenderer(deleteIcon, 20));
}
      private void buscarEnTabla(String textoBusqueda) {
    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
    TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
    jTable1.setRowSorter(sorter);
    
    RowFilter<DefaultTableModel, Object> rowFilter = RowFilter.regexFilter("(?i)" + Pattern.quote(textoBusqueda));
    sorter.setRowFilter(rowFilter);
}
      private void reiniciarBusqueda() {
    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
    TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
    jTable1.setRowSorter(sorter);
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jButton1.setText("buscar");
        jButton1.setToolTipText("");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

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

        jButton2.setText("subir archivos");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("reiniciar tabla");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTextField1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1)))
                .addContainerGap(74, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 391, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addContainerGap(16, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setMultiSelectionEnabled(true); // Permitir selección múltiple de archivos
    int returnValue = fileChooser.showOpenDialog(this);

    if (returnValue == JFileChooser.APPROVE_OPTION) {
        File[] selectedFiles = fileChooser.getSelectedFiles();

        // Subir cada archivo seleccionado
        for (File file : selectedFiles) {
            byte[] contenidoArchivo;
            try {
                contenidoArchivo = Files.readAllBytes(file.toPath());
                gestorArchivos.subirArchivo(idProfesor, file.getName(), contenidoArchivo);
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al subir archivo: " + e.getMessage());
            }
        }

        // Actualizar la tabla de archivos después de subir los archivos
        mostrarArchivos();
    }
    }//GEN-LAST:event_jButton2ActionPerformed

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

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
         reiniciarBusqueda();
    }//GEN-LAST:event_jButton3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
