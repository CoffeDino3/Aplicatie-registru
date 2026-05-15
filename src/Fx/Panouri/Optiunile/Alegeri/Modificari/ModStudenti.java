package Fx.Panouri.Optiunile.Alegeri.Modificari;
import BazaDeDate.CRUD.GrupeCRUD;
import BazaDeDate.CRUD.StudentiCRUD;
import Fx.App;
import Functionalitate.Simple.Grupe;
import Functionalitate.Simple.Studenti;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
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
import java.util.function.UnaryOperator;

public class ModStudenti {
    private Scene scene;

    private void aratPopUp(String titlu, String mesaj, String culoareHex, Stage owner) {
        Stage popup=new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);

        popup.initOwner(owner);
        popup.initStyle(StageStyle.TRANSPARENT);
        StackPane popRoot =new StackPane();

        popRoot.setStyle("-fx-background-color: transparent;");
        Rectangle popRect = new Rectangle(380, 220);
        popRect.setArcWidth(24);
        popRect.setArcHeight(24);

        popRect.setFill(new RadialGradient(1, 0, 0.5, 0.5, 1.5, true, CycleMethod.NO_CYCLE, new Stop(0, javafx.scene.paint.Color.web("#e7dee2")), new Stop(1, javafx.scene.paint.Color.web(culoareHex))));
        final double[] delta =new double[2];

        popRoot.setOnMousePressed(e -> {
            delta[0] =e.getSceneX();
            delta[1]= e.getSceneY();
        });

        popRoot.setOnMouseDragged(e -> {
            popup.setX(e.getScreenX() -delta[0]);
            popup.setY(e.getScreenY()- delta[1]);
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
        StackPane btnOk = new StackPane(okRect, okText);

        btnOk.setCursor(Cursor.HAND);
        btnOk.setOnMouseClicked(e -> popup.close());
        continut.getChildren().addAll(tTitlu, tMsg, btnOk);
        popRoot.getChildren().addAll(popRect, continut);
        Scene popScene =new Scene(popRoot, 380, 220);

        popScene.setFill(javafx.scene.paint.Color.TRANSPARENT);
        popup.setScene(popScene);
        popup.showAndWait();
    }

    public ModStudenti(App app) {
        StackPane root=new StackPane();

        root.setStyle("-fx-background-color: #874035;");
        Rectangle rect = new Rectangle();
        rect.setArcWidth(30);

        rect.setArcHeight(30);
        rect.setFill(new RadialGradient(1, 0, 0.5, 0.5, 1.5, true, CycleMethod.NO_CYCLE, new Stop(0, javafx.scene.paint.Color.web("#e7dee2")), new Stop(1, javafx.scene.paint.Color.web("#ff8144"))));
        rect.widthProperty().bind(root.widthProperty().multiply(0.85));

        rect.heightProperty().bind(root.heightProperty().multiply(0.85));

        Text title= new Text("MODIFICA STUDENT");
        title.styleProperty().bind(root.widthProperty().divide(14).asString("-fx-font-size: %.0fpx; -fx-font-weight: bold; -fx-font-family: 'League Spartan';"));

        HBox headerRow=new HBox(0, creeazaHeaderCelula("ID", 0.10, root), creeazaHeaderCelula("Nume", 0.18, root), creeazaHeaderCelula("Prenume", 0.18, root), creeazaHeaderCelula("Grupa", 0.13, root), creeazaHeaderCelula("Poza", 0.20, root));
        headerRow.setAlignment(Pos.CENTER);

        ComboBox<String> cbId =new ComboBox<>();

        cbId.prefWidthProperty().bind(root.widthProperty().multiply(0.10));
        cbId.prefHeightProperty().bind(root.widthProperty().multiply(0.04));
        cbId.styleProperty().bind(root.widthProperty().divide(70).asString("-fx-background-color: #c97a60; -fx-background-radius: 5; -fx-font-size: %.0fpx; -fx-font-weight: bold;"));

        Runnable refreshIds = () -> {
            cbId.getItems().clear();
            try {
                for (Studenti s : StudentiCRUD.getAll()) {
                    cbId.getItems().add(s.getId()+ " - " +s.getNume() + " " + s.getPrenume());
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        };
        refreshIds.run();

        Text tNume =new Text("-");
        Text tPrenume = new Text("-");

        Text tGrupa= new Text("-");
        ImageView tabelPozaView =new ImageView();
        tabelPozaView.setPreserveRatio(true);

        tabelPozaView.fitWidthProperty().bind(root.widthProperty().multiply(0.16));
        tabelPozaView.fitHeightProperty().bind(root.widthProperty().multiply(0.038));

        Rectangle clip=new Rectangle();

        clip.widthProperty().bind(root.widthProperty().multiply(0.16));
        clip.heightProperty().bind(root.widthProperty().multiply(0.038));
        clip.setArcWidth(10);
        clip.setArcHeight(10);

        tabelPozaView.setClip(clip);

        StackPane celulaPoza = new StackPane();
        Rectangle rPoza =new Rectangle();
        rPoza.widthProperty().bind(root.widthProperty().multiply(0.20));

        rPoza.heightProperty().bind(root.widthProperty().multiply(0.04));
        rPoza.setArcWidth(10);
        rPoza.setArcHeight(10);
        rPoza.setFill(javafx.scene.paint.Color.WHITE);

        rPoza.setStroke(javafx.scene.paint.Color.web("#a04e41"));
        celulaPoza.getChildren().addAll(rPoza, tabelPozaView);

        HBox dataRow =new HBox(0, new StackPane(cbId), creeazaDataCelula(tNume, 0.18, root), creeazaDataCelula(tPrenume, 0.18, root), creeazaDataCelula(tGrupa, 0.13, root), celulaPoza);
        dataRow.setAlignment(Pos.CENTER);

        final Studenti[] studentSelectat= {null};
        cbId.setOnAction(e -> {
            try {
                String val =cbId.getValue();
                if (val ==null) return;
                int id = Integer.parseInt(val.split(" ")[0]);

                studentSelectat[0]= StudentiCRUD.getById(id);
                if (studentSelectat[0]!= null) {
                    tNume.setText(studentSelectat[0].getNume());
                    tPrenume.setText(studentSelectat[0].getPrenume());
                    tGrupa.setText(studentSelectat[0].getGrupa().getGrupa());

                    tabelPozaView.setImage(null);
                    String poza = studentSelectat[0].getPoza();
                    if (poza !=null && !poza.isEmpty()) {
                        try {
                            Image img = poza.startsWith("/") ? new Image(App.class.getResourceAsStream(poza)):new Image(new File(poza).toURI().toString());
                            tabelPozaView.setImage(img);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        CheckBox ckNume = new CheckBox();
        CheckBox ckPrenume =new CheckBox();

        CheckBox ckGrupa= new CheckBox();
        CheckBox ckPoza = new CheckBox();
        ckPoza.selectedProperty().addListener((obs, old, newVal) -> {
            if (newVal) {
                ckNume.setSelected(false);
                ckPrenume.setSelected(false);

                ckGrupa.setSelected(false);
            }
        });
        ckNume.selectedProperty().addListener((obs, old, newVal) -> {
            if (newVal) ckPoza.setSelected(false);
        });

        ckPrenume.selectedProperty().addListener((obs, old, newVal) -> {
            if (newVal) ckPoza.setSelected(false);
        });
        ckGrupa.selectedProperty().addListener((obs, old, newVal) -> {
            if (newVal) ckPoza.setSelected(false);
        });

        HBox checkRow= new HBox(50, creeazaCheckLabel("Nume", ckNume, root), creeazaCheckLabel("Prenume", ckPrenume, root), creeazaCheckLabel("Grupa", ckGrupa, root), creeazaCheckLabel("Poza", ckPoza, root));
        checkRow.spacingProperty().bind(root.widthProperty().multiply(0.06));

        checkRow.setAlignment(Pos.CENTER);

        UnaryOperator<TextFormatter.Change> filter = change -> change.getControlNewText().matches("[a-zA-Z\\s\\-]*") ? change: null;
        TextField fNume =new TextField();
        fNume.setTextFormatter(new TextFormatter<>(filter));

        TextField fPrenume = new TextField();
        fPrenume.setTextFormatter(new TextFormatter<>(filter));
        ComboBox<String> cbGrupaField = new ComboBox<>();
        String fieldStyle = "-fx-background-color: #c97a60; -fx-background-radius: 8; -fx-font-weight: bold;";

        fNume.styleProperty().bind(root.widthProperty().divide(70).asString(fieldStyle+ " -fx-font-size: %.0fpx;"));
        fPrenume.styleProperty().bind(root.widthProperty().divide(70).asString(fieldStyle+ " -fx-font-size: %.0fpx;"));
        cbGrupaField.styleProperty().bind(root.widthProperty().divide(70).asString(fieldStyle +" -fx-font-size: %.0fpx;"));

        fNume.prefWidthProperty().bind(root.widthProperty().multiply(0.25));
        fPrenume.prefWidthProperty().bind(root.widthProperty().multiply(0.25));
        cbGrupaField.prefWidthProperty().bind(root.widthProperty().multiply(0.25));

        try {
            for (Grupe g : GrupeCRUD.getAll()) cbGrupaField.getItems().add(g.getGrupa());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        final String[] pozaPath = {null};
        ImageView photoView = new ImageView();
        photoView.setOpacity(0);

        photoView.setPreserveRatio(true);
        photoView.fitWidthProperty().bind(root.widthProperty().multiply(0.15));
        photoView.fitHeightProperty().bind(root.heightProperty().multiply(0.25));

        Rectangle addRect = new Rectangle();
        addRect.setArcWidth(10);
        addRect.setArcHeight(10);

        addRect.setFill(javafx.scene.paint.Color.web("#a04e41"));
        addRect.widthProperty().bind(root.widthProperty().multiply(0.18));
        addRect.heightProperty().bind(root.heightProperty().multiply(0.15));

        Text plusText = new Text("+");
        plusText.setTranslateY(-10);

        plusText.setFill(javafx.scene.paint.Color.WHITE);
        plusText.styleProperty().bind(root.widthProperty().divide(10).asString("-fx-font-size: %.0fpx; -fx-font-weight: bold;"));

        StackPane photoBox = new StackPane(addRect, photoView, plusText);
        photoBox.setCursor(Cursor.HAND);

        photoBox.setOnMouseClicked(e -> {
            FileChooser fc =new FileChooser();
            fc.setTitle("Selectează Poza Studentului");
            fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Toate Imaginile", "*.jpg", "*.jpeg", "*.png", "*.bmp", "*.gif"));

            File file = fc.showOpenDialog((Stage) root.getScene().getWindow());
            if (file != null) {
                pozaPath[0]= file.getAbsolutePath();
                photoView.setImage(new Image(file.toURI().toString()));
                photoView.setOpacity(1);

                plusText.setText("");
            }
        });

        Rectangle checkRect = new Rectangle(40, 40);
        checkRect.setArcWidth(8);
        checkRect.setArcHeight(8);
        checkRect.setFill(javafx.scene.paint.Color.web("#a04e41"));

        Text checkText =new Text("✓");
        checkText.setFill(javafx.scene.paint.Color.WHITE);
        StackPane btnCheck = new StackPane(checkRect, checkText);
        btnCheck.setCursor(Cursor.HAND);

        Rectangle xRect= new Rectangle(40, 40);
        xRect.setArcWidth(8);
        xRect.setArcHeight(8);
        xRect.setFill(javafx.scene.paint.Color.web("#a04e41"));

        Text xText= new Text("✕");
        xText.setFill(javafx.scene.paint.Color.WHITE);
        StackPane btnX = new StackPane(xRect, xText);
        btnX.setCursor(Cursor.HAND);

        btnX.setOnMouseClicked(e -> {
            var stream = App.class.getResourceAsStream("/Imagine/pfp.png");
            if (stream!= null) {
                pozaPath[0] ="/Imagine/pfp.png";
                photoView.setImage(new Image(stream));
                photoView.setOpacity(1);
                plusText.setText("");
            }
        });

        HBox photoBtns =new HBox(10, btnCheck, btnX);
        photoBtns.setAlignment(Pos.CENTER);
        VBox photoColumn = new VBox(-15, photoBox, photoBtns);

        photoColumn.setAlignment(Pos.CENTER);
        VBox editFieldsContainer = new VBox(10);
        editFieldsContainer.setAlignment(Pos.CENTER);

        HBox randNume = creeazaRandEdit("Nume:", fNume, root);
        randNume.visibleProperty().bind(ckNume.selectedProperty());
        randNume.managedProperty().bind(ckNume.selectedProperty());

        HBox randPrenume = creeazaRandEdit("Prenume:", fPrenume, root);
        randPrenume.visibleProperty().bind(ckPrenume.selectedProperty());
        randPrenume.managedProperty().bind(ckPrenume.selectedProperty());

        HBox randGrupa = creeazaRandEdit("Grupa:", cbGrupaField, root);
        randGrupa.visibleProperty().bind(ckGrupa.selectedProperty());
        randGrupa.managedProperty().bind(ckGrupa.selectedProperty());

        photoColumn.visibleProperty().bind(ckPoza.selectedProperty());
        photoColumn.managedProperty().bind(ckPoza.selectedProperty());
        editFieldsContainer.getChildren().addAll(randNume, randPrenume, randGrupa, photoColumn);

        Rectangle btnRect = new Rectangle();
        btnRect.setArcWidth(15);
        btnRect.setArcHeight(15);
        btnRect.setFill(javafx.scene.paint.Color.web("#a04e41"));

        btnRect.widthProperty().bind(root.widthProperty().multiply(0.20));
        btnRect.heightProperty().bind(root.heightProperty().multiply(0.08));
        Text btnText= new Text("Confirma");
        btnText.setFill(javafx.scene.paint.Color.WHITE);

        StackPane btnConfirm = new StackPane(btnRect, btnText);
        btnConfirm.styleProperty().bind(root.widthProperty().divide(35).asString("-fx-font-size: %.0fpx; -fx-font-weight: bold;"));
        btnConfirm.setCursor(Cursor.HAND);

        btnConfirm.setOnMouseClicked(e -> {
            Stage owner = (Stage) root.getScene().getWindow();
            if (studentSelectat[0] ==null) {
                aratPopUp("EROARE", "Selecteaza ID!", "#ff8144", owner);
                return;
            }
            if (ckNume.isSelected() && fNume.getText().trim().isEmpty()) {
                aratPopUp("EROARE", "Numele nu poate fi gol!", "#ff8144", owner);
                return;
            }
            if (ckPrenume.isSelected() && fPrenume.getText().trim().isEmpty()) {
                aratPopUp("EROARE", "Prenumele nu poate fi gol!", "#ff8144", owner);
                return;
            }
            if (ckPoza.isSelected() && pozaPath[0] == null) {
                aratPopUp("EROARE", "Selecteaza o poza!", "#ff8144", owner);
                return;
            }

            try {
                if (ckNume.isSelected()) {
                    studentSelectat[0].setNume(fNume.getText());
                    tNume.setText(fNume.getText());
                }
                if (ckPrenume.isSelected()) {
                    studentSelectat[0].setPrenume(fPrenume.getText());
                    tPrenume.setText(fPrenume.getText());
                }
                if (ckGrupa.isSelected() && cbGrupaField.getValue()!= null) {
                    for (Grupe g : GrupeCRUD.getAll()) {
                        if (g.getGrupa().equals(cbGrupaField.getValue())) {
                            studentSelectat[0].setGrupa(g);
                            tGrupa.setText(g.getGrupa());
                            break;
                        }
                    }
                }
                if (ckPoza.isSelected()) {
                    studentSelectat[0].setPoza(pozaPath[0]);
                    Image img = pozaPath[0].startsWith("/") ? new Image(App.class.getResourceAsStream(pozaPath[0])): new Image(new File(pozaPath[0]).toURI().toString());
                    tabelPozaView.setImage(img);
                }
                StudentiCRUD.actualizeaza(studentSelectat[0]);
                refreshIds.run();
                aratPopUp("SUCCES", "Modificat!", "#ff8144", owner);
            } catch (Exception ex) {
                aratPopUp("EROARE", ex.getMessage(), "#ff8144", owner);
            }
        });

        ImageView backView =new ImageView(new Image(App.class.getResourceAsStream("/Imagine/arrow.png")));
        backView.setPreserveRatio(true);
        backView.fitWidthProperty().bind(root.widthProperty().multiply(0.08));

        Button btnBack = new Button();
        btnBack.setGraphic(backView);
        btnBack.setStyle("-fx-background-color: transparent;");

        btnBack.setCursor(Cursor.HAND);
        btnBack.setOnAction(e -> app.arataAlegStud());

        VBox layout = new VBox(0);
        layout.setAlignment(Pos.CENTER);
        VBox tableGroup = new VBox(0, headerRow, dataRow);

        tableGroup.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(creeazaSpatiu(0), title, creeazaSpatiu(20), tableGroup, creeazaSpatiu(10), checkRow, creeazaSpatiu(10), editFieldsContainer, creeazaSpatiu(10), btnConfirm);
        root.getChildren().addAll(rect, layout, btnBack);

        StackPane.setAlignment(btnBack, Pos.BOTTOM_LEFT);
        btnBack.translateXProperty().bind(root.widthProperty().multiply(0.08));
        btnBack.translateYProperty().bind(root.heightProperty().multiply(-0.07));

        scene=new Scene(root, 900, 600);
    }

    private StackPane creeazaHeaderCelula(String text, double wMult, StackPane root) {
        Rectangle r=new Rectangle();
        r.widthProperty().bind(root.widthProperty().multiply(wMult));
        r.setHeight(35);

        r.setArcWidth(10);
        r.setArcHeight(10);
        r.setFill(javafx.scene.paint.Color.web("#a04e41"));

        Text t = new Text(text);
        t.setFill(javafx.scene.paint.Color.WHITE);
        t.styleProperty().bind(root.widthProperty().divide(65).asString("-fx-font-weight: bold; -fx-font-size: %.0fpx;"));

        return new StackPane(r, t);
    }

    private StackPane creeazaDataCelula(Text t, double wMult, StackPane root) {
        Rectangle r = new Rectangle();
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
        HBox box = new HBox(5);
        box.setAlignment(Pos.CENTER);
        StackPane sLabel = new StackPane();
        Rectangle r = new Rectangle();
        r.widthProperty().bind(root.widthProperty().multiply(0.12));
        r.heightProperty().bind(root.widthProperty().multiply(0.035));
        r.setArcWidth(8);
        r.setArcHeight(8);
        r.setFill(javafx.scene.paint.Color.web("#2b2b2b"));
        Text t=new Text(label);
        t.setFill(javafx.scene.paint.Color.WHITE);

        t.styleProperty().bind(root.widthProperty().divide(80).asString("-fx-font-weight: bold; -fx-font-size: %.0fpx;"));
        cb.scaleXProperty().bind(root.widthProperty().divide(450));
        cb.scaleYProperty().bind(root.widthProperty().divide(450));

        sLabel.getChildren().addAll(r, t);
        box.getChildren().addAll(sLabel, cb);
        return box;
    }

    private HBox creeazaRandEdit(String eticheta, javafx.scene.Node field, StackPane root) {
        HBox rand=new HBox(15);
        rand.setAlignment(Pos.CENTER);
        StackPane sLabel =new StackPane();

        Rectangle bgL=new Rectangle();
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
        Region spacer =new Region();
        spacer.setPrefHeight(inaltime);
        return spacer;
    }

    public Scene getScene() {
        return scene;
    }
}