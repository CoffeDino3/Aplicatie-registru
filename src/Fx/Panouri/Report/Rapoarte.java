package Fx.Panouri.Report;
import BazaDeDate.CRUD.NoteCRUD;
import BazaDeDate.CRUD.StudentiCRUD;
import Functionalitate.Simple.EvidStud;
import Functionalitate.Simple.Note;
import Functionalitate.Simple.Studenti;
import Fx.App;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class Rapoarte {
    private Scene scene;
    private int raportSelectat= -1;
    private int formatSelectat =-1;
    private final StackPane[] btnRapoarte= new StackPane[3];
    private final Rectangle[] rectRapoarte =new Rectangle[3];
    private final StackPane[] btnFormate= new StackPane[2];
    private final Rectangle[] rectFormate =new Rectangle[2];
    private static final String COL_NORMAL= "#a04e41";
    private static final String COL_SEL ="#3d0b04";
    private static final String COL_HOVER= "#7a1f1f";
    private void aratPopUp(String titlu, String mesaj, String culoareHex, Stage owner, Runnable onDeschide) {
        Stage popup= new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.initOwner(owner);
        popup.initStyle(StageStyle.TRANSPARENT);
        StackPane popRoot =new StackPane();
        popRoot.setStyle("-fx-background-color: transparent;");
        Rectangle popRect= new Rectangle(400, onDeschide!= null ? 260 : 220);
        popRect.setArcWidth(24);
        popRect.setArcHeight(24);
        popRect.setFill(new RadialGradient(1, 0, 0.5, 0.5, 1.5, true, CycleMethod.NO_CYCLE, new Stop(0, javafx.scene.paint.Color.web("#e7dee2")), new Stop(1, javafx.scene.paint.Color.web(culoareHex))));
        final double[] delta =new double[2];
        popRoot.setOnMousePressed(e-> {
            delta[0]= e.getSceneX();
            delta[1] =e.getSceneY();
        });
        popRoot.setOnMouseDragged(e-> {
            popup.setX(e.getScreenX()- delta[0]);
            popup.setY(e.getScreenY() -delta[1]);
        });
        VBox continut =new VBox(15);
        continut.setAlignment(Pos.CENTER);
        Text tTitlu= new Text(titlu);
        tTitlu.setFill(javafx.scene.paint.Color.web("#7a1f1f"));

        tTitlu.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-font-family: 'League Spartan';");
        Text tMsg= new Text(mesaj);
        tMsg.setFill(javafx.scene.paint.Color.web("#000000"));
        tMsg.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        tMsg.setWrappingWidth(330);
        tMsg.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        continut.getChildren().addAll(tTitlu, tMsg);

        if (onDeschide !=null) {
            Rectangle deschideRect= new Rectangle(150, 40);
            deschideRect.setArcWidth(12);
            deschideRect.setArcHeight(12);
            deschideRect.setFill(javafx.scene.paint.Color.web(COL_NORMAL));
            Text deschideText =new Text("Deschide");
            deschideText.setFill(javafx.scene.paint.Color.web("#ffffff"));

            deschideText.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");
            StackPane btnDeschide= new StackPane(deschideRect, deschideText);
            btnDeschide.setCursor(Cursor.HAND);
            btnDeschide.setOnMouseEntered(e ->deschideRect.setFill(javafx.scene.paint.Color.web(COL_HOVER)));
            btnDeschide.setOnMouseExited(e-> deschideRect.setFill(javafx.scene.paint.Color.web(COL_NORMAL)));
            btnDeschide.setOnMouseClicked(e-> {
                onDeschide.run();
                popup.close();
            });

            Rectangle okRect2= new Rectangle(120, 40);
            okRect2.setArcWidth(12);
            okRect2.setArcHeight(12);
            okRect2.setFill(javafx.scene.paint.Color.web(COL_HOVER));
            Text okText2 =new Text("Inchide");
            okText2.setFill(javafx.scene.paint.Color.web("#ffffff"));
            okText2.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");

            StackPane btnClose= new StackPane(okRect2, okText2);
            btnClose.setCursor(Cursor.HAND);
            btnClose.setOnMouseEntered(e ->okRect2.setFill(javafx.scene.paint.Color.web(COL_SEL)));
            btnClose.setOnMouseExited(e-> okRect2.setFill(javafx.scene.paint.Color.web(COL_HOVER)));
            btnClose.setOnMouseClicked(e ->popup.close());
            HBox btns= new HBox(15, btnDeschide, btnClose);
            btns.setAlignment(Pos.CENTER);
            continut.getChildren().add(btns);


        } else {
            Rectangle okRect =new Rectangle(120, 40);
            okRect.setArcWidth(12);
            okRect.setArcHeight(12);
            okRect.setFill(javafx.scene.paint.Color.web(COL_NORMAL));
            Text okText= new Text("OK");
            okText.setFill(javafx.scene.paint.Color.web("#ffffff"));
            okText.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");
            StackPane btnOk= new StackPane(okRect, okText);
            btnOk.setCursor(Cursor.HAND);
            btnOk.setOnMouseEntered(e ->okRect.setFill(javafx.scene.paint.Color.web(COL_HOVER)));
            btnOk.setOnMouseExited(e-> okRect.setFill(javafx.scene.paint.Color.web(COL_NORMAL)));
            btnOk.setOnMouseClicked(e ->popup.close());
            continut.getChildren().add(btnOk);
        }

        popRoot.getChildren().addAll(popRect, continut);
        Scene popScene= new Scene(popRoot, 400, onDeschide!= null ? 260 : 220);
        popScene.setFill(javafx.scene.paint.Color.TRANSPARENT);
        popup.setScene(popScene);
        popup.showAndWait();
    }
    private void selecteazaRaport(int idx) {
        raportSelectat= idx;
        for (int i= 0; i< rectRapoarte.length; i++) {
            rectRapoarte[i].setFill(javafx.scene.paint.Color.web(i ==idx ? COL_SEL : COL_NORMAL));
        }
    }

    private void selecteazaFormat(int idx) {
        formatSelectat =idx;
        for (int i =0; i <rectFormate.length; i++) {
            rectFormate[i].setFill(javafx.scene.paint.Color.web(i== idx ? COL_SEL : COL_NORMAL));
        }
    }
    private StackPane creeazaBtnSelectie(String label, Rectangle[] rectArr, int idx, StackPane root, double wMult, double hMult) {
        Rectangle r =new Rectangle();
        r.setArcWidth(15);
        r.setArcHeight(15);
        r.setFill(javafx.scene.paint.Color.web(COL_NORMAL));

        r.widthProperty().bind(root.widthProperty().multiply(wMult));
        r.heightProperty().bind(root.heightProperty().multiply(hMult));
        rectArr[idx]= r;
        Text t =new Text(label);
        t.setFill(javafx.scene.paint.Color.web("#ffffff"));
        t.styleProperty().bind(root.widthProperty().divide(38).asString("-fx-font-size: %.0fpx; -fx-font-weight: bold;"));

        StackPane sp= new StackPane(r, t);
        sp.setCursor(Cursor.HAND);
        sp.setOnMouseEntered(e ->{
            if (r.getFill().equals(javafx.scene.paint.Color.web(COL_NORMAL))) r.setFill(javafx.scene.paint.Color.web(COL_HOVER));
        });
        sp.setOnMouseExited(e-> {
            if (!r.getFill().equals(javafx.scene.paint.Color.web(COL_SEL))) r.setFill(javafx.scene.paint.Color.web(COL_NORMAL));
        });

        return sp;
    }
    public Rapoarte(App app) {
        StackPane root =new StackPane();
        root.setStyle("-fx-background-color: #874035;");
        Rectangle rect= new Rectangle();
        rect.setArcWidth(30);
        rect.setArcHeight(30);

        rect.setFill(new RadialGradient(1, 0, 0.5, 0.5, 1.5, true, CycleMethod.NO_CYCLE, new Stop(0, javafx.scene.paint.Color.web("#e7dee2")), new Stop(1, javafx.scene.paint.Color.web("#ff8144"))));
        rect.widthProperty().bind(root.widthProperty().multiply(0.85));
        rect.heightProperty().bind(root.heightProperty().multiply(0.85));
        Text title =new Text("RAPOARTE");
        title.styleProperty().bind(root.widthProperty().divide(11).asString("-fx-font-size: %.0fpx; -fx-font-weight: bold; -fx-font-family: 'League Spartan';"));

        Text tLabelRap= new Text("Selecteaza raportul:");
        tLabelRap.styleProperty().bind(root.widthProperty().divide(45).asString("-fx-font-size: %.0fpx; -fx-font-weight: bold;"));
        btnRapoarte[0] =creeazaBtnSelectie("Media peste 8", rectRapoarte, 0, root, 0.26, 0.09);
        btnRapoarte[1]= creeazaBtnSelectie("Media sub 5", rectRapoarte, 1, root, 0.26, 0.09);
        btnRapoarte[2] =creeazaBtnSelectie("Nume cu A / E", rectRapoarte, 2, root, 0.26, 0.09);

        btnRapoarte[0].setOnMouseClicked(e-> selecteazaRaport(0));
        btnRapoarte[1].setOnMouseClicked(e ->selecteazaRaport(1));
        btnRapoarte[2].setOnMouseClicked(e-> selecteazaRaport(2));
        HBox rapRow= new HBox(18, btnRapoarte[0], btnRapoarte[1], btnRapoarte[2]);
        rapRow.setAlignment(Pos.CENTER);
        Text tLabelFmt =new Text("Selecteaza formatul:");

        tLabelFmt.styleProperty().bind(root.widthProperty().divide(45).asString("-fx-font-size: %.0fpx; -fx-font-weight: bold;"));
        btnFormate[0]= creeazaBtnSelectie("TXT", rectFormate, 0, root, 0.16, 0.09);
        btnFormate[1] =creeazaBtnSelectie("CSV", rectFormate, 1, root, 0.16, 0.09);
        btnFormate[0].setOnMouseClicked(e-> selecteazaFormat(0));
        btnFormate[1].setOnMouseClicked(e ->selecteazaFormat(1));

        HBox fmtRow= new HBox(18, btnFormate[0], btnFormate[1]);
        fmtRow.setAlignment(Pos.CENTER);
        Rectangle btnRect =new Rectangle();
        btnRect.setArcWidth(15);
        btnRect.setArcHeight(15);
        btnRect.setFill(javafx.scene.paint.Color.web(COL_NORMAL));
        btnRect.widthProperty().bind(root.widthProperty().multiply(0.22));

        btnRect.heightProperty().bind(root.heightProperty().multiply(0.09));
        Text btnText= new Text("Exporta");
        btnText.setFill(javafx.scene.paint.Color.web("#ffffff"));
        StackPane btnExport =new StackPane(btnRect, btnText);
        btnExport.styleProperty().bind(root.widthProperty().divide(35).asString("-fx-font-size: %.0fpx; -fx-font-weight: bold;"));
        btnExport.setCursor(Cursor.HAND);

        btnExport.setOnMouseEntered(e ->btnRect.setFill(javafx.scene.paint.Color.web(COL_HOVER)));
        btnExport.setOnMouseExited(e-> btnRect.setFill(javafx.scene.paint.Color.web(COL_NORMAL)));
        btnExport.setOnMouseClicked(e-> {
            Stage owner =(Stage) root.getScene().getWindow();
            if (raportSelectat <0) {
                aratPopUp("EROARE", "Selecteaza un raport!", "#ff8144", owner, null);
                return;
            }

            if (formatSelectat <0) {
                aratPopUp("EROARE", "Selecteaza un format!", "#ff8144", owner, null);
                return;
            }
            try {
                ArrayList<Studenti> toti =StudentiCRUD.getAll();
                EvidStud.studenti.clear();

                for (Studenti s :toti) {
                    try {
                        ArrayList<Note> note= NoteCRUD.getByStudent(s.getId());
                        for (Note n :note) s.adaugaNota(n);
                    } catch (Exception ignored) {}
                    EvidStud.studenti.add(s);
                }

                EvidStud evidStud= new EvidStud();
                boolean csv =(formatSelectat == 1);
                String numeFisier;
                if (raportSelectat ==0) numeFisier= csv ? "raport1.csv" : "raport1.txt";
                else if (raportSelectat== 1) numeFisier =csv ? "raport2.csv" : "raport2.txt";
                else numeFisier =csv ? "raport3.csv" : "raport3.txt";

                if (raportSelectat== 0) {
                    if (csv) evidStud.toCSV1();
                    else evidStud.toRaport1();
                } else if (raportSelectat ==1) {
                    if (csv) evidStud.toCSV2();
                    else evidStud.toRaport2();
                } else {
                    if (csv) evidStud.toCSV3();
                    else evidStud.toRaport3();
                }
                File fisier =new File(numeFisier);
                aratPopUp("SUCCES", "Raportul a fost exportat!\n" +fisier.getAbsolutePath(), "#ff8144", owner, ()-> {
                    try {
                        Desktop.getDesktop().open(fisier);
                    } catch (IOException ex) {
                        aratPopUp("EROARE", "Nu s-a putut deschide fisierul!\n" +ex.getMessage(), "#ff8144", owner, null);
                    }
                });
            } catch (SQLException ex) {

                aratPopUp("EROARE", "Eroare BD: "+ ex.getMessage(), "#ff8144", owner, null);
            }
        });
        ImageView btnView =new ImageView(new Image(App.class.getResourceAsStream("/Imagine/arrow.png")));
        btnView.setPreserveRatio(true);
        btnView.fitWidthProperty().bind(root.widthProperty().multiply(0.08));

        Button btnBack =new Button();
        btnBack.setGraphic(btnView);
        btnBack.setCursor(Cursor.HAND);
        btnBack.setOnMouseEntered(e ->btnView.setOpacity(0.9));
        btnBack.setOnMouseExited(e-> btnView.setOpacity(1.0));
        btnBack.setStyle("-fx-background-color: transparent; -fx-padding: 0;");
        btnBack.setOnAction(e-> app.arataOptiunile());
        VBox layout= new VBox(22, title, tLabelRap, rapRow, tLabelFmt, fmtRow, btnExport);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20, 0, 0, 0));
        root.getChildren().addAll(rect, layout, btnBack);
        StackPane.setAlignment(btnBack, Pos.BOTTOM_LEFT);
        btnBack.translateXProperty().bind(root.widthProperty().multiply(0.08));

        btnBack.translateYProperty().bind(root.heightProperty().multiply(-0.07));
        scene =new Scene(root, 900, 600);
    }
    public Scene getScene() {
        return scene;
    }
}