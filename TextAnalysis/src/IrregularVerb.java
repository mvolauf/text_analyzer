import java.util.Arrays;
import java.util.List;


public class IrregularVerb {

	private String base;
	private List<String> pasts;
	private List<String> pps;

	public IrregularVerb(String base, String past, String pp) {
		this.base = base;
		this.pasts = Arrays.asList(past.split("/"));
		this.pps = Arrays.asList(pp.split("/"));
	}

	public String getBase() {
		return base;
	}
}
