public class Page {

	private int start;
	private int end;
	private int page;

	public Page(int page, int start, int end) {
		super();
		this.page = page;
		this.start = start;
		this.end = end;
	}

	public int getPage() {
		return page;
	}

	public int getStart() {
		return start;
	}

	public int getEnd() {
		return end;
	}
}
