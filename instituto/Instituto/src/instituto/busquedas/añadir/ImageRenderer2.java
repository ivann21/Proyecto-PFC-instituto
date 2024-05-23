/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package instituto.busquedas.añadir;

import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class ImageRenderer2 extends DefaultTableCellRenderer {
    private ImageIcon trueIcon;
    private ImageIcon falseIcon;
    private int defaultSize; // Tamaño por defecto

    // Constructor con un solo ImageIcon para ambos casos
    public ImageRenderer2(ImageIcon icon, int defaultSize) {
        this.trueIcon = icon;
        this.falseIcon = icon; // Utiliza la misma imagen para true y false
        this.defaultSize = defaultSize;
    }

    public ImageRenderer2(ImageIcon trueIcon, ImageIcon falseIcon, int defaultSize) {
        this.trueIcon = trueIcon;
        this.falseIcon = falseIcon;
        this.defaultSize = defaultSize;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        // Verificar si el valor es un ImageIcon
        if (value instanceof ImageIcon) {
            ImageIcon icon = (ImageIcon) value;
            // Ajustar el tamaño del ícono
            ImageIcon scaledIcon = new ImageIcon(icon.getImage().getScaledInstance(defaultSize, defaultSize, java.awt.Image.SCALE_SMOOTH));
            label.setIcon(scaledIcon);
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setText(""); // Eliminar cualquier texto que se muestre en la celda
        }

        return label;
    }
}