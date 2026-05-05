package Functionalitate;

public class Note {
    private int Nota;
    public Note(int N) {
        if(N >= 1 && N <= 10){
            Nota = N;
        }else{
            throw new IllegalArgumentException("Nota invalida!");
        }
    }

    public int getNota() {
        return Nota;
    }
    public void setNota(int N) {
        if(N >= 1 && N <= 10){
            Nota = N;
        }else{
            throw new IllegalArgumentException("Nota invalida!");
        }
    }
}
