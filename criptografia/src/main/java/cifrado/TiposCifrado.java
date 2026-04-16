package cifrado;

public enum TiposCifrado {
    CESAR("Cifrado de Cesar \nEs un método de encriptación muy simple donde cada letra de un texto se desplaza un número fijo de posiciones en el alfabeto."),
    ATBASH("Cifrado Atbash \nEs un método donde cada letra se sustituye por su opuesta en el alfabeto."),
    VIGENERE("Cifrado Vigenere\nUsa una palabra clave para aplicar múltiples desplazamientos."),
    RAIL("Cifrado Rail Fence\nReorganiza el texto en forma de zigzag."),
    PLAYFAIR("Cifrado Playfair\nCifra pares de letras usando una matriz.");


    private String descripcion;

    TiposCifrado(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
