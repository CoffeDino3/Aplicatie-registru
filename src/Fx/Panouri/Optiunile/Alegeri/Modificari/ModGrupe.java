package Fx.Panouri.Optiunile.Alegeri.Modificari;
import BazaDeDate.CRUD.GrupeCRUD;
import Fx.App;
import Functionalitate.Enumerari.Specialitati;
import Functionalitate.Simple.Grupe;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
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

public class ModGrupe {
    private Scene scene;

    private void aratPopUp(String titlu, String mesaj, String culoareHex, Stage owner) {
        Stage popup= new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.initOwner(owner);
        popup.initStyle(StageStyle.TRANSPARENT);
        StackPane popRoot =new StackPane();
        popRoot.setStyle("-fx-background-color: transparent;");
        Rectangle popRect =new Rectangle(380, 220);
        popRect.setArcWidth(24);
        popRect.setArcHeight(24);
        popRect.setFill(new RadialGradient(1, 0, 0.5, 0.5, 1.5, true, CycleMethod.NO_CYCLE, new Stop(0, javafx.scene.paint.Color.web("#e7dee2")), new Stop(1, javafx.scene.paint.Color.web(culoareHex))));

        final double[] delta= new double[2];
        popRoot.setOnMousePressed(e -> {
            delta[0]= e.getSceneX();
            delta[1] =e.getSceneY();
        });
        popRoot.setOnMouseDragged(e -> {
            popup.setX(e.getScreenX()- delta[0]);
            popup.setY(e.getScreenY() -delta[1]);
        });

        VBox continut =new VBox(15);
        continut.setAlignment(Pos.CENTER);
        Text tTitlu =new Text(titlu);
        tTitlu.setFill(javafx.scene.paint.Color.web("#7a1f1f"));
        tTitlu.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-font-family: 'League Spartan';");
        Text tMsg =new Text(mesaj);
        tMsg.setFill(javafx.scene.paint.Color.web("#000000"));
        tMsg.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        tMsg.setWrappingWidth(300);
        tMsg.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        Rectangle okRect =new Rectangle(120, 40);
        okRect.setArcWidth(12);
        okRect.setArcHeight(12);
        okRect.setFill(javafx.scene.paint.Color.web("#a04e41"));
        Text okText =new Text("OK");
        okText.setFill(javafx.scene.paint.Color.web("#ffffff"));
        okText.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");
        StackPane btnOk =new StackPane(okRect, okText);
        btnOk.setCursor(Cursor.HAND);
        btnOk.setOnMouseClicked(e -> popup.close());
        continut.getChildren().addAll(tTitlu, tMsg, btnOk);
        popRoot.getChildren().addAll(popRect, continut);
        Scene popScene= new Scene(popRoot, 380, 220);
        popScene.setFill(javafx.scene.paint.Color.TRANSPARENT);
        popup.setScene(popScene);
        popup.showAndWait();
    }

    public ModGrupe(App app) {
        StackPane root =new StackPane();
        root.setStyle("-fx-background-color: #874035;");
        Rectangle rect= new Rectangle();
        rect.setArcWidth(30);
        rect.setArcHeight(30);
        rect.setFill(new RadialGradient(1, 0, 0.5, 0.5, 1.5, true, CycleMethod.NO_CYCLE, new Stop(0, javafx.scene.paint.Color.web("#e7dee2")), new Stop(1, javafx.scene.paint.Color.web("#ff8144"))));
        rect.widthProperty().bind(root.widthProperty().multiply(0.85));
        rect.heightProperty().bind(root.heightProperty().multiply(0.85));

        Text title= new Text("MODIFICA GRUPA");
        title.styleProperty().bind(root.widthProperty().divide(14).asString("-fx-font-size: %.0fpx; -fx-font-weight: bold; -fx-font-family: 'League Spartan';"));

        HBox headerRow= new HBox(0, creeazaHeaderCelula("ID", 0.10, root),
                creeazaHeaderCelula("Grupa", 0.18, root), creeazaHeaderCelula("An", 0.10, root),
                creeazaHeaderCelula("An", 0.10, root), creeazaHeaderCelula("Nr", 0.10, root),
                creeazaHeaderCelula("Specialitate", 0.21, root));
        headerRow.setAlignment(Pos.CENTER);

        ComboBox<String> cbId =new ComboBox<>();
        cbId.prefWidthProperty().bind(root.widthProperty().multiply(0.10));
        cbId.prefHeightProperty().bind(root.widthProperty().multiply(0.04));
        cbId.styleProperty().bind(root.widthProperty().divide(70).asString("-fx-background-color: #c97a60; -fx-background-radius: 5;" + " -fx-font-size: %.0fpx; -fx-font-weight: bold;"));

        Runnable refreshIds = () -> {
            cbId.getItems().clear();
            try {
                for (Grupe g : GrupeCRUD.getAll()) {
                    cbId.getItems().add(g.getId()+ " - " +g.getGrupa());
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        };
        refreshIds.run();

        Text tGrupa =new Text("-");
        Text tAn = new Text("-");
        Text tClasa= new Text("-");
        Text tNr =new Text("-");
        Text tSpec = new Text("-");

        HBox dataRow =new HBox(0, new StackPane(cbId), creeazaDataCelula(tGrupa, 0.18, root), creeazaDataCelula(tAn, 0.10, root), creeazaDataCelula(tClasa, 0.10, root), creeazaDataCelula(tNr, 0.10, root), creeazaDataCelula(tSpec, 0.21, root));
        dataRow.setAlignment(Pos.CENTER);

        final Grupe[] grupaSelectata= new Grupe[1];

        cbId.setOnAction(e -> {
            try {
                String val =cbId.getValue();
                if (val ==null) return;
                int id = Integer.parseInt(val.split(" ")[0]);
                grupaSelectata[0]= GrupeCRUD.getById(id);
                if (grupaSelectata[0]!= null) {
                    tGrupa.setText(grupaSelectata[0].getGrupa());
                    tAn.setText(String.valueOf(grupaSelectata[0].getAn()));
                    tClasa.setText(String.valueOf(grupaSelectata[0].getClasa()));
                    tNr.setText(String.valueOf(grupaSelectata[0].getNrGrupa()));
                    tSpec.setText(grupaSelectata[0].getSpecialitate().getNume());
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        CheckBox ckAn= new CheckBox();
        CheckBox ckClasa =new CheckBox();
        CheckBox ckNr= new CheckBox();
        CheckBox ckSpec = new CheckBox();

        HBox checkRow =new HBox(50, creeazaCheckLabel("An", ckAn, root), creeazaCheckLabel("An", ckClasa, root), creeazaCheckLabel("Nr", ckNr, root), creeazaCheckLabel("Specialitate", ckSpec, root));
        checkRow.spacingProperty().bind(root.widthProperty().multiply(0.06));
        checkRow.setAlignment(Pos.CENTER);

        String fieldStyle = "-fx-background-color: #c97a60; -fx-background-radius: 8; -fx-font-weight: bold;";

        ComboBox<Integer> cbAnField =new ComboBox<>();
        for (int i= 20; i <=30; i++) cbAnField.getItems().add(i);
        cbAnField.styleProperty().bind(root.widthProperty().divide(70).asString(fieldStyle+ " -fx-font-size: %.0fpx;"));
        cbAnField.prefWidthProperty().bind(root.widthProperty().multiply(0.25));

        ComboBox<Integer> cbClasaField = new ComboBox<>();
        for (int i = 1; i <=4; i++) cbClasaField.getItems().add(i);
        cbClasaField.styleProperty().bind(root.widthProperty().divide(70).asString(fieldStyle + " -fx-font-size: %.0fpx;"));
        cbClasaField.prefWidthProperty().bind(root.widthProperty().multiply(0.25));

        ComboBox<Integer> cbNrField =new ComboBox<>();
        for (int i= 1; i <=5; i++) cbNrField.getItems().add(i);
        cbNrField.styleProperty().bind(root.widthProperty().divide(70).asString(fieldStyle+ " -fx-font-size: %.0fpx;"));
        cbNrField.prefWidthProperty().bind(root.widthProperty().multiply(0.25));

        ComboBox<String> cbSpecField = new ComboBox<>();
        for (Specialitati s : Specialitati.values()) cbSpecField.getItems().add(s.getNume());
        cbSpecField.styleProperty().bind(root.widthProperty().divide(70).asString(fieldStyle+ " -fx-font-size: %.0fpx;"));
        cbSpecField.prefWidthProperty().bind(root.widthProperty().multiply(0.25));

        VBox editFieldsContainer =new VBox(10);
        editFieldsContainer.setAlignment(Pos.CENTER);

        HBox randAn =creeazaRandEdit("An:", cbAnField, root);
        randAn.visibleProperty().bind(ckAn.selectedProperty());
        randAn.managedProperty().bind(ckAn.selectedProperty());

        HBox randClasa = creeazaRandEdit("An:", cbClasaField, root);
        randClasa.visibleProperty().bind(ckClasa.selectedProperty());
        randClasa.managedProperty().bind(ckClasa.selectedProperty());

        HBox randNr =creeazaRandEdit("Nr grupa:", cbNrField, root);
        randNr.visibleProperty().bind(ckNr.selectedProperty());
        randNr.managedProperty().bind(ckNr.selectedProperty());

        HBox randSpec = creeazaRandEdit("Specialitate:", cbSpecField, root);
        randSpec.visibleProperty().bind(ckSpec.selectedProperty());
        randSpec.managedProperty().bind(ckSpec.selectedProperty());

        editFieldsContainer.getChildren().addAll(randAn, randClasa, randNr, randSpec);

        Rectangle btnRect =new Rectangle();
        btnRect.setArcWidth(15);
        btnRect.setArcHeight(15);
        btnRect.setFill(javafx.scene.paint.Color.web("#a04e41"));
        btnRect.widthProperty().bind(root.widthProperty().multiply(0.20));
        btnRect.heightProperty().bind(root.heightProperty().multiply(0.08));
        Text btnText= new Text("Confirma");
        btnText.setFill(javafx.scene.paint.Color.WHITE);
        StackPane btnConfirm =new StackPane(btnRect, btnText);
        btnConfirm.styleProperty().bind(root.widthProperty().divide(35).asString("-fx-font-size: %.0fpx; -fx-font-weight: bold;"));
        btnConfirm.setCursor(Cursor.HAND);

        btnConfirm.setOnMouseClicked(e -> {
            Stage owner = (Stage) root.getScene().getWindow();
            if (grupaSelectata[0] ==null) {
                aratPopUp("EROARE", "Selecteaza ID!", "#ff8144", owner);
                return;
            }
            if (!ckAn.isSelected()&& !ckClasa.isSelected() && !ckNr.isSelected()&& !ckSpec.isSelected()) {
                aratPopUp("EROARE", "Selecteaza ce vrei sa modifici!", "#ff8144", owner);
                return;
            }
            try {
                if (ckAn.isSelected() &&cbAnField.getValue() != null) {
                    grupaSelectata[0].setAn(cbAnField.getValue());
                    tAn.setText(String.valueOf(cbAnField.getValue()));
                }
                if (ckClasa.isSelected()&& cbClasaField.getValue() != null) {
                    grupaSelectata[0].setClasa(cbClasaField.getValue());
                    tClasa.setText(String.valueOf(cbClasaField.getValue()));
                }
                if (ckNr.isSelected() &&cbNrField.getValue() != null) {
                    grupaSelectata[0].setNrGrupa(cbNrField.getValue());
                    tNr.setText(String.valueOf(cbNrField.getValue()));
                }
                if (ckSpec.isSelected()&& cbSpecField.getValue() != null) {
                    for (Specialitati s : Specialitati.values()) {
                        if (s.getNume().equals(cbSpecField.getValue())) {
                            grupaSelectata[0].setSpecialitate(s);
                            tSpec.setText(s.getNume());
                            break;
                        }
                    }
                }
                tGrupa.setText(grupaSelectata[0].getGrupa());
                GrupeCRUD.actualizeaza(grupaSelectata[0]);
                refreshIds.run();
                aratPopUp("SUCCES", "Modificat!", "#ff8144", owner);
            } catch (Exception ex) {
                aratPopUp("EROARE", ex.getMessage(), "#ff8144", owner);
            }
        });

        ImageView btnView =new ImageView(new Image(App.class.getResourceAsStream("/Imagine/arrow.png")));
        btnView.setPreserveRatio(true);
        btnView.fitWidthProperty().bind(root.widthProperty().multiply(0.08));
        Button btnBack =new Button();
        btnBack.setGraphic(btnView);
        btnBack.setStyle("-fx-background-color: transparent;");
        btnBack.setCursor(Cursor.HAND);
        btnBack.setOnAction(e -> app.arataAlegGrupe());

        VBox layout= new VBox(0);
        layout.setAlignment(Pos.CENTER);
        VBox tableGroup =new VBox(0, headerRow, dataRow);
        tableGroup.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(creeazaSpatiu(0), title, creeazaSpatiu(20), tableGroup, creeazaSpatiu(10), checkRow, creeazaSpatiu(10), editFieldsContainer, creeazaSpatiu(10), btnConfirm);
        root.getChildren().addAll(rect, layout, btnBack);
        StackPane.setAlignment(btnBack, Pos.BOTTOM_LEFT);
        btnBack.translateXProperty().bind(root.widthProperty().multiply(0.08));
        btnBack.translateYProperty().bind(root.heightProperty().multiply(-0.07));
        scene= new Scene(root, 900, 600);
    }

    private StackPane creeazaHeaderCelula(String text, double wMult, StackPane root) {
        Rectangle r= new Rectangle();
        r.widthProperty().bind(root.widthProperty().multiply(wMult));
        r.setHeight(35);
        r.setArcWidth(10);
        r.setArcHeight(10);
        r.setFill(javafx.scene.paint.Color.web("#a04e41"));
        Text t =new Text(text);
        t.setFill(javafx.scene.paint.Color.WHITE);
        t.styleProperty().bind(root.widthProperty().divide(65).asString("-fx-font-weight: bold; -fx-font-size: %.0fpx;"));
        return new StackPane(r, t);
    }

    private StackPane creeazaDataCelula(Text t, double wMult, StackPane root) {
        Rectangle r =new Rectangle();
        r.widthProperty().bind(root.widthProperty().multiply(wMult));
        r.heightProperty().bind(root.widthProperty().multiply(0.04));
        r.setArcWidth(10);
        r.setArcHeight(10);
        r.setFill(javafx.scene.paint.Color.WHITE);
        r.setStroke(javafx.scene.paint.Color.web("#a04e41"));
        t.styleProperty().bind(root.widthProperty().divide(70).asString("-fx-font-weight: bold; -fx-font-size: %.0fpx;"));
        return new StackPane(r, t);
    }

    private HBox creeazaCheckLabel(String label, CheckBox cb, StackPane root) {
        HBox box= new HBox(5);
        box.setAlignment(Pos.CENTER);
        StackPane sLabel =new StackPane();
        Rectangle r =new Rectangle();
        r.widthProperty().bind(root.widthProperty().multiply(0.12));
        r.heightProperty().bind(root.widthProperty().multiply(0.035));
        r.setArcWidth(8);
        r.setArcHeight(8);
        r.setFill(javafx.scene.paint.Color.web("#2b2b2b"));
        Text t= new Text(label);
        t.setFill(javafx.scene.paint.Color.WHITE);
        t.styleProperty().bind(root.widthProperty().divide(80).asString("-fx-font-weight: bold; -fx-font-size: %.0fpx;"));
        cb.scaleXProperty().bind(root.widthProperty().divide(450));
        cb.scaleYProperty().bind(root.widthProperty().divide(450));
        sLabel.getChildren().addAll(r, t);
        box.getChildren().addAll(sLabel, cb);
        return box;
    }

    private HBox creeazaRandEdit(String eticheta, javafx.scene.Node field, StackPane root) {
        HBox rand =new HBox(15);
        rand.setAlignment(Pos.CENTER);
        StackPane sLabel =new StackPane();
        Rectangle bgL= new Rectangle();
        bgL.setArcWidth(8);
        bgL.setArcHeight(8);
        bgL.setFill(javafx.scene.paint.Color.web("#a04e41"));
        bgL.widthProperty().bind(root.widthProperty().multiply(0.15));
        bgL.heightProperty().bind(root.heightProperty().multiply(0.04));
        Text txt =new Text(eticheta);
        txt.setFill(javafx.scene.paint.Color.WHITE);
        txt.styleProperty().bind(root.widthProperty().divide(60).asString("-fx-font-size: %.0fpx; -fx-font-weight: bold;"));
        sLabel.getChildren().addAll(bgL, txt);
        rand.getChildren().addAll(sLabel, field);
        return rand;
    }

    private Region creeazaSpatiu(double inaltime) {
        Region spacer= new Region();
        spacer.setPrefHeight(inaltime);
        return spacer;
    }

    public Scene getScene() {
        return scene;
    }
}