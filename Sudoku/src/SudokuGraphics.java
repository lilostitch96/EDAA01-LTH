import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

/**
 * The {@code SudokuGraphics} class represents a user interface of a
 * sudoku-solver. The ui consists of a 9x9 grid with TextFields in which the
 * user are allowed to insert any number in between 1-9. The ui also contains
 * two buttons, one that clears the grid and one that solve the current grid.
 * The rules are described in the {@code BackTrackingSudoku} class.
 * 
 * @author Eneas Hållsten
 *
 */

public class SudokuGraphics extends Application {

	@Override
	public void start(Stage stage) throws Exception {

		BacktrackingSudoku sudoku = new BacktrackingSudoku();
		BorderPane root = new BorderPane();
		TilePane grid = new TilePane();
		HBox box = new HBox();
		Button clear = new Button("Clear");
		Button solve = new Button("Solve");
		OneLetterTextField[][] tf = new OneLetterTextField[9][9];

		box.setPadding(new Insets(10, 10, 10, 10));
		box.setSpacing(20);
		box.getChildren().addAll(clear, solve);
		box.setAlignment(Pos.CENTER);

		grid.setHgap(5);
		grid.setVgap(5);
		grid.setPrefColumns(5);
		grid.setPrefRows(5);
		grid.setMaxSize(280, 280);

		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				tf[row][col] = new OneLetterTextField();
				if (changeColor(row, col)) {
					tf[row][col].setStyle("-fx-background-color: orange;");
				}
				tf[row][col].setPrefSize(25, 25);
				grid.getChildren().add(tf[row][col]);
			}
		}

		root.setCenter(grid);
		root.setBottom(box);


		solve.setOnAction((ActionEvent e) -> { // When solve button is pressed, solve if possivle and show the solution
												// otherwise notify the user no possible solution can be done.
			int[][] numbers = new int[9][9]; // Matrix on which the input numbers are placed
			for (int row = 0; row < 9; row++) {
				for (int col = 0; col < 9; col++) {
					String tempString = tf[row][col].getText(); // The number in String type
					if (!tempString.isEmpty()) { // If the user did put in a number in the textfield
						int tempInt = Integer.parseInt((String) tempString); // Convert the String to int
						numbers[row][col] = tempInt; // Add this number to the transition matrix
					}
				}
			}

			if (sudoku.solve(numbers)) { // If the sudoku was solved
				for (int row = 0; row < 9; row++) {
					for (int col = 0; col < 9; col++) {
						tf[row][col].insertText(0, Integer.toString(numbers[row][col]));
					}
				}
			} else { // If the sudoku could not be solved
				Alert alert = new Alert(AlertType.WARNING, "Can not solve the Sudoku");
				alert.showAndWait();
			}

		});

		clear.setOnAction((ActionEvent e) -> {
			for (int row = 0; row < 9; row++) {
				for (int col = 0; col < 9; col++) {
					tf[row][col].clear();
				}
			}
		});

		stage.setScene(new Scene(root, 400, 400));
		stage.setTitle("Sudoku");
		stage.show();
	}

	private boolean changeColor(int row, int col) {
		if (row < 3 && col < 3 || row > 5 && col < 3 || row > 2 && row < 6 && col > 2 && col < 6 || row < 3 && col > 5
				|| row > 5 && col > 5) {
			return true;
		}
		return false;
	}

}
