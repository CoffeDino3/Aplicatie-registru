package Fx.Panouri.Optiunile.Alegeri.Stergeri;
import BazaDeDate.CRUD.NoteCRUD;
import BazaDeDate.CRUD.StudentiCRUD;
import Fx.App;
import Functionalitate.Simple.Studenti;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.File;
import java.sql.SQLException;

public class StergStudenti {
    private Scene scene;
    private void aratPopUp(String titlu, String mesaj, String culoareHex, Stage owner) {
        Stage popup =new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.initOwner(owner);
        popup.initStyle(StageStyle.TRANSPARENT);
        StackPane popRoot =new StackPane();
        popRoot.setStyle("-fx-background-color: transparent;");
        Rectangle popRect= new Rectangle(380, 220);
        popRect.setArcWidth(24);
        popRect.setArcHeight(24);
        popRect.setFill(new RadialGradient(1, 0, 0.5, 0.5, 1.5, true, CycleMethod.NO_CYCLE, new Stop(0, javafx.scene.paint.Color.web("#e7dee2")), new Stop(1, javafx.scene.paint.Color.web(culoareHex))));
        final double[] delta= new double[2];
        popRoot.setOnMousePressed(e-> {
            delta[0]= e.getSceneX();
            delta[1] =e.getSceneY();
        });
        popRoot.setOnMouseDragged(e-> {
            popup.setX(e.getScreenX()- delta[0]);
            popup.setY(e.getScreenY() -delta[1]);
        });


        VBox continut= new VBox(15);
        continut.setAlignment(Pos.CENTER);
        Text tTitlu =new Text(titlu);
        tTitlu.setFill(javafx.scene.paint.Color.web("#7a1f1f"));
        tTitlu.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-font-family: 'League Spartan';");
        Text tMsg= new Text(mesaj);
        tMsg.setFill(javafx.scene.paint.Color.web("#000000"));
        tMsg.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        tMsg.setWrappingWidth(300);
        tMsg.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);


        Rectangle okRect =new Rectangle(120, 40);
        okRect.setArcWidth(12);
        okRect.setArcHeight(12);
        okRect.setFill(javafx.scene.paint.Color.web("#a04e41"));
        Text okText= new Text("OK");
        okText.setFill(javafx.scene.paint.Color.web("#ffffff"));
        okText.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");
        StackPane btnOk= new StackPane(okRect, okText);
        btnOk.setCursor(Cursor.HAND);
        btnOk.setOnMouseEntered(e ->okRect.setFill(javafx.scene.paint.Color.web("#7a1f1f")));
        btnOk.setOnMouseExited(e-> okRect.setFill(javafx.scene.paint.Color.web("#a04e41")));
        btnOk.setOnMouseClicked(e ->popup.close());
        continut.getChildren().addAll(tTitlu, tMsg, btnOk);
        popRoot.getChildren().addAll(popRect, continut);
        Scene popScene =new Scene(popRoot, 380, 220);
        popScene.setFill(javafx.scene.paint.Color.TRANSPARENT);
        popup.setScene(popScene);
        popup.showAndWait();
    }
    private boolean aratConfirmare(Studenti s, Stage owner) {
        int nrNote= 0;
        try {
            nrNote= NoteCRUD.countByStudent(s.getId());
        } catch (SQLException ignored) {}
        String mesaj= "Esti sigur ca vrei sa stergi\nstudentul "+ s.getNume()+ " " +s.getPrenume()+ "?";
        if (nrNote> 0) mesaj += "\n\nAceasta va sterge si "+ nrNote+ (nrNote ==1 ? " nota asociata." : " note asociate.");
        final boolean[] confirmat ={false};
        Stage popup =new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.initOwner(owner);
        popup.initStyle(StageStyle.TRANSPARENT);
        StackPane popRoot= new StackPane();
        popRoot.setStyle("-fx-background-color: transparent;");
        double popH= nrNote >0 ? 270 : 240;
        Rectangle popRect =new Rectangle(400, popH);
        popRect.setArcWidth(24);
        popRect.setArcHeight(24);
        popRect.setFill(new RadialGradient(1, 0, 0.5, 0.5, 1.5, true, CycleMethod.NO_CYCLE, new Stop(0, javafx.scene.paint.Color.web("#e7dee2")), new Stop(1, javafx.scene.paint.Color.web("#ff8144"))));
        final double[] delta =new double[2];
        popRoot.setOnMousePressed(e-> {
            delta[0] =e.getSceneX();
            delta[1]= e.getSceneY();
        });
        popRoot.setOnMouseDragged(e ->{
            popup.setX(e.getScreenX()- delta[0]);
            popup.setY(e.getScreenY() -delta[1]);
        });



        VBox continut =new VBox(15);
        continut.setAlignment(Pos.CENTER);
        Text tTitlu= new Text("CONFIRMARE STERGERE");
        tTitlu.setFill(javafx.scene.paint.Color.web("#7a1f1f"));
        tTitlu.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-font-family: 'League Spartan';");
        Text tMsg =new Text(mesaj);
        tMsg.setFill(javafx.scene.paint.Color.web("#000000"));
        tMsg.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        tMsg.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        tMsg.setWrappingWidth(340);
        Rectangle daRect= new Rectangle(110, 40);
        daRect.setArcWidth(12);
        daRect.setArcHeight(12);
        daRect.setFill(javafx.scene.paint.Color.web("#a04e41"));
        Text daText =new Text("Da");
        daText.setFill(javafx.scene.paint.Color.web("#ffffff"));
        daText.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");
        StackPane btnDa= new StackPane(daRect, daText);
        btnDa.setCursor(Cursor.HAND);
        btnDa.setOnMouseEntered(e ->daRect.setFill(javafx.scene.paint.Color.web("#7a1f1f")));
        btnDa.setOnMouseExited(e-> daRect.setFill(javafx.scene.paint.Color.web("#a04e41")));
        Rectangle nuRect =new Rectangle(110, 40);
        nuRect.setArcWidth(12);
        nuRect.setArcHeight(12);
        nuRect.setFill(javafx.scene.paint.Color.web("#a04e41"));
        Text nuText= new Text("Nu");
        nuText.setFill(javafx.scene.paint.Color.web("#ffffff"));
        nuText.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");
        StackPane btnNu= new StackPane(nuRect, nuText);
        btnNu.setCursor(Cursor.HAND);
        btnNu.setOnMouseEntered(e ->nuRect.setFill(javafx.scene.paint.Color.web("#7a1f1f")));
        btnNu.setOnMouseExited(e-> nuRect.setFill(javafx.scene.paint.Color.web("#a04e41")));
        HBox btns =new HBox(20, btnDa, btnNu);
        btns.setAlignment(Pos.CENTER);
        btnDa.setOnMouseClicked(e-> {
            confirmat[0]= true;
            popup.close();
        });
        btnNu.setOnMouseClicked(e ->popup.close());
        continut.getChildren().addAll(tTitlu, tMsg, btns);
        popRoot.getChildren().addAll(popRect, continut);
        Scene popScene= new Scene(popRoot, 400, popH);
        popScene.setFill(javafx.scene.paint.Color.TRANSPARENT);
        popup.setScene(popScene);
        popup.showAndWait();
        return confirmat[0];
    }
    public StergStudenti(App app) {
        StackPane root =new StackPane();
        root.setStyle("-fx-background-color: #874035;");
        Rectangle rect= new Rectangle();
        rect.setArcWidth(30);
        rect.setArcHeight(30);
        rect.setFill(new RadialGradient(1, 0, 0.5, 0.5, 1.5, true,
                CycleMethod.NO_CYCLE, new Stop(0, javafx.scene.paint.Color.web("#e7dee2")),
                new Stop(1, javafx.scene.paint.Color.web("#ff8144"))));
        rect.widthProperty().bind(root.widthProperty().multiply(0.85));
        rect.heightProperty().bind(root.heightProperty().multiply(0.85));
        Text title =new Text("STERGE STUDENT");
        title.styleProperty().bind(root.widthProperty().divide(14).asString("-fx-font-size: %.0fpx; -fx-font-weight: bold; -fx-font-family: 'League Spartan';"));
        ImageView photoView =new ImageView();
        photoView.setPreserveRatio(true);
        photoView.fitWidthProperty().bind(root.widthProperty().multiply(0.15));
        photoView.fitHeightProperty().bind(root.heightProperty().multiply(0.40));
        try {
            photoView.setImage(new Image(App.class.getResourceAsStream("/Imagine/pfp.png")));
        } catch (Exception ignored) {}
        Rectangle addRect= new Rectangle();
        addRect.setArcWidth(10);
        addRect.setArcHeight(10);
        addRect.setFill(javafx.scene.paint.Color.web("#a04e41"));
        addRect.widthProperty().bind(root.widthProperty().multiply(0.18));
        addRect.heightProperty().bind(root.heightProperty().multiply(0.30));
        StackPane photoBox= new StackPane(addRect, photoView);



        VBox photoColumn =new VBox(8, photoBox);
        photoColumn.setAlignment(Pos.CENTER);
        photoColumn.setPadding(new Insets(50, 0, 0, 30));
        ComboBox<String> cbId= new ComboBox<>();
        cbId.setMinSize(0, 0);
        cbId.prefWidthProperty().bind(root.widthProperty().multiply(0.25));
        cbId.prefHeightProperty().bind(root.widthProperty().multiply(0.025));
        cbId.styleProperty().bind(root.widthProperty().divide(70).asString("-fx-background-color: #c97a60; -fx-background-radius: 10;-fx-font-size: %.0fpx; -fx-font-weight: bold;"));
        Text tNume= makeDisplayText(root);
        Text tPrenume =new Text("-");
        tPrenume.setFill(javafx.scene.paint.Color.web("#000000"));
        tPrenume.styleProperty().bind(root.widthProperty().divide(60).asString("-fx-font-size: %.0fpx; -fx-font-weight: bold;"));
        Text tGrupa =makeDisplayText(root);
        final Studenti[] studentSelectat ={null};
        Runnable refreshIds= ()-> {
            cbId.getItems().clear();
            try {
                for (Studenti s : StudentiCRUD.getAll()) cbId.getItems().add(s.getId()+ " - " +s.getNume()+ " " +s.getPrenume());
            } catch (SQLException ignored) {}
        };
        refreshIds.run();
        cbId.setOnAction(e-> {
            if (cbId.getValue() ==null) {
                studentSelectat[0]= null;
                tNume.setText("-");
                tPrenume.setText("-");
                tGrupa.setText("-");
                resetPhoto(photoView, root);
                return;
            }
            try {
                int id= Integer.parseInt(cbId.getValue().split(" - ")[0]);
                studentSelectat[0] =StudentiCRUD.getById(id);
                if (studentSelectat[0]!= null) {
                    tNume.setText(studentSelectat[0].getNume());
                    tPrenume.setText(studentSelectat[0].getPrenume());
                    tGrupa.setText(studentSelectat[0].getGrupa().getGrupa());
                    String poza= studentSelectat[0].getPoza();
                    if (poza !=null&& !poza.isEmpty()) {
                        try {
                            Image img= poza.startsWith("/") ? new Image(App.class.getResourceAsStream(poza)) : new Image(new File(poza).toURI().toString());
                            photoView.setImage(img);
                        } catch (Exception ex) {
                            resetPhoto(photoView, root);
                        }
                    } else {
                        resetPhoto(photoView, root);
                    }
                }
            } catch (Exception ignored) {}
        });
        VBox formFields =new VBox(12);
        formFields.setAlignment(Pos.CENTER_LEFT);
        formFields.setPadding(new Insets(50, 0, 0, 0));
        formFields.getChildren().addAll(creeazaRand("Nume:", tNume, root), creeazaRand("Prenume:", tPrenume, root), creeazaRand("Grupa:", tGrupa, root), creeazaRand("ID:", cbId, root));
        HBox mainRow= new HBox(30, photoColumn, formFields);
        mainRow.setAlignment(Pos.CENTER);
        VBox.setMargin(mainRow, new Insets(-50, 0, 0, 0));
        Rectangle btnRect =new Rectangle();
        btnRect.setArcWidth(15);
        btnRect.setArcHeight(15);
        btnRect.setFill(javafx.scene.paint.Color.web("#a04e41"));
        btnRect.widthProperty().bind(root.widthProperty().multiply(0.20));
        btnRect.heightProperty().bind(root.heightProperty().multiply(0.08));


        Text btnText= new Text("Sterge");
        btnText.setFill(javafx.scene.paint.Color.web("#ffffff"));
        StackPane btnSterge =new StackPane(btnRect, btnText);
        btnSterge.styleProperty().bind(root.widthProperty().divide(35).asString("-fx-font-size: %.0fpx; -fx-font-weight: bold;"));
        btnSterge.setCursor(Cursor.HAND);
        btnSterge.setOnMouseEntered(e ->btnRect.setFill(javafx.scene.paint.Color.web("#7a1f1f")));
        btnSterge.setOnMouseExited(e-> btnRect.setFill(javafx.scene.paint.Color.web("#a04e41")));
        btnSterge.setOnMouseClicked(e-> {
            Stage owner =(Stage) root.getScene().getWindow();
            if (studentSelectat[0]== null) {
                aratPopUp("EROARE", "Selecteaza un student!", "#ff8144", owner);
                return;
            }
            if (aratConfirmare(studentSelectat[0], owner)) {
                try {
                    StudentiCRUD.sterge(studentSelectat[0].getId());
                    aratPopUp("SUCCES", "Studentul a fost sters!", "#ff8144", owner);
                    studentSelectat[0]= null;
                    tNume.setText("-");
                    tPrenume.setText("-");
                    tGrupa.setText("-");
                    resetPhoto(photoView, root);
                    refreshIds.run();
                    cbId.setValue(null);
                } catch (SQLException ex) {
                    aratPopUp("EROARE", "Eroare BD: "+ ex.getMessage(), "#ff8144", owner);
                }
            }
        });



        ImageView btnView= new ImageView(new Image(App.class.getResourceAsStream("/Imagine/arrow.png")));
        btnView.setPreserveRatio(true);
        btnView.fitWidthProperty().bind(root.widthProperty().multiply(0.08));
        Button btnBack =new Button();
        btnBack.setCursor(Cursor.HAND);
        btnBack.setOnMouseEntered(e ->btnView.setOpacity(0.9));
        btnBack.setOnMouseExited(e-> btnView.setOpacity(1.0));
        btnBack.setGraphic(btnView);
        btnBack.setStyle("-fx-background-color: transparent; -fx-padding: 0;");
        btnBack.setOnAction(e-> app.arataAlegStud());
        VBox mainBox= new VBox(20, title, mainRow, btnSterge, btnBack);
        mainBox.setAlignment(Pos.CENTER);
        mainBox.setPadding(new Insets(50, 0, 0, 0));
        root.getChildren().addAll(rect, mainBox);
        scene =new Scene(root, 900, 600);
    }
    private Text makeDisplayText(StackPane root) {
        Text t =new Text("-");
        t.setFill(javafx.scene.paint.Color.web("#000000"));
        t.styleProperty().bind(root.widthProperty().divide(60).asString("-fx-font-size: %.0fpx; -fx-font-weight: bold;"));
        return t;
    }
    private void resetPhoto(ImageView photoView, StackPane root) {
        try {
            photoView.setImage(new Image(App.class.getResourceAsStream("/Imagine/pfp.png")));
        } catch (Exception ignored) {}
    }

    private HBox creeazaRand(String eticheta, javafx.scene.Node field, StackPane root) {
        HBox rand =new HBox(15);
        rand.setAlignment(Pos.CENTER_LEFT);
        StackPane sLabel= new StackPane();
        Rectangle bgL =new Rectangle();
        bgL.setArcWidth(8);
        bgL.setArcHeight(8);
        bgL.setFill(javafx.scene.paint.Color.web("#a04e41"));
        bgL.widthProperty().bind(root.widthProperty().multiply(0.20));
        bgL.heightProperty().bind(root.heightProperty().multiply(0.05));
        Text txt =new Text(eticheta);
        txt.setFill(javafx.scene.paint.Color.web("#ffffff"));
        txt.styleProperty().bind(root.widthProperty().divide(60).asString("-fx-font-size: %.0fpx; -fx-font-weight: bold;"));
        sLabel.getChildren().addAll(bgL, txt);
        if (field instanceof Text displayText) {
            Rectangle bgF= new Rectangle();
            bgF.setArcWidth(8);
            bgF.setArcHeight(8);
            bgF.setFill(javafx.scene.paint.Color.web("#c97a60"));
            bgF.widthProperty().bind(root.widthProperty().multiply(0.25));

            bgF.heightProperty().bind(root.heightProperty().multiply(0.05));
            StackPane fieldPane= new StackPane(bgF, displayText);
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