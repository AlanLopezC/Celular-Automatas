/*
 * Autómatas
 */
package automatas;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

/**
 *
 * @author blackzafiro
 */
public class Automata extends AnimationTimer {
    
    private Group root;
    private Text text;
    
    public Automata(Group root) {
        this.root = root;
        text = new Text ("Inicio");
        text.setStroke(Color.WHITE);
        text.setY(200);
        Line line = new Line(0, 210, 800, 210);
        line.setStroke(Color.WHITE);
        root.getChildren().add(text);
        root.getChildren().add(line);
        
    }

    @Override
    public void handle(long now) {
        text.setText("Tiempo " + now);
    }
    
}
