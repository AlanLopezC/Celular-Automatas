package automatas;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class CellIncendio extends Rectangle {
    // 0 empty, 1 tree, 2 burning
    private int state = 0;

    public CellIncendio(int cellSide) {
        super(cellSide, cellSide, Color.WHITE);
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public boolean isBurning() {
        return state == 2;
    }

    public void makeEmpty() {
        state = 0;
        this.setFill(Color.WHITE);
    }

    public void makeTree() {
        state = 1;
        this.setFill(Color.GREEN);
    }

    public void makeBurn() {
        state = 2;
        this.setFill(Color.RED);
    }

}