package testqueue;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class CounterPane extends HBox {
	private int counter = 0;
	private Button button;
	private Label label;

	public CounterPane(String s) {
		button = new Button(s);
		button.setOnAction(event -> {
			counter ++;
			label.setText(Integer.toString(counter));
		});
		label = new Label("0");
		setPadding(new Insets(10, 10, 10, 10));
		setSpacing(20);
		setAlignment(Pos.CENTER_LEFT);
		getChildren().addAll(button, label);
	}
}