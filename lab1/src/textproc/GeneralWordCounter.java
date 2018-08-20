vim: syntax=java
package textproc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * The {@code GeneralWordCounter} class analyzes words from a text-file and
 * sorts the words in frequency order.
 * 
 * @author Eneas Hållsten
 *
 */
public class GeneralWordCounter implements TextProcessor {
	Set<String> stopwords = new HashSet<String>(); // Set (dubbletter förbjudna) för alla ord som ej skall räknas
	Map<String, Integer> m = new TreeMap<String, Integer>(); // Map (lexikon) med nyckel-värde-par för alla ord som
																// räknas

	/**
	 * Constructs a GeneralWordCounter with every unique word from the Set
	 * stopwords.
	 * 
	 * @param stopwords
	 */
	public GeneralWordCounter(Set<String> stopwords) {
		this.stopwords.addAll(stopwords);
	}

	public GeneralWordCounter() { // Till labb 3
	}

	/**
	 * Returns a Set view of the Map.
	 * 
	 * @return Set with every unique word
	 */
	public Set<Map.Entry<String, Integer>> getWords() { // Till labb 3
		return m.entrySet();
	}

	/**
	 * Clear the Map from elements.
	 */
	public void resetWords() { // Till labb 3
		m.clear();
	}

	/**
	 * Puts the word (key) in the list and increases the value of the word (key). If
	 * stopwords conatins the word, dont add.
	 */
	public void process(String w) {
		if (!stopwords.contains(w)) { // Om ordet skall räknas
			try { // Om ordet redan har räknats tidigare
				m.put(w, m.get(w).intValue() + 1);
			} catch (NullPointerException e) {
				m.put(w, 1); // Om ordet räknas för första gången
			}
		}
	}

	/**
	 * Sorts the words in increasing frequency order and prints the 5 first elements
	 * in the list.
	 */
	public void report() {
		/**
		 * for (String key : m.keySet()) { if (m.get(key).intValue() > 200) {
		 * System.out.println(key + " " + m.get(key)); } }
		 */
		Set<Map.Entry<String, Integer>> wordSet = m.entrySet();
		List<Map.Entry<String, Integer>> wordList = new ArrayList<>(wordSet);
		wordList.sort(new WordCountComparator());
		for (int i = 0; i < 5; i++) {
			System.out.println(wordList.get(i));
		}
	}

}
