
public class Word implements Comparable<Word> {

	private final String word;
	private final WordType type;
	private int count;

	public Word(String word, WordType type) {
		super();
		this.word = word;
		this.type = type;
	}
	
	public String getWord() {
		return word;
	}
	
	public WordType getType() {
		return type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((word == null) ? 0 : word.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Word other = (Word) obj;
		if (type != other.type)
			return false;
		if (word == null) {
			if (other.word != null)
				return false;
		} else if (!word.equals(other.word))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return word + ' ' + type;
	}

	@Override
	public int compareTo(Word o) {
		return word.compareTo(o.word);
	}
	
	public int getCount() {
		return count;
	}
	
	public void incrementCount() {
		count++;
	}
}