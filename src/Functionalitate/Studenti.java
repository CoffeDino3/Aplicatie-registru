package Functionalitate;

import java.util.ArrayList;

public class Studenti extends Persoana {
    private int Id;
    private Grupe Grupa;
    private ArrayList<Note> Note = new ArrayList<>();

    public Studenti(int id, String nume, String prenume, Grupe grupa) {
        super(nume, prenume);
        this.Id = id;
        this.Grupa = grupa;
    }
    @Override
    public String getRol() {
        return "Student";
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
    public ArrayList<Note> getNote() {
        return Note;
    }

    public double getMedia() {
        if (Note.isEmpty()) return 0;
        double suma = 0;
        for (Note n : Note) suma += n.getNota();
        return suma / Note.size();
    }
}
