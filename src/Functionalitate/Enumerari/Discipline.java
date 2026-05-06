package Functionalitate.Enumerari;

public enum Discipline {
    MATEMATICA("Matematica"),
    ROMANA("Limba si lit. romana"),
    FIZICA("Fizica"),
    ENGLEZA("Limba Engleza"),
    ISTORIA("Istoria"),
    CHIMIA("Chimia"),
    BIOLOGIA("Biologia"),
    SOCIETATE("Educatia pentru societate"),
    DIRIGINTIA("Dirigintie");


    private final String denumire;
    Discipline(String d){
        denumire=d;
    }
    public String getDenumire() {
        return denumire;
    }
}
