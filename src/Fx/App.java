package Fx;

import Fx.Panouri.Afisari.AfisGrupe;
import Fx.Panouri.Afisari.AfisNote;
import Fx.Panouri.Afisari.AfisStudenti;
import Fx.Panouri.Afisari.AfisareTabele;
import Fx.Panouri.Meniu;
import Fx.Panouri.Optiuni;
import Fx.Panouri.Optiunile.Alegeri.Adaugari.AdaugGrupe;
import Fx.Panouri.Optiunile.Alegeri.Adaugari.AdaugNote;
import Fx.Panouri.Optiunile.Alegeri.Adaugari.AdaugStudenti;
import Fx.Panouri.Optiunile.Alegeri.AlegereaGrupe;
import Fx.Panouri.Optiunile.Alegeri.AlegereaNote;
import Fx.Panouri.Optiunile.Alegeri.AlegereaStudenti;
import Fx.Panouri.Optiunile.Alegeri.Modificari.ModGrupe;
import Fx.Panouri.Optiunile.Alegeri.Modificari.ModNote;
import Fx.Panouri.Optiunile.Alegeri.Modificari.ModStudenti;
import Fx.Panouri.Optiunile.Alegeri.Stergeri.StergGrupe;
import Fx.Panouri.Optiunile.Alegeri.Stergeri.StergNote;
import Fx.Panouri.Optiunile.Alegeri.Stergeri.StergStudenti;
import Fx.Panouri.Optiunile.Tabele;
import Fx.Panouri.Report.Rapoarte;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.image.Image;


public class App extends Application {
    private Stage stage;
    @Override
    public void start(Stage stage) {
        this.stage=stage;
        BorderPane root=new BorderPane();
        root.setStyle("-fx-background-color: #874035;");
        stage.setMinWidth(700);
        stage.setMinHeight(500);
        Scene scene=new Scene(root,900,600);
        currentWidth =900;
        currentHeight=600;
        stage.setTitle("Evidenta Studentilor");
        stage.setScene(scene);
        stage.show();
        currentWidth=stage.getWidth();
        currentHeight =stage.getHeight();
        arataMeniul();
        stage.getIcons().add(new Image(App.class.getResourceAsStream("/Imagine/icon.png")));
    }
    private double currentWidth=900;
    private double currentHeight= 600;

    private void switchScene(Scene newScene) {
        currentWidth=stage.getWidth();
        currentHeight =stage.getHeight();
        stage.setScene(newScene);
        javafx.application.Platform.runLater(() -> {
            stage.setWidth(currentWidth);
            stage.setHeight(currentHeight);
        });
    }

    public void arataMeniul() {
        switchScene(new Meniu(this).getScene());
    }

    public void arataOptiunile(){
        switchScene(new Optiuni(this).getScene());
    }

    public void arataModificare() {
        switchScene(new Tabele(this).getScene());
    }

    public void arataAlegStud(){
        switchScene(new AlegereaStudenti(this).getScene());
    }

    public void arataAlegNote(){
        switchScene(new AlegereaNote(this).getScene());
    }

    public void arataAlegGrupe(){
        switchScene(new AlegereaGrupe(this).getScene());
    }

    public void arataAdaugGrupe(){
        switchScene(new AdaugGrupe(this).getScene());
    }

    public void arataAdaugNote(){
        switchScene(new AdaugNote(this).getScene());
    }

    public void arataAdaugStudenti(){
        switchScene(new AdaugStudenti(this).getScene());
    }

    public void arataModStudenti() {
        switchScene(new ModStudenti(this).getScene());
    }

    public void arataModGrupe(){
        switchScene(new ModGrupe(this).getScene());
    }

    public void arataModNote(){
        switchScene(new ModNote(this).getScene());
    }

    public void arataStergStudenti(){
        switchScene(new StergStudenti(this).getScene());
    }

    public void arataStergGrupe(){
        switchScene(new StergGrupe(this).getScene());
    }

    public void arataStergNote(){
        switchScene(new StergNote(this).getScene());
    }

    public void arataAfisareTabele() {
        switchScene(new AfisareTabele(this).getScene());
    }

    public void arataAfisStudenti() {
        switchScene(new AfisStudenti(this).getScene());
    }

    public void arataAfisGrupe() {
        switchScene(new AfisGrupe(this).getScene());
    }

    public void arataAfisNote() {
        switchScene(new AfisNote(this).getScene());
    }

    public void arataRapoarte() {
        switchScene(new Rapoarte(this).getScene());
    }


    public static void main(String[] args) {
        launch(args);
    }
}
