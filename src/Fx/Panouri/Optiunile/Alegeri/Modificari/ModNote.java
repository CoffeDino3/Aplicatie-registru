package Fx.Panouri.Optiunile.Alegeri.Modificari;

import BazaDeDate.CRUD.NoteCRUD;
import Fx.App;
import Functionalitate.Enumerari.Discipline;
import Functionalitate.Simple.Note;
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

public class ModNote {
    private Scene scene;
    private void aratPopUp(String titlu, String mesaj, String culoareHex, Stage owner) {
        Stage popup=new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.initOwner(owner);
        popup.initStyle(StageStyle.TRANSPARENT);
        StackPane popRoot=new StackPane();
        popRoot.setStyle("-fx-background-color: transparent;");
        Rectangle popRect=new Rectangle(380,220);
        popRect.setArcWidth(24);
        popRect.setArcHeight(24);
        popRect.setFill(new RadialGradient(1,0,0.5,0.5,1.5,true,CycleMethod.NO_CYCLE,
                new Stop(0,javafx.scene.paint.Color.web("#e7dee2")),
                new Stop(1,javafx.scene.paint.Color.web(culoareHex))));

        // DRAG LOGIC ADDED HERE
        final double[] delta = new double[2];
        popRoot.setOnMousePressed(e -> {
            delta[0] = e.getSceneX();
            delta[1] = e.getSceneY();
        });
        popRoot.setOnMouseDragged(e -> {
            popup.setX(e.getScreenX() - delta[0]);
            popup.setY(e.getScreenY() - delta[1]);
        });

        VBox continut=new VBox(15);
        continut.setAlignment(Pos.CENTER);
        Text tTitlu=new Text(titlu);
        tTitlu.setFill(javafx.scene.paint.Color.web("#7a1f1f"));
        tTitlu.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-font-family: 'League Spartan';");
        Text tMsg=new Text(mesaj);
        tMsg.setFill(javafx.scene.paint.Color.web("#000000"));
        tMsg.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        tMsg.setWrappingWidth(300);
        tMsg.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        Rectangle okRect=new Rectangle(120,40);
        okRect.setArcWidth(12);
        okRect.setArcHeight(12);
        okRect.setFill(javafx.scene.paint.Color.web("#a04e41"));
        Text okText=new Text("OK");
        okText.setFill(javafx.scene.paint.Color.web("#ffffff"));
        okText.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");
        StackPane btnOk=new StackPane(okRect,okText);
        btnOk.setCursor(Cursor.HAND);
        btnOk.setOnMouseClicked(e -> {
            popup.close();
        });
        continut.getChildren().addAll(tTitlu,tMsg,btnOk);
        popRoot.getChildren().addAll(popRect,continut);
        Scene popScene=new Scene(popRoot,380,220);
        popScene.setFill(javafx.scene.paint.Color.TRANSPARENT);
        popup.setScene(popScene);
        popup.showAndWait();
    }



    public ModNote(App app) {
        StackPane root=new StackPane();
        root.setStyle("-fx-background-color: #874035;");
        Rectangle rect=new Rectangle();
        rect.setArcWidth(30);
        rect.setArcHeight(30);
        rect.setFill(new RadialGradient(1,0,0.5,0.5,1.5,true,CycleMethod.NO_CYCLE,
                new Stop(0,javafx.scene.paint.Color.web("#e7dee2")),
                new Stop(1,javafx.scene.paint.Color.web("#ff8144"))));
        rect.widthProperty().bind(root.widthProperty().multiply(0.85));
        rect.heightProperty().bind(root.heightProperty().multiply(0.85));
        Text title=new Text("MODIFICA NOTA");
        title.styleProperty().bind(root.widthProperty().divide(14).asString(
                "-fx-font-size: %.0fpx; -fx-font-weight: bold; -fx-font-family: 'League Spartan';"));
        HBox headerRow=new HBox(0,
                creeazaHeaderCelula("ID",0.10,root),
                creeazaHeaderCelula("Student",0.25,root),
                creeazaHeaderCelula("Nota",0.13,root),
                creeazaHeaderCelula("Disciplina",0.31,root)
        );
        headerRow.setAlignment(Pos.CENTER);
        ComboBox<String> cbId=new ComboBox<>();
        cbId.prefWidthProperty().bind(root.widthProperty().multiply(0.10));
        cbId.prefHeightProperty().bind(root.widthProperty().multiply(0.04));
        cbId.styleProperty().bind(root.widthProperty().divide(70).asString(
                "-fx-background-color: #c97a60; -fx-background-radius: 5;" +
                        " -fx-font-size: %.0fpx; -fx-font-weight: bold;"));



        Runnable refreshIds=() -> {
            cbId.getItems().clear();
            try {
                for (Object[] row : NoteCRUD.getAllFull()) {
                    cbId.getItems().add(String.valueOf(row[0]));
                }
            } catch (SQLException ex) {
            }
        };
        refreshIds.run();
        Text tStudent=new Text("-");
        Text tNota=new Text("-");
        Text tDisc=new Text("-");
        HBox dataRow=new HBox(0,new StackPane(cbId),creeazaDataCelula(tStudent,0.25,root),
                creeazaDataCelula(tNota,0.13,root),creeazaDataCelula(tDisc,0.31,root));
        dataRow.setAlignment(Pos.CENTER);
        final int[] noteIdSelectat={-1};
        final Note[] notaSelectata={null};
        cbId.setOnAction(e -> {
            try {
                if (cbId.getValue()==null) {
                    return;
                }
                int id=Integer.parseInt(cbId.getValue());
                for (Object[] row : NoteCRUD.getAllFull()) {
                    if ((int)row[0]==id) {
                        noteIdSelectat[0]=id;
                        notaSelectata[0]=NoteCRUD.getById(id);
                        tStudent.setText(row[4]+" "+row[5]);
                        tNota.setText(String.valueOf(row[1]));
                        tDisc.setText(String.valueOf(row[2]));
                        break;
                    }
                }
            } catch (Exception ex) {
            }
        });
        CheckBox ckNota=new CheckBox();
        CheckBox ckDisc=new CheckBox();
        HBox checkRow=new HBox(50,creeazaCheckLabel("Nota",ckNota,root),
                creeazaCheckLabel("Disciplina",ckDisc,root));
        checkRow.spacingProperty().bind(root.widthProperty().multiply(0.06));
        checkRow.setAlignment(Pos.CENTER);
        String fieldStyle="-fx-background-color: #c97a60; -fx-background-radius: 8; -fx-font-weight: bold;";




        ComboBox<Integer> cbNotaField=new ComboBox<>();
        for (int i=1; i<=10; i++) {
            cbNotaField.getItems().add(i);
        }
        cbNotaField.styleProperty().bind(root.widthProperty().divide(70).asString(fieldStyle+" -fx-font-size: %.0fpx;"));
        cbNotaField.prefWidthProperty().bind(root.widthProperty().multiply(0.25));
        ComboBox<String> cbDiscField=new ComboBox<>();
        for (Discipline d : Discipline.values()) {
            cbDiscField.getItems().add(d.getDenumire());
        }
        cbDiscField.styleProperty().bind(root.widthProperty().divide(70).asString(fieldStyle+" -fx-font-size: %.0fpx;"));
        cbDiscField.prefWidthProperty().bind(root.widthProperty().multiply(0.25));
        VBox editFieldsContainer=new VBox(10);
        editFieldsContainer.setAlignment(Pos.CENTER);
        HBox randNota=creeazaRandEdit("Nota:",cbNotaField,root);
        randNota.visibleProperty().bind(ckNota.selectedProperty());
        randNota.managedProperty().bind(ckNota.selectedProperty());
        HBox randDisc=creeazaRandEdit("Disciplina:",cbDiscField,root);
        randDisc.visibleProperty().bind(ckDisc.selectedProperty());
        randDisc.managedProperty().bind(ckDisc.selectedProperty());


        editFieldsContainer.getChildren().addAll(randNota,randDisc);
        Rectangle btnRect=new Rectangle();
        btnRect.setArcWidth(15);
        btnRect.setArcHeight(15);
        btnRect.setFill(javafx.scene.paint.Color.web("#a04e41"));
        btnRect.widthProperty().bind(root.widthProperty().multiply(0.20));
        btnRect.heightProperty().bind(root.heightProperty().multiply(0.08));
        Text btnText=new Text("Confirma");
        btnText.setFill(javafx.scene.paint.Color.WHITE);
        StackPane btnConfirm=new StackPane(btnRect,btnText);
        btnConfirm.styleProperty().bind(root.widthProperty().divide(35).asString("-fx-font-size: %.0fpx; -fx-font-weight: bold;"));
        btnConfirm.setCursor(Cursor.HAND);
        btnConfirm.setOnMouseClicked(e -> {
            Stage owner=(Stage)root.getScene().getWindow();
            if (noteIdSelectat[0]==-1) {
                aratPopUp("EROARE","Selecteaza ID!","#ff8144",owner);
                return;
            }
            if (!ckNota.isSelected()&&!ckDisc.isSelected()) {
                aratPopUp("EROARE","Selecteaza ce vrei sa modifici!","#ff8144",owner);
                return;
            }
            try {
                if (ckNota.isSelected()&&cbNotaField.getValue()!=null) {
                    notaSelectata[0].setNota(cbNotaField.getValue());
                }
                if (ckDisc.isSelected()&&cbDiscField.getValue()!=null) {
                    for (Discipline d : Discipline.values()) {
                        if (d.getDenumire().equals(cbDiscField.getValue())) {
                            notaSelectata[0].setDisciplina(d);
                            break;
                        }
                    }
                }
                NoteCRUD.actualizeaza(noteIdSelectat[0],notaSelectata[0]);
                refreshIds.run();
                aratPopUp("SUCCES","Modificat!","#ff8144",owner);
            } catch (Exception ex) {
                aratPopUp("EROARE",ex.getMessage(),"#ff8144",owner);
            }
        });

        ImageView btnView=new ImageView(new Image(App.class.getResourceAsStream("/Imagine/arrow.png")));
        btnView.setPreserveRatio(true);
        btnView.fitWidthProperty().bind(root.widthProperty().multiply(0.08));
        Button btnBack=new Button();
        btnBack.setGraphic(btnView);
        btnBack.setStyle("-fx-background-color: transparent;");
        btnBack.setCursor(Cursor.HAND);
        btnBack.setOnAction(e -> {
            app.arataAlegNote();
        });
        VBox layout=new VBox(0);
        layout.setAlignment(Pos.CENTER);
        VBox tableGroup=new VBox(0,headerRow,dataRow);
        tableGroup.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(creeazaSpatiu(0),title,creeazaSpatiu(20),
                tableGroup,creeazaSpatiu(10),
                checkRow,creeazaSpatiu(10),editFieldsContainer,creeazaSpatiu(10),btnConfirm);
        root.getChildren().addAll(rect,layout,btnBack);
        StackPane.setAlignment(btnBack,Pos.BOTTOM_LEFT);

        btnBack.translateXProperty().bind(root.widthProperty().multiply(0.08));
        btnBack.translateYProperty().bind(root.heightProperty().multiply(-0.07));
        scene=new Scene(root,900,600);
    }

    private StackPane creeazaHeaderCelula(String text,double wMult,StackPane root) {
        Rectangle r=new Rectangle();
        r.widthProperty().bind(root.widthProperty().multiply(wMult));
        r.setHeight(35);
        r.setArcWidth(10);
        r.setArcHeight(10);

        r.setFill(javafx.scene.paint.Color.web("#a04e41"));
        Text t=new Text(text);
        t.setFill(javafx.scene.paint.Color.WHITE);
        t.styleProperty().bind(root.widthProperty().divide(65).asString("-fx-font-weight: bold; -fx-font-size: %.0fpx;"));
        return new StackPane(r,t);
    }

    private StackPane creeazaDataCelula(Text t,double wMult,StackPane root) {
        Rectangle r=new Rectangle();
        r.widthProperty().bind(root.widthProperty().multiply(wMult));
        r.heightProperty().bind(root.widthProperty().multiply(0.04));
        r.setArcWidth(10);
        r.setArcHeight(10);
        r.setFill(javafx.scene.paint.Color.WHITE);
        r.setStroke(javafx.scene.paint.Color.web("#a04e41"));
        t.styleProperty().bind(root.widthProperty().divide(70).asString("-fx-font-weight: bold; -fx-font-size: %.0fpx;"));
        return new StackPane(r,t);
    }

    private HBox creeazaCheckLabel(String label,CheckBox cb,StackPane root) {
        HBox box=new HBox(5);
        box.setAlignment(Pos.CENTER);
        StackPane sLabel=new StackPane();
        Rectangle r=new Rectangle();
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
        sLabel.getChildren().addAll(r,t);
        box.getChildren().addAll(sLabel,cb);
        return box;
    }

    private HBox creeazaRandEdit(String eticheta,javafx.scene.Node field,StackPane root) {
        HBox rand=new HBox(15);
        rand.setAlignment(Pos.CENTER);
        StackPane sLabel=new StackPane();
        Rectangle bgL=new Rectangle();
        bgL.setArcWidth(8);
        bgL.setArcHeight(8);
        bgL.setFill(javafx.scene.paint.Color.web("#a04e41"));
        bgL.widthProperty().bind(root.widthProperty().multiply(0.15));
        bgL.heightProperty().bind(root.heightProperty().multiply(0.04));
        Text txt=new Text(eticheta);
        txt.setFill(javafx.scene.paint.Color.WHITE);
        txt.styleProperty().bind(root.widthProperty().divide(60).asString("-fx-font-size: %.0fpx; -fx-font-weight: bold;"));
        sLabel.getChildren().addAll(bgL,txt);
        rand.getChildren().addAll(sLabel,field);
        return rand;
    }

    private Region creeazaSpatiu(double inaltime) {
        Region spacer=new Region();
        spacer.setPrefHeight(inaltime);
        return spacer;
    }

    public Scene getScene() {
        return scene;
    }
}