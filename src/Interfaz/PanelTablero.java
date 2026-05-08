package Interfaz;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.*;

public class PanelTablero extends JPanel {
    private int[] solucion;
    private int N;
    private Image reinaImg;

    public PanelTablero(int N) {
        this.N = N;
        setPreferredSize(new Dimension(700, 700));
    }

    public void setSolucion(int[] solucion) {
        this.solucion = solucion;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int tamaño = Math.min(getWidth(), getHeight());
        int casilla = tamaño / N;

        for (int fila = 0; fila < N; fila++) {
            for (int col = 0; col < N; col++) {
                g.setColor((fila + col) % 2 == 0 ? Utilidades.txt : Utilidades.prim);
                g.fillRect(col * casilla, fila * casilla, casilla, casilla);

                if (solucion != null && solucion[fila] == col) {
                	int fontSize = (int)(casilla * 0.7);
                    g.setFont(new Font("Serif", Font.PLAIN, fontSize));
                    FontMetrics fm = g.getFontMetrics();
                    String q = "♛";
                    int x = col * casilla + (casilla - fm.stringWidth(q)) / 2;
                    int y = fila * casilla + (casilla - fm.getHeight()) / 2 + fm.getAscent();
                    g.setColor(Color.BLACK);
                    g.drawString(q, x, y);
                }
            }
        }
    }
}