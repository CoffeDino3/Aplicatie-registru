package Fx.Panouri.Optiunile.Alegeri;

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

public class AlegereaGrupe {
    private Scene scene;

    public AlegereaGrupe(App app) {
        BorderPane root =new BorderPane();
        root.setStyle("-fx-background-color: #874035;");
        Rectangle rect= new Rectangle( 1,1);
        rect.setArcWidth(30);
        rect.setArcHeight(30);
        rect.setFill(new RadialGradient(1, 0 ,0.5,0.5, 1.5,true, CycleMethod.NO_CYCLE,
                new Stop(0, javafx.scene.paint.Color.web("#e7dee2")),
                new Stop( 1, javafx.scene.paint.Color.web("#ff8144") )));
        rect.widthProperty().bind(root.widthProperty().multiply(0.85) );
        rect.heightProperty().bind(root.heightProperty().multiply( 0.85));

        Text text =new Text("GRUPE");
        text.setFill(javafx.scene.paint.Color.web("#000000"));
        text.styleProperty().bind(root.widthProperty().divide( 9 ).asString(
                "-fx-font-size: %.0fpx; -fx-font-weight: bold; -fx-font-family: 'League Spartan';"));
        Pane textLayer=new Pane();
        textLayer.getChildren().add(text);
        StackPane centru =  new StackPane();
        centru.getChildren().addAll(rect,textLayer);
        root.setCenter(centru);
        textLayer.prefWidthProperty().bind(centru.widthProperty());
        textLayer.prefHeightProperty().bind(centru.heightProperty());
        root.widthProperty().addListener((obs,oldVal, newVal) ->{
            javafx.application.Platform.runLater(()->
                    text.setLayoutX((textLayer.getWidth() -text.getBoundsInLocal().getWidth()) /2));
        });
        root.heightProperty().addListener((obs ,oldVal,newVal ) -> {
            javafx.application.Platform.runLater(() ->
                    text.setLayoutY(textLayer.getHeight()* 0.30));
        });
        javafx.application.Platform.runLater(()-> {
            text.setLayoutX((textLayer.getWidth() -text.getBoundsInLocal().getWidth()) /2);
            text.setLayoutY(textLayer.getHeight() *0.28);
        });

        Rectangle r1 =  new Rectangle(1, 1);
        r1.setArcWidth(20); r1.setArcHeight(20);
        r1.setFill(javafx.scene.paint.Color.web("#a04e41"));
        r1.widthProperty().bind(root.widthProperty().multiply(0.29 ));
        r1.heightProperty().bind(root.heightProperty().multiply( 0.15));
        Text t1= new Text("Adauga");
        t1.setFill(javafx.scene.paint.Color.web("#ffffff"));
        StackPane btn1=new StackPane( r1,t1);
        btn1.setCursor(Cursor.HAND);
        btn1.setOnMouseEntered(e ->r1.setFill(javafx.scene.paint.Color.web("#7a1f1f")));
        btn1.setOnMouseExited(e-> r1.setFill(javafx.scene.paint.Color.web("#a04e41")));
        btn1.styleProperty().bind(root.widthProperty().divide(28)
                .asString("-fx-font-size: %.0fpx; -fx-font-weight: bold;"));
        textLayer.getChildren().add(btn1);

        Rectangle r2= new Rectangle( 1,1);
        r2.setArcWidth(20); r2.setArcHeight(20);
        r2.setFill(javafx.scene.paint.Color.web("#a04e41"));
        r2.widthProperty().bind(root.widthProperty().multiply( 0.29));
        r2.heightProperty().bind(root.heightProperty().multiply(0.15) );
        Text t2 =new Text("Modifica");
        t2.setFill(javafx.scene.paint.Color.web("#ffffff"));
        StackPane btn2 = new StackPane(r2,t2);
        btn2.setCursor(Cursor.HAND);
        btn2.setOnMouseEntered(e -> r2.setFill(javafx.scene.paint.Color.web("#7a1f1f")));
        btn2.setOnMouseExited(e->  r2.setFill(javafx.scene.paint.Color.web("#a04e41")));
        btn2.styleProperty().bind(root.widthProperty().divide(28)
                .asString("-fx-font-size: %.0fpx; -fx-font-weight: bold;"));
        textLayer.getChildren().add(btn2);

        Rectangle r3 =  new Rectangle(1, 1);
        r3.setArcWidth(20); r3.setArcHeight(20);
        r3.setFill(javafx.scene.paint.Color.web("#a04e41"));
        r3.widthProperty().bind(root.widthProperty().multiply(0.29 ));
        r3.heightProperty().bind(root.heightProperty().multiply( 0.15));
        Text t3= new Text("Sterge");
        t3.setFill(javafx.scene.paint.Color.web("#ffffff"));
        StackPane btn3=new StackPane( r3,t3);
        btn3.setCursor(Cursor.HAND);
        btn3.setOnMouseEntered(e ->r3.setFill(javafx.scene.paint.Color.web("#7a1f1f")));
        btn3.setOnMouseExited(e-> r3.setFill(javafx.scene.paint.Color.web("#a04e41")));
        btn3.styleProperty().bind(root.widthProperty().divide(28)
                .asString("-fx-font-size: %.0fpx; -fx-font-weight: bold;"));
        textLayer.getChildren().add(btn3);
        root.widthProperty().addListener((obs, oldVal,newVal )-> {
            javafx.application.Platform.runLater(()-> {
                btn1.setLayoutX(textLayer.getWidth()* 0.13);
                btn2.setLayoutX(textLayer.getWidth() *0.35);
                btn3.setLayoutX(textLayer.getWidth()*0.57);
            });
        });
        root.heightProperty().addListener((obs,oldVal, newVal)-> {
            javafx.application.Platform.runLater(() -> {
                btn1.setLayoutY(textLayer.getHeight() *0.43);
                btn2.setLayoutY(textLayer.getHeight() * 0.63);
                btn3.setLayoutY(textLayer.getHeight() *0.43);
            });
        });
        javafx.application.Platform.runLater(()->  {
            btn1.setLayoutX(textLayer.getWidth() * 0.10);
            btn2.setLayoutX(textLayer.getWidth()* 0.39);
            btn3.setLayoutX(textLayer.getWidth() *0.68);
            btn1.setLayoutY(textLayer.getHeight()* 0.45);
            btn2.setLayoutY(textLayer.getHeight()* 0.45);
            btn3.setLayoutY(textLayer.getHeight()* 0.45);
        });

        Image btnImage = new Image(App.class.getResourceAsStream("/Imagine/arrow.png"));
        ImageView btnView =new ImageView( btnImage);
        btnView.setPreserveRatio(true);
        Button btnBack=new Button();
        btnBack.setCursor(Cursor.HAND);
        btnBack.setOnMouseEntered(e ->btnView.setOpacity(0.9));
        btnBack.setOnMouseExited(e -> btnView.setOpacity(1.0));
        btnBack.setGraphic(btnView);
        btnBack.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-padding: 0;");
        textLayer.getChildren().add(btnBack);
        root.widthProperty().addListener((obs, oldVal, newVal ) ->{
            javafx.application.Platform.runLater(() -> {
                btnView.setFitWidth(newVal.doubleValue()* 0.14);
                btnBack.setLayoutX(textLayer.getWidth() *0.07);
            });
        });
        root.heightProperty().addListener((obs,oldVal, newVal) ->{
            javafx.application.Platform.runLater(()-> {
                btnView.setFitHeight(newVal.doubleValue()* 0.14);
                btnBack.setLayoutY(textLayer.getHeight() *0.79);
            });
        });
        javafx.application.Platform.runLater(() -> {
            btnView.setFitWidth(root.getWidth()* 0.08);
            btnView.setFitHeight(root.getHeight()* 0.08);
            btnBack.setLayoutX(textLayer.getWidth() * 0.05);
            btnBack.setLayoutY(textLayer.getHeight() *0.80);
        });

        btnBack.setOnAction(e ->app.arataModificare());
        btn1.setOnMouseClicked(e-> app.arataAdaugGrupe());
        //btn2.setOnMouseClicked(e -> app.arataModGrupe());
        //btn3.setOnMouseClicked(e -> app.arataStergGrupe());
        scene =new Scene(root,900, 600);
    }

    public Scene getScene() {
        return scene;
    }
}