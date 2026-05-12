package Fx.Panouri;

import Fx.App;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Meniu {
    private Scene scene;

    public Meniu(App app) {
        BorderPane root=  new BorderPane();
        root.setStyle("-fx-background-color: #874035;");
        Rectangle rect =new Rectangle(1 , 1);
        rect.setArcWidth( 30 );
        rect.setArcHeight(30);
        rect.setFill(new RadialGradient(1,0, 0.5,0.5, 1.5,true, CycleMethod.NO_CYCLE,
                new Stop( 0, javafx.scene.paint.Color.web("#e7dee2")),
                new Stop(1,javafx.scene.paint.Color.web("#ff8144") )));
        rect.widthProperty().bind(root.widthProperty().multiply(0.85) );
        rect.heightProperty().bind(root.heightProperty().multiply( 0.85));
        Text text =new Text("REGISTRU");
        text.setFill(javafx.scene.paint.Color.web("#000000"));
        text.styleProperty().bind(root.widthProperty().divide( 9 )
                .asString("-fx-font-size: %.0fpx; -fx-font-weight: bold; -fx-font-family: 'League Spartan';"));
        Pane textLayer=new Pane();
        textLayer.getChildren().add(text);
        StackPane center =  new StackPane();
        center.getChildren().addAll(rect,textLayer);
        root.setCenter(center);
        textLayer.prefWidthProperty().bind(center.widthProperty());
        textLayer.prefHeightProperty().bind(center.heightProperty());
        root.widthProperty().addListener((obs,oldVal, newVal) ->{
            javafx.application.Platform.runLater(()-> {
                text.setLayoutX((textLayer.getWidth()- text.getBoundsInLocal().getWidth() )/2);
            });
        });
        root.heightProperty().addListener((obs ,oldVal,newVal ) -> {
            javafx.application.Platform.runLater(() ->{
                javafx.application.Platform.runLater(()->  {
                    text.setLayoutX(( textLayer.getWidth() -text.getBoundsInLocal().getWidth()) / 2);
                    text.setLayoutY(textLayer.getHeight()* 0.30);
                });
            });
        });
        javafx.application.Platform.runLater(()-> {
            text.setLayoutX((textLayer.getWidth() -text.getBoundsInLocal().getWidth()) /2);
            text.setLayoutY(textLayer.getHeight() *0.30);
        });


        Image btnImage =new Image(App.class.getResourceAsStream("/Imagine/play.png"));
        ImageView btnView= new ImageView(btnImage);
        btnView.setFitWidth( 250);
        btnView.setFitHeight(250);
        btnView.setPreserveRatio(true);
        Button btn =  new Button();
        btn.setCursor(Cursor.HAND);
        btn.setOnMouseEntered(e-> btnView.setOpacity(0.9));
        btn.setOnMouseExited(e ->btnView.setOpacity(1.0));
        btn.setGraphic(btnView);
        btn.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-padding: 0;");
        textLayer.getChildren().add(btn);
        root.widthProperty().addListener((obs, oldVal,newVal)-> {
            javafx.application.Platform.runLater(() -> {
                btnView.setFitWidth(newVal.doubleValue()* 0.4);
                btn.setLayoutX(textLayer.getWidth() *0.39);
            });
        });
        root.heightProperty().addListener((obs ,oldVal, newVal) -> {
            javafx.application.Platform.runLater(()-> {
                btnView.setFitHeight(newVal.doubleValue() *0.4);
                btn.setLayoutY(textLayer.getHeight()* 0.4);
            });
        });
        javafx.application.Platform.runLater(() -> {
            btnView.setFitWidth(root.getWidth()* 0.4);
            btnView.setFitHeight(root.getHeight() *0.4);
            btn.setLayoutX(textLayer.getWidth()* 0.379);
            btn.setLayoutY(textLayer.getHeight() * 0.4);
        });


        btn.setOnAction(e ->app.arataOptiunile());
        scene =new Scene(root,900, 600);
    }

    public Scene getScene() {
        return scene;
    }

}