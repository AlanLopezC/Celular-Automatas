package automatas;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class CellEpidemia extends Rectangle {
    // 0 alive, 1 ill, 2 immune
    private int state = 0;
    private int lastIll = Integer.MIN_VALUE;
    private int lastImmune = Integer.MIN_VALUE;

    public CellEpidemia(int cellSide) {
        super(cellSide, cellSide, Color.WHITE);
    }

    public int getLastImmune() {
        return lastImmune;
    }

    public void setLastImmune(int lastImmune) {
        this.lastImmune = lastImmune;
    }

    public int getLastIll() {
        return lastIll;
    }

    public void setLastIll(int lastIll) {
        this.lastIll = lastIll;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void makeIll(int currStep) {
        state = 1;
        lastIll = currStep;
        this.setFill(Color.RED);
    }

    public void makeImmune(int currStep) {
        state = 2;
        lastImmune = currStep;
        this.setFill(Color.GREEN);
    }

    public void makeNormal(int currStep) {
        state = 0;
        this.setFill(Color.WHITE);
    }

    public boolean isIll() {
        return state == 1;
    }
}
