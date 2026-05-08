package Interfaz;

import javax.swing.*;

import Exceptions.InvalidNumException;
import Logica.Reinas_primerSolucion;
import Logica.Reinas;
import java.awt.*;

public class InicioGUI extends JFrame {
	
	private static int verificaNum (String input) throws InvalidNumException{
		if (!input.trim().matches("-?\\d+"))
			throw new InvalidNumException("Ingrese un número entero.");
		int N = Integer.parseInt(input.trim());
		if (N < 1) throw new InvalidNumException("Ingrese un número mayor a 0.");
		if (N >12) throw new InvalidNumException("Ingrese un número menor o igual a 12.");
		return N;
	}
	
    public InicioGUI() {
        setTitle("Backtracking N-Reinas");
        setSize(500, 380);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(Utilidades.bg);
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(35, 50, 30, 50));

        JLabel titulo = new JLabel("♛  N-Reinas", SwingConstants.CENTER);
        titulo.setFont(new Font("Serif", Font.BOLD, 42));
        titulo.setForeground(Utilidades.txt);

        JLabel subtitulo = new JLabel("Backtracking", SwingConstants.CENTER);
        subtitulo.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitulo.setForeground(Utilidades.prim);

        JPanel panelTitulos = new JPanel(new GridLayout(2, 1, 0, 8));
        panelTitulos.setOpaque(false);
        panelTitulos.add(titulo);
        panelTitulos.add(subtitulo);

        //Botón: Ver Una Solución
        JButton btnUnaSolucion = Utilidades.crearBoton("Ver Una Solución", Utilidades.prim);
        btnUnaSolucion.addActionListener(e -> {
            String input = mostrarInputDialog(this, "Ingrese el valor de N:");
            if (input == null) return;
            try {
                int N = verificaNum(input);

                //una sola solución
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

            } catch (InvalidNumException ex) {
                mostrarMensaje(this, ex.getMessage());
            }
        });

        //Botón: Ver Todas las Soluciones
        JButton btnTodasSoluciones = Utilidades.crearBoton("Ver Todas las Soluciones", Utilidades.prim.darker());
        btnTodasSoluciones.addActionListener(e -> {
            String input = mostrarInputDialog(this, "Ingrese el valor de N:");
            if (input == null) return;
            try {
                int N = verificaNum(input);

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

            }catch (InvalidNumException ex) {
                mostrarMensaje(this, ex.getMessage());
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
        panel.setBackground(Utilidades.bg);
        panel.setBorder(BorderFactory.createEmptyBorder(28, 35, 22, 35));

        JLabel lbl = new JLabel(mensaje, SwingConstants.CENTER);
        lbl.setFont(new Font("Arial", Font.PLAIN, 15));
        lbl.setForeground(Utilidades.txt);

        JTextField campo = new JTextField();
        campo.setFont(new Font("Arial", Font.BOLD, 22));
        campo.setBackground(Utilidades.bg.brighter());
        campo.setForeground(Color.WHITE);
        campo.setCaretColor(Color.WHITE);
        campo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Utilidades.prim, 2),
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

        JButton btnOk       = Utilidades.crearBoton("Aceptar",  Utilidades.prim);
        JButton btnCancelar = Utilidades.crearBoton("Cancelar", Utilidades.bg.brighter().brighter());

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
        panel.setBackground(Utilidades.bg);
        panel.setBorder(BorderFactory.createEmptyBorder(28, 30, 22, 30));

        JLabel lbl = new JLabel(mensaje, SwingConstants.CENTER);
        lbl.setFont(new Font("Arial", Font.PLAIN, 14));
        lbl.setForeground(Utilidades.txt);

        JButton btnOk = Utilidades.crearBoton("Aceptar", Utilidades.prim);
        btnOk.addActionListener(e -> dialog.dispose());

        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBoton.setOpaque(false);
        panelBoton.add(btnOk);

        panel.add(lbl,        BorderLayout.CENTER);
        panel.add(panelBoton, BorderLayout.SOUTH);

        dialog.setContentPane(panel);
        dialog.setVisible(true);
    }

    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InicioGUI().setVisible(true));
    }
}