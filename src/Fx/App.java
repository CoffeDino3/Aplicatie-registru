package Fx;

import Fx.Panouri.Meniu;
import Fx.Panouri.Optiuni;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.image.Image;


public class App extends Application {
    private Stage stage;
    @Override
    public void start(Stage stage) {
        this.stage = stage;
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #874035;");
        stage.setMinWidth(700);
        stage.setMinHeight(500);
        Scene scene = new Scene(root, 900, 600);
        stage.setTitle("Evidenta Studentilor");
        stage.setScene(scene);
        arataMeniul();
        stage.show();
        stage.getIcons().add(new Image(App.class.getResourceAsStream("/Imagine/icon.png")));
    }

    public void arataMeniul() {
        Meniu menu = new Meniu(this);
        stage.setScene(menu.getScene());
    }
    public void arataOptiunile(){
        Optiuni optiune =new Optiuni(this);
        stage.setScene(optiune.getScene());
    }

    /*public void arataModificare() {
        Modificare mod = new Modificare(this);
        stage.setScene(mod.getScene());
    }
    public void arataAfisare() {
        Afisare afis = new Afisare(this);
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