package Functionalitate.Simple;

import Functionalitate.Enumerari.Discipline;

public class Note {
    private int Nota;
    private Discipline disciplina;
    public Note(int N, Discipline d) {
        if(N >= 1 && N<=10){
            Nota= N;
        }else{
            throw new IllegalArgumentException("Nota invalida.");
        }
        disciplina=d;
    }

    public int getNota() {
        return Nota;
    }
    public void setNota(int N) {
        if(N>=1 && N<= 10){
            Nota=N;
        }else{
            throw new IllegalArgumentException("Nota invalida.");
        }
    }
    public Discipline getDisciplina(){
        return disciplina;
    }
    public void setDisciplina(Discipline d){
        disciplina=d;
    }
}
