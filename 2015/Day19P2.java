import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class Day19P2 {

	public static HashMap<String, String> changes = new HashMap<>();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner s = new Scanner(System.in);

		HashSet<String> set = new HashSet<>();

		String str = s.nextLine().trim();

		while(str.length() > 0) {
			String[] parts = str.split(" => ");
			changes.put(parts[1], parts[0]);

			str = s.nextLine().trim();
		}

		str = s.nextLine().trim();

		HashSet<String> used = new HashSet<>();

		ArrayList<Node> unused = new ArrayList<>();

		Node start = new Node(str);
		start.distance = 0;

		Node current = start;
		unused.add(current);

		String starts = str;
		
		str = "e";
		
		while(current.distance != Integer.MAX_VALUE && !used.contains(str) && !current.s.equals(str)) {
			for(String c : connections(current.s)) {
				if(!used.contains(c)) {
					if(!c.equals(starts) && c.length() >= starts.length()) {
						continue;
					}
					if(!unused.contains(new Node(c))) {
						unused.add(new Node(c));
					}
					int v = current.distance+1;
					if(v < unused.get(getIndex(unused, c)).distance) {
						unused.get(getIndex(unused, c)).distance = v;
					}
				}
			}
			used.add(current.s);

			unused.remove(current);

			Node smallest = unused.get(0);
			for(Node n : unused) {
				if(n.distance + n.s.length() < smallest.distance + smallest.s.length() && n.distance != Integer.MAX_VALUE) {
					smallest = n;
				}
			}
			current = smallest;
			//System.out.println(current.distance + " " + current.s.length());
		}

		System.out.println(current.distance);
	}

	private static class Node {
		String s = "";
		int distance = Integer.MAX_VALUE;
		boolean used = false;

		public Node(String s) {
			this.s = s;
		}

		public boolean equals(Node other) {
			return s.equals(other.s);
		}

		public String toString() {
			return s;
		}
	}

	public static HashSet<String> connections(String s) {
		HashSet<String> set = new HashSet<>();

		for(String key : changes.keySet()) {
			int index = s.indexOf(key);
			while(index >= 0) {
				String change = changes.get(key);
				set.add(s.substring(0, index) + change + s.substring(index+key.length()));

				index = s.indexOf(key, index+1);
			}
		}

		return set;
	}

	public static int getIndex(ArrayList<Node> l, String s) {
		for(int i = 0; i < l.size(); i++) {
			if(l.get(i).s.equals(s)) {
				return i;
			}
		}
		return -1;
	}
}
