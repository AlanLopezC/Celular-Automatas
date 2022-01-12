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

public class Epidemia extends Automata {

    private int g = 3, a = 3;
    private int[][] stateChanges;
    private CellEpidemia[][] cells;
    private ArrayList<Integer> criticals = new ArrayList<Integer>();

    public Epidemia(int width, int height) {
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
        // Inmume desp de g, se vuelve susceptible.
        Text text1 = new Text("Steps from immune to normal");
        // Infeccioso desp de a, se vuelve inmune.
        Text text2 = new Text("Steps from ill to immune");
        TextField textField1 = new TextField(String.valueOf(g));
        TextField textField2 = new TextField(String.valueOf(a));
        Button button1 = new Button("Change parameters");
        button1.setOnAction(e -> {
            if (isInt(textField1.getText()) & isInt(textField2.getText())) {
                g = Integer.parseInt(textField1.getText());
                a = Integer.parseInt(textField2.getText());
            }
        });
        rightMenu.getChildren().addAll(text1, textField1, text2, textField2, button1);
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
                if (cells[i][j].getState() == 1) {
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

        // normal Check neighbors make ill.
        // : change, lastIll = currStep

        // *ill check count for becoming immune. - a
        // currStep > lastIll + a : change, lastImune = currStep

        // *immune check count for becoming normal. - g
        // currStep > lastImmune + g : change

        // Save changes
        stateChanges = new int[cells.length][cells[0].length];
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
                if (cells[i][j].getState() == 0 & hasIllNeighbor(i, j)) {
                    stateChanges[i][j] = 1;
                } else if (cells[i][j].getState() == 1 & currStep > cells[i][j].getLastIll() + a) {
                    stateChanges[i][j] = 2;
                } else if (cells[i][j].getState() == 2 & currStep > cells[i][j].getLastImmune() + g) {
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
                    cells[i][j].makeNormal(currStep);
                } else if (stateChanges[i][j] == 1) {
                    cells[i][j].makeIll(currStep);
                } else if (stateChanges[i][j] == 2) {
                    cells[i][j].makeImmune(currStep);
                }
            }
        }

        countCriticals();

    }

    private boolean hasIllNeighbor(int i, int j) {
        boolean n1 = cells[(i - 1 + cells.length) % cells.length][(j + cells[0].length) % cells[0].length].isIll();
        boolean n2 = cells[(i + 1 + cells.length) % cells.length][(j + cells[0].length) % cells[0].length].isIll();
        boolean n3 = cells[(i + cells.length) % cells.length][(j - 1 + cells[0].length) % cells[0].length].isIll();
        boolean n4 = cells[(i + cells.length) % cells.length][(j + 1 + cells[0].length) % cells[0].length].isIll();
        return n1 | n2 | n3 | n4;
    }

    @Override
    protected void createGrid() {
        cells = new CellEpidemia[height][width];

        int cellSide = 650 / Integer.max(height, width);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                CellEpidemia cell = new CellEpidemia(cellSide);
                cells[i][j] = cell;
                cell.setOnMouseClicked(e -> {
                    cell.makeIll(currStep);
                });
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
