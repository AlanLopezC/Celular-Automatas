/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automatas;

import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Demo extends Application {

    Stage stage;
    GridPane grid;
    int x = 40, y = 30;
    Rectangle[][] matrix;
    BorderPane borderPane;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        stage.setTitle("Autómatas");

        // Top menu
        HBox topMenu = new HBox(10);
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll("Incendio Forestal", "Epidemia");
        choiceBox.setValue("Incendio Forestal");
        topMenu.getChildren().add(choiceBox);

        // Listen ChoiceBox changes
        choiceBox.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            // *Need to change
            System.out.println(newValue);
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
                createNewGrid();
            }
        });
        Button button2 = new Button("Begin");

        bottomMenu.getChildren().addAll(text1, textField1, text2, textField2, button1, button2);

        // Grid
        grid = new GridPane();
        grid.setMaxHeight(400);
        grid.setPadding(new Insets(10));
        createGrid();
        grid.setGridLinesVisible(true);

        // Layout
        borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: beige;");
        borderPane.setPadding(new Insets(10));
        borderPane.setTop(topMenu);
        borderPane.setBottom(bottomMenu);
        borderPane.setCenter(grid);

        // Scene
        Scene scene = new Scene(borderPane, 800, 650);
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

    private void createGrid() {

        createMatrix();
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                grid.add(matrix[i][j], i, j);
            }
        }
    }

    private void createMatrix() {
        matrix = new Rectangle[x][y];
        int max = Integer.max(x, y);
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                Rectangle rect = new Rectangle(750 / max, 750 / max, Color.WHITE);
                matrix[i][j] = rect;
                rect.setOnMouseClicked(e -> {
                    rect.setFill(Color.RED);
                });
            }
        }
    }

    private void createNewGrid() {
        grid = new GridPane();
        grid.setMaxHeight(1200);
        grid.setPadding(new Insets(10));
        createGrid();
        grid.setGridLinesVisible(true);
        borderPane.setCenter(grid);
    }
}
