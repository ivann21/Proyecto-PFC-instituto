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

public class ImageRenderer extends DefaultTableCellRenderer {
    private ImageIcon trueIcon;
    private ImageIcon falseIcon;
    private int defaultSize; // Tamaño por defecto

    // Constructor con un solo ImageIcon para ambos casos
    public ImageRenderer(ImageIcon icon, int defaultSize) {
        this.trueIcon = icon;
        this.falseIcon = icon; // Utiliza la misma imagen para true y false
        this.defaultSize = defaultSize;
    }
     public ImageRenderer(ImageIcon trueIcon, ImageIcon falseIcon, int defaultSize) {
        this.trueIcon = trueIcon;
        this.falseIcon = falseIcon;
        this.defaultSize = defaultSize;
    }
   @Override
public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
    JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    
    if (value != null && !value.toString().isEmpty()) {
        // Si la celda no está vacía, mostrar la imagen
        ImageIcon icon = new ImageIcon(value.toString());
        ImageIcon scaledIcon = new ImageIcon(icon.getImage().getScaledInstance(defaultSize, defaultSize, java.awt.Image.SCALE_SMOOTH));
        label.setIcon(scaledIcon);
    } else {
        // Si la celda está vacía, mostrar la imagen basada en el valor booleano
        boolean boolValue = value != null && (Boolean) value;
        ImageIcon icon = boolValue ? trueIcon : falseIcon;
        ImageIcon scaledIcon = new ImageIcon(icon.getImage().getScaledInstance(defaultSize, defaultSize, java.awt.Image.SCALE_SMOOTH));
        label.setIcon(scaledIcon);
    }
    
    label.setHorizontalAlignment(JLabel.CENTER);
    return label;
}
}