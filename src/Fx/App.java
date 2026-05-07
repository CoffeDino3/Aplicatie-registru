package Fx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

import java.awt.*;

public class App extends Application {
    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #874035;");
        stage.setMinWidth(700);
        stage.setMinHeight(500);
        Scene scene = new Scene(root, 900, 600);
        stage.setTitle("Evidenta Studentilor");
        stage.setScene(scene);
        stage.show();
        stage.getIcons().add(new Image(App.class.getResourceAsStream("/Imagine/icon.png")));

        Rectangle rect = new Rectangle(1, 1);
        rect.setArcWidth(30);
        rect.setArcHeight(30);
        rect.setFill(new RadialGradient(
                1,
                0,
                0.5, 0.5,
                1.5,
                true,
                CycleMethod.NO_CYCLE,
                new Stop(0, javafx.scene.paint.Color.web("#e7dee2")),
                new Stop(1, javafx.scene.paint.Color.web("#ff8144"))
        ));
        root.setCenter(rect);
        rect.widthProperty().bind(root.widthProperty().multiply(0.85));
        rect.heightProperty().bind(root.heightProperty().multiply(0.85));
    }


    public static void main(String[] args) {
        launch(args);
    }
}

