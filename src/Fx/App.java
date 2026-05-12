package Fx;

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
import javafx.application.Application;
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

    private Scene mainer;
    public void arataMeniul() {
        if(mainer==null){
            mainer=new Meniu(this).getScene();
        }
        switchScene(mainer);
    }
    private Scene optiune;
    public void arataOptiunile(){
        if(optiune==null){
            optiune=new Optiuni(this).getScene();
        }
        switchScene(optiune);
    }

    private Scene mod;
    public void arataModificare() {
        if(mod==null){
            mod=new Tabele(this).getScene();
        }
        switchScene(mod);
    }

    private Scene aleg;
    public void arataAlegStud(){
        if(aleg==null){
            aleg=new AlegereaStudenti(this).getScene();
        }
        switchScene(aleg);
    }

    private Scene aleg2;
    public void arataAlegNote(){
        if(aleg2==null){
            aleg2=new AlegereaNote(this).getScene();
        }
        switchScene(aleg2);
    }

    private Scene aleg3;
    public void arataAlegGrupe(){
        if(aleg3==null){
            aleg3=new AlegereaGrupe(this).getScene();
        }
        switchScene(aleg3);
    }

    private Scene ad;
    public void arataAdaugGrupe(){
        if(ad==null){
            ad=new AdaugGrupe(this).getScene();
        }
        switchScene(ad);
    }

    private Scene ad2;
    public void arataAdaugNote(){
        if(ad2==null){
            ad2=new AdaugNote(this).getScene();
        }
        switchScene(ad2);
    }

    private Scene ad3;
    public void arataAdaugStudenti(){
        if(ad3==null){
            ad3=new AdaugStudenti(this).getScene();
        }
        switchScene(ad3);
    }
    /*
    private Scene mod1;
    public void arataModGrupe(){
        if(mod1==null){
            mod1=new ModGrupe(this).getScene();
        }
        switchScene(mod1);
    }

    private Scene mod2;
    public void arataModNote(){
        if(mod2==null){
            mod2=new ModNote(this).getScene();
        }
        switchScene(mod2);
    }

    private Scene mod3;
    public void arataModStudenti(){
        if(mod3==null){
            mod3=new ModStudenti(this).getScene();
        }
        switchScene(mod3);
    }

    private Scene st;
    public void arataStergGrupe(){
        if(st==null){
            st=new StergGrupe(this).getScene();
        }
        switchScene(st);
    }

    private Scene st2;
    public void arataStergNote(){
        if(st2==null){
            st2=new StergNote(this).getScene();
        }
        switchScene(st2);
    }

    private Scene st3;
    public void arataStergStudenti(){
        if(st3==null){
            st3=new StergStudenti(this).getScene();
        }
        switchScene(st3);
    }

     */
    /*
    public void arataAfisare() {
        Afisari afis = new Afisari(this);
        stage.setScene(afis.getScene());
    }
    public void arataRapoarte() {
        Rapoarte rap = new Rapoarte(this);
        stage.setScene(rap.getScene());
    }

     */

    public static void main(String[] args) {
        launch(args);
    }
}