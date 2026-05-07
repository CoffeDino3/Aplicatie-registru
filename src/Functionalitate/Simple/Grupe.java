package Functionalitate.Simple;

import Functionalitate.Enumerari.Specialitati;

public class Grupe {
    private int IdGrupa;
    private Specialitati Specialitate;
    private String Grupa;

    public Grupe(int Id, Specialitati Spec) {
        if(Id>=2000 && Id<=2700){
            IdGrupa = Id;
        }else{
            throw new IllegalArgumentException("Id invalid!");
        }
        Specialitate = Spec;
    }

    public int getIdGrupa() {
        return IdGrupa;
    }
    public void setIdGrupa(int Id) {
        if(Id>=1000 && Id<=9999){
            IdGrupa = Id;
        }else{
            throw new IllegalArgumentException("Id invalid!");
        }
    }
    public Specialitati getSpecialitate() {
        return Specialitate;
    }
    public void setSpecialitate(Specialitati Spec) {
        Specialitate = Spec;
    }
    public void makeGrupa(){
        Grupa = Specialitate.getPrefix() + "-" + IdGrupa;
    }
    public String getGrupa() {
        return Grupa;
    }
}