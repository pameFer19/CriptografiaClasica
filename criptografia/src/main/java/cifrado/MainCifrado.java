package cifrado;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import static cifrado.MetodosCifrado.*;

public class MainCifrado extends JFrame {

    private JTextArea inputText;
    private JTextArea outputText;
    private JTextArea descripcion;
    private TiposCifrado metodo;

    public MainCifrado() {
        setTitle("🔐 Aplicación de Cifrado");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Fuente general
        Font font = new Font("Segoe UI", Font.PLAIN, 14);

        // ================== DESCRIPCIÓN ==================
        descripcion = new JTextArea("Selecciona un tipo de cifrado desde el menú");
        descripcion.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        descripcion.setLineWrap(true);
        descripcion.setWrapStyleWord(true);
        descripcion.setEditable(false);
        descripcion.setOpaque(false);
        descripcion.setBorder(new EmptyBorder(10, 10, 10, 10));

        add(descripcion, BorderLayout.NORTH);

        // ================== ÁREAS DE TEXTO ==================
        inputText = new JTextArea();
        inputText.setFont(font);
        inputText.setBorder(new EmptyBorder(10, 10, 10, 10));

        outputText = new JTextArea();
        outputText.setFont(font);
        outputText.setEditable(false);
        outputText.setBackground(new Color(245, 245, 245));
        outputText.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Panel con títulos
        JPanel panelCentro = new JPanel(new GridLayout(2, 1, 10, 10));
        panelCentro.setBorder(new EmptyBorder(10, 10, 10, 10));

        panelCentro.add(crearPanel("Texto de entrada", inputText));
        panelCentro.add(crearPanel("Texto cifrado", outputText));

        add(panelCentro, BorderLayout.CENTER);

        // ================== BOTÓN ==================
        JButton btnCifrar = new JButton("Cifrar");
        btnCifrar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnCifrar.setBackground(new Color(52, 152, 219));
        btnCifrar.setForeground(Color.WHITE);
        btnCifrar.setFocusPainted(false);
        btnCifrar.setBorder(new EmptyBorder(10, 20, 10, 20));

        btnCifrar.addActionListener(e -> cifrarTexto());

        JPanel panelSur = new JPanel();
        panelSur.add(btnCifrar);

        add(panelSur, BorderLayout.SOUTH);

        // ================== MENÚ ==================
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Métodos");

        JMenuItem cesar = new JMenuItem("Cifrado César");
        JMenuItem atbash = new JMenuItem("Cifrado Atbash");
        JMenuItem vigenere = new JMenuItem("Cifrado Vigenere");
        JMenuItem rail = new JMenuItem("Cifrado Rail Fence");
        JMenuItem playfair = new JMenuItem("Cifrado Playfair");

        cesar.addActionListener(e -> seleccionarMetodo(TiposCifrado.CESAR));
        atbash.addActionListener(e -> seleccionarMetodo(TiposCifrado.ATBASH));
        vigenere.addActionListener(e -> seleccionarMetodo(TiposCifrado.VIGENERE));
        rail.addActionListener(e -> seleccionarMetodo(TiposCifrado.RAIL));
        playfair.addActionListener(e -> seleccionarMetodo(TiposCifrado.PLAYFAIR));

        menu.add(cesar);
        menu.add(atbash);
        menu.add(vigenere);
        menu.add(rail);
        menu.add(playfair);

        menuBar.add(menu);
        setJMenuBar(menuBar);
    }

    // Panel con borde y título
    private JPanel crearPanel(String titulo, JTextArea area) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new TitledBorder(new LineBorder(Color.GRAY), titulo));
        panel.add(new JScrollPane(area), BorderLayout.CENTER);
        return panel;
    }

    private void seleccionarMetodo(TiposCifrado m) {
        metodo = m;
        descripcion.setText(m.getDescripcion());
    }

    private void cifrarTexto() {
        String texto = inputText.getText();
        String resultado;

        if (metodo == null) {
            resultado = "⚠ Selecciona un método";
        } else {
            switch (metodo) {
                case CESAR:
                    resultado = cifradoCesar(texto, 3);
                    break;
                case ATBASH:
                    resultado = cifradoAtbash(texto);
                    break;
                default:
                    resultado = "Método aún no implementado";
            }
        }

        outputText.setText(resultado);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainCifrado().setVisible(true);
        });
    }
}