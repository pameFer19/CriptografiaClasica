package cifrado;

public class MetodosCifrado {

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

    public static String cifradoVigenere(String texto, String clave) {
        StringBuilder resultado = new StringBuilder();
        clave = clave.toLowerCase();
        int j = 0;

        for (char c : texto.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                int desp = clave.charAt(j % clave.length()) - 'a';

                resultado.append((char) ((c - base + desp) % 26 + base));
                j++;
            } else {
                resultado.append(c);
            }
        }
        return resultado.toString();
    }

    public static String descifradoVigenere(String texto, String clave) {
        StringBuilder resultado = new StringBuilder();
        clave = clave.toLowerCase();
        int j = 0;

        for (char c : texto.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                int desp = clave.charAt(j % clave.length()) - 'a';

                resultado.append((char) ((c - base - desp + 26) % 26 + base));
                j++;
            } else {
                resultado.append(c);
            }
        }
        return resultado.toString();
    }

    public static String cifradoRailFence(String texto, int filas) {
        if (filas <= 1) return texto;

        StringBuilder[] rail = new StringBuilder[filas];
        for (int i = 0; i < filas; i++) rail[i] = new StringBuilder();

        int fila = 0;
        boolean abajo = true;

        for (char c : texto.toCharArray()) {
            rail[fila].append(c);

            if (fila == 0) abajo = true;
            else if (fila == filas - 1) abajo = false;

            fila += abajo ? 1 : -1;
        }

        StringBuilder resultado = new StringBuilder();
        for (StringBuilder r : rail) resultado.append(r);

        return resultado.toString();
    }

    public static String descifradoRailFence(String texto, int filas) {
        if (filas <= 1) return texto;

        boolean[][] marca = new boolean[filas][texto.length()];
        int fila = 0;
        boolean abajo = true;

        for (int i = 0; i < texto.length(); i++) {
            marca[fila][i] = true;

            if (fila == 0) abajo = true;
            else if (fila == filas - 1) abajo = false;

            fila += abajo ? 1 : -1;
        }

        char[] resultado = new char[texto.length()];
        int index = 0;

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < texto.length(); j++) {
                if (marca[i][j]) {
                    resultado[j] = texto.charAt(index++);
                }
            }
        }

        StringBuilder finalText = new StringBuilder();
        fila = 0;
        abajo = true;

        for (int i = 0; i < texto.length(); i++) {
            finalText.append(resultado[i]);

            if (fila == 0) abajo = true;
            else if (fila == filas - 1) abajo = false;

            fila += abajo ? 1 : -1;
        }

        return finalText.toString();
    }

    public static char[][] generarMatriz(String clave) {
        clave = clave.toUpperCase().replace("J", "I");
        boolean[] usado = new boolean[26];
        char[][] matriz = new char[5][5];

        StringBuilder sb = new StringBuilder();

        for (char c : clave.toCharArray()) {
            if (!usado[c - 'A']) {
                usado[c - 'A'] = true;
                sb.append(c);
            }
        }

        for (char c = 'A'; c <= 'Z'; c++) {
            if (c != 'J' && !usado[c - 'A']) {
                sb.append(c);
            }
        }

        int k = 0;
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 5; j++)
                matriz[i][j] = sb.charAt(k++);

        return matriz;
    }

    public static int[] buscar(char[][] m, char c) {
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 5; j++)
                if (m[i][j] == c) return new int[]{i, j};
        return null;
    }

    public static String prepararTexto(String texto) {
        texto = texto.toUpperCase().replaceAll("[^A-Z]", "").replace("J", "I");
        StringBuilder sb = new StringBuilder(texto);

        for (int i = 0; i < sb.length(); i += 2) {
            if (i + 1 >= sb.length() || sb.charAt(i) == sb.charAt(i + 1)) {
                sb.insert(i + 1, 'X');
            }
        }

        if (sb.length() % 2 != 0) sb.append('X');

        return sb.toString();
    }


    public static String cifradoPlayfair(String texto, String clave) {
        char[][] matriz = generarMatriz(clave);
        texto = prepararTexto(texto);

        StringBuilder res = new StringBuilder();

        for (int i = 0; i < texto.length(); i += 2) {
            char a = texto.charAt(i);
            char b = texto.charAt(i + 1);

            int[] p1 = buscar(matriz, a);
            int[] p2 = buscar(matriz, b);

            if (p1[0] == p2[0]) {
                res.append(matriz[p1[0]][(p1[1] + 1) % 5]);
                res.append(matriz[p2[0]][(p2[1] + 1) % 5]);
            } else if (p1[1] == p2[1]) {
                res.append(matriz[(p1[0] + 1) % 5][p1[1]]);
                res.append(matriz[(p2[0] + 1) % 5][p2[1]]);
            } else {
                res.append(matriz[p1[0]][p2[1]]);
                res.append(matriz[p2[0]][p1[1]]);
            }
        }
        return res.toString();
    }

    public static String descifradoPlayfair(String texto, String clave) {
        char[][] matriz = generarMatriz(clave);

        // 🔥 LIMPIAR TEXTO (esto faltaba)
        texto = texto.toUpperCase().replaceAll("[^A-Z]", "").replace("J", "I");

        StringBuilder res = new StringBuilder();

        for (int i = 0; i < texto.length(); i += 2) {
            if (i + 1 >= texto.length()) break; // evitar error

            char a = texto.charAt(i);
            char b = texto.charAt(i + 1);

            int[] p1 = buscar(matriz, a);
            int[] p2 = buscar(matriz, b);

            // 🔥 VALIDACIÓN (evita crash)
            if (p1 == null || p2 == null) {
                return "Error: texto inválido para Playfair";
            }

            if (p1[0] == p2[0]) {
                res.append(matriz[p1[0]][(p1[1] + 4) % 5]);
                res.append(matriz[p2[0]][(p2[1] + 4) % 5]);
            } else if (p1[1] == p2[1]) {
                res.append(matriz[(p1[0] + 4) % 5][p1[1]]);
                res.append(matriz[(p2[0] + 4) % 5][p2[1]]);
            } else {
                res.append(matriz[p1[0]][p2[1]]);
                res.append(matriz[p2[0]][p1[1]]);
            }
        }

        return res.toString();
    }


}
