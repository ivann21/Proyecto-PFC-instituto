/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package instituto.busquedas.añadir;

import java.awt.Component;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class ImageRenderer3 extends DefaultTableCellRenderer {
    private ImageIcon icon;
    private int iconSize;

    public ImageRenderer3(ImageIcon icon, int iconSize) {
        this.icon = icon;
        this.iconSize = iconSize;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        label.setHorizontalAlignment(JLabel.CENTER); // Opcional: centrar ícono

        // Escalar la imagen al tamaño especificado
        Image scaledImage = icon.getImage().getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        label.setIcon(scaledIcon);
        label.setText(""); // Opcional: si no deseas mostrar texto junto al ícono

        return label;
    }
}