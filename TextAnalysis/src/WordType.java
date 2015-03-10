import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public enum WordType {

	N("NN", "NNS"),
	V("VB", "VBD", "VBG", "VBN", "VBP", "VBZ"),
	ADJ("JJ", "JJR", "JJS"),
	ADV("RB", "RBR", "RBS", "WRB"),

	;
	
	public static WordType fromTag(String tag) {
		for (WordType t : WordType.values()) {
			if (t.tags.contains(tag)) {
				return t;
			}
		}
		return null;
	}

	private Set<String> tags;

	private WordType(String... tags) {
		this.tags = new HashSet<>(Arrays.asList(tags));
	}
	public String toString() {
		return super.toString().toLowerCase();
	}

}
