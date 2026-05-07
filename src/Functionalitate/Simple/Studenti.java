package Functionalitate.Simple;

import Functionalitate.Abstracte.Persoana;

import java.util.ArrayList;

public class Studenti extends Persoana {
    private int Id;
    private Grupe Grupa;
    private ArrayList<Note> Note = new ArrayList<>();
    private String poza;

    public Studenti(int i, String n,String p,Grupe g) {
        super(n, p);
        Id=i;
        Grupa=g;
    }
    public int getId() {
        return Id;
    }
    public Grupe getGrupa() {
        return Grupa;
    }
    public void setGrupa(Grupe g) {
        Grupa = g;
    }
    public void adaugaNota(Note n) {
        Note.add(n);
    }
    public void scoateNota(Note n) {
        Note.remove(n);
    }
    public String getPoza(){
        return poza;
    }
    public void setPoza(String path){
        poza=path;
    }
    public ArrayList<Note> getNote() {
        return Note;
    }

    public double getMedia() {
        if (Note.isEmpty()) return 0;
        double suma = 0;
        for (Note n : Note){
            suma +=n.getNota();
        }
        return suma/Note.size();
    }
}
