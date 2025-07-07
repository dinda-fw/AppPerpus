/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appperpus;

import java.awt.Component;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Acer
 */
public class ImageRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        // Pastikan nilai adalah byte array (data gambar)
        if (value instanceof byte[]) {
            byte[] imageData = (byte[]) value;
            if (imageData != null && imageData.length > 0) {
                ImageIcon imageIcon = new ImageIcon(imageData);
                // Skalakan gambar agar sesuai dengan tinggi baris atau ukuran yang diinginkan
                // Anda mungkin perlu mendapatkan tinggi baris dari tabel
                int rowHeight = table.getRowHeight(row);
                Image image = imageIcon.getImage().getScaledInstance(rowHeight, rowHeight, Image.SCALE_SMOOTH); // Contoh scaling
                label.setIcon(new ImageIcon(image));
                label.setText(""); // Hapus teks jika ada gambar
            } else {
                label.setIcon(null);
                label.setText("Tidak Ada"); // Atau teks default lainnya
            }
        } else {
            label.setIcon(null);
            label.setText(value != null ? value.toString() : "Tidak Ada"); // Untuk kasus non-gambar
        }
        label.setHorizontalAlignment(JLabel.CENTER); // Tengah gambar
        return label;
    }
}