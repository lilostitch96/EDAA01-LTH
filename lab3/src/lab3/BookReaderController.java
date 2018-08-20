package lab3;

import java.io.File;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import textproc.GeneralWordCounter;

/**
 * The {@code BookReaderController} represents a program that reads a text file
 * and analyses the frequency of words in the file. The user can chose to sort
 * the words alphabeticaly or in frequency (decreasing) and also search for a
 * specific word.
 * 
 * @author Eneas Hållsten
 *
 */
public class BookReaderController extends Application {
	private GeneralWordCounter counter = new GeneralWordCounter();
	private ObservableList<Map.Entry<String, Integer>> words;
	private ListView<Map.Entry<String, Integer>> listView;
	private BorderPane root = new BorderPane();

	@Override
	public void start(Stage primaryStage) throws Exception {

		words = FXCollections.observableArrayList(counter.getWords()); // listan börjas tom
		listView = new ListView<Map.Entry<String, Integer>>(words);
		root.setCenter(listView);

		ToggleGroup group = new ToggleGroup(); // Grupperings-objekt för det två sorteringsknaparna så endast en kan
												// vara vald vid en tidpunkt
		TextField tf = new TextField(); // Sökfältet
		Button find = new Button("Find");
		Button browseButton = new Button("Browse"); // Knapp till browse för att öppna ny fil
		RadioButton fr = new RadioButton("Frequency");
		RadioButton al = new RadioButton("Alpabetic"); // RadioButton är sådan som endast en av alla i gruppen kan vara
														// vald vid en tidpunkt

		al.setOnAction(action -> { // Alpabethic, När tryckt, sortera och scrolla till toppen
			words.sort((w1, w2) -> w1.getKey().compareTo(w2.getKey()));
			listView.scrollTo(0);
		});
		al.setSelected(true); // Sätt denna knapp vald till att börja med
		al.setToggleGroup(group); // Gruppera.

		fr.setOnAction(action -> { // Frequency, När tryckt, sortera och scrolla till toppen
			words.sort((w1, w2) -> w2.getValue() - w1.getValue()); // Sjunkarnde ordning
			listView.scrollTo(0);
		});
		fr.setToggleGroup(group);

		find.setDefaultButton(true); // Vid tryck av knappen enter aktiveras setOnAction-metoden
		find.setOnAction(action -> { // när Find-knappen trycks utförs detta labmda-uttryck
			for (int i = 0; i < words.size(); i++) { // Sök igenom hela listan
				if (words.get(i).getKey().equalsIgnoreCase(tf.getText().trim())) { // Om vi får en match, ignorera
																					// stora/små bokstäver
					listView.scrollTo(i - 9); // Scrolla så det sökta ordet hamnar typ i mitten av synfältet
					listView.getSelectionModel().select(i); // Highlighta det sökta ordet
					tf.setText(""); // Ta bort den sökta texten
					return;
				}
			}
			Alert alert = new Alert(AlertType.WARNING,
					"Can not find the word: " + tf.getText() + "\n" + "Please try again!"); // Om inte det sökta ordet
																							// finns med i listan
			alert.showAndWait(); // Visar en ruta med fel-meddelandet
			tf.setText(""); // Raderar text efter felskrivning
		});

		browseButton.setOnAction(action -> { // Lambdauttryck när browse aktiveras
			try {
				counter.resetWords(); // Återställ listan så de gamla ej finns kvar till nästa
				al.setSelected(true);
				readFile();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		HBox hbox = new HBox(al, fr, tf, find, browseButton); // Lägger till alla knappar och textfält i en horisontell
																// HBox
		hbox.setSpacing(5); // Avstånd mellan children
		hbox.setPadding(new Insets(12, 10, 10, 10));
		HBox.setHgrow(tf, Priority.ALWAYS); // Prioriterar att textfältet ändrar storlek när fönstrets storlek ändras
		root.setBottom(hbox); // Placera på botten av vyn

		Scene scene = new Scene(root, 500, 500); // Skapar ett Scene-objekt med upplägget från root
		primaryStage.setTitle("BookReader"); // Titel
		primaryStage.setScene(scene);
		primaryStage.show(); // Visa fönstret

	}

	/**
	 * Reads the selected file into an FileChooser, returns a Map of every word in
	 * the file with a counted frequency.
	 * 
	 * @return <Map.Entry<String, Integer>
	 */
	private Set<Map.Entry<String, Integer>> readFile() throws Exception {
		root.setCenter(null); // sudda bort gamla inläsing
		FileChooser fc = new FileChooser();
		fc.setTitle("Choose file");
		fc.getExtensionFilters().add(new ExtensionFilter(".txt ", "*.txt"));

		File file = fc.showOpenDialog(root.getScene().getWindow()); // Öpnnar inläsningnsfönster
		if (file == null) {
			return null;
		}
		return fileToString(file);

	}

	/**
	 * Processing the file into an Map.
	 * 
	 * @param file
	 * @return Map<String, Integer>
	 */
	private Set<Map.Entry<String, Integer>> fileToString(File file) throws Exception {

		Scanner s = new Scanner(file);
		s.useDelimiter("(\\s|,|\\.|:|;|!|\\?|'|\\\")+"); // se handledning

		while (s.hasNext()) {
			String st = s.next().toLowerCase();
			counter.process(st);
		}
		s.close();

		words = FXCollections.observableArrayList(counter.getWords()); // Uppdatera listan
		listView = new ListView<Map.Entry<String, Integer>>(words);
		root.setCenter(listView); // Uppdatera vyn
		return counter.getWords();
	}

	public static void main(String[] args) {
		Application.launch(args);
	}
}
