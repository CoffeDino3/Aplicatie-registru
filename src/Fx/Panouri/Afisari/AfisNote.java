package Fx.Panouri.Afisari;

import BazaDeDate.CRUD.NoteCRUD;
import Fx.App;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AfisNote {
    private Scene scene;
    private static final double[] COL_W = {0.07, 0.22, 0.10, 0.30, 0.15};
    private static final String[] COL_NAMES = {"ID", "Student", "Nota", "Disciplina", "Grupa"};
    private static final double TABLE_FRAC = 0.92;
    private static final double ROW_HEIGHT_FRAC = 0.08;
    private static final double HEADER_HEIGHT_FRAC = 0.07;
    private static final double STAT_WIDTH_FRAC = 0.16;
    private static final double STAT_HEIGHT_FRAC = 0.08;
    private int sortCol = -1;
    private boolean sortAsc = true;
    private final List<NotaRow> masterData =new ArrayList<>();
    private final List<NotaRow> displayData =new ArrayList<>();
    private final String[] searchFilters =new String[COL_NAMES.length];
    private GridPane rowGrid;
    private StackPane root;
    private Text[] headerArrows;
    private Text[] searchDots;

    private static class NotaRow {
        final int id, nota, idStudent;
        final String disciplina, nume, prenume, grupa;
        NotaRow(Object[] row) {
            this.id= (int)row[0];
            this.nota= (int)row[1];
            this.disciplina= (String)row[2];
            this.idStudent= (int)row[3];
            this.nume= (String)row[4];
            this.prenume= (String)row[5];
            this.grupa= row.length >6 &&row[6]!= null ? (String) row[6] : "-";
        }
        String student() { return nume +" " +prenume; }
        String cell(int col) {
            return switch (col) {
                case 0 -> String.valueOf(id);
                case 1 -> student();
                case 2 -> String.valueOf(nota);
                case 3 -> disciplina;
                case 4 -> grupa;
                default -> "";
            };
        }
    }

    public AfisNote(App app) {
        root =new StackPane();
        root.setStyle("-fx-background-color: #874035;");
        Rectangle rect =new Rectangle();
        rect.setArcWidth(30); rect.setArcHeight(30);
        rect.setFill(new RadialGradient(1 ,0,0.5, 0.5, 1.5,true, CycleMethod.NO_CYCLE,
                new Stop( 0, javafx.scene.paint.Color.web("#e7dee2")),
                new Stop(1,javafx.scene.paint.Color.web("#ff8144") )));
        rect.widthProperty().bind(root.widthProperty().multiply(0.96));
        rect.heightProperty().bind(root.heightProperty().multiply(0.96));

        try {
            for (Object[] row : NoteCRUD.getAllFull()){
                masterData.add(new NotaRow(row));
            }
        } catch (SQLException ignored) {}
        displayData.addAll(masterData);
        int totalNote = masterData.size();
        long noteBune = masterData.stream().filter(r -> r.nota >= 5).count();
        long noteSlabe = masterData.stream().filter(r -> r.nota < 5).count();
        double medieGenerala = masterData.stream().mapToInt(r -> r.nota).average().orElse(0);
        VBox mainBox =new VBox();
        mainBox.spacingProperty().bind(root.heightProperty().multiply(0.02));
        mainBox.setAlignment(Pos.TOP_CENTER);
        mainBox.maxWidthProperty().bind(root.widthProperty().multiply(TABLE_FRAC));

        Text title =new Text("NOTE");
        title.styleProperty().bind(root.widthProperty().divide(22).asString(
                "-fx-font-size: %.0fpx; -fx-font-weight: bold; -fx-font-family: 'League Spartan';"));

        HBox statsBar =new HBox();
        statsBar.spacingProperty().bind(root.widthProperty().multiply(0.02));
        statsBar.setAlignment(Pos.CENTER);
        statsBar.getChildren().addAll(
                statBox("Total note",String.valueOf(totalNote),"#7a1f1f"),
                statBox("Medie",String.format("%.1f", medieGenerala),"#7a1f1f"),
                statBox("Note ≥5",String.valueOf(noteBune),"#2d5a0e"),
                statBox("Note <5",String.valueOf(noteSlabe),"#7a1f1f")
        );

        HBox legend =new HBox( 14);
        legend.setAlignment(Pos.CENTER);
        legend.getChildren().addAll(legendItem("≥ 5  bine", "#3B6D11"), legendItem("< 5  slab", "#791F1F"));

        GridPane headerGrid =new GridPane();
        applyColConstraints(headerGrid);
        headerArrows = new Text[COL_NAMES.length];
        searchDots = new Text[COL_NAMES.length];
        for (int c = 0; c < COL_NAMES.length; c++) headerGrid.add(buildHeaderCell(c), c, 0);

        rowGrid =new GridPane();
        applyColConstraints(rowGrid);
        rebuildRows();

        ScrollPane scroll =new ScrollPane(rowGrid);
        scroll.setFitToWidth(true);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent;");
        scroll.prefHeightProperty().bind(root.heightProperty().multiply(0.50));

        VBox tableBlock =new VBox(0, headerGrid, scroll);
        tableBlock.setAlignment(Pos.CENTER);

        ImageView btnView =new ImageView(new Image(App.class.getResourceAsStream("/Imagine/arrow.png")));
        btnView.setPreserveRatio(true);
        btnView.fitWidthProperty().bind(root.widthProperty().multiply(0.06));
        Button btnBack =new Button();
        btnBack.setCursor(Cursor.HAND);
        btnBack.setGraphic(btnView);
        btnBack.setStyle("-fx-background-color: transparent; -fx-padding: 0;");
        btnBack.setOnAction(e ->app.arataAfisareTabele());

        root.heightProperty().addListener((obs,oldVal, newVal) ->{
            StackPane.setMargin(mainBox, new Insets(newVal.doubleValue() *0.06, 0, 0, 0));
        });
        root.widthProperty().addListener((obs,oldVal, newVal) ->{
            VBox.setMargin(tableBlock, new Insets(0, 0, 0, newVal.doubleValue() *0.15));
        });
        mainBox.getChildren().addAll(title, statsBar, legend, tableBlock, btnBack);
        root.getChildren().addAll(rect, mainBox);
        scene =new Scene(root, 950, 650);
    }

    private void applyColConstraints(GridPane gp) {
        gp.getColumnConstraints().clear();
        for (double w : COL_W) {
            ColumnConstraints cc =new ColumnConstraints();
            cc.setPercentWidth(w *100);
            cc.setHalignment(javafx.geometry.HPos.CENTER);
            cc.setFillWidth(true);
            gp.getColumnConstraints().add(cc);
        }
    }

    private StackPane buildHeaderCell(int c) {
        StackPane sp =new StackPane();
        Rectangle r =new Rectangle();
        r.heightProperty().bind(root.heightProperty().multiply(HEADER_HEIGHT_FRAC));
        r.widthProperty().bind(root.widthProperty().multiply(TABLE_FRAC *COL_W[c]));
        r.setFill(javafx.scene.paint.Color.web("#a04e41"));
        r.setStroke(javafx.scene.paint.Color.web("#7a1f1f")); r.setStrokeWidth(0.5);

        Text label =new Text(COL_NAMES[c]);
        label.setFill(javafx.scene.paint.Color.WHITE);
        label.styleProperty().bind(root.widthProperty().divide(65).asString("-fx-font-size: %.0fpx; -fx-font-weight: bold;"));

        Text arrow =new Text("");
        arrow.setFill(javafx.scene.paint.Color.web("#ffd0b0"));
        arrow.styleProperty().bind(root.widthProperty().divide(65).asString("-fx-font-size: %.0fpx;"));
        headerArrows[c] = arrow;

        Text dot =new Text(""); dot.setFill(javafx.scene.paint.Color.web("#ffe082"));
        dot.styleProperty().bind(root.widthProperty().divide(120).asString("-fx-font-size: %.0fpx; -fx-font-weight: bold;"));
        searchDots[c] = dot;

        HBox hb =new HBox(3, label, arrow, dot); hb.setAlignment(Pos.CENTER);
        sp.getChildren().addAll(r, hb);
        sp.setCursor(Cursor.HAND);

        sp.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                if (sortCol == c) sortAsc = !sortAsc; else { sortCol = c; sortAsc = true; }
                updateArrows(); applyFilterAndSort(); rebuildRows();
            } else if (e.getButton() == MouseButton.SECONDARY) {
                arataCautare(c, (Stage) root.getScene().getWindow());
            }
        });
        sp.setOnMouseEntered(e ->r.setFill(javafx.scene.paint.Color.web("#7a1f1f")));
        sp.setOnMouseExited(e -> r.setFill(sortCol == c ? javafx.scene.paint.Color.web("#5a1010") : javafx.scene.paint.Color.web("#a04e41")));
        return sp;
    }

    private void rebuildRows() {
        rowGrid.getChildren().clear();
        for (int i = 0; i < displayData.size(); i++) {
            NotaRow nr = displayData.get(i);
            String bg = (i %2 == 0) ? "rgba(255,255,255,0.60)" : "rgba(255,255,255,0.36)";
            for (int c = 0; c < COL_NAMES.length; c++) {
                StackPane cell = switch (c) {
                    case 2 -> cellNotaBadge(nr.nota, COL_W[c]);
                    case 4 -> cellBadge(nr.grupa, "#a04e41", COL_W[c]);
                    default -> cellText(nr.cell(c), "#3a0e05", COL_W[c]);
                };
                cell.setStyle(cell.getStyle() +"-fx-background-color: " +bg +";");
                rowGrid.add(cell, c, i);
            }
        }
    }

    private StackPane cellText(String val, String color, double wFrac) {
        StackPane sp =new StackPane();
        Rectangle r =new Rectangle();
        r.widthProperty().bind(root.widthProperty().multiply(TABLE_FRAC *wFrac));
        r.heightProperty().bind(root.heightProperty().multiply(ROW_HEIGHT_FRAC));
        r.setFill(javafx.scene.paint.Color.TRANSPARENT);
        r.setStroke(javafx.scene.paint.Color.web("#c07060")); r.setStrokeWidth(0.4);

        Text t =new Text(val);
        t.setFill(javafx.scene.paint.Color.web(color));
        t.setTextAlignment(TextAlignment.CENTER);
        t.wrappingWidthProperty().bind(r.widthProperty().multiply(0.9));
        t.styleProperty().bind(root.widthProperty().divide(60).asString("-fx-font-size: %.0fpx; -fx-font-weight: bold;"));
        sp.getChildren().addAll(r, t);
        return sp;
    }

    private StackPane cellNotaBadge(int nota, double wFrac) {
        StackPane sp =new StackPane();
        Rectangle r =new Rectangle();
        r.widthProperty().bind(root.widthProperty().multiply(TABLE_FRAC *wFrac));
        r.heightProperty().bind(root.heightProperty().multiply(ROW_HEIGHT_FRAC));
        r.setFill(javafx.scene.paint.Color.TRANSPARENT);
        r.setStroke(javafx.scene.paint.Color.web("#c07060")); r.setStrokeWidth(0.4);

        Rectangle badge =new Rectangle();
        badge.setArcWidth(10); badge.setArcHeight(10);
        badge.setFill(nota >= 5 ? javafx.scene.paint.Color.web("#3B6D11") : javafx.scene.paint.Color.web("#791F1F"));
        badge.widthProperty().bind(r.widthProperty().multiply(0.85));
        badge.heightProperty().bind(r.heightProperty().multiply(0.65));
        Text t =new Text(String.valueOf(nota));
        t.setFill(javafx.scene.paint.Color.WHITE);
        t.styleProperty().bind(badge.heightProperty().multiply(0.75).asString("-fx-font-size: %.0fpx; -fx-font-weight: bold;"));
        sp.getChildren().addAll(r, badge, t);
        return sp;
    }

    private StackPane cellBadge(String val, String color, double wFrac) {
        StackPane sp =new StackPane();
        Rectangle r =new Rectangle();
        r.widthProperty().bind(root.widthProperty().multiply(TABLE_FRAC *wFrac));
        r.heightProperty().bind(root.heightProperty().multiply(ROW_HEIGHT_FRAC));
        r.setFill(javafx.scene.paint.Color.TRANSPARENT);
        r.setStroke(javafx.scene.paint.Color.web("#c07060")); r.setStrokeWidth(0.4);
        if (val.equals("-")){
            return cellText("-", "#3a0e05", wFrac);
        }
        Rectangle badge =new Rectangle();
        badge.setArcWidth(10); badge.setArcHeight(10);
        badge.setFill(javafx.scene.paint.Color.web(color));
        badge.widthProperty().bind(r.widthProperty().multiply(0.90));
        badge.heightProperty().bind(r.heightProperty().multiply(0.65));
        Text t =new Text(val); t.setFill(javafx.scene.paint.Color.WHITE);
        t.styleProperty().bind(badge.heightProperty().multiply(0.7).asString("-fx-font-size: %.0fpx; -fx-font-weight: bold;"));
        sp.getChildren().addAll(r, badge, t);
        return sp;
    }

    private StackPane statBox(String label, String value, String valueColor) {
        StackPane sp =new StackPane();
        Rectangle r =new Rectangle();
        r.setArcWidth(12); r.setArcHeight(12);
        r.setFill(javafx.scene.paint.Color.web("#c9896a")); r.setOpacity(0.55);
        r.widthProperty().bind(root.widthProperty().multiply(STAT_WIDTH_FRAC));
        r.heightProperty().bind(root.heightProperty().multiply(STAT_HEIGHT_FRAC));

        VBox vb =new VBox( -1); vb.setAlignment(Pos.CENTER);
        Text tVal =new Text(value); tVal.setFill(javafx.scene.paint.Color.web(valueColor));
        tVal.styleProperty().bind(r.heightProperty().multiply(0.45).asString("-fx-font-size: %.0fpx; -fx-font-weight: bold;"));

        Text tLbl =new Text(label); tLbl.setFill(javafx.scene.paint.Color.web("#5a1a10"));
        tLbl.styleProperty().bind(r.heightProperty().multiply(0.30).asString("-fx-font-size: %.0fpx; -fx-font-weight: bold;"));
        vb.getChildren().addAll(tVal, tLbl);
        sp.getChildren().addAll(r, vb);
        return sp;
    }

    private HBox legendItem(String text, String color) {
        HBox box =new HBox( 5); box.setAlignment(Pos.CENTER);
        Rectangle dot =new Rectangle(12, 12); dot.setArcWidth(3); dot.setArcHeight(3);
        dot.setFill(javafx.scene.paint.Color.web(color));
        Text t =new Text(text); t.setFill(javafx.scene.paint.Color.web(color));
        t.styleProperty().bind(root.widthProperty().divide(82).asString("-fx-font-size: %.0fpx; -fx-font-weight: bold;"));
        box.getChildren().addAll(dot, t);
        return box;
    }

    private void updateArrows() {
        for (int i = 0; i < headerArrows.length; i++) {
            if (headerArrows[i] != null)
                headerArrows[i].setText(i == sortCol ? (sortAsc ? " ▲" : " ▼") : "");
        }
    }

    private void applyFilterAndSort() {
        displayData.clear();
        for (NotaRow row : masterData) {
            boolean pass = true;
            for (int c= 0; c <COL_NAMES.length; c++) {
                String f =searchFilters[c];
                if (f !=null && !f.isEmpty() && !row.cell(c).toLowerCase().contains(f.toLowerCase())) {
                    pass= false; break;
                }
            }
            if (pass) displayData.add(row);
        }
        if (sortCol >= 0) {
            final int sc = sortCol;
            boolean numeric = sc == 0 || sc == 2;
            Comparator<NotaRow> comp = numeric
                    ? Comparator.comparingDouble(r -> { try { return Double.parseDouble(r.cell(sc)); } catch (Exception ex) { return 0.0; } })
                    : Comparator.comparing(r -> r.cell(sc).toLowerCase());
            if (!sortAsc) comp = comp.reversed();
            displayData.sort(comp);
        }
    }

    private void arataCautare(int c, Stage owner) {
        Stage popup =new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.initOwner(owner);
        popup.initStyle(StageStyle.TRANSPARENT);

        StackPane popRoot =new StackPane();
        popRoot.setStyle("-fx-background-color: transparent;");

        final double[] delta = { 0, 0};
        popRoot.setOnMousePressed(e -> { delta[ 0] = e.getSceneX(); delta[1] = e.getSceneY(); });
        popRoot.setOnMouseDragged(e -> { popup.setX(e.getScreenX() -delta[ 0]); popup.setY(e.getScreenY() -delta[1]); });
        Rectangle bg =new Rectangle(300, 155);
        bg.setArcWidth(20); bg.setArcHeight(20);
        bg.setFill(new RadialGradient(1, 0, 0.5, 0.5, 1.5, true, CycleMethod.NO_CYCLE,
                new Stop( 0, javafx.scene.paint.Color.web("#e7dee2")),
                new Stop(1, javafx.scene.paint.Color.web("#ff8144"))));
        bg.setStroke(javafx.scene.paint.Color.web("#7a1f1f"));
        bg.setStrokeWidth(1.5);

        VBox vb =new VBox( 12);
        vb.setAlignment(Pos.CENTER);
        vb.setPadding(new Insets( 15));
        Text lbl =new Text("CĂUTARE: " +COL_NAMES[c].toUpperCase());
        lbl.setFill(javafx.scene.paint.Color.web("#7a1f1f"));
        lbl.styleProperty().bind(javafx.beans.binding.Bindings.concat("-fx-font-size: 15px; -fx-font-weight: bold; -fx-font-family: 'League Spartan';"));

        TextField tf =new TextField(searchFilters[c] != null ? searchFilters[c] : "");
        tf.setPromptText("Introdu text...");
        tf.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-border-color: #a04e41; -fx-border-radius: 10; -fx-padding: 5; -fx-font-weight: bold;");
        tf.setMaxWidth(220);
        HBox btnRow =new HBox( 12);
        btnRow.setAlignment(Pos.CENTER);

        StackPane btnOk = popupBtn("APLICĂ", "#a04e41");
        StackPane btnClear = popupBtn("ȘTERGE", "#7a1f1f");
        btnOk.setOnMouseClicked(e -> {
            searchFilters[c] = tf.getText().trim();
            if (searchDots[c] != null) searchDots[c].setText(searchFilters[c].isEmpty() ? "" : " ●");
            applyFilterAndSort(); rebuildRows(); popup.close();
        });
        btnClear.setOnMouseClicked(e -> {
            searchFilters[c] = "";
            if (searchDots[c] != null) searchDots[c].setText("");
            applyFilterAndSort(); rebuildRows(); popup.close();
        });
        tf.setOnAction(e -> {
            searchFilters[c] = tf.getText().trim();
            if (searchDots[c] != null) searchDots[c].setText(searchFilters[c].isEmpty() ? "" : " ●");
            applyFilterAndSort(); rebuildRows(); popup.close();
        });

        btnRow.getChildren().addAll(btnOk, btnClear);
        vb.getChildren().addAll(lbl, tf, btnRow);
        popRoot.getChildren().addAll(bg, vb);
        Scene ps =new Scene(popRoot, 300, 155);
        ps.setFill(javafx.scene.paint.Color.TRANSPARENT);
        popup.setScene(ps);
        popup.showAndWait();
    }

    private StackPane popupBtn(String label, String color) {
        Rectangle r =new Rectangle(100, 32);
        r.setArcWidth(12); r.setArcHeight(12);
        r.setFill(javafx.scene.paint.Color.web(color));
        r.setStroke(javafx.scene.paint.Color.BLACK);
        r.setStrokeWidth(0.3);
        Text t =new Text(label);
        t.setFill(javafx.scene.paint.Color.WHITE);
        t.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-font-family: 'League Spartan';");
        StackPane sp =new StackPane(r, t);
        sp.setCursor(Cursor.HAND);
        sp.setOnMouseEntered(e ->r.setFill(javafx.scene.paint.Color.web("#3d0b04")));
        sp.setOnMouseExited(e -> r.setFill(javafx.scene.paint.Color.web(color)));
        return sp;
    }

    public Scene getScene() { return scene; }
}