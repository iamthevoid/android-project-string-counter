import java.util.*;
import java.util.regex.*;

public class GradleNode {

	private static Pattern space = Pattern.compile("\\s");
	private static Pattern letter = Pattern.compile("\\w");

	private String title;
	private List<GradleNode> nodes = new ArrayList<>();

	public GradleNode(String name, String data) {
		this.title = name.trim();

		StringBuilder sb = new StringBuilder(); 

		for (int i = 0, l = data.length(); i < l; i++) {
			String s = String.valueOf(data.charAt(i));
			
			sb.append(s);
			

			if (space.matcher(s).matches()) {

				
				String next = i == l - 1 ? "" : String.valueOf(data.charAt(i + 1));



				if ("{".equals(String.valueOf(next))) {
					String nodeName = sb.toString();

					int startIndex = i;
					int bracesCount = 1;
					i ++;

					StringBuilder containsStringBuilder = new StringBuilder("{");
					while (bracesCount != 0) {
						i++;
						String currentSymbol = String.valueOf(data.charAt(i));
						
						if ("{".equals(currentSymbol)) {
							bracesCount++;
						} else if ("}".equals(currentSymbol)) {
							bracesCount--;
						}

						containsStringBuilder.append(currentSymbol);
					}
					nodes.add(new GradleNode(nodeName, containsStringBuilder.toString()));
				} 

				sb.delete(0, sb.length());
			} 
		}
	}

	public List<String> childNames() {
		List<String> names = new ArrayList<>();
		for (GradleNode gn : nodes) {
			names.add(gn.title);
		}
		return names;
	}

	public void print() {
		System.out.println(title);
		for (GradleNode gn : nodes) {
			gn.print();
		}
	}

	public GradleNode byName(String name) {
		if (title.equals(name)) {
			return this;
		}

		GradleNode gradleNode;

		for (GradleNode gn : nodes) {
			gradleNode = gn.byName(name);
			if (gradleNode !=null) {
				return gradleNode;
			}
		}

		return null;
	}
}