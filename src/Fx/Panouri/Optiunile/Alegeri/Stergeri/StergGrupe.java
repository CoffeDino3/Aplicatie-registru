package Fx.Panouri.Optiunile.Alegeri.Stergeri;

import BazaDeDate.CRUD.GrupeCRUD;
import BazaDeDate.CRUD.StudentiCRUD;
import Fx.App;
import Functionalitate.Simple.Grupe;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
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
import java.util.ArrayList;
import java.util.List;

public class StergGrupe {
    private Scene scene;
    private final ArrayList<Grupe> toateGrupele =new ArrayList<>();

    private void aratPopUp(String titlu, String mesaj, String culoareHex, Stage owner) {
        Stage popup= new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.initOwner(owner);
        popup.initStyle(StageStyle.TRANSPARENT);
        StackPane popRoot = new StackPane();
        popRoot.setStyle("-fx-background-color: transparent;");
        Rectangle popRect = new Rectangle(380, 220);
        popRect.setArcWidth(24); popRect.setArcHeight(24);
        popRect.setFill(new RadialGradient(1, 0, 0.5, 0.5, 1.5, true, CycleMethod.NO_CYCLE,
                new Stop(0, javafx.scene.paint.Color.web("#e7dee2")),
                new Stop(1, javafx.scene.paint.Color.web(culoareHex))));
        final double[] delta = new double[2];
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
        Text tTitlu=new Text(titlu);
        tTitlu.setFill(javafx.scene.paint.Color.web("#7a1f1f"));
        tTitlu.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-font-family: 'League Spartan';");
        Text tMsg =new Text(mesaj);
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
        btnOk.setCursor(Cursor.HAND);
        btnOk.setOnMouseClicked(e -> popup.close());
        btnOk.setOnMouseEntered(e -> okRect.setFill(javafx.scene.paint.Color.web("#7a1f1f")));
        btnOk.setOnMouseExited(e -> okRect.setFill(javafx.scene.paint.Color.web("#a04e41")));
        continut.getChildren().addAll(tTitlu, tMsg, btnOk);
        popRoot.getChildren().addAll(popRect, continut);
        Scene popScene =new Scene(popRoot, 380, 220);
        popScene.setFill(javafx.scene.paint.Color.TRANSPARENT);
        popup.setScene(popScene);
        popup.showAndWait();
    }

    private boolean aratConfirmare(Grupe g, Stage owner) {
        int nrStudenti= 0;
        try {
            nrStudenti =StudentiCRUD.countByGrupa(g.getId());
        } catch (SQLException ignored) {}
        String mesaj="Esti sigur ca vrei sa stergi grupa\n"+g.getGrupa()+"?";
        if (nrStudenti > 0) {
            mesaj+="\n\nAceasta va sterge automat si cei "+nrStudenti+"\nstudenti asociati acestei grupe.";
        }
        final boolean[] confirmat ={false};
        Stage popup= new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.initOwner(owner);
        popup.initStyle(StageStyle.TRANSPARENT);
        StackPane popRoot= new StackPane();
        popRoot.setStyle("-fx-background-color: transparent;");
        Rectangle popRect =new Rectangle(420, 260);
        popRect.setArcWidth(24);
        popRect.setArcHeight(24);
        popRect.setFill(new RadialGradient(1, 0, 0.5, 0.5, 1.5, true, CycleMethod.NO_CYCLE,
                new Stop(0, javafx.scene.paint.Color.web("#e7dee2")),
                new Stop(1, javafx.scene.paint.Color.web("#ff8144"))));
        final double[] delta =new double[2];
        popRoot.setOnMousePressed(e -> {
            delta[0]=e.getSceneX();
            delta[1]=e.getSceneY();
        });
        popRoot.setOnMouseDragged(e -> {
            popup.setX(e.getScreenX()-delta[0]);
            popup.setY(e.getScreenY()-delta[1]);
        });
        VBox continut=new VBox(15);
        continut.setAlignment(Pos.CENTER);
        Text tTitlu=new Text("CONFIRMARE STERGERE");
        tTitlu.setFill(javafx.scene.paint.Color.web("#7a1f1f"));
        tTitlu.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-font-family: 'League Spartan';");
        Text tMsg=new Text(mesaj);
        tMsg.setFill(javafx.scene.paint.Color.web("#000000"));
        tMsg.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        tMsg.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        tMsg.setWrappingWidth(360);
        Rectangle daRect=new Rectangle(110, 40);
        daRect.setArcWidth(12); daRect.setArcHeight(12);
        daRect.setFill(javafx.scene.paint.Color.web("#a04e41"));
        Text daText=new Text("Da");
        daText.setFill(javafx.scene.paint.Color.web("#ffffff"));
        daText.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");
        StackPane btnDa=new StackPane(daRect, daText);
        btnDa.setCursor(Cursor.HAND);
        Rectangle nuRect=new Rectangle(110, 40);
        nuRect.setArcWidth(12); nuRect.setArcHeight(12);
        nuRect.setFill(javafx.scene.paint.Color.web("#a04e41"));
        Text nuText=new Text("Nu");
        nuText.setFill(javafx.scene.paint.Color.web("#ffffff"));
        nuText.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");
        StackPane btnNu=new StackPane(nuRect, nuText);
        btnNu.setCursor(Cursor.HAND);
        HBox btns=new HBox(20, btnDa, btnNu);
        btns.setAlignment(Pos.CENTER);
        btnDa.setOnMouseClicked(e -> {
            confirmat[0]=true;
            popup.close();
        });
        btnNu.setOnMouseClicked(e -> popup.close());
        continut.getChildren().addAll(tTitlu, tMsg, btns);
        popRoot.getChildren().addAll(popRect, continut);
        Scene popScene= new Scene(popRoot, 420, 260);
        popScene.setFill(javafx.scene.paint.Color.TRANSPARENT);
        popup.setScene(popScene);
        popup.showAndWait();
        return confirmat[0];
    }

    public StergGrupe(App app) {
        StackPane root= new StackPane();
        root.setStyle("-fx-background-color: #874035;");
        Rectangle rect = new Rectangle();
        rect.setArcWidth(30); rect.setArcHeight(30);
        rect.setFill(new RadialGradient(1, 0, 0.5, 0.5, 1.5, true, CycleMethod.NO_CYCLE,
                new Stop(0, javafx.scene.paint.Color.web("#e7dee2")),
                new Stop(1, javafx.scene.paint.Color.web("#ff8144"))));
        rect.widthProperty().bind(root.widthProperty().multiply(0.85));
        rect.heightProperty().bind(root.heightProperty().multiply(0.90));
        Text title =new Text("STERGE GRUPA");
        title.styleProperty().bind(root.widthProperty().divide(14).asString(
                "-fx-font-size: %.0fpx; -fx-font-weight: bold; -fx-font-family: 'League Spartan';"));
        ComboBox<String> cbId =new ComboBox<>();
        cbId.prefWidthProperty().bind(root.widthProperty().multiply(0.40));
        cbId.prefHeightProperty().bind(root.heightProperty().multiply(0.05));
        cbId.styleProperty().bind(root.widthProperty().divide(70).asString(
                "-fx-background-color: #c97a60; -fx-background-radius: 10; -fx-font-size: %.0fpx; -fx-font-weight: bold;"));
        cbId.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) setText(null);
                else {
                    setText(item);
                    setAlignment(Pos.CENTER);
                }
            }
        });
        cbId.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item==null) setText(null);
                else {
                    setText(item);
                    setAlignment(Pos.CENTER);
                }
            }
        });

        Text tGrupaNume =new Text("-");
        Text tSpec=new Text("-");
        Text tAn=new Text("-");
        Text tClasa =new Text("-");
        Text tNrGrupa=new Text("-");
        String displayStyle ="-fx-font-size: 16px; -fx-font-weight: bold;";
        tGrupaNume.setStyle(displayStyle);
        tSpec.setStyle(displayStyle);
        tAn.setStyle(displayStyle);
        tClasa.setStyle(displayStyle);
        tNrGrupa.setStyle(displayStyle);

        final Grupe[] selectata ={null};
        Runnable refreshIds =() -> {
            cbId.getItems().clear();
            toateGrupele.clear();
            try {
                List<Grupe> lista =GrupeCRUD.getAll();
                toateGrupele.addAll(lista);
                for (Grupe g : toateGrupele) {
                    cbId.getItems().add(String.valueOf(g.getId())+" - "+g.getGrupa());
                }
            } catch (SQLException ignored) {}
        };
        refreshIds.run();

        cbId.setOnAction(e -> {
            int idx =cbId.getSelectionModel().getSelectedIndex();
            if (idx< 0 || idx >=toateGrupele.size()) {
                selectata[0]=null;
                tGrupaNume.setText("-");
                tSpec.setText("-");
                tAn.setText("-");
                tClasa.setText("-");
                tNrGrupa.setText("-");
                return;
            }
            Grupe g =toateGrupele.get(idx);
            selectata[0]=g;
            tGrupaNume.setText(g.getGrupa());
            tSpec.setText(g.getSpecialitate().getNume());
            tAn.setText(String.valueOf(g.getAn()));
            tClasa.setText(String.valueOf(g.getClasa()));
            tNrGrupa.setText(String.valueOf(g.getNrGrupa()));
        });
        VBox rowsBox =new VBox(10);
        rowsBox.setAlignment(Pos.CENTER);
        rowsBox.getChildren().addAll(
                creeazaPreviewRand("ID Grupa:", cbId, root),
                creeazaPreviewRand("Cod Grupa:", tGrupaNume, root),
                creeazaPreviewRand("Specialitate:", tSpec, root),
                creeazaPreviewRand("An Studii:", tAn, root),
                creeazaPreviewRand("An:", tClasa, root),
                creeazaPreviewRand("Numar Grupa:", tNrGrupa, root)
        );



        Rectangle btnRect =new Rectangle();
        btnRect.setArcWidth(15); btnRect.setArcHeight(15);
        btnRect.setFill(javafx.scene.paint.Color.web("#a04e41"));
        btnRect.widthProperty().bind(root.widthProperty().multiply(0.20));
        btnRect.heightProperty().bind(root.heightProperty().multiply(0.08));
        Text btnText =new Text("Sterge");
        btnText.setFill(javafx.scene.paint.Color.web("#ffffff"));
        StackPane btnSterge =new StackPane(btnRect, btnText);
        btnSterge.setCursor(Cursor.HAND);
        btnSterge.styleProperty().bind(root.widthProperty().divide(35).asString(
                "-fx-font-size: %.0fpx; -fx-font-weight: bold;"));
        btnSterge.setOnMouseEntered(e -> btnRect.setFill(javafx.scene.paint.Color.web("#7a1f1f")));
        btnSterge.setOnMouseExited(e -> btnRect.setFill(javafx.scene.paint.Color.web("#a04e41")));
        btnSterge.setOnMouseClicked(e -> {
            Stage owner =(Stage) root.getScene().getWindow();
            if (selectata[0] == null) {
                aratPopUp("EROARE", "Te rog sa selectezi o grupa!", "#ff8144", owner);
                return;
            }
            if (aratConfirmare(selectata[0], owner)) {
                try {
                    GrupeCRUD.sterge(selectata[0].getId());
                    aratPopUp("SUCCES", "Grupa a fost eliminata!", "#ff8144", owner);
                    selectata[0]=null;
                    tGrupaNume.setText("-"); tSpec.setText("-");
                    tAn.setText("-"); tClasa.setText("-"); tNrGrupa.setText("-");
                    refreshIds.run();
                    cbId.setValue(null);
                } catch (SQLException ex) {
                    aratPopUp("EROARE", "Eroare SQL: "+ex.getMessage(), "#ff8144", owner);
                }
            }
        });
        ImageView btnView =new ImageView(new Image(App.class.getResourceAsStream("/Imagine/arrow.png")));
        btnView.setPreserveRatio(true);
        btnView.fitWidthProperty().bind(root.widthProperty().multiply(0.08));
        Button btnBack =new Button();
        btnBack.setGraphic(btnView);
        btnBack.setCursor(Cursor.HAND);
        btnBack.setStyle("-fx-background-color: transparent; -fx-padding: 0;");
        btnBack.setOnAction(e -> app.arataAlegGrupe());
        VBox layout =new VBox(20, title, rowsBox, btnSterge);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(10, 0, 0, 0));
        root.getChildren().addAll(rect, layout, btnBack);
        StackPane.setAlignment(btnBack, Pos.BOTTOM_LEFT);
        btnBack.translateXProperty().bind(root.widthProperty().multiply(0.08));
        btnBack.translateYProperty().bind(root.heightProperty().multiply(-0.07));
        scene =new Scene(root, 900, 650);
    }

    private HBox creeazaPreviewRand(String eticheta, javafx.scene.Node field, StackPane root) {
        HBox rand =new HBox(15);
        rand.setAlignment(Pos.CENTER);
        StackPane sLabel =new StackPane();
        Rectangle bgL =new Rectangle();
        bgL.setArcWidth(8); bgL.setArcHeight(8);
        bgL.setFill(javafx.scene.paint.Color.web("#a04e41"));
        bgL.widthProperty().bind(root.widthProperty().multiply(0.18));
        bgL.heightProperty().bind(root.heightProperty().multiply(0.05));
        Text txt =new Text(eticheta);
        txt.setFill(javafx.scene.paint.Color.web("#ffffff"));
        txt.styleProperty().bind(root.widthProperty().divide(60).asString(
                "-fx-font-size: %.0fpx; -fx-font-weight: bold;"));
        sLabel.getChildren().addAll(bgL, txt);

        if (field instanceof Text textNode) {
            Rectangle bgF =new Rectangle();
            bgF.setArcWidth(8); bgF.setArcHeight(8);
            bgF.setFill(javafx.scene.paint.Color.web("#c97a60"));
            bgF.widthProperty().bind(root.widthProperty().multiply(0.40));
            bgF.heightProperty().bind(root.heightProperty().multiply(0.05));
            StackPane fieldPane =new StackPane(bgF, textNode);
            rand.getChildren().addAll(sLabel, fieldPane);
        } else {
            rand.getChildren().addAll(sLabel, field);
        }
        return rand;
    }

    public Scene getScene() {
        return scene;
    }
}