package cifrado;

public class ServicioCifrado {

    public static String procesar(TiposCifrado metodo, String texto, String clave, boolean cifrar) {

        try {
            // 🔥 VALIDACIÓN DE CLAVE (solo letras)
            if ((metodo == TiposCifrado.VIGENERE || metodo == TiposCifrado.PLAYFAIR)
                    && !clave.matches("[a-zA-Z]+")) {
                return "Error: La clave debe contener solo letras";
            }

            switch (metodo) {
                case CESAR:
                    int num = Integer.parseInt(clave);
                    return MetodosCifrado.cifradoCesar(texto, cifrar ? num : -num);

                case ATBASH:
                    return MetodosCifrado.cifradoAtbash(texto);

                case VIGENERE:
                    return cifrar
                            ? MetodosCifrado.cifradoVigenere(texto, clave)
                            : MetodosCifrado.descifradoVigenere(texto, clave);

                case RAIL:
                    int filas = Integer.parseInt(clave);
                    return cifrar
                            ? MetodosCifrado.cifradoRailFence(texto, filas)
                            : MetodosCifrado.descifradoRailFence(texto, filas);

                case PLAYFAIR:
                    return cifrar
                            ? MetodosCifrado.cifradoPlayfair(texto, clave)
                            : MetodosCifrado.descifradoPlayfair(texto, clave);

                default:
                    return "Método no implementado";
            }
        }catch (NumberFormatException e) {
                return "Error: Debes ingresar un número válido";
        } catch (Exception e) {
                return "Error en el procesamiento";
        }
    }
}