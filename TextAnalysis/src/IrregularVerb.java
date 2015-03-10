import java.util.ArrayList;
import java.util.List;

public class IrregularVerb {

	private String base;
	private List<String> pasts;
	private List<String> pps;

	public IrregularVerb(String base, String past, String pp) {
		this.base = base;
		this.pasts = create(past);
		this.pps = create(pp);
	}

	private static List<String> create(String source) {
		List<String> list = new ArrayList<String>();
		for (String part : source.split("/")) {
			String s = part.trim();
			if (!s.isEmpty()) {
				list.add(s);
			}
		}
		return list;
	}

	public String getBase() {
		return base;
	}

	public boolean isPastForm(String wordText) {
		return pasts.contains(wordText.trim());
	}

	public boolean isPpForm(String wordText) {
		return pps.contains(wordText.trim());
	}
}
