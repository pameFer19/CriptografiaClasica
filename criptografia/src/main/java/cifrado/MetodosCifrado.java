package cifrado;

public class MetodosCifrado {


    // CIFRADO CESAR
    public static String cifradoCesar(String texto, int desplazamiento) {
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
    public static String cifradoAtbash(String texto) {
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
}
