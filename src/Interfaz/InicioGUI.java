package Interfaz;

import javax.swing.*;
import Logica.Reinas_primerSolucion;
import Logica.Reinas;
import java.awt.*;

public class InicioGUI extends JFrame {
	
    public InicioGUI() {
    	setTitle("Backtracking N-Reinas");
        setSize(500, 380);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(new Color(30, 30, 30));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(35, 50, 30, 50));

        JLabel titulo = new JLabel("♛  N-Reinas", SwingConstants.CENTER);
        titulo.setFont(new Font("Serif", Font.BOLD, 42));
        titulo.setForeground(new Color(240, 217, 181));

        JLabel subtitulo = new JLabel("Backtracking", SwingConstants.CENTER);
        subtitulo.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitulo.setForeground(new Color(181, 136, 99));

        JPanel panelTitulos = new JPanel(new GridLayout(2, 1, 0, 8));
        panelTitulos.setOpaque(false);
        panelTitulos.add(titulo);
        panelTitulos.add(subtitulo);

        //Botón: Ver Una Solución
        JButton btnUnaSolucion = crearBoton("Ver Una Solución", new Color(181, 136, 99));
        btnUnaSolucion.addActionListener(e -> {
            String input = mostrarInputDialog(this, "Ingrese el valor de N:");
            if (input == null || input.trim().isEmpty()) return;
            try {
                int N = Integer.parseInt(input.trim());
                if (N < 1) throw new NumberFormatException();

                // Usamos OchoReinas para encontrar una sola solución
                Reinas_primerSolucion r = new Reinas_primerSolucion(N);
                r.solucionar(0);

                if (r.getSolucion() == null) {
                    mostrarMensaje(this, "No hay solución para N=" + N);
                    return;
                }

                // Envolvemos la solución única en un int[][] para reutilizar VentanaSoluciones
                int[][] soluciones = { r.getSolucion() };
                VentanaSoluciones ventana = new VentanaSoluciones(soluciones, N, this);
                ventana.setVisible(true);
                setVisible(false);

            } catch (NumberFormatException ex) {
                mostrarMensaje(this, "Ingrese un número válido mayor a 0.");
            }
        });

        //Botón: Ver Todas las Soluciones
        JButton btnTodasSoluciones = crearBoton("Ver Todas las Soluciones", new Color(120, 90, 60));
        btnTodasSoluciones.addActionListener(e -> {
            String input = mostrarInputDialog(this, "Ingrese el valor de N:");
            if (input == null || input.trim().isEmpty()) return;
            try {
                int N = Integer.parseInt(input.trim());
                if (N < 1) throw new NumberFormatException();

                // Usamos Reinas para encontrar todas las soluciones
                Reinas r = new Reinas(N);
                r.solucionar(0);

                if (r.getSoluciones().length == 0) {
                    mostrarMensaje(this, "No hay soluciones para N=" + N);
                    return;
                }

                VentanaSoluciones ventana = new VentanaSoluciones(r.getSoluciones(), N, this);
                ventana.setVisible(true);
                setVisible(false);

            } catch (NumberFormatException ex) {
                mostrarMensaje(this, "Ingrese un número válido mayor a 0.");
            }
        });

        //Panel de botones (apilados verticalmente)
        JPanel panelBotones = new JPanel(new GridLayout(2, 1, 0, 12));
        panelBotones.setOpaque(false);
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        panelBotones.add(btnUnaSolucion);
        panelBotones.add(btnTodasSoluciones);

        panelPrincipal.add(panelTitulos,  BorderLayout.CENTER);
        panelPrincipal.add(panelBotones,  BorderLayout.SOUTH);
        setContentPane(panelPrincipal);
    }

    private static String mostrarInputDialog(JFrame parent, String mensaje) {
        JDialog dialog = new JDialog(parent, "N-Reinas", true);
        dialog.setSize(420, 230);
        dialog.setLocationRelativeTo(parent);
        dialog.setResizable(false);

        JPanel panel = new JPanel(new BorderLayout(0, 0));
        panel.setBackground(new Color(30, 30, 30));
        panel.setBorder(BorderFactory.createEmptyBorder(28, 35, 22, 35));

        JLabel lbl = new JLabel(mensaje, SwingConstants.CENTER);
        lbl.setFont(new Font("Arial", Font.PLAIN, 15));
        lbl.setForeground(new Color(240, 217, 181));

        JTextField campo = new JTextField();
        campo.setFont(new Font("Arial", Font.BOLD, 22));
        campo.setBackground(new Color(50, 50, 50));
        campo.setForeground(Color.WHITE);
        campo.setCaretColor(Color.WHITE);
        campo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(181, 136, 99), 2),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        campo.setHorizontalAlignment(JTextField.CENTER);
        campo.setPreferredSize(new Dimension(0, 50));

        JPanel panelCampo = new JPanel(new BorderLayout());
        panelCampo.setOpaque(false);
        panelCampo.setBorder(BorderFactory.createEmptyBorder(14, 0, 0, 0));
        panelCampo.add(campo, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new GridLayout(1, 2, 12, 0));
        panelBotones.setOpaque(false);
        panelBotones.setBorder(BorderFactory.createEmptyBorder(16, 0, 0, 0));

        String[] resultado = {null};

        JButton btnOk       = crearBoton("Aceptar",  new Color(181, 136, 99));
        JButton btnCancelar = crearBoton("Cancelar", new Color(80, 80, 80));

        btnOk.addActionListener(e      -> { resultado[0] = campo.getText(); dialog.dispose(); });
        btnCancelar.addActionListener(e -> dialog.dispose());
        campo.addActionListener(e      -> { resultado[0] = campo.getText(); dialog.dispose(); });

        panelBotones.add(btnOk);
        panelBotones.add(btnCancelar);

        panel.add(lbl,          BorderLayout.NORTH);
        panel.add(panelCampo,   BorderLayout.CENTER);
        panel.add(panelBotones, BorderLayout.SOUTH);

        dialog.setContentPane(panel);
        dialog.setVisible(true);
        return resultado[0];
    }

    private static void mostrarMensaje(JFrame parent, String mensaje) {
        JDialog dialog = new JDialog(parent, "N-Reinas", true);
        dialog.setSize(340, 165);
        dialog.setLocationRelativeTo(parent);
        dialog.setResizable(false);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(30, 30, 30));
        panel.setBorder(BorderFactory.createEmptyBorder(28, 30, 22, 30));

        JLabel lbl = new JLabel(mensaje, SwingConstants.CENTER);
        lbl.setFont(new Font("Arial", Font.PLAIN, 14));
        lbl.setForeground(new Color(240, 217, 181));

        JButton btnOk = crearBoton("Aceptar", new Color(181, 136, 99));
        btnOk.addActionListener(e -> dialog.dispose());

        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBoton.setOpaque(false);
        panelBoton.add(btnOk);

        panel.add(lbl,        BorderLayout.CENTER);
        panel.add(panelBoton, BorderLayout.SOUTH);

        dialog.setContentPane(panel);
        dialog.setVisible(true);
    }

    public static JButton crearBoton(String texto, Color colorFondo) {
        JButton btn = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isPressed()
                        ? colorFondo.darker()
                        : getModel().isRollover() ? colorFondo.brighter() : colorFondo);
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InicioGUI().setVisible(true));
    }
}