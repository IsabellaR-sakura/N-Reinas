package Interfaz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class VentanaSoluciones extends JFrame {

    private int[][] soluciones;
    private int indice = 0;
    private PanelTablero tablero;
    private JLabel lblInfo;
    private JFrame ventanaInicio;
    private JButton btnAnterior;
    private JButton btnSiguiente;
    private JDialog dialogSelector;

    public VentanaSoluciones(int[][] soluciones, int N, JFrame ventanaInicio) {
        this.soluciones = soluciones;
        this.ventanaInicio = ventanaInicio;

        setTitle("Soluciones N-Reinas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(Utilidades.bg);

        //Header
        JPanel panelHeader = new JPanel(new BorderLayout(10, 0));
        panelHeader.setBackground(Utilidades.bg.darker());
        panelHeader.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        lblInfo = new JLabel("", SwingConstants.CENTER);
        lblInfo.setFont(new Font("Serif", Font.BOLD, 17));
        lblInfo.setForeground(Utilidades.txt);

        JButton btnSelector = new JButton("☰") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color c = Utilidades.bg.brighter();
                g2.setColor(getModel().isRollover() ? c.brighter() : c);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btnSelector.setFont(new Font("Arial", Font.PLAIN, 16));
        btnSelector.setForeground(Utilidades.txt);
        btnSelector.setContentAreaFilled(false);
        btnSelector.setBorderPainted(false);
        btnSelector.setFocusPainted(false);
        btnSelector.setPreferredSize(new Dimension(36, 36));
        btnSelector.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSelector.setToolTipText("Ver todas las soluciones");
        btnSelector.addActionListener(e -> toggleSelector());

        panelHeader.add(btnSelector, BorderLayout.WEST);
        panelHeader.add(lblInfo,     BorderLayout.CENTER);

        // Tablero
        tablero = new PanelTablero(N);
        tablero.setBackground(Utilidades.bg.darker());

        //Botones
        JPanel panelBotones = new JPanel(new GridLayout(1, 3, 10, 0));
        panelBotones.setBackground(Utilidades.bg.darker());
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 15, 12, 15));

        JButton btnInicio = Utilidades.crearBoton("⌂ Inicio", Utilidades.bg.brighter().brighter());
        btnAnterior = Utilidades.crearBoton("← Anterior", Utilidades.prim);
        btnSiguiente= Utilidades.crearBoton("Siguiente →", Utilidades.prim);

        btnInicio.addActionListener(e->{ ventanaInicio.setVisible(true); dispose(); });
        btnAnterior.addActionListener(e -> mostrarAnterior());
        btnSiguiente.addActionListener(e -> mostrarSiguiente());

        panelBotones.add(btnInicio);
        panelBotones.add(btnAnterior);
        panelBotones.add(btnSiguiente);

        panelPrincipal.add(panelHeader, BorderLayout.NORTH);
        panelPrincipal.add(tablero, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        setContentPane(panelPrincipal);
        pack();
        setLocationRelativeTo(null);
        mostrarSolucion();
        actualizarBotones();

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentMoved(ComponentEvent e) {
                reposicionarSelector();
            }
        });
    }

    private void toggleSelector() {
        if (dialogSelector != null && dialogSelector.isVisible()) {
            dialogSelector.setVisible(false);
            return;
        }
        construirSelector();
        reposicionarSelector();
        dialogSelector.setVisible(true);
    }

    private void construirSelector() {
        if (dialogSelector != null) dialogSelector.dispose();

        dialogSelector = new JDialog(this, false);
        dialogSelector.setUndecorated(true);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Utilidades.bg);
        panel.setBorder(BorderFactory.createLineBorder(Utilidades.bg.brighter(), 1));

        JLabel titulo = new JLabel("  Soluciones");
        titulo.setFont(new Font("Arial", Font.BOLD, 12));
        titulo.setForeground(Utilidades.alt.darker());
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 12, 8, 12));
        panel.add(titulo, BorderLayout.NORTH);

        JPanel listaPanel = new JPanel();
        listaPanel.setLayout(new BoxLayout(listaPanel, BoxLayout.Y_AXIS));
        listaPanel.setBackground(Utilidades.bg.darker());

        for (int i = 0; i < soluciones.length; i++) {
            listaPanel.add(crearItemLista(i));
        }

        JScrollPane scroll = new JScrollPane(listaPanel);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(Utilidades.bg);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        int altura = Math.min(soluciones.length * 38 + 10, 400);
        scroll.setPreferredSize(new Dimension(200, altura));

        panel.add(scroll, BorderLayout.CENTER);
        dialogSelector.setContentPane(panel);
        dialogSelector.pack();
    }

    private JPanel crearItemLista(int idx) {
        boolean esActual = (idx == indice);

        JPanel item = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(esActual ? Utilidades.prim.darker().darker().darker() : Utilidades.bg.darker());
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
                super.paintComponent(g);
            }
        };
        item.setOpaque(false);
        item.setPreferredSize(new Dimension(200, 36));
        item.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        item.setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 12));
        item.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel icono = new JLabel(esActual ? "♛" : "  ");
        icono.setFont(new Font("Serif", Font.PLAIN, 13));
        icono.setForeground(Utilidades.prim);
        icono.setPreferredSize(new Dimension(22, 36));

        JLabel texto = new JLabel("Solución #" + (idx + 1));
        texto.setFont(new Font("Arial", esActual ? Font.BOLD : Font.PLAIN, 13));
        texto.setForeground(esActual ? Utilidades.txt : Utilidades.alt);

        item.add(icono, BorderLayout.WEST);
        item.add(texto, BorderLayout.CENTER);

        item.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (!esActual) item.setBackground(Utilidades.bg);
                item.repaint();
            }
            @Override
            public void mouseExited(MouseEvent e) { item.repaint(); }
            @Override
            public void mouseClicked(MouseEvent e) {
                indice = idx;
                mostrarSolucion();
                actualizarBotones();
                dialogSelector.setVisible(false);
                construirSelector();
            }
        });

        return item;
    }

    private void reposicionarSelector() {
        if (dialogSelector == null) return;
        Point p = getLocationOnScreen();
        dialogSelector.setLocation(p.x, p.y + 47);
    }

    private void mostrarSolucion() {
        tablero.setSolucion(soluciones[indice]);
        lblInfo.setText("Solución " + (indice + 1) + " de " + soluciones.length);
    }

    private void mostrarAnterior() {
        if (indice > 0) { indice--; mostrarSolucion(); actualizarBotones(); }
    }

    private void mostrarSiguiente() {
        if (indice < soluciones.length - 1) { indice++; mostrarSolucion(); actualizarBotones(); }
    }

    private void actualizarBotones() {
        btnAnterior.setEnabled(indice > 0);
        btnSiguiente.setEnabled(indice < soluciones.length - 1);
    }
}