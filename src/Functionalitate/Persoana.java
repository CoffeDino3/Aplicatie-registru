package Functionalitate;

public abstract class Persoana {
    protected String Nume;
    protected String Prenume;

    public Persoana(String nume, String prenume) {
        char fir1 = nume.charAt(0);
        char fir2 = prenume.charAt(0);
        if (fir1 < 'A' || fir1 > 'Z')
            throw new IllegalArgumentException("Nume invalid!");
        if (fir2 < 'A' || fir2 > 'Z')
            throw new IllegalArgumentException("Prenume invalid!");
        this.Nume = nume;
        this.Prenume = prenume;
    }
    public String getNume() {
        return Nume;
    }
    public String getPrenume() {
        return Prenume;
    }
    public void setNume(String nume) {
        if (nume.charAt(0) < 'A' || nume.charAt(0) > 'Z')
            throw new IllegalArgumentException("Nume invalid!");
        this.Nume = nume;
    }
    public void setPrenume(String prenume) {
        if (prenume.charAt(0) < 'A' || prenume.charAt(0) > 'Z')
            throw new IllegalArgumentException("Prenume invalid!");
        this.Prenume = prenume;
    }

    public abstract String getRol();
}
