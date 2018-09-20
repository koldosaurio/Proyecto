/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Control.LandareakGertu;
import Model.Landareak;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 *
 * @author DM3-2-15
 */
public class Koldosaurus_app extends Application {

    private final TableView<Landareak> table = new TableView<>();

    final HBox hb = new HBox();

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new Group());

        ObservableList<Landareak> data = LandareakGertu.cargarDatos();

        stage.setTitle("Datuen Taula");
        stage.setWidth(800);
        stage.setHeight(550);
        final Label label = new Label("Landareak");
        label.setFont(new Font("Arial", 20));

        table.setEditable(true);

        TableColumn<Landareak, String> NameCol
                = new TableColumn<>("Name");
        NameCol.setMinWidth(100);
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
        Description.setMinWidth(200);
        Description.setCellValueFactory(
                new PropertyValueFactory<>("description"));
        Description.setCellFactory(TextFieldTableCell.<Landareak>forTableColumn());
        Description.setOnEditCommit(
                (TableColumn.CellEditEvent<Landareak, String> t) -> {
                    ((Landareak) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setDescription(t.getNewValue());
                });

        TableColumn<Landareak, String> Color = new TableColumn<>("Color");
        Color.setMinWidth(100);
        Color.setCellValueFactory(
                new PropertyValueFactory<>("color"));
        Color.setCellFactory(TextFieldTableCell.<Landareak>forTableColumn());
        Color.setOnEditCommit(
                (TableColumn.CellEditEvent<Landareak, String> t) -> {
                    ((Landareak) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setColor(t.getNewValue());
                });
        
        TableColumn<Landareak, String> AverageHeight
                = new TableColumn<>("Average Height");
        AverageHeight.setMinWidth(100);
        AverageHeight.setCellValueFactory(
                new PropertyValueFactory<>("averageheight"));
        AverageHeight.setCellFactory(TextFieldTableCell.<Landareak>forTableColumn());
        AverageHeight.setOnEditCommit(
                (TableColumn.CellEditEvent<Landareak, String> t) -> {
                    ((Landareak) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setAverageHeight(t.getNewValue());
                });
        
        TableColumn<Landareak, String> Flower
                = new TableColumn<>("Flowers");
        Flower.setMinWidth(100);
        Flower.setCellValueFactory(
                new PropertyValueFactory<>("flowers"));
        Flower.setCellFactory(TextFieldTableCell.<Landareak>forTableColumn());
        Flower.setOnEditCommit(
                (TableColumn.CellEditEvent<Landareak, String> t) -> {
                    ((Landareak) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setAverageHeight(t.getNewValue());
                });
        TableColumn<Landareak, String> CName
                = new TableColumn<>("Cientific Name");
        CName.setMinWidth(150);
        CName.setCellValueFactory(
                new PropertyValueFactory<>("cname"));
        CName.setCellFactory(TextFieldTableCell.<Landareak>forTableColumn());
        CName.setOnEditCommit(
                (TableColumn.CellEditEvent<Landareak, String> t) -> {
                    ((Landareak) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setAverageHeight(t.getNewValue());
                });
        
        table.setItems(data);
        table.getColumns().addAll(NameCol, Description, Color, AverageHeight,Flower,CName);
        
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
        addAvHeight.setMaxWidth(Color.getPrefWidth());
        addAvHeight.setPromptText("Average Height");
        
        final TextField addCName = new TextField();
        addCName.setMaxWidth(CName.getPrefWidth());
        addCName.setPromptText("Cientific name");
        
        final RadioButton Flowers = new RadioButton();
        Flowers.setMaxWidth(Color.getPrefWidth());
        Flowers.setText("Flowers");
        
        final Button addButton = new Button("Add");
        addButton.setOnAction((ActionEvent e) -> {
            //objetu berria sortu
            Landareak p = new Landareak(
                    addName.getText(),
                    addDescription.getText(),
                    addColor.getText(),
                    addAvHeight.getText(),
                    Flowers.isSelected(),
                    addCName.getText()
                    );
            data.add(p);

            //testuak garbitu
            addName.clear();
            addDescription.clear();
            addColor.clear();
            addAvHeight.clear();
            addCName.clear();
        });

        final Button removeButton = new Button("Delete selected");
        removeButton.setOnAction((ActionEvent e) -> {
            Landareak landare = table.getSelectionModel().getSelectedItem();
            data.remove(landare);
        });

        hb.getChildren().addAll(addName, addDescription, addColor,addAvHeight,Flowers,addCName, addButton, removeButton);
        hb.setSpacing(3);
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table, hb);
        ((Group) scene.getRoot()).getChildren().addAll(vbox);
        stage.setScene(scene);
        stage.show();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
