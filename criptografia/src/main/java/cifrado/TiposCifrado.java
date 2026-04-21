package cifrado;

public enum TiposCifrado {

    CESAR(
            "Cesar", "Caesar",
            "Cifrado de Cesar\nEs un método de encriptación muy simple donde cada letra se desplaza un número fijo.",
            "Caesar Cipher\nA simple encryption method where each letter is shifted by a fixed number."
    ),

    ATBASH(
            "Atbash", "Atbash",
            "Cifrado Atbash\nSustituye cada letra por su opuesta en el alfabeto.",
            "Atbash Cipher\nEach letter is replaced by its opposite in the alphabet."
    ),

    VIGENERE(
            "Vigenere", "Vigenere",
            "Cifrado Vigenere\nUsa una clave para cifrar el texto.",
            "Vigenere Cipher\nUses a keyword to encrypt text."
    ),

    RAIL(
            "Rail Fence", "Rail Fence",
            "Cifrado Rail Fence\nReorganiza el texto en forma de zigzag.",
            "Rail Fence Cipher\nRearranges text in a zigzag pattern."
    ),

    PLAYFAIR(
            "Playfair", "Playfair",
            "Cifrado Playfair\nUsa una matriz de letras para cifrar pares.",
            "Playfair Cipher\nUses a letter matrix to encrypt pairs."
    );

    private String nombreES;
    private String nombreEN;
    private String descES;
    private String descEN;

    TiposCifrado(String nombreES, String nombreEN, String descES, String descEN) {
        this.nombreES = nombreES;
        this.nombreEN = nombreEN;
        this.descES = descES;
        this.descEN = descEN;
    }

    public String getNombre(String idioma) {
        return idioma.equals("ES") ? nombreES : nombreEN;
    }

    public String getDescripcion(String idioma) {
        return idioma.equals("ES") ? descES : descEN;
    }
}