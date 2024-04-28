/*

 */
package vista;

import java.awt.Color;
import static java.awt.Component.CENTER_ALIGNMENT;
import java.awt.Cursor;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

public class Componentes {
    
    public static JLabel labelTitulo(int x, int y, int ancho, int alto, String texto){
        JLabel labelTitulo = new JLabel(texto);
        labelTitulo.setBounds(x, y, ancho, alto);
        labelTitulo.setFont(new Font("Verdana", Font.BOLD, 25));
        labelTitulo.setForeground(Color.white);
        labelTitulo.setBackground(new Color(0, 0, 26));
        labelTitulo.setOpaque(true);
        labelTitulo.setHorizontalAlignment((int) CENTER_ALIGNMENT);
        return labelTitulo;
    }
    
    public static JButton boton(int x, int y, int ancho, int alto, String texto, ImageIcon icono){
        JButton boton = new JButton(icono);
        boton.setBounds(x, y, ancho, alto);
        boton.setBackground(new Color(170, 166, 202));
        boton.setCursor(new Cursor(12));
        boton.setText(texto);
        return boton;
    }
    
    
}
