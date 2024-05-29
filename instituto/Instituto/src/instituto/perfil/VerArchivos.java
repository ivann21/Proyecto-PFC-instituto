/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package instituto.perfil;

import instituto.busquedas.añadir.ImageRenderer2;
import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.regex.Pattern;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
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
        addTableMouseListener();
        jTable1.setRowHeight(30);
    }

    private void addTableMouseListener() {
        jTable1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JTable table = (JTable) e.getSource();
                int row = table.getSelectedRow();
                int column = table.convertColumnIndexToModel(table.getSelectedColumn()); 

                if (column == 3 && e.getClickCount() == 1) {
                    int response = JOptionPane.showConfirmDialog(null, "¿Ver vista previa?", "ver archivo", JOptionPane.YES_NO_OPTION);
                    if (response == JOptionPane.YES_OPTION) {
                        String nombreArchivo = (String) table.getValueAt(row, 0);
                        verArchivo(nombreArchivo);
                    } 
                } else if (column == 4 && e.getClickCount() == 2) {
                    int response = JOptionPane.showConfirmDialog(null, "¿Desea descargar el archivo?", "Confirmar descarga", JOptionPane.YES_NO_OPTION);
                    if (response == JOptionPane.YES_OPTION) {
                        String nombreArchivo = (String) table.getValueAt(row, 0);
                         descargarArchivo(nombreArchivo);
                    }
                } else if (column == 5 && e.getClickCount() == 1) {
                    int response = JOptionPane.showConfirmDialog(null, "¿Desea eliminar el archivo?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
                    if (response == JOptionPane.YES_OPTION) {
                        String nombreArchivo = (String) table.getValueAt(row, 0);
                        eliminarArchivo(nombreArchivo);
                    }
                }

                new SwingWorker<Void, Void>() {
                    @Override
                    protected Void doInBackground() throws Exception {
                        Thread.sleep(1000);
                        return null;
                    }

                    @Override
                    protected void done() {
                        SwingUtilities.invokeLater(() -> {
                            mostrarArchivos();
                            reiniciarBusqueda();
                        });
                    }
                }.execute();
            }
        });
    }

    private void eliminarArchivo(String nombreArchivo) {
        String rutaArchivo = CARPETA_ARCHIVOS + idProfesor + "/" + nombreArchivo;
        File archivo = new File(rutaArchivo);

        if (archivo.exists()) {
            if (archivo.delete()) {
                gestorArchivos.eliminarArchivo(idProfesor, rutaArchivo);
                System.out.println("Archivo eliminado: " + rutaArchivo);
                JOptionPane.showMessageDialog(this, "Archivo eliminado exitosamente.");
                mostrarArchivos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar el archivo.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "El archivo no existe.");
        }
    }
    private void descargarArchivo(String nombreArchivo) {
      JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar archivo como");

        // Agregar filtro de extensión para archivos de imagen
        FileNameExtensionFilter imageFilter = new FileNameExtensionFilter("Archivos de imagen", "jpg", "png", "gif");
        fileChooser.setFileFilter(imageFilter);

        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            nombreArchivo = fileToSave.getName();

            byte[] contenidoArchivo = gestorArchivos.descargarArchivo(idProfesor, nombreArchivo);
            if (contenidoArchivo != null) {
                try (FileOutputStream fos = new FileOutputStream(fileToSave)) {
                    fos.write(contenidoArchivo);
                    JOptionPane.showMessageDialog(this, "Archivo guardado con éxito: " + fileToSave.getAbsolutePath());
                } catch (IOException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error al guardar el archivo: " + e.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(this, "El archivo no existe o no se pudo descargar.");
            }
        }
    }

    private void verArchivo(String nombreArchivo) {
        String rutaArchivo = CARPETA_ARCHIVOS + idProfesor + "/" + nombreArchivo;
        File archivo = new File(rutaArchivo);
        if (archivo.exists()) {
            try {
                Desktop.getDesktop().open(archivo);
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
        ImageIcon deleteIcon = new ImageIcon(getClass().getResource("/imagenes/delete.png"));
        ImageIcon viewIcon = new ImageIcon(getClass().getResource("/imagenes/preview.png"));
        

        List<?> archivosUsuario = gestorArchivos.obtenerArchivosUsuario(idProfesor, true);

        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        model.addColumn("NOMBRE COMPLETO");
        model.addColumn("NOMBRE DEL ARCHIVO");
        model.addColumn("EXTENSION");
        model.addColumn("VISTA PREVIA");
        model.addColumn("DESCARGAR");
        model.addColumn("ELIMINAR");

        for (Object archivo : archivosUsuario) {
            if (archivo instanceof Archivo) {
                Archivo archivoObj = (Archivo) archivo;
                model.addRow(new Object[]{
                    archivoObj.getNombreReal(),
                    archivoObj.getNombrePersonalizado(),
                    archivoObj.getExtension(),
                    viewIcon,
                    downloadIcon,
                    deleteIcon,
                });
            }
        }

        jTable1.setModel(model);

        int columnIndexVistaPrevia = 3;
        TableColumn columnVistaPrevia = jTable1.getColumnModel().getColumn(columnIndexVistaPrevia);
        columnVistaPrevia.setCellRenderer(new ImageRenderer2(viewIcon, 20));
        
        int columnIndexDescargar = 4;
        TableColumn columnDescargar = jTable1.getColumnModel().getColumn(columnIndexDescargar);
        columnDescargar.setCellRenderer(new ImageRenderer2(downloadIcon, 20));

        int columnIndexEliminar = 5;
        TableColumn columnEliminar = jTable1.getColumnModel().getColumn(columnIndexEliminar);
        columnEliminar.setCellRenderer(new ImageRenderer2(deleteIcon, 20));
        

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        jTable1.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);

        jTable1.getColumnModel().getColumn(0).setPreferredWidth(300);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(200);
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(90);
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(100);
    }

    private void buscarEnTabla(String textoBusqueda) {
        try {
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
            jTable1.setRowSorter(sorter);

            int columnIndex = jComboBox1.getSelectedIndex() == 0 ? 1 : 2;

            RowFilter<DefaultTableModel, Object> rowFilter = RowFilter.regexFilter("(?i)" + Pattern.quote(textoBusqueda), columnIndex);
            sorter.setRowFilter(rowFilter);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

        addButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jTextField1 = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        addButton.setText("subir");
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });
        add(addButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 500, 80, -1));

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

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 730, -1));

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 30, 530, -1));

        jButton4.setText("buscar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 30, 80, -1));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "nombre", "estension" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 100, -1));

        jButton1.setText("actualizar tabla");
        jButton1.setActionCommand("tabla tabla");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 500, 130, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(true);
        int returnValue = fileChooser.showOpenDialog(this);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File[] selectedFiles = fileChooser.getSelectedFiles();

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

            mostrarArchivos();
        }
    }//GEN-LAST:event_addButtonActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        String textoBusqueda = jTextField1.getText().trim();
        if (textoBusqueda.isEmpty()) {
            reiniciarBusqueda();
        } else {
            buscarEnTabla(textoBusqueda);
        }
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
           String textoBusqueda = jTextField1.getText().trim();
        if (textoBusqueda.isEmpty()) {
            reiniciarBusqueda();
        } else {
            buscarEnTabla(textoBusqueda);
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
          reiniciarBusqueda();
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
