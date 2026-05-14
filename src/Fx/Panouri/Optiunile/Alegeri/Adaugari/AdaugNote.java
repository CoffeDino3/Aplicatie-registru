package Fx.Panouri.Optiunile.Alegeri.Adaugari;

import BazaDeDate.CRUD.NoteCRUD;
import BazaDeDate.CRUD.StudentiCRUD;
import Fx.App;
import Functionalitate.Enumerari.Discipline;
import Functionalitate.Simple.Note;
import Functionalitate.Simple.Studenti;
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

public class AdaugNote {
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

    public AdaugNote(App app) {
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
        Text title =new Text("ADAUGA NOTA");
        title.styleProperty().bind(root.widthProperty().divide(14).asString(
                "-fx-font-size: %.0fpx; -fx-font-weight: bold; -fx-font-family: 'League Spartan';"));

        VBox form=new VBox(12);
        form.setAlignment(Pos.CENTER);

        ComboBox<String> cbStudent =new ComboBox<>();
        cbStudent.setMinHeight(0);
        cbStudent.setMinWidth(0);
        cbStudent.prefWidthProperty().bind(root.widthProperty().multiply(0.30));
        cbStudent.prefHeightProperty().bind(root.widthProperty().multiply(0.025));
        cbStudent.styleProperty().bind(root.widthProperty().divide(70).asString(
                "-fx-background-color: #c97a60; -fx-background-radius: 10; -fx-font-size: %.0fpx; -fx-font-weight: bold;"
        ));
        try {
            for (Studenti s : StudentiCRUD.getAll()){
                cbStudent.getItems().add(s.getId()+" - "+s.getNume()+" "+s.getPrenume());
            }
        } catch (SQLException ex) {
            cbStudent.getItems().add("Eroare incarcare");
        }

        ComboBox<Integer> cbNota =creeazaCombo(root);
        for (int i=1; i<=10; i++){
            cbNota.getItems().add(i);
        }

        ComboBox<String> cbDisc =new ComboBox<>();
        cbDisc.setMinHeight(0);
        cbDisc.setMinWidth(0);
        cbDisc.prefWidthProperty().bind(root.widthProperty().multiply(0.30));
        cbDisc.prefHeightProperty().bind(root.widthProperty().multiply(0.025));
        cbDisc.styleProperty().bind(root.widthProperty().divide(70).asString(
                "-fx-background-color: #c97a60; -fx-background-radius: 10; -fx-font-size: %.0fpx; -fx-font-weight: bold;"
        ));
        for (Discipline d : Discipline.values()){
            cbDisc.getItems().add(d.getDenumire());
        }

        form.getChildren().addAll(
                creeazaRand("Student:", cbStudent, root),
                creeazaRand("Nota:", cbNota, root),
                creeazaRand("Disciplina:", cbDisc, root)
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
            String studentStr=cbStudent.getValue();
            Integer nota=cbNota.getValue();
            String discStr=cbDisc.getValue();
            Stage owner=(Stage) root.getScene().getWindow();
            if (studentStr==null || nota==null || discStr==null) {
                aratPopUp("EROARE", "Completeaza toate campurile!", "#ff8144", owner);
                return;
            }
            try {
                int idStudent=Integer.parseInt(studentStr.split(" - ")[0]);
                Discipline disc=null;
                for (Discipline d : Discipline.values()) {
                    if (d.getDenumire().equals(discStr)) {
                        disc=d; break;
                    }
                }
                Note n=new Note(nota, disc);
                NoteCRUD.adauga(n, idStudent);
                aratPopUp("SUCCES", "Nota "+nota+" a fost adaugata cu succes!", "#ff8144", owner);
                cbStudent.setValue(null); cbNota.setValue(null);
                cbDisc.setValue(null);
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
        btnBack.setOnAction(e -> app.arataAlegNote());
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