package textproc;

import java.util.Map;
import java.util.TreeMap;

/**
 * A Class that counts how many times a word is present in a String array.
 * 
 * @author Eneas Hållsten
 *
 */
public class MultiWordCounter implements TextProcessor {
	Map<String, Integer> m;

	public MultiWordCounter(String[] landscape) {
		m = new TreeMap<String, Integer>();
		for (String s : landscape) {
			m.put(s, 0);
		}
	}

	public void process(String w) {
		if (m.containsKey(w)) {
			m.put(w, m.get(w).intValue() + 1); // Öka antalet förekomster med 1
		}
	}

	public void report() {
		for (String key : m.keySet()) {
			System.out.println(key + " " + m.get(key));
		}
	}
}
