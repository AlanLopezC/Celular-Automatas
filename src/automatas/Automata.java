package automatas;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public abstract class Automata {

    protected int width;
    protected int height;
    protected GridPane grid;

    protected VBox rightMenu = new VBox(10);

    protected int currStep = 0;

    public Automata(int width, int height) {
        this.width = width;
        this.height = height;
        createGrid();
    }

    protected void doStep() {
        currStep++;
    }

    protected abstract void createGrid();

    protected abstract void initTimeline(int steps);

    public void createNewGrid(int width, int height) {
        this.width = width;
        this.height = height;
        createGrid();
    }

    public GridPane getGrid() {
        return grid;
    }

    public VBox getRightMenu() {
        return rightMenu;
    }

    protected abstract void initRightMenu();

    public abstract void step();

    public abstract Object[] getCriticals();

    public abstract void resetCriticals();
}
