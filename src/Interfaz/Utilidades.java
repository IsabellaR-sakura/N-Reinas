package Interfaz;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JButton;

public class Utilidades {
	public static final Color bg = new Color(30, 30, 30);
	public static final Color txt = new Color(240, 217, 181);
	public static final Color alt = new Color(200, 200, 200);
	public static final Color prim = new Color(181, 136, 99);
	
	public static JButton crearBoton(String texto, Color colorFondo) {
        JButton btn = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isEnabled()? (getModel().isPressed()
                        ? colorFondo.darker()
                        : getModel().isRollover() ? colorFondo.brighter() : colorFondo): colorFondo.darker().darker());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 18, 18);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setBackground(colorFondo);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.setPreferredSize(new Dimension(200, 46));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
}
