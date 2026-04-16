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

    private static final Font FONT_GENERAL = new Font("Segoe UI", Font.PLAIN, 25);
    private static final Font FONT_TITULO = new Font("Segoe UI", Font.BOLD, 18);
    private static final Font FONT_DESC = new Font("Segoe UI", Font.ITALIC, 22);

    public UI() {
        setTitle("🔐 Aplicación de Cifrado");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        add(crearPanelSuperior(), BorderLayout.NORTH);
        add(crearPanelCentral(), BorderLayout.CENTER);
    }

    // ================== PANEL SUPERIOR ==================
    private JPanel crearPanelSuperior() {
        JPanel panelTop = new JPanel();
        panelTop.setLayout(new BoxLayout(panelTop, BoxLayout.Y_AXIS));

        JPanel panelMetodos = new JPanel(new FlowLayout());

        ButtonGroup grupo = new ButtonGroup();

        panelMetodos.add(crearBotonMetodo("César", TiposCifrado.CESAR, grupo));
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
        descripcion.setBorder(new EmptyBorder(5, 10, 5, 10));

        panelTop.add(panelMetodos);
        panelTop.add(descripcion);

        return panelTop;
    }

    private JToggleButton crearBotonMetodo(String texto, TiposCifrado metodo, ButtonGroup grupo) {
        JToggleButton btn = new JToggleButton(texto);
        btn.setFont(FONT_GENERAL);
        btn.setFocusPainted(false);

        Color normal = new Color(240, 240, 240);
        Color seleccionado = new Color(52, 152, 219);

        btn.setBackground(normal);

        btn.addChangeListener(e -> {
            if (btn.isSelected()) {
                btn.setBackground(seleccionado);
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
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        inputText = crearAreaTexto();
        outputText = crearAreaTexto();
        outputText.setEditable(false);
        outputText.setBackground(new Color(240, 240, 240));

        JButton btnCifrar = crearBoton("Cifrar", e -> procesarTexto(true));
        JButton btnDescifrar = crearBoton("Descifrar", e -> procesarTexto(false));
        JButton btnLimpiar = crearBoton("Limpiar", e -> limpiarCampos());

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnCifrar);
        panelBotones.add(btnDescifrar);
        panelBotones.add(btnLimpiar);

        panel.add(crearPanel("Entrada", inputText));
        panel.add(Box.createVerticalStrut(10));
        panel.add(panelBotones);
        panel.add(Box.createVerticalStrut(10));
        panel.add(crearPanel("Resultado", outputText));

        return panel;
    }

    private JTextArea crearAreaTexto() {
        JTextArea area = new JTextArea(4, 20);
        area.setFont(FONT_GENERAL);
        return area;
    }

    private JButton crearBoton(String texto, java.awt.event.ActionListener accion) {
        JButton btn = new JButton(texto);
        btn.setFont(FONT_GENERAL);
        btn.addActionListener(accion);
        return btn;
    }

    private JPanel crearPanel(String titulo, JTextArea area) {
        JPanel panel = new JPanel(new BorderLayout());
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

        // 🔥 limpiar automáticamente al cambiar método
        limpiarCampos();
    }

    private void limpiarCampos() {
        inputText.setText("");
        outputText.setText("");
    }

    private void procesarTexto(boolean cifrar) {
        String texto;

        // 🔥 USAR INPUT PARA CIFRAR, OUTPUT PARA DESCIFRAR
        if (cifrar) {
            texto = inputText.getText();
        } else {
            texto = outputText.getText();
        }

        if (metodo == null) {
            outputText.setText("⚠ Selecciona un método");
            return;
        }

        String resultado;

        switch (metodo) {
            case CESAR:
                resultado = cifradoCesar(texto, cifrar ? 3 : -3);
                break;

            case ATBASH:
                resultado = cifradoAtbash(texto); // mismo método
                break;

            case VIGENERE:
                String claveV = "clave";
                resultado = cifrar ? cifradoVigenere(texto, claveV)
                        : descifradoVigenere(texto, claveV);
                break;

            case RAIL:
                int filas = 3;
                resultado = cifrar ? cifradoRailFence(texto, filas)
                        : descifradoRailFence(texto, filas);
                break;

            case PLAYFAIR:
                String claveP = "clave";
                resultado = cifrar ? cifradoPlayfair(texto, claveP)
                        : descifradoPlayfair(texto, claveP);
                break;

            default:
                resultado = "Método no implementado";
        }

        outputText.setText(resultado);
    }


}