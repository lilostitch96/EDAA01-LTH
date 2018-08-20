package testqueue;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CounterView extends Application {
    @Override
    public void start(Stage stage) {
        VBox root = new VBox();
        root.getChildren().add(new CounterPane("Yes"));
        root.getChildren().add(new CounterPane("No"));
        root.getChildren().add(new CounterPane("Neutral"));
        root.setPrefSize(150, 100);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Counters");
        stage.show();
}
    public static void main(String[] args) {
        launch(args);
    }
}