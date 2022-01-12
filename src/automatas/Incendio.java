package automatas;

import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class Incendio extends Automata {

    private double p = .3, f = .3, g = .3;
    private int[][] stateChanges;
    private CellIncendio[][] cells;
    private ArrayList<Integer> criticals = new ArrayList<Integer>();

    public Incendio(int width, int height) {
        super(width, height);
        initRightMenu();
    }

    @Override
    public void initTimeline(int steps) {
        final Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.millis(500),
                        event -> {
                            step();
                        }));
        timeline.setCycleCount(steps);
        timeline.play();
        timeline.setOnFinished(e -> {
            Graph.display(getCriticals());
        });
    }

    @Override
    public Object[] getCriticals() {
        return criticals.toArray();
    }

    @Override
    protected void initRightMenu() {
        // p probabilidad de que crezca un árbol 1-100
        Text text1 = new Text("Probabilidad de que crezca un árbol del 0-100");
        // f probabilidad de que se incendie
        Text text2 = new Text("Probabilidad de que un árbol se incendie del 0-100");
        // g probabilidad de que un arbol sea inmune al fuego
        Text text3 = new Text("Probabilidad de que un árbol sea inmune al fuego 0-100");
        TextField textField1 = new TextField(String.valueOf((int) (p * 100)));
        TextField textField2 = new TextField(String.valueOf((int) (f * 100)));
        TextField textField3 = new TextField(String.valueOf((int) (g * 100)));
        Button button1 = new Button("Change parameters");
        button1.setOnAction(e -> {
            if (isInt(textField1.getText()) & isInt(textField2.getText()) & isInt(textField3.getText())) {
                p = ((double) Integer.parseInt(textField1.getText())) / 100;
                f = ((double) Integer.parseInt(textField2.getText())) / 100;
                g = ((double) Integer.parseInt(textField3.getText())) / 100;
            }
        });
        rightMenu.getChildren().addAll(text1, textField1, text2, textField2, text3, textField3, button1);
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

    private void countCriticals() {
        int currCrit = 0;
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
                if (cells[i][j].getState() == 2) {
                    currCrit++;
                }
            }
        }
        criticals.add(currCrit);
    };

    @Override
    public void step() {
        if (currStep == 0) {
            countCriticals();
        }
        doStep();

        // 0 -> 1, con p probabilidad

        // 1 -> 2, con (1-g) probabilidad si algun vecino
        // 1 -> 2, con f*(1-g) si no hay vecino

        // 2 -> 0, next step

        // Save changes
        stateChanges = new int[cells.length][cells[0].length];
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
                if (cells[i][j].getState() == 0 & pProb()) {
                    stateChanges[i][j] = 1;
                } else if (cells[i][j].getState() == 1) {
                    if (hasBurningNeighbor(i, j) & gProb()) {
                        stateChanges[i][j] = 2;

                    } else if (!hasBurningNeighbor(i, j) & fgProb()) {
                        stateChanges[i][j] = 2;
                    } else {
                        stateChanges[i][j] = 1;
                    }

                } else if (cells[i][j].getState() == 2) {
                    stateChanges[i][j] = 0;
                } else {
                    stateChanges[i][j] = cells[i][j].getState();
                }
            }
        }
        // Make changes
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
                if (stateChanges[i][j] == cells[i][j].getState()) {
                    continue;
                } else if (stateChanges[i][j] == 0) {
                    cells[i][j].makeEmpty();
                } else if (stateChanges[i][j] == 1) {
                    cells[i][j].makeTree();
                } else if (stateChanges[i][j] == 2) {
                    cells[i][j].makeBurn();
                }
            }
        }

        countCriticals();
    }

    private boolean pProb() {
        // 0->1
        double num = Math.random();
        return num < p;
    }

    private boolean gProb() {
        double num = Math.random();
        return num < (1 - g);
    }

    private boolean fgProb() {
        double num = Math.random();
        return num < f * (1 - g);
    }

    private boolean hasBurningNeighbor(int i, int j) {
        boolean n1 = cells[(i - 1 + cells.length) % cells.length][(j +
                cells[0].length) % cells[0].length].isBurning();
        boolean n2 = cells[(i + 1 + cells.length) % cells.length][(j +
                cells[0].length) % cells[0].length].isBurning();
        boolean n3 = cells[(i + cells.length) % cells.length][(j - 1 +
                cells[0].length) % cells[0].length].isBurning();
        boolean n4 = cells[(i + cells.length) % cells.length][(j + 1 +
                cells[0].length) % cells[0].length].isBurning();
        return n1 | n2 | n3 | n4;
    }

    @Override
    protected void createGrid() {
        cells = new CellIncendio[height][width];

        int cellSide = 650 / Integer.max(height, width);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                cells[i][j] = new CellIncendio(cellSide);
            }
        }

        // GridPane layout
        grid = new GridPane();
        grid.setPadding(new Insets(10));
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                grid.add(cells[i][j], i, j);
            }
        }
        grid.setGridLinesVisible(true);

    }

    @Override
    public void resetCriticals() {
        criticals = new ArrayList<Integer>();
    }

}
