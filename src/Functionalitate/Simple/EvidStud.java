package Functionalitate.Simple;

import Interfete.Printabil;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class EvidStud implements Printabil {
    static ArrayList<Studenti> studenti = new ArrayList<>();
    static ArrayList<Grupe> grupe = new ArrayList<>();
    static ArrayList<Note> note = new ArrayList<>();
    public static <T> void adauga(ArrayList<T> lista, T element) {
        lista.add(element);
    }
    public static <T> void scoate(ArrayList<T> lista, T element) {
        lista.remove(element);
    }
    public static double getMedia(ArrayList<Note> note){
        double suma = 0;
        for(Note n : note){
            suma += n.getNota();
        }
        return suma/note.size();
    }

    //Studenti, grupa, si media care au media peste 8
    @Override
    public void toRaport1() {
        try (FileWriter fw = new FileWriter("raport1.txt")) {
            fw.write("Studenti cu media peste 8:\n");
            for (Studenti s : studenti) {
                if (s.getMedia()>=8) {
                    fw.write(s.getNume() + " " + s.getPrenume() +
                            " | Grupa: " + s.getGrupa().getGrupa() +
                            " | Media: " + s.getMedia() + "\n");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //Studenti, grupa, si media care au media sub 5
    @Override
    public void toRaport2() {
        try (FileWriter fw= new FileWriter("raport2.txt")) {
            fw.write("Studenti cu media sub 5:\n");
            for (Studenti s : studenti) {
                if (s.getMedia()<5) {
                    fw.write(s.getNume() + " " + s.getPrenume() +
                            " | Grupa: " + s.getGrupa().getGrupa() +
                            " | Media: " + s.getMedia() + "\n");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //Student, grupa si media la care numele se incepe cu A or E
    @Override
    public void toRaport3() {
        try (FileWriter fw = new FileWriter("raport3.txt")) {
            fw.write("Studenti cu numele ce incepe cu A sau E:\n");
            for (Studenti s : studenti) {
                char prima= s.getNume().charAt(0);
                if (prima =='A' || prima=='E') {
                    fw.write(s.getNume() + " " + s.getPrenume() +
                            " | Grupa: " + s.getGrupa().getGrupa() +
                            " | Media: " + s.getMedia() + "\n");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //Studenti, grupa, si media care au media peste 8
    @Override
    public void toCSV1() {
        try (FileWriter fw = new FileWriter("raport1.csv")) {
            fw.write("Nume,Prenume,Grupa,Media\n");
            for (Studenti s : studenti) {
                if (s.getMedia()>=8) {
                    fw.write(s.getNume() + "," + s.getPrenume() + "," +
                            s.getGrupa().getGrupa() + "," + s.getMedia() + "\n");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //Studenti, grupa, si media care au media sub 5
    @Override
    public void toCSV2() {
        try (FileWriter fw = new FileWriter("raport2.csv")) {
            fw.write("Nume,Prenume,Grupa,Media\n");
            for (Studenti s : studenti) {
                if (s.getMedia()<5) {
                    fw.write(s.getNume() + "," + s.getPrenume() + "," +
                            s.getGrupa().getGrupa() + "," + s.getMedia() + "\n");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //Student, grupa si media la care numele se incepe cu A or E
    @Override
    public void toCSV3() {
        try (FileWriter fw = new FileWriter("raport3.csv")) {
            fw.write("Nume,Prenume,Grupa,Media\n");
            for (Studenti s : studenti) {
                char prima = s.getNume().charAt(0);
                if (prima == 'A' || prima == 'E') {
                    fw.write(s.getNume() + "," + s.getPrenume() + "," +
                            s.getGrupa().getGrupa() + "," + s.getMedia() + "\n");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
