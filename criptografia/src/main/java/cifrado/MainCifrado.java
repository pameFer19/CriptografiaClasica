package cifrado;
import javax.swing.*;
import java.awt.*;

public class MainCifrado extends JFrame {

    private JTextArea inputText;
    private JTextArea outputText;
    private JLabel descripcion;
    private String metodo = "";

    public MainCifrado() {
        setTitle("Aplicación de Cifrado");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        inputText = new JTextArea();
        outputText = new JTextArea();
        outputText.setEditable(false);

        // Panel central
        JPanel panelCentro = new JPanel(new GridLayout(2,1));
        panelCentro.add(new JScrollPane(inputText));
        panelCentro.add(new JScrollPane(outputText));

        add(panelCentro, BorderLayout.CENTER);

        // Botón cifrar
        JButton btnCifrar = new JButton("Cifrar");
        btnCifrar.addActionListener(e -> cifrarTexto());
        add(btnCifrar, BorderLayout.SOUTH);

        //descripcion
        descripcion = new JLabel("Selecciona un tipo de cifrado", SwingConstants.CENTER);
        descripcion.setFont(new Font("Arial", Font.ITALIC, 14));
        descripcion.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        add(descripcion, BorderLayout.NORTH);

        // Menú opciones
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Opciones");

        JMenuItem cesar = new JMenuItem("Cifrado César");
        JMenuItem atbash = new JMenuItem("Cifrado Atbash");
        JMenuItem vigenere = new JMenuItem("Cifrado Vigenere");
        JMenuItem rail = new JMenuItem("Cifrado Rail Fence");
        JMenuItem playfair = new JMenuItem("Cifrado Playfair");

        cesar.addActionListener(e ->{ metodo = "cesar";
        descripcion.setText("El cifrado César es un método de encriptación" +
                "que consiste en sustituir letras desplazándolas un número fijo " +
                "de posiciones en el alfabeto, sentando así las bases de las técnicas" +
                " criptográficas modernas.");
        });

        atbash.addActionListener(e -> metodo = "atbash");
        vigenere.addActionListener(e -> metodo = "vigenere");
        rail.addActionListener(e -> metodo = "rail");
        playfair.addActionListener(e -> metodo = "playfair");

        menu.add(cesar);
        menu.add(atbash);
        menu.add(vigenere);
        menu.add(rail);
        menu.add(playfair);

        menuBar.add(menu);
        setJMenuBar(menuBar);
    }

    private void cifrarTexto() {
        String texto = inputText.getText();
        String resultado = "";

        switch (metodo) {
            case "cesar":
                resultado = cifradoCesar(texto, 3);

                break;
            case "atbash":
                resultado = cifradoAtbash(texto);
                break;
            case "vigenere":
                resultado = "Vigenere (falta implementar)";
                break;
            case "rail":
                resultado = "Rail Fence (falta implementar)";
                break;
            case "playfair":
                resultado = "Playfair (falta implementar)";
                break;
            default:
                resultado = "Selecciona un método";
        }

        outputText.setText(resultado);
    }

    // CIFRADO CESAR
    private String cifradoCesar(String texto, int desplazamiento) {
        StringBuilder resultado = new StringBuilder();

        for (char c : texto.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                char cifrado = (char) ((c - base + desplazamiento) % 26 + base);
                resultado.append(cifrado);
            } else {
                resultado.append(c);
            }
        }
        return resultado.toString();
    }

    // CIFRADO ATBASH
    private String cifradoAtbash(String texto) {
        StringBuilder resultado = new StringBuilder();

        for (char c : texto.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                char cifrado = (char) (base + (25 - (c - base)));
                resultado.append(cifrado);
            } else {
                resultado.append(c);
            }
        }
        return resultado.toString();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainCifrado().setVisible(true);
        });
    }
}