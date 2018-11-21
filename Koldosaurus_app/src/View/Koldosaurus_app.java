/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Control.LandareakGertu;
import Model.Landareak;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author DM3-2-15
 */
public class Koldosaurus_app extends Application {

    private final TableView<Landareak> table = new TableView<>();
    private Scene colorScene;
    private String datuBaseIzena;
    ObservableList<Landareak> data = null;
    final HBox hb = new HBox();

    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(new Group());
        stage.setTitle("Data Table");
        stage.setWidth(900);
        stage.setHeight(600);
        final Label label = new Label("Landareak");
        label.setFont(new Font("Arial", 20));

        final Button dataChooser = new Button("Aukeratu Datu-basea");
        dataChooser.setOnAction((ActionEvent e) -> {
            datuBaseaAukeratzen();
            
        });

        final Label lab = new Label("");
        lab.setTextFill(Color.web("#ff0000"));
        lab.setFont(new Font("Arial", 20));

        table.setEditable(true);

        TableColumn<Landareak, String> NameCol
                = new TableColumn<>("Name");
        NameCol.setMinWidth(100);
        NameCol.setMaxWidth(150);
        NameCol.setCellValueFactory(
                new PropertyValueFactory<>("name"));
        NameCol.setCellFactory(TextFieldTableCell.<Landareak>forTableColumn());
        NameCol.setOnEditCommit(
                (TableColumn.CellEditEvent<Landareak, String> t) -> {
                    ((Landareak) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setName(t.getNewValue());
                });

        TableColumn<Landareak, String> Description
                = new TableColumn<>("Description");
        Description.setMinWidth(230);
        Description.setMaxWidth(280);
        Description.setCellValueFactory(
                new PropertyValueFactory<>("description"));
        Description.setCellFactory(TextFieldTableCell.<Landareak>forTableColumn());
        Description.setOnEditCommit(
                (TableColumn.CellEditEvent<Landareak, String> t) -> {
                    ((Landareak) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setDescription(t.getNewValue());
                });

        TableColumn<Landareak, String> Color = new TableColumn<>("Color");
        Color.setMinWidth(150);
        Color.setMaxWidth(190);
        Color.setCellValueFactory(
                new PropertyValueFactory<>("color"));
        Color.setCellFactory(TextFieldTableCell.<Landareak>forTableColumn());
        Color.setOnEditCommit(
                (TableColumn.CellEditEvent<Landareak, String> t) -> {
                    ((Landareak) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setColor(t.getNewValue());
                });

        TableColumn<Landareak, String> Height = new TableColumn<>("Height");
        Height.setMinWidth(100);
        Height.setMaxWidth(150);
        Height.setCellValueFactory(
                new PropertyValueFactory<>("size"));
        Height.setCellFactory(TextFieldTableCell.<Landareak>forTableColumn());
        Height.setOnEditCommit(
                (TableColumn.CellEditEvent<Landareak, String> t) -> {
                    ((Landareak) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setSize(t.getNewValue());
                });

        TableColumn<Landareak, String> Flower
                = new TableColumn<>("Flowers");
        Flower.setMinWidth(100);
        Flower.setMaxWidth(150);

        Flower.setCellValueFactory(
                new PropertyValueFactory<>("flowers"));
        Flower.setCellFactory(TextFieldTableCell.<Landareak>forTableColumn());
        Flower.setOnEditCommit(
                (TableColumn.CellEditEvent<Landareak, String> t) -> {
                    ((Landareak) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setSize(t.getNewValue());
                });
        TableColumn<Landareak, String> CName
                = new TableColumn<>("Cientific Name");
        CName.setMinWidth(180);
        CName.setCellValueFactory(
                new PropertyValueFactory<>("CName"));
        CName.setCellFactory(TextFieldTableCell.<Landareak>forTableColumn());
        CName.setOnEditCommit(
                (TableColumn.CellEditEvent<Landareak, String> t) -> {
                    ((Landareak) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setSize(t.getNewValue());
                });

        table.setItems(data);
        table.getColumns().addAll(NameCol, Description, Color, Height, Flower, CName);

        final TextField addName = new TextField();
        addName.setPromptText("Name");
        addName.setMaxWidth(NameCol.getPrefWidth());

        final TextField addDescription = new TextField();
        addDescription.setMaxWidth(Description.getPrefWidth());
        addDescription.setPromptText("Description");

        final TextField addColor = new TextField();
        addColor.setMaxWidth(Color.getPrefWidth());
        addColor.setPromptText("Color");

        final TextField addAvHeight = new TextField();
        addAvHeight.setMaxWidth(Height.getPrefWidth());
        addAvHeight.setPromptText("Average Height");

        final TextField addCName = new TextField();
        addCName.setMaxWidth(150);
        addCName.setPromptText("Cientific name");

        final RadioButton Flowers = new RadioButton();
        Flowers.setMaxWidth(Color.getPrefWidth());
        Flowers.setText("Flowers");

        final Button addButton = new Button("Add");
        addButton.setOnAction((ActionEvent e) -> {
            //objetu berria sortu
            if (addName.getText().equals("") || addDescription.getText().equals("") || addColor.getText().equals("") || addAvHeight.getText().equals("") || addCName.getText().equals("") || Flowers.getText().equals("")) {
                lab.setText("You should fill all the fields");
            } else {
                try {
                    Float.parseFloat(addAvHeight.getText());
                    Landareak p = new Landareak(
                            addName.getText(),
                            addDescription.getText(),
                            addColor.getText(),
                            addAvHeight.getText(),
                            Flowers.isSelected(),
                            addCName.getText()
                    );
                    //hau ez dago guztiz ondo funzionatzen
                    LandareakGertu.addToDatabase(p, datuBaseIzena);
                    data.add(p);

                    //testuak garbitu
                    addName.clear();
                    addDescription.clear();
                    addColor.clear();
                    addAvHeight.clear();
                    addCName.clear();
                    Flowers.setSelected(false);
                    lab.setText("");
                } catch (NumberFormatException nf) {
                    lab.setText("Average height should be a number");
                }

            }
        });

        final Button removeButton = new Button("Delete selected");
        removeButton.setOnAction((ActionEvent e) -> {
            Landareak landare = table.getSelectionModel().getSelectedItem();
            LandareakGertu.deleteFromDatabase(landare, datuBaseIzena);
            data.remove(landare);
        });

        hb.getChildren().addAll(addName, addDescription, addColor, addAvHeight, Flowers, addCName, addButton, removeButton);
        hb.setSpacing(3);
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, dataChooser, lab, table, hb);
        ((Group) scene.getRoot()).getChildren().addAll(vbox);
        stage.setOnCloseRequest((WindowEvent event) -> {
            try {
                try {
                    //LandareakGertu.datuakGordeFitxategia(data, aukeratua);
                } catch (NullPointerException er) {
                }
            } catch (Exception ex) {
                Logger.getLogger(Koldosaurus_app.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        stage.setScene(scene);

        stage.show();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public void datuBaseaAukeratzen() {
        //beste lehio bat irekiko da
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(20, 0, 0, 20));
        ObservableList<String> datubasemotak = FXCollections.observableArrayList();
        datubasemotak.add("SQLite");
        datubasemotak.add("MySQL");
        Label secondLabel = new Label("Aukeratu datu-base mota:");

        ComboBox womboCombo = new ComboBox();
        womboCombo.setItems((ObservableList) datubasemotak);

        ComboBox womboCombo2 = new ComboBox();
        womboCombo2.setEditable(true);

        womboCombo.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if (newValue == "SQLite") {
                womboCombo2.setItems(LandareakGertu.datuBaseIzenZerrenda("SQLite"));
            } else {
                womboCombo2.setItems(LandareakGertu.datuBaseIzenZerrenda(""));
            }
        }
        );

        Label firstLabel = new Label("Sartu edo aukeratu datu-basearen izena:\n(Datu basea ez bada existitzen sortu egingo da)");
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Datu-basea falta da");
        alert.setHeaderText("Kontuz!");
        alert.setContentText("Ez duzu datu basearen izena edo mota aukeratu");

        Button btnGorde = new Button("Konexioa egin");
        btnGorde.setOnAction(e
                -> {
            try{
            if (womboCombo.getSelectionModel().getSelectedItem().toString().equals("") & womboCombo2.getSelectionModel().getSelectedItem().toString().equals("")) {
            } else {
                if (womboCombo.getSelectionModel().getSelectedItem().toString().equals("SQLite")) {
                    data = LandareakGertu.SQLiteDatuak(womboCombo2.getSelectionModel().getSelectedItem().toString() + ".db");
                    datuBaseIzena=womboCombo2.getSelectionModel().getSelectedItem().toString();
                    table.setItems(data);
                } else {

                }
            }
            }catch(NullPointerException ex){
                alert.showAndWait();
            }
            //LandareakGertu.connect("db1"+".db");
        });

        vbox.getChildren().addAll(secondLabel, womboCombo, firstLabel, womboCombo2, btnGorde);
        vbox.setSpacing(10);
        colorScene = new Scene(vbox, 420, 230);

        //lehio berria ireki
        Stage newWindow = new Stage();
        newWindow.setTitle("Datu basea aukeratzen");
        newWindow.setScene(colorScene);

        // lehioaren posizioa
        newWindow.setX(700);
        newWindow.setY(350);

        newWindow.show();
    }

}
