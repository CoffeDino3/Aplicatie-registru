package Fx.Panouri.Optiunile.Alegeri.Adaugari;

import BazaDeDate.CRUD.GrupeCRUD;
import Fx.App;
import Functionalitate.Enumerari.Specialitati;
import Functionalitate.Simple.Grupe;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.SQLException;

public class AdaugGrupe {
    private Scene scene;
    private void aratPopUp(String titlu, String mesaj, String culoareHex, Stage owner) {
        Stage popup =new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.initOwner(owner);
        popup.initStyle(StageStyle.TRANSPARENT);
        StackPane popRoot =new StackPane();
        popRoot.setStyle("-fx-background-color: transparent;");
        Rectangle popRect =new Rectangle(380, 220);
        popRect.setArcWidth(24); popRect.setArcHeight(24);
        popRect.setFill(new RadialGradient(1, 0, 0.5, 0.5, 1.5, true, CycleMethod.NO_CYCLE,
                new Stop(0, javafx.scene.paint.Color.web("#e7dee2")),
                new Stop(1, javafx.scene.paint.Color.web(culoareHex))));
        final double[] delta =new double[2];
        popRoot.setOnMousePressed(e -> {
            delta[0]=e.getSceneX();
            delta[1]=e.getSceneY();
        });
        popRoot.setOnMouseDragged(e -> {
            popup.setX(e.getScreenX()-delta[0]);
            popup.setY(e.getScreenY()-delta[1]);
        });
        VBox continut =new VBox(15);
        continut.setAlignment(Pos.CENTER);

        Text tTitlu =new Text(titlu);
        tTitlu.setFill(javafx.scene.paint.Color.web("#7a1f1f"));
        tTitlu.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-font-family: 'League Spartan';");
        Text tMsg=new Text(mesaj);
        tMsg.setFill(javafx.scene.paint.Color.web("#000000"));
        tMsg.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        tMsg.setWrappingWidth(300);
        tMsg.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);

        Rectangle okRect =new Rectangle(120, 40);
        okRect.setArcWidth(12); okRect.setArcHeight(12);
        okRect.setFill(javafx.scene.paint.Color.web("#a04e41"));
        Text okText =new Text("OK");
        okText.setFill(javafx.scene.paint.Color.web("#ffffff"));
        okText.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");
        StackPane btnOk =new StackPane(okRect, okText);
        btnOk.setOnMouseClicked(e -> popup.close());
        btnOk.setOnMouseEntered(e -> okRect.setFill(javafx.scene.paint.Color.web("#7a1f1f")));
        btnOk.setOnMouseExited(e -> okRect.setFill(javafx.scene.paint.Color.web("#a04e41")));
        continut.getChildren().addAll(tTitlu, tMsg, btnOk);
        popRoot.getChildren().addAll(popRect, continut);
        Scene popScene=new Scene(popRoot, 380, 220);
        popScene.setFill(javafx.scene.paint.Color.TRANSPARENT);
        popup.setScene(popScene);
        popup.showAndWait();
    }

    public AdaugGrupe(App app) {
        StackPane root =new StackPane();
        root.setStyle("-fx-background-color: #874035;");
        Rectangle rect =new Rectangle();
        rect.setArcWidth(30); rect.setArcHeight(30);
        rect.setFill(new RadialGradient(1, 0, 0.5, 0.5, 1.5, true, CycleMethod.NO_CYCLE,
                new Stop(0, javafx.scene.paint.Color.web("#e7dee2")),
                new Stop(1, javafx.scene.paint.Color.web("#ff8144"))));
        rect.widthProperty().bind(root.widthProperty().multiply(0.85));
        rect.heightProperty().bind(root.heightProperty().multiply(0.85));
        VBox mainBox =new VBox(20);
        mainBox.setAlignment(Pos.CENTER);
        Text title =new Text("ADAUGA GRUPA");
        title.styleProperty().bind(root.widthProperty().divide(14).asString(
                "-fx-font-size: %.0fpx; -fx-font-weight: bold; -fx-font-family: 'League Spartan';"));

        VBox form=new VBox(12);
        form.setAlignment(Pos.CENTER);
        ComboBox<Integer> cbAn =creeazaCombo(root);
        for (int i=20; i<=30; i++){
            cbAn.getItems().add(i);
        }

        ComboBox<Integer> cbClasa =creeazaCombo(root);
        for (int i=1; i<=4; i++){
            cbClasa.getItems().add(i);
        }

        ComboBox<Integer> cbNr =creeazaCombo(root);
        for (int i=1; i<=5; i++){
            cbNr.getItems().add(i);
        }

        ComboBox<String> cbSpec =new ComboBox<>();
        for (Specialitati s : Specialitati.values()){
            cbSpec.getItems().add(s.getNume());
        }
        cbSpec.setMinHeight(0);
        cbSpec.setMinWidth(0);
        cbSpec.prefWidthProperty().bind(root.widthProperty().multiply(0.30));
        cbSpec.prefHeightProperty().bind(root.widthProperty().multiply(0.025));
        cbSpec.styleProperty().bind(root.widthProperty().divide(70).asString(
                "-fx-background-color: #c97a60; -fx-background-radius: 10; -fx-font-size: %.0fpx; -fx-font-weight: bold;"
        ));

        form.getChildren().addAll(
                creeazaRand("An inmatriculare:", cbAn, root),
                creeazaRand("Clasa:", cbClasa, root),
                creeazaRand("Nr. grupa:", cbNr, root),
                creeazaRand("Specialitate:", cbSpec, root)
        );

        Rectangle btnRect=new Rectangle();
        btnRect.setArcWidth(15);
        btnRect.setArcHeight(15);
        btnRect.setFill(javafx.scene.paint.Color.web("#a04e41"));
        btnRect.widthProperty().bind(root.widthProperty().multiply(0.20));
        btnRect.heightProperty().bind(root.heightProperty().multiply(0.08));
        Text btnText=new Text("Confirma");
        btnText.setFill(javafx.scene.paint.Color.web("#ffffff"));
        StackPane btnConfirm =new StackPane(btnRect, btnText);
        btnConfirm.styleProperty().bind(root.widthProperty().divide(35).asString(
                "-fx-font-size: %.0fpx; -fx-font-weight: bold;"));

        btnConfirm.setOnMouseEntered(e -> btnRect.setFill(javafx.scene.paint.Color.web("#7a1f1f")));
        btnConfirm.setOnMouseExited(e -> btnRect.setFill(javafx.scene.paint.Color.web("#a04e41")));

        btnConfirm.setOnMouseClicked(e -> {
            Integer an=cbAn.getValue();
            Integer clasa= cbClasa.getValue();
            Integer nr=cbNr.getValue();
            String specStr= cbSpec.getValue();
            Stage owner=(Stage) root.getScene().getWindow();
            if (an==null || clasa==null || nr==null || specStr==null) {
                aratPopUp("EROARE", "Completeaza toate campurile!", "#ff8144", owner);
                return;
            }
            try {
                Specialitati spec =null;
                for (Specialitati s : Specialitati.values()) {
                    if (s.getNume().equals(specStr)) {
                        spec=s; break;
                    }
                }
                Grupe g=new Grupe(0, an, clasa, nr, spec);
                GrupeCRUD.adauga(g);
                aratPopUp("SUCCES", "Grupa "+g.getGrupa()+" a fost adaugata cu succes!", "#ff8144", owner);
                cbAn.setValue(null); cbClasa.setValue(null);
                cbNr.setValue(null); cbSpec.setValue(null);
            } catch (SQLException ex) {
                aratPopUp("EROARE", "Eroare BD: "+ex.getMessage(), "#ff8144", owner);
            } catch (IllegalArgumentException ex) {
                aratPopUp("EROARE", ex.getMessage(), "#ff8144", owner);
            }
        });

        ImageView btnView =new ImageView(new Image(App.class.getResourceAsStream("/Imagine/arrow.png")));
        btnView.setPreserveRatio(true);
        btnView.fitWidthProperty().bind(root.widthProperty().multiply(0.14));
        Button btnBack=new Button();
        btnBack.setCursor(Cursor.HAND);
        btnBack.setOnMouseEntered(e -> btnView.setOpacity(0.9));
        btnBack.setOnMouseExited(e -> btnView.setOpacity(1.0));
        btnBack.setGraphic(btnView);
        btnBack.setStyle("-fx-background-color: transparent; -fx-padding: 0;");
        btnBack.setOnAction(e -> app.arataAlegGrupe());
        mainBox.getChildren().addAll(title, form, btnConfirm, btnBack);
        root.getChildren().addAll(rect, mainBox);
        scene = new Scene(root, 900, 600);
    }

    private HBox creeazaRand(String eticheta, ComboBox<?> cb, StackPane root) {
        HBox rand=new HBox(15);
        rand.setAlignment(Pos.CENTER);
        StackPane sLabel =new StackPane();
        Rectangle bgL= new Rectangle();
        bgL.setArcWidth(8);
        bgL.setArcHeight(8);
        bgL.setFill(javafx.scene.paint.Color.web("#a04e41"));
        bgL.widthProperty().bind(root.widthProperty().multiply(0.25));
        bgL.heightProperty().bind(root.heightProperty().multiply(0.05));
        Text txt=new Text(eticheta);
        txt.setFill(javafx.scene.paint.Color.web("#ffffff"));
        txt.styleProperty().bind(root.widthProperty().divide(60).asString(
                "-fx-font-size: %.0fpx; -fx-font-weight: bold;"));
        sLabel.getChildren().addAll(bgL, txt);
        rand.getChildren().addAll(sLabel, cb);
        return rand;
    }
    private ComboBox<Integer> creeazaCombo(StackPane root) {
        ComboBox<Integer> cb=new ComboBox<>();
        cb.setMinHeight(0);
        cb.setMinWidth(0);
        cb.prefWidthProperty().bind(root.widthProperty().multiply(0.30));
        cb.prefHeightProperty().bind(root.widthProperty().multiply(0.025));
        cb.styleProperty().bind(root.widthProperty().divide(70).asString(
                "-fx-background-color: #c97a60; -fx-background-radius: 10; -fx-font-size: %.0fpx; -fx-font-weight: bold;"
        ));
        return cb;
    }
    public Scene getScene() {
        return scene;
    }
}