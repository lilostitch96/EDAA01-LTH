package textproc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Holgersson {
	private static Set<String> stopwords = new HashSet<String>();

	public static final String[] REGIONS = { "blekinge", "bohuslän", "dalarna", "dalsland", "gotland", "gästrikland",
			"halland", "hälsingland", "härjedalen", "jämtland", "lappland", "medelpad", "närke", "skåne", "småland",
			"södermanland", "uppland", "värmland", "västerbotten", "västergötland", "västmanland", "ångermanland",
			"öland", "östergötland" };

	public static void main(String[] args) throws FileNotFoundException {

		long t0 = System.nanoTime(); // Mäter tid i nanosekunder
		scanStopwordsFile(); 
		Scanner s = new Scanner(new File("nilsholg.txt"));
		s.useDelimiter("(\\s|,|\\.|:|;|!|\\?|'|\\\")+"); // se handledning
		s.close();
		List<TextProcessor> pList = new ArrayList<TextProcessor>(); // Typ TextProcessor så vi sedan kan använda process
																	// och report
		// pList.add(new SingleWordCounter("nils"));
		// pList.add(new SingleWordCounter("norge"));
		// pList.add(new MultiWordCounter(REGIONS));
		pList.add(new GeneralWordCounter(stopwords));
		while (s.hasNext()) {
			String word = s.next().toLowerCase();
			for (TextProcessor tp : pList) {
				tp.process(word); 
			}
		}
		for (TextProcessor tp : pList) {
			tp.report(); 
		}
		long t1 = System.nanoTime();

		System.out.println();
		System.out.println("Tid: " + (t1 - t0) / 1000000 + " ms");
	}
	
	private static void scanStopwordsFile() throws FileNotFoundException {
		Scanner scan = new Scanner(new File("undantagsord.txt"));
		while (scan.hasNext()) {
			stopwords.add(scan.next());
		}
		scan.close();
	}

}