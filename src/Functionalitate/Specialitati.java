package Functionalitate;

public enum Specialitati {
    PROGRAMARE("Programare", "P"),
    CONTABILITATE("Contabilitate", "C"),
    APLICATII_WEB("Aplicatii Web", "W"),
    JURISPRUDENTA("Jurisprudenta", "J"),
    ADMINISTRARE_BD("Administrare BD", "B"),
    OPERATOR("Operator", "O"),
    RETELE("Retele", "R"),
    SECRETARIAT("Secretariat", "S");

    private final String nume;
    private final String prefix;
    Specialitati(String nume, String prefix) {
        this.nume = nume;
        this.prefix = prefix;
    }
    public String getNume() {
        return nume;
    }
    public String getPrefix() {
        return prefix;
    }
}