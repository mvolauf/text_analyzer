
public class WordInfo {

	private int firstPage;
	private int count;
	
	public WordInfo(int firstPage) {
		this.firstPage = firstPage;
	}
	
	public void incrementCount() {
		count++;
	}
	
	public int getFirstPage() {
		return firstPage;
	}
	
	public int getCount() {
		return count;
	}
}
