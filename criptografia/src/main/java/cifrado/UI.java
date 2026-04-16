package cifrado;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

import cifrado.TiposCifrado.*;
import static cifrado.MetodosCifrado.*;

public class UI extends JFrame {

    private JTextArea inputText;
    private JTextArea outputText;
    private JTextArea descripcion;
    private TiposCifrado metodo;

    private static final Font FONT_GENERAL = new Font("Segoe UI", Font.PLAIN, 24);
    private static final Font FONT_TITULO = new Font("Segoe UI", Font.BOLD, 18);
    private static final Font FONT_DESC = new Font("Segoe UI", Font.ITALIC, 22);

    private final Color PRIMARY = new Color(219, 150, 147);
    private final Color BG = new Color(255, 246, 247);

    public UI() {
        setTitle("> Aplicación de Cifrado");
        setSize(700, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        getContentPane().setBackground(BG);

        add(crearPanelSuperior(), BorderLayout.NORTH);
        add(crearPanelCentral(), BorderLayout.CENTER);
    }

    // ================== PANEL SUPERIOR ==================
    private JPanel crearPanelSuperior() {
        JPanel panelTop = new JPanel();
        panelTop.setLayout(new BoxLayout(panelTop, BoxLayout.Y_AXIS));
        panelTop.setBackground(BG);

        JPanel panelMetodos = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        panelMetodos.setBackground(BG);

        ButtonGroup grupo = new ButtonGroup();

        panelMetodos.add(crearBotonMetodo("Cesar", TiposCifrado.CESAR, grupo));
        panelMetodos.add(crearBotonMetodo("Atbash", TiposCifrado.ATBASH, grupo));
        panelMetodos.add(crearBotonMetodo("Vigenere", TiposCifrado.VIGENERE, grupo));
        panelMetodos.add(crearBotonMetodo("Rail Fence", TiposCifrado.RAIL, grupo));
        panelMetodos.add(crearBotonMetodo("Playfair", TiposCifrado.PLAYFAIR, grupo));

        descripcion = new JTextArea("Selecciona un tipo de cifrado");
        descripcion.setFont(FONT_DESC);
        descripcion.setLineWrap(true);
        descripcion.setWrapStyleWord(true);
        descripcion.setEditable(false);
        descripcion.setOpaque(false);
        descripcion.setBorder(new EmptyBorder(5, 15, 10, 15));

        panelTop.add(panelMetodos);
        panelTop.add(descripcion);

        return panelTop;
    }

    private JToggleButton crearBotonMetodo(String texto, TiposCifrado metodo, ButtonGroup grupo) {
        JToggleButton btn = new JToggleButton(texto);
        btn.setFont(FONT_GENERAL);
        btn.setFocusPainted(false);
        btn.setBorder(new EmptyBorder(8, 15, 8, 15));

        Color normal = Color.WHITE;

        btn.setBackground(normal);

        btn.addChangeListener(e -> {
            if (btn.isSelected()) {
                btn.setBackground(PRIMARY);
                btn.setForeground(Color.WHITE);
            } else {
                btn.setBackground(normal);
                btn.setForeground(Color.BLACK);
            }
        });

        btn.addActionListener(e -> seleccionarMetodo(metodo));

        grupo.add(btn);
        return btn;
    }

    // ================== PANEL CENTRAL ==================
    private JPanel crearPanelCentral() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(10, 20, 10, 20));
        panel.setBackground(BG);

        inputText = crearAreaTexto();
        outputText = crearAreaTexto();
        outputText.setEditable(false);
        outputText.setBackground(new Color(255, 225, 245));

        JButton btnCifrar = crearBotonAccion("Cifrar");
        JButton btnDescifrar = crearBotonAccion("Descifrar");
        JButton btnLimpiar = crearBotonSecundario("Limpiar");

        btnCifrar.addActionListener(e -> procesarTexto(true));
        btnDescifrar.addActionListener(e -> procesarTexto(false));
        btnLimpiar.addActionListener(e -> limpiarCampos());

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        panelBotones.setBackground(BG);

        panelBotones.add(btnCifrar);
        panelBotones.add(btnDescifrar);
        panelBotones.add(btnLimpiar);

        panel.add(crearPanel("Entrada", inputText));
        panel.add(Box.createVerticalStrut(15));
        panel.add(panelBotones);
        panel.add(Box.createVerticalStrut(15));
        panel.add(crearPanel("Resultado", outputText));

        return panel;
    }

    private JTextArea crearAreaTexto() {
        JTextArea area = new JTextArea(4, 20);
        area.setFont(FONT_GENERAL);
        area.setBorder(new EmptyBorder(10, 10, 10, 10));
        return area;
    }

    private JButton crearBotonAccion(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(FONT_GENERAL);
        btn.setBackground(PRIMARY);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(new EmptyBorder(8, 20, 8, 20));
        return btn;
    }

    private JButton crearBotonSecundario(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(FONT_GENERAL);
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(219, 150, 147));
        btn.setFocusPainted(false);
        btn.setBorder(new EmptyBorder(8, 20, 8, 20));
        return btn;
    }

    private JPanel crearPanel(String titulo, JTextArea area) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG);

        TitledBorder border = new TitledBorder(titulo);
        border.setTitleFont(FONT_TITULO);

        panel.setBorder(border);
        panel.add(new JScrollPane(area), BorderLayout.CENTER);
        return panel;
    }

    // ================== LÓGICA ==================
    private void seleccionarMetodo(TiposCifrado m) {
        metodo = m;
        descripcion.setText(m.getDescripcion());
        limpiarCampos();
    }

    private void limpiarCampos() {
        inputText.setText("");
        outputText.setText("");
    }

    private void procesarTexto(boolean cifrar) {
        String texto = cifrar ? inputText.getText() : outputText.getText();

        if (metodo == null) {
            outputText.setText("Selecciona un método");
            return;
        }

        String resultado;

        switch (metodo) {
            case CESAR:
                resultado = cifradoCesar(texto, cifrar ? 3 : -3);
                break;
            case ATBASH:
                resultado = cifradoAtbash(texto);
                break;
            case VIGENERE:
                resultado = cifrar ? cifradoVigenere(texto, "clave")
                        : descifradoVigenere(texto, "clave");
                break;
            case RAIL:
                resultado = cifrar ? cifradoRailFence(texto, 3)
                        : descifradoRailFence(texto, 3);
                break;
            case PLAYFAIR:
                resultado = cifrar ? cifradoPlayfair(texto, "clave")
                        : descifradoPlayfair(texto, "clave");
                break;
            default:
                resultado = "Método no implementado";
        }

        outputText.setText(resultado);
    }
}
