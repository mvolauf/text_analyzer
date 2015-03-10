import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextAnalysis {

	private static final Set<String> skip = new HashSet<String>(
		Arrays.asList("the", "and",
			"but", "that", "you", "for", "with", "her", "they",
			"can", "was", "not", "from", "his", "she", "one", "this"));

//	private static final Pattern PAGE_PATTERN = Pattern.compile("\\[page\\s*(\\d+)\\]");
//	private static final Pattern WORD_PATTERN = Pattern.compile("([a-zA-Z\\+\\-]+)\\[([a-z]+)\\]");
	private static final Pattern PAGE_PATTERN = Pattern.compile("page(\\d+)_");
	private static final Pattern WORD_PATTERN = Pattern.compile("([a-zA-Z\\+\\-]+)_([A-Z]+)");

	public static void main(String[] args) throws Exception {
		StringBuilder sb = new StringBuilder();

		// File file = new File("sample.txt");
		// File file = new File("C:\\Users\\Milos Volauf\\Documents\\matka\\studium_aj\\materials\\magisterske\\diplomovka\\My DP\\hunger_games_analysis.txt");
		// File file = new File("C:\\Users\\Milos Volauf\\Documents\\matka\\studium_aj\\materials\\magisterske\\diplomovka\\My DP\\hunger_games_analysis_source_maxent_tagger.txt");
		File file = new File("hunger_games_claws.txt");
		FileReader reader = new FileReader(file);
		BufferedReader bufferedReader = new BufferedReader(reader);
		String line = bufferedReader.readLine();
		while (line != null) {
			sb.append(line);
			sb.append('\n');
			line = bufferedReader.readLine();
		}
		bufferedReader.close();

		String text = sb.toString();
		TextAnalysis obj = new TextAnalysis(text);
		obj.run();
	}

	private final String text;
	private List<Page> pages = new ArrayList<Page>();
	private Map<Word, WordInfo> infoMap = new HashMap<Word, WordInfo>();
	private List<IrregularVerb> verbs = new ArrayList<IrregularVerb>();

	public TextAnalysis(String text) throws Exception {
		this.text = text;
		readIrregularVerbs();
	}

	private void readIrregularVerbs() throws Exception {
		BufferedReader bufferedReader = new BufferedReader(new FileReader("irregular_verbs.txt"));
		String line;
		while ((line = bufferedReader.readLine()) != null) {
			line = line.replace("\"", "");
			String[] parts = line.split(",");
			verbs.add(new IrregularVerb(parts[0], parts[1], parts[2]));
		}
		bufferedReader.close();
	}

	public void run() throws Exception {

		createPages();

		for (Page page : pages) {
			processPage(page);
		}

		List<Word> words = new ArrayList<Word>(infoMap.keySet());
		Collections.sort(words);

		// File file = new File("C:\\Users\\Milos Volauf\\Documents\\matka\\studium_aj\\materials\\magisterske\\diplomovka\\My DP\\hunger_games_analysis_output.csv");
		File file = new File("output.txt");
		PrintStream ps = new PrintStream(file);
		for (Word word : words) {
			WordInfo info = infoMap.get(word);
			//System.out.println(word + " " + info.getFirstPage() + " " + info.getCount());
			String sep = "\t";
			ps.println(word.getWord() + sep + word.getType() + sep + info.getFirstPage() + sep + info.getCount());
		}
		ps.close();
		//System.out.println();
		//System.out.println("TOTAL: " + words.size());

		//printAllWordStat();
	}

	private void processPage(Page page) {
		String pageText = text.substring(page.getStart(), page.getEnd());
		Matcher matcher = WORD_PATTERN.matcher(pageText);
		while (matcher.find()) {
			String wordText = matcher.group(1);
			wordText = wordText.toLowerCase();
			wordText = wordText.replace('+', ' ');
			String tag = matcher.group(2).toUpperCase();
			
			WordType type = null;
			//type = WordType.fromTag(tag);
			
			switch (tag) {
			case "VVB":
			case "VVI":
				type = WordType.V;
				break;
			case "VVD":
				type = WordType.V;
				wordText = vvd(wordText);
				break;
			case "VVG":
				type = WordType.V;
				if (wordText.endsWith("ing")) {
					wordText = wordText.substring(0, wordText.length() - 3);
				}
				break;
			case "VVN":
				type = WordType.V;
				wordText = vvn(wordText);
				break;
			case "VVZ":
				type = WordType.V;
				if (wordText.endsWith("s")) {
					wordText = wordText.substring(0, wordText.length() - 1);
				}
				break;
			}
			
			if (type != null) {
				Word word = new Word(wordText, type);
				WordInfo info = infoMap.get(word);
				if (info == null) {
					info = new WordInfo(page.getPage());
					infoMap.put(word, info);
				}
				info.incrementCount();
			}
		}
	}

	private String vvd(String wordText) {
		// TODO Auto-generated method stub
		return wordText;
	}

	private String vvn(String wordText) {
		// TODO Auto-generated method stub
		return wordText;
	}

	private void createPages() {
		int last = -1;
		int lastStart = -1;
		Matcher matcher = PAGE_PATTERN.matcher(text);
		while (matcher.find()) {
			int page = Integer.parseInt(matcher.group(1));
			if (last != -1 && page != last + 1) {
				throw new RuntimeException("duplicate page " + page);
			}
			int pageStart = matcher.start();
			if (last != -1) {
				Page p = new Page(last, lastStart, pageStart);
				pages.add(p);
			}
			last = page;
			lastStart = pageStart;
		}
		if (last != -1) {
			Page p = new Page(last, lastStart, text.length());
			pages.add(p);
		}
	}

	@SuppressWarnings("unused")
	private void printAllWordStat() {
		
		Map<String, Word> allWords = new HashMap<>();
		Matcher m = Pattern.compile("[a-zA-Z]+").matcher(text);
		while (m.find()) {
			String wordText = m.group().toLowerCase();
			if (wordText.length() > 2 && !skip.contains(wordText)) {
				Word word = allWords.get(wordText);
				if (word == null) {
					word = new Word(wordText, null);
					allWords.put(wordText, word);
				}
				word.incrementCount();
			}
		}
		List<Word> list = new ArrayList<Word>(allWords.values());
		Collections.sort(list, new Comparator<Word>() {

			@Override
			public int compare(Word o1, Word o2) {
				return -Integer.compare(o1.getCount(), o2.getCount());
			}
		});
		for (int i = 0; i < 100; i++) {
			Word word = list.get(i);
			System.out.println(word.getWord() + " " + word.getCount());
		}
		
		System.out.println("ALL WORDS TOTAL: " + allWords.size());
	}

}
