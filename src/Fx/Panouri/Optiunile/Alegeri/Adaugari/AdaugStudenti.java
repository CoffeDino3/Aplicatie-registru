package Fx.Panouri.Optiunile.Alegeri.Adaugari;

import BazaDeDate.CRUD.GrupeCRUD;
import BazaDeDate.CRUD.StudentiCRUD;
import Fx.App;
import Functionalitate.Simple.Grupe;
import Functionalitate.Simple.Studenti;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
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
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.function.UnaryOperator;

public class AdaugStudenti {
    private Scene scene;
    private String pozaPath = "/Imagine/pfp.png" ;

    private void aratPopUp(String titlu, String mesaj, String culoareHex, Stage owner) {
        Stage popup =new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.initOwner(owner);
        popup.initStyle(StageStyle.TRANSPARENT);
        StackPane popRoot =new StackPane();
        popRoot.setStyle("-fx-background-color: transparent;");
        Rectangle popRect = new Rectangle(380, 220);
        popRect.setArcWidth(24);
        popRect.setArcHeight(24);
        popRect.setFill(new RadialGradient(1, 0, 0.5, 0.5, 1.5, true, CycleMethod.NO_CYCLE,
                new Stop(0, javafx.scene.paint.Color.web("#e7dee2")),
                new Stop( 1, javafx.scene.paint.Color.web(culoareHex))));
        final double[] delta = new double[ 2];
        popRoot.setOnMousePressed( e -> {
            delta[ 0] = e.getSceneX();
            delta[1 ] = e.getSceneY();
        });
        popRoot.setOnMouseDragged(e-> {
            popup.setX(e.getScreenX()- delta[0]);
            popup.setY(e.getScreenY() -delta[1]);
        });
        VBox continut = new VBox( 15);
        continut.setAlignment(Pos.CENTER);
        Text tTitlu = new Text( titlu);
        tTitlu.setFill(javafx.scene.paint.Color.web("#7a1f1f"));
        tTitlu.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-font-family: 'League Spartan';");
        Text tMsg = new Text(mesaj);
        tMsg.setFill(javafx.scene.paint.Color.web("#000000"));
        tMsg.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        tMsg.setWrappingWidth( 300);
        tMsg.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        Rectangle okRect = new Rectangle(120, 40);
        okRect.setArcWidth( 12);
        okRect.setArcHeight(12);
        okRect.setFill(javafx.scene.paint.Color.web("#a04e41"));
        Text okText = new Text("OK");
        okText.setFill(javafx.scene.paint.Color.web("#ffffff"));
        okText.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");
        StackPane btnOk = new StackPane(okRect, okText);
        btnOk.setOnMouseClicked(e -> popup.close());
        btnOk.setOnMouseEntered( e -> okRect.setFill(javafx.scene.paint.Color.web("#7a1f1f")));
        btnOk.setOnMouseExited(e -> okRect.setFill(javafx.scene.paint.Color.web("#a04e41")));
        continut.getChildren().addAll(tTitlu, tMsg, btnOk);
        popRoot.getChildren().addAll(popRect, continut);
        Scene popScene = new Scene(popRoot, 380, 220);
        popScene.setFill(javafx.scene.paint.Color.TRANSPARENT);
        popup.setScene(popScene);
        popup.showAndWait();
    }

    public AdaugStudenti(App app) {
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: #874035;");
        Rectangle rect = new Rectangle();
        rect.setArcWidth(30);
        rect.setArcHeight(30);
        rect.setFill(new RadialGradient(1, 0, 0.5, 0.5, 1.5, true, CycleMethod.NO_CYCLE,
                new Stop(0, javafx.scene.paint.Color.web("#e7dee2")),
                new Stop(1, javafx.scene.paint.Color.web("#ff8144"))));
        rect.widthProperty().bind(root.widthProperty().multiply(0.85));
        rect.heightProperty().bind(root.heightProperty().multiply(0.85));

        Text title = new Text("ADAUGA STUDENT");
        title.setTranslateY(40);
        title.styleProperty().bind(root.widthProperty().divide(14).asString(
                "-fx-font-size: %.0fpx; -fx-font-weight: bold; -fx-font-family: 'League Spartan';"));

        ImageView photoView = new ImageView();
        photoView.setOpacity( 0);
        photoView.setPreserveRatio(true);
        photoView.fitWidthProperty().bind(root.widthProperty().multiply( 0.15));
        photoView.fitHeightProperty().bind(root.heightProperty().multiply( 0.55));
        try {
            photoView.setImage(new Image(App.class.getResourceAsStream("/Imagine/pfp.png")));
        } catch (Exception ex ) { }

        Rectangle addRect = new Rectangle();
        addRect.setArcWidth( 10);
        addRect.setArcHeight( 10);
        addRect.setFill(javafx.scene.paint.Color.web("#a04e41"));
        addRect.widthProperty().bind(root.widthProperty().multiply(0.18));
        addRect.heightProperty().bind(root.heightProperty().multiply(0.30));
        Text plusText = new Text("+");
        plusText.setTranslateY(-10);
        plusText.setFill(javafx.scene.paint.Color.web("#ffffff"));
        plusText.styleProperty().bind(root.widthProperty().divide(10).asString("-fx-font-size: %.0fpx; -fx-font-weight: bold;"));

        StackPane photoBox = new StackPane( addRect, photoView, plusText);
        photoBox.setCursor(javafx.scene.Cursor.HAND);
        final boolean[] pozaSelectata = { false };
        photoBox.setOnMouseClicked(e -> {
            FileChooser fc = new FileChooser();
            fc.setTitle("Selecteaza poza");
            fc.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Imagini", "*.png", "*.jpg", "*.jpeg"));
            File file = fc.showOpenDialog((Stage) root.getScene().getWindow());
            if (file != null) {
                pozaPath = file.getAbsolutePath();
                photoView.setImage(new Image(file.toURI().toString()));
                photoView.setOpacity(1);
                plusText.setText("");
                pozaSelectata[ 0] = true;
            }
        });

        Rectangle checkRect = new Rectangle( 40, 40);
        checkRect.setArcWidth( 8);
        checkRect.setArcHeight(8);
        checkRect.setFill(javafx.scene.paint.Color.web("#a04e41"));
        Text checkText = new Text("✓");
        checkText.setFill(javafx.scene.paint.Color.web("#ffffff"));
        checkText.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        StackPane btnCheck = new StackPane(checkRect, checkText);
        btnCheck.setCursor(javafx.scene.Cursor.HAND);
        btnCheck.setOnMouseEntered(e -> checkRect.setFill(javafx.scene.paint.Color.web("#7a1f1f")));
        btnCheck.setOnMouseExited(e-> checkRect.setFill(javafx.scene.paint.Color.web("#a04e41")));

        Rectangle xRect = new Rectangle( 40, 40);
        xRect.setArcWidth( 8);
        xRect.setArcHeight(8);
        xRect.setFill(javafx.scene.paint.Color.web("#a04e41"));
        Text xText = new Text("✕");
        xText.setFill(javafx.scene.paint.Color.web("#ffffff"));
        xText.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        StackPane btnX = new StackPane(xRect, xText);
        btnX.setCursor(javafx.scene.Cursor.HAND);
        btnX.setOnMouseEntered(e -> xRect.setFill(javafx.scene.paint.Color.web("#7a1f1f")));
        btnX.setOnMouseExited(e -> xRect.setFill(javafx.scene.paint.Color.web("#a04e41")));
        btnX.setOnMouseClicked(e -> {
            pozaPath = "/Imagine/pfp.png";
            try {
                photoView.setImage(new Image(App.class.getResourceAsStream("/Imagine/pfp.png")));
                photoView.setOpacity(1);
            } catch (Exception ex) { }
            plusText.setText("");
            pozaSelectata[ 0] = true;
        });

        HBox photoBtns = new HBox( 10, btnCheck, btnX);
        photoBtns.setAlignment(Pos.CENTER);
        VBox photoColumn = new VBox( 8, photoBox, photoBtns);
        photoColumn.setAlignment(Pos.CENTER);
        photoColumn.setPadding(new Insets( 50, 0, 0, 30));

        UnaryOperator<TextFormatter.Change> filter = change -> {
            if (change.getControlNewText().matches("[a-zA-Z\\s\\-]*")) {
                return change;
            }
            return null;
        };

        TextField fNume = new TextField();
        fNume.setTextFormatter(new TextFormatter<>(filter));
        fNume.setMinSize(0, 0);
        fNume.prefWidthProperty().bind(root.widthProperty().multiply( 0.25));
        fNume.prefHeightProperty().bind(root.widthProperty().multiply(0.025));
        fNume.styleProperty().bind(root.widthProperty().divide(70).asString(
                "-fx-background-color: #c97a60; -fx-background-radius: 10; -fx-font-size: %.0fpx; -fx-font-weight: bold;"));

        TextField fPrenume = new TextField();
        fPrenume.setTextFormatter(new TextFormatter<>(filter));
        fPrenume.setMinSize(0, 0);
        fPrenume.prefWidthProperty().bind(root.widthProperty().multiply(0.25));
        fPrenume.prefHeightProperty().bind(root.widthProperty().multiply(0.025));
        fPrenume.styleProperty().bind(root.widthProperty().divide(70).asString(
                "-fx-background-color: #c97a60; -fx-background-radius: 10; -fx-font-size: %.0fpx; -fx-font-weight: bold;"));

        ComboBox<String> cbGrupa = new ComboBox<>();
        cbGrupa.setMinSize( 0, 0);
        cbGrupa.prefWidthProperty().bind(root.widthProperty().multiply(0.25));
        cbGrupa.prefHeightProperty().bind(root.widthProperty().multiply( 0.025));
        cbGrupa.styleProperty().bind(root.widthProperty().divide(70).asString(
                "-fx-background-color: #c97a60; -fx-background-radius: 10; -fx-font-size: %.0fpx; -fx-font-weight: bold;"));
        try {
            ArrayList<Grupe> grupe = GrupeCRUD.getAll();
            for (Grupe g : grupe)
                cbGrupa.getItems().add(g.getGrupa());
        } catch (SQLException ex) {
            cbGrupa.getItems().add("Eroare incarcare");
        }

        VBox formFields = new VBox( 12);
        formFields.setAlignment(Pos.CENTER_LEFT);
        formFields.getChildren().addAll(
                creeazaRand("Nume:", fNume, root),
                creeazaRand("Prenume:", fPrenume, root),
                creeazaRand("Grupa:", cbGrupa, root)
        );
        HBox mainRow = new HBox( 30, photoColumn, formFields);
        mainRow.setAlignment(Pos.CENTER);

        Rectangle btnRect2 = new Rectangle();
        btnRect2.setArcWidth( 15);
        btnRect2.setArcHeight(15);
        btnRect2.setFill(javafx.scene.paint.Color.web("#a04e41"));
        btnRect2.widthProperty().bind(root.widthProperty().multiply( 0.20));
        btnRect2.heightProperty().bind(root.heightProperty().multiply( 0.08));
        Text btnText2 = new Text("Confirma");
        btnText2.setFill(javafx.scene.paint.Color.web("#ffffff"));
        StackPane btnConfirm = new StackPane(btnRect2, btnText2);
        btnConfirm.styleProperty().bind(root.widthProperty().divide(35).asString(
                "-fx-font-size: %.0fpx; -fx-font-weight: bold;"));
        btnConfirm.setCursor(javafx.scene.Cursor.HAND);
        btnConfirm.setOnMouseEntered(e -> btnRect2.setFill(javafx.scene.paint.Color.web("#7a1f1f")));
        btnConfirm.setOnMouseExited(e -> btnRect2.setFill(javafx.scene.paint.Color.web("#a04e41")));

        btnConfirm.setOnMouseClicked(e -> {
            Stage owner = (Stage) root.getScene().getWindow();
            String nume = fNume.getText().trim();
            String prenume = fPrenume.getText().trim();
            String grupaStr = cbGrupa.getValue();

            if (nume.isEmpty() || prenume.isEmpty() || grupaStr == null ) {
                aratPopUp("EROARE", "Completeaza toate campurile!", "#ff8144", owner);
                return;
            }
            if (!pozaSelectata[ 0]) {
                aratPopUp("EROARE", "Selecteaza o poza sau apasa X pentru poza implicita!", "#ff8144", owner);
                return;
            }

            try {
                ArrayList<Grupe> grupe = GrupeCRUD.getAll();
                Grupe grupaSelectata = null;
                for (Grupe g : grupe) {
                    if (g.getGrupa().equals(grupaStr)) {
                        grupaSelectata = g;
                        break;
                    }
                }
                if (grupaSelectata == null) {
                    aratPopUp("EROARE", "Grupa invalida!", "#ff8144", owner);
                    return;
                }

                Studenti s = new Studenti( 0, nume, prenume, grupaSelectata);
                s.setPoza(pozaPath);
                StudentiCRUD.adauga(s);
                aratPopUp("SUCCES", "Studentul " + nume + " " + prenume + " a fost adaugat!", "#ff8144", owner);
                fNume.clear();
                fPrenume.clear();
                cbGrupa.setValue( null);
                pozaPath = "/Imagine/pfp.png";
                try {
                    photoView.setImage(new Image(App.class.getResourceAsStream("/Imagine/pfp.png")));
                    photoView.setOpacity( 0);
                } catch (Exception ex) { }
                plusText.setText("+");
                pozaSelectata[0] = false;
            } catch (SQLException ex) {
                aratPopUp("EROARE", "Eroare BD: " + ex.getMessage(), "#ff8144", owner);
            } catch (IllegalArgumentException ex) {
                aratPopUp("EROARE", ex.getMessage(), "#ff8144", owner);
            }
        });

        ImageView btnView = new ImageView(new Image(App.class.getResourceAsStream("/Imagine/arrow.png")));
        btnView.setPreserveRatio( true);
        btnView.fitWidthProperty().bind(root.widthProperty().multiply(0.08));
        Button btnBack = new Button();
        btnBack.setCursor(Cursor.HAND);
        btnBack.setOnMouseEntered( e -> btnView.setOpacity( 0.9));
        btnBack.setOnMouseExited(e -> btnView.setOpacity( 1.0));
        btnBack.setGraphic( btnView);
        btnBack.setStyle("-fx-background-color: transparent; -fx-padding: 0;");
        btnBack.setOnAction(e -> app.arataAlegStud());

        VBox mainBox = new VBox( 20, title, mainRow, btnConfirm, btnBack);
        mainBox.setAlignment(Pos.CENTER);
        root.getChildren().addAll(rect, mainBox);
        scene = new Scene(root, 900, 600);
    }

    private HBox creeazaRand(String eticheta, javafx.scene.Node field, StackPane root) {
        HBox rand = new HBox( 15);
        rand.setAlignment(Pos.CENTER);
        StackPane sLabel = new StackPane();
        Rectangle bgL = new Rectangle();
        bgL.setArcWidth( 8);
        bgL.setArcHeight( 8);
        bgL.setFill(javafx.scene.paint.Color.web("#a04e41"));
        bgL.widthProperty().bind(root.widthProperty().multiply( 0.20));
        bgL.heightProperty().bind(root.heightProperty().multiply(0.05));
        Text txt = new Text(eticheta);
        txt.setFill(javafx.scene.paint.Color.web("#ffffff"));
        txt.styleProperty().bind(root.widthProperty().divide(60).asString(
                "-fx-font-size: %.0fpx; -fx-font-weight: bold;"));
        sLabel.getChildren().addAll(bgL, txt);
        rand.getChildren().addAll(sLabel, field);
        return rand;
    }

    public Scene getScene() {
        return scene;
    }
}