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

public class ImageBooleanRenderer extends DefaultTableCellRenderer {
    private ImageIcon trueIcon;
    private ImageIcon falseIcon;
    private int defaultSize;

    public ImageBooleanRenderer(ImageIcon trueIcon, ImageIcon falseIcon, int defaultSize) {
        this.trueIcon = trueIcon;
        this.falseIcon = falseIcon;
        this.defaultSize = defaultSize;
    }
    public ImageIcon getTrueIcon() {
    return trueIcon;
    }
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        // Si el valor es un booleano, establece la imagen correspondiente
        if (value instanceof Boolean) {
            boolean boolValue = (Boolean) value;
            if (boolValue) {
                label.setIcon(new ImageIcon(trueIcon.getImage().getScaledInstance(defaultSize, defaultSize, java.awt.Image.SCALE_SMOOTH)));
            } else {
                label.setIcon(new ImageIcon(falseIcon.getImage().getScaledInstance(defaultSize, defaultSize, java.awt.Image.SCALE_SMOOTH)));
            }
            label.setText(""); // Elimina el texto
        }

        label.setHorizontalAlignment(JLabel.CENTER);
        return label;
    }
}