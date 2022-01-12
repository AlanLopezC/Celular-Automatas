package automatas;

import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Graph {

    public static void display(Object[] criticals) {
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);

        // * Graph
        // defining the axes
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Steps");
        yAxis.setLabel("Criticals");

        // Creating chart
        final LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);

        lineChart.setTitle("Critical cells");
        // defining a series
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Graph");
        // populating the series with data
        for (int i = 0; i < criticals.length; i++) {
            series.getData().add(new XYChart.Data<Number, Number>(i, (int) criticals[i]));
        }
        lineChart.getData().add(series);

        StackPane layout = new StackPane();
        layout.getChildren().add(lineChart);

        Scene scene = new Scene(layout, 500, 500);
        stage.setScene(scene);
        stage.showAndWait();
    }
}
