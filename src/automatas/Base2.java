/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automatas;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Base2 extends Application {

    int x = 30, y = 40;
    Automata automata;
    BorderPane layout;
    GridPane grid;
    Epidemia epidemia = new Epidemia(x, y);
    Incendio incendio = new Incendio(x, y);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Automátas");

        // Top menu
        HBox topMenu = new HBox(10);
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll("Epidemia", "Incendio Forestal");
        choiceBox.setValue("Epidemia");
        topMenu.getChildren().add(choiceBox);

        // Listen ChoiceBox changes
        choiceBox.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            if (newValue.compareTo("Epidemia") == 0) {
                automata = epidemia;
            } else {
                automata = incendio;
            }
            layout.setCenter(automata.getGrid());
            layout.setRight(automata.getRightMenu());
        });

        // Bottom menu
        HBox bottomMenu = new HBox(10);
        Text text1 = new Text("x");
        Text text2 = new Text("y");
        TextField textField1 = new TextField(String.valueOf(x));
        TextField textField2 = new TextField(String.valueOf(y));
        Button button1 = new Button("Change dimensions / Reset");
        button1.setOnAction(e -> {
            if (isInt(textField1.getText()) & isInt(textField2.getText())) {
                x = Integer.parseInt(textField1.getText());
                y = Integer.parseInt(textField2.getText());
                automata.resetCriticals();
                automata.createNewGrid(x, y);
                grid = automata.getGrid();
                layout.setCenter(grid);
            }
        });

        Button button2 = new Button("Step");
        button2.setOnAction(e -> {
            automata.step();
            // display count
        });

        Button button3 = new Button("Graph");
        button3.setOnAction(e -> {
            Graph.display(automata.getCriticals());
        });

        bottomMenu.getChildren().addAll(text1, textField2, text2, textField1, button1, button2, button3);

        // Automata
        automata = epidemia;

        // Grid
        grid = automata.getGrid();

        // RightMenu
        VBox rightMenu = automata.getRightMenu();

        // Layout
        layout = new BorderPane();
        layout.setStyle("-fx-background-color: beige;");
        layout.setPadding(new Insets(10));
        layout.setTop(topMenu);
        layout.setBottom(bottomMenu);
        layout.setCenter(grid);
        layout.setRight(rightMenu);

        // Scene
        Scene scene = new Scene(layout, 1000, 700);
        stage.setScene(scene);
        stage.show();
    }

    private boolean isInt(String message) {
        try {
            Integer.parseInt(message);
            return true;
        } catch (NumberFormatException e) {
            // * Display message, also if 0 < num < constraints
            return false;
        }
    }
}
