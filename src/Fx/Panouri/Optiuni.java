package Fx.Panouri;

import Fx.App;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Optiuni {
    private Scene scene;

    public Optiuni(App app){
        BorderPane root=new BorderPane();
        root.setStyle("-fx-background-color: #874035;");
        Rectangle rect=new Rectangle(1, 1);
        rect.setArcWidth(30);
        rect.setArcHeight(30);
        rect.setFill(new RadialGradient(1,0,0.5,0.5,1.5,true,CycleMethod.NO_CYCLE,
                new Stop(0, javafx.scene.paint.Color.web("#e7dee2")),
                new Stop(1, javafx.scene.paint.Color.web("#ff8144"))));
        StackPane center = new StackPane();
        center.getChildren().add(rect);
        root.setCenter(center);
        rect.widthProperty().bind(root.widthProperty().multiply(0.85));
        rect.heightProperty().bind(root.heightProperty().multiply(0.85));

        Text text = new Text("OPTIUNI");
        text.setFill(javafx.scene.paint.Color.web("#000000"));
        text.styleProperty().bind(root.widthProperty().divide(9)
                .asString("-fx-font-size: %.0fpx; -fx-font-weight: bold; -fx-font-family: 'League Spartan';"));
        Pane textLayer = new Pane();
        textLayer.getChildren().add(text);
        StackPane centru = new StackPane();
        centru.getChildren().addAll(rect, textLayer);
        root.setCenter(centru);
        textLayer.prefWidthProperty().bind(centru.widthProperty());
        textLayer.prefHeightProperty().bind(centru.heightProperty());
        root.widthProperty().addListener((obs, oldVal, newVal) -> {
            javafx.application.Platform.runLater(() -> {
                text.setLayoutX((textLayer.getWidth() - text.getBoundsInLocal().getWidth()) / 2);
            });
        });
        root.heightProperty().addListener((obs, oldVal, newVal) -> {
            javafx.application.Platform.runLater(() -> {
                text.setLayoutY(textLayer.getHeight() * 0.30);
            });
        });
        javafx.application.Platform.runLater(() -> {
            text.setLayoutX((textLayer.getWidth() - text.getBoundsInLocal().getWidth()) / 2);
            text.setLayoutY(textLayer.getHeight() * 0.30);
        });

        Rectangle btnRect1 = new Rectangle(1, 1);
        btnRect1.setArcWidth(20);
        btnRect1.setArcHeight(20);
        btnRect1.setFill(javafx.scene.paint.Color.web("#a04e41"));
        Text btnText1 = new Text("Modificare");
        btnText1.setFill(javafx.scene.paint.Color.web("#ffffff"));
        btnRect1.widthProperty().bind(root.widthProperty().multiply(0.3));
        btnRect1.heightProperty().bind(root.heightProperty().multiply(0.15));
        StackPane btn1 = new StackPane(btnRect1, btnText1);
        btn1.styleProperty().bind(root.widthProperty().divide(25)
                .asString("-fx-font-size: %.0fpx;"));
        textLayer.getChildren().add(btn1);
        root.widthProperty().addListener((obs, oldVal, newVal) -> {
            javafx.application.Platform.runLater(() -> {
                btn1.setLayoutX(textLayer.getWidth() * 0.15);
            });
        });
        root.heightProperty().addListener((obs, oldVal, newVal) -> {
            javafx.application.Platform.runLater(() -> {
                btn1.setLayoutY(textLayer.getHeight() * 0.39);
            });
        });
        javafx.application.Platform.runLater(() -> {
            btn1.setLayoutX(textLayer.getWidth() * 0.15);
            btn1.setLayoutY(textLayer.getHeight() * 0.5);
        });

        Rectangle btnRect2 = new Rectangle(1, 1);
        btnRect2.setArcWidth(20);
        btnRect2.setArcHeight(20);
        btnRect2.setFill(javafx.scene.paint.Color.web("#a04e41"));
        Text btnText2 = new Text("Afisare");
        btnText2.setFill(javafx.scene.paint.Color.web("#ffffff"));
        btnRect2.widthProperty().bind(root.widthProperty().multiply(0.3));
        btnRect2.heightProperty().bind(root.heightProperty().multiply(0.15));
        StackPane btn2 = new StackPane(btnRect2, btnText2);
        btn2.styleProperty().bind(root.widthProperty().divide(25)
                .asString("-fx-font-size: %.0fpx;"));
        textLayer.getChildren().add(btn2);
        root.widthProperty().addListener((obs, oldVal, newVal) -> {
            javafx.application.Platform.runLater(() -> {
                btn2.setLayoutX(textLayer.getWidth() * 0.55);
            });
        });
        root.heightProperty().addListener((obs, oldVal, newVal) -> {
            javafx.application.Platform.runLater(() -> {
                btn2.setLayoutY(textLayer.getHeight() * 0.39);
            });
        });
        javafx.application.Platform.runLater(() -> {
            btn2.setLayoutX(textLayer.getWidth() * 0.35);
            btn2.setLayoutY(textLayer.getHeight() * 0.5);
        });

        Rectangle btnRect3 = new Rectangle(1, 1);
        btnRect3.setArcWidth(20);
        btnRect3.setArcHeight(20);
        btnRect3.setFill(javafx.scene.paint.Color.web("#a04e41"));
        Text btnText3 = new Text("Rapoarte");
        btnText3.setFill(javafx.scene.paint.Color.web("#ffffff"));
        btnRect3.widthProperty().bind(root.widthProperty().multiply(0.7));
        btnRect3.heightProperty().bind(root.heightProperty().multiply(0.15));
        StackPane btn3 = new StackPane(btnRect3, btnText3);
        btn3.styleProperty().bind(root.widthProperty().divide(25)
                .asString("-fx-font-size: %.0fpx;"));
        textLayer.getChildren().add(btn3);
        root.widthProperty().addListener((obs, oldVal, newVal) -> {
            javafx.application.Platform.runLater(() -> {
                btn3.setLayoutX(textLayer.getWidth() * 0.15);
            });
        });
        root.heightProperty().addListener((obs, oldVal, newVal) -> {
            javafx.application.Platform.runLater(() -> {
                btn3.setLayoutY(textLayer.getHeight() * 0.59);
            });
        });
        javafx.application.Platform.runLater(() -> {
            btn3.setLayoutX(textLayer.getWidth() * 0.55);
            btn3.setLayoutY(textLayer.getHeight() * 0.5);
        });


        Image btnImage = new Image(App.class.getResourceAsStream("/Imagine/acasa.png"));
        ImageView btnView = new ImageView(btnImage);
        btnView.setFitWidth(250);
        btnView.setFitHeight(250);
        btnView.setPreserveRatio(true);
        Button btn = new Button();
        btn.setGraphic(btnView);
        btn.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-padding: 0;");
        textLayer.getChildren().add(btn);
        root.widthProperty().addListener((obs, oldVal, newVal) -> {
            javafx.application.Platform.runLater(() -> {
                btnView.setFitWidth(newVal.doubleValue() * 0.13);
                btn.setLayoutX(textLayer.getWidth() * 0.449);
            });
        });
        root.heightProperty().addListener((obs, oldVal, newVal) -> {
            javafx.application.Platform.runLater(() -> {
                btnView.setFitHeight(newVal.doubleValue() * 0.13);
                btn.setLayoutY(textLayer.getHeight() * 0.8);
            });
        });
        javafx.application.Platform.runLater(() -> {
            btnView.setFitWidth(root.getWidth() * 0.2);
            btnView.setFitHeight(root.getHeight() * 0.2);
            btn.setLayoutX(textLayer.getWidth() * 0.179);
            btn.setLayoutY(textLayer.getHeight() * 0.1);
        });


        btn.setOnAction(e -> app.arataMeniul());



        //btn1.setOnMouseClicked(e -> app.arataModificare());
        //btn2.setOnMouseClicked(e -> app.arataAfisare());
        //btn3.setOnMouseClicked(e -> app.arataRapoarte());

        scene = new Scene(root, 900, 600);

    }

    public Scene getScene() {
        return scene;
    }
}
