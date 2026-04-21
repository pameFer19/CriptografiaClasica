package cifrado;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

import cifrado.TiposCifrado.*;
import static cifrado.MetodosCifrado.*;
//import static cifrado.ServicioCifrado.*;

public class UI extends JFrame {

    private java.util.List<JToggleButton> botonesMetodos = new java.util.ArrayList<>();

    private JTextArea inputText;
    private JTextArea outputText;
    private JTextArea descripcion;
    private TiposCifrado metodo;
    private JTextField campoParametro;
    private String idioma ="ES";

    private JLabel titulo;
    private JLabel subtitulo;
    private JLabel lblParametro;

    private JButton btnCifrar;
    private JButton btnDescifrar;
    private JButton btnLimpiar;

    private TitledBorder borderEntrada;
    private TitledBorder borderResultado;

    private static final Font FONT_GENERAL = new Font("Century Gothic", Font.PLAIN, 24);
    private static final Font FONT_TITULO = new Font("Consolas", Font.BOLD, 18);
    private static final Font FONT_DESC = new Font("Segoe UI", Font.ITALIC, 22);

    private final Color PRIMARY = new Color(219, 150, 147);
    private final Color BG = new Color(255, 246, 247);

    public UI() {
        setTitle("> Aplicación de Cifrado");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        getContentPane().setBackground(BG);

        JPanel top = new JPanel(new BorderLayout());
        top.add(crearBarraIdioma(), BorderLayout.NORTH);
        top.add(crearPanelSuperior(), BorderLayout.CENTER);

        add(top, BorderLayout.NORTH);
        add(crearPanelCentral(), BorderLayout.CENTER);
    }

    private JPanel crearBarraIdioma() {

        JPanel barra = new JPanel(new FlowLayout(FlowLayout.LEFT));
        barra.setBackground(BG);

        JLabel lbl = new JLabel("Idioma:");
        lbl.setFont(FONT_GENERAL.deriveFont(Font.BOLD, 16f));

        String[] opciones = {"Español", "English"};
        JComboBox<String> cambio = new JComboBox<>(opciones);
        cambio.setFont(FONT_GENERAL.deriveFont(16f));

        cambio.addActionListener(e -> {
            if (cambio.getSelectedIndex() == 0) {
                idioma = "ES";
            } else {
                idioma = "EN";
            }
            actualizarIdioma();
        });

        barra.add(lbl);
        barra.add(cambio);

        return barra;
    }

    private void actualizarIdioma() {

        int i = 0;
        for (TiposCifrado tipo : TiposCifrado.values()) {
            botonesMetodos.get(i).setText("•" + tipo.getNombre(idioma));
            i++;
        }

        if (idioma.equals("ES")) {

            titulo.setText("Criptografía Clásica");
            subtitulo.setText("Métodos de cifrado:");
            descripcion.setText("Selecciona un tipo de cifrado");

            lblParametro.setText("Número / Clave:");

            btnCifrar.setText("Cifrar");
            btnDescifrar.setText("Descifrar");
            btnLimpiar.setText("Limpiar");

            borderEntrada.setTitle("Entrada");
            borderResultado.setTitle("Resultado");

        } else {

            titulo.setText("Classical Cryptography");
            subtitulo.setText("Cipher methods:");
            descripcion.setText("Select a cipher method");

            lblParametro.setText("Number / Key:");

            btnCifrar.setText("Encrypt");
            btnDescifrar.setText("Decrypt");
            btnLimpiar.setText("Clear");

            borderEntrada.setTitle("Input");
            borderResultado.setTitle("Output");
        }

        repaint(); // refresca la UI
    }

    private JPanel crearPanelSuperior() {
        JPanel panelTop = new JPanel();
        panelTop.setLayout(new BoxLayout(panelTop, BoxLayout.Y_AXIS));
        panelTop.setBackground(BG);

        JPanel panelMetodos = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        panelMetodos.setBackground(BG);

        ButtonGroup grupo = new ButtonGroup();

        titulo = new JLabel("Criptografía Clásica", SwingConstants.CENTER);
        titulo.setFont(new Font("Times New Roman", Font.BOLD, 26));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        subtitulo = new JLabel("Métodos de cifrado: ", SwingConstants.CENTER);
        subtitulo.setFont(new Font("Century Gothic", Font.PLAIN, 22));
        subtitulo.setForeground(Color.DARK_GRAY);
        subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelMetodos.add(crearBotonMetodo(TiposCifrado.CESAR, grupo));
        panelMetodos.add(crearBotonMetodo(TiposCifrado.ATBASH, grupo));
        panelMetodos.add(crearBotonMetodo(TiposCifrado.VIGENERE, grupo));
        panelMetodos.add(crearBotonMetodo(TiposCifrado.RAIL, grupo));
        panelMetodos.add(crearBotonMetodo(TiposCifrado.PLAYFAIR, grupo));

        descripcion = new JTextArea("Selecciona un tipo de cifrado");
        descripcion.setFont(FONT_DESC);
        descripcion.setLineWrap(true);
        descripcion.setWrapStyleWord(true);
        descripcion.setEditable(false);
        descripcion.setOpaque(false);
        descripcion.setBorder(new EmptyBorder(5, 15, 10, 15));

        panelTop.add(titulo);
        panelTop.add(Box.createVerticalStrut(5)); // espacio

        panelTop.add(subtitulo);
        panelTop.add(Box.createVerticalStrut(10));

        panelTop.add(panelMetodos);
        panelTop.add(descripcion);


        return panelTop;
    }

    private JToggleButton crearBotonMetodo(TiposCifrado metodo, ButtonGroup grupo) {

        JToggleButton btn = new JToggleButton("•" + metodo.getNombre(idioma));

        btn.setFont(FONT_GENERAL);
        btn.setFocusPainted(false);

        Color normal = Color.WHITE;

        btn.setBackground(normal);
        btn.setForeground(Color.BLACK);

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
        botonesMetodos.add(btn);

        return btn;
    }


    private JPanel crearPanelCentral() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(10, 20, 10, 20));
        panel.setBackground(BG);

        JPanel panelConfig = new JPanel(new FlowLayout(FlowLayout.CENTER, 10,5));

        inputText = crearAreaTexto();
        outputText = crearAreaTexto();
        outputText.setEditable(false);
        outputText.setBackground(new Color(255, 225, 245));
        panelConfig.setBackground(BG);

        campoParametro = new JTextField(5);
        campoParametro.setFont((FONT_GENERAL));


        lblParametro = new JLabel("Número / Clave: ", SwingConstants.CENTER);
        lblParametro.setFont(FONT_GENERAL.deriveFont(Font.BOLD, 18f));
        lblParametro.setForeground(Color.DARK_GRAY);

        panelConfig.add(lblParametro);
        panelConfig.add(campoParametro);
        campoParametro.setPreferredSize(new Dimension(100, 30));

        btnCifrar = crearBotonAccion("Cifrar");
        btnDescifrar = crearBotonAccion("Descifrar");
        btnLimpiar = crearBotonSecundario("Limpiar");

        btnCifrar.addActionListener(e -> procesarTexto(true));
        btnDescifrar.addActionListener(e -> procesarTexto(false));
        btnLimpiar.addActionListener(e -> limpiarCampos());

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        panelBotones.setBackground(BG);

        panelBotones.add(btnCifrar);
        panelBotones.add(btnDescifrar);
        panelBotones.add(btnLimpiar);

        panel.add(panelConfig);
        panel.add(crearPanel("Entrada", inputText, true));
        panel.add(Box.createVerticalStrut(15));
        panel.add(panelBotones);
        panel.add(Box.createVerticalStrut(15));
        panel.add(crearPanel("Resultado", outputText, false));

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

    private JPanel crearPanel(String tituloTexto, JTextArea area, boolean esEntrada) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG);

        TitledBorder border = new TitledBorder(tituloTexto);
        border.setTitleFont(FONT_TITULO);

        if (esEntrada) {
            borderEntrada = border;
        } else {
            borderResultado = border;
        }

        panel.setBorder(border);
        panel.add(new JScrollPane(area), BorderLayout.CENTER);
        return panel;
    }


    private void seleccionarMetodo(TiposCifrado m) {
        metodo = m;
        descripcion.setText(m.getDescripcion(idioma));
        limpiarCampos();

        if (m == TiposCifrado.ATBASH) {
            campoParametro.setEnabled(false);
            campoParametro.setText("");
        } else {
            campoParametro.setEnabled(true);
        }
    }

    private void limpiarCampos() {
        inputText.setText("");
        outputText.setText("");
    }

    private void procesarTexto(boolean cifrar) {

        String texto = inputText.getText();
        String parametro = campoParametro.getText();

        if (metodo == null) {
            outputText.setText("Selecciona un método");
            return;
        }

        String resultado = ServicioCifrado.procesar(metodo, texto,parametro,cifrar);

        outputText.setText(resultado);
    }
}
