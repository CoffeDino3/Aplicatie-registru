package Functionalitate.Simple;

import Functionalitate.Enumerari.Specialitati;

public class Grupe {
    private int Id;
    private int An;
    private int Clasa;
    private int NrGrupa;
    private Specialitati Specialitate;
    private String Grupa;

    public Grupe(int i, int a, int c, int nr, Specialitati spec) {
        Id=i;
        An=a;
        Clasa=c;
        NrGrupa=nr;
        Specialitate=spec;
        makeGrupa();
    }

    public int getId() {
        return Id;
    }
    public int getIdGrupa() {
        return Id;
    }

    public int getAn() {
        return An;
    }
    public void setAn(int an) {
        if (an<20 || an>99) {
            throw new IllegalArgumentException("Anul de inmatriculare invalid.");
        }
        An=an;
    }

    public int getClasa() {
        return Clasa;
    }
    public void setClasa(int clasa) {
        if (clasa<1 || clasa>4) {
            throw new IllegalArgumentException("Clasa invalida.");
        }
        Clasa=clasa;
    }

    public int getNrGrupa() {
        return NrGrupa;
    }
    public void setNrGrupa(int nr) {
        if (nr<1 || nr>9){
            throw new IllegalArgumentException("Numarul grupei invalid.");
        }
        NrGrupa=nr;
    }

    public Specialitati getSpecialitate() {
        return Specialitate;
    }
    public void setSpecialitate(Specialitati spec) {
        if (spec==null)
            throw new IllegalArgumentException("Specialitatea nu poate fi nula.");
        Specialitate=spec;
        makeGrupa();
    }

    public void setGrupa(String grupa) {
        Grupa=grupa;
    }
    public String getGrupa() {
        return Grupa;
    }

    public void makeGrupa() {
        if (Specialitate!=null) {
            Grupa = Specialitate.getPrefix() + "-" + An + Clasa + NrGrupa;
        }
    }

    @Override
    public String toString() { return Grupa; }
}