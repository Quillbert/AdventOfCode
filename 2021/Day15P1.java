import java.util.*;

public class Day15P1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner s = new Scanner(System.in);
		ArrayList<String> lines = new ArrayList<String>();
		String str = s.nextLine().trim();
		while(str.length() > 0) {
			lines.add(str);
			str = s.nextLine().trim();
		}

		Node[][] nodes = new Node[lines.size()][lines.get(0).length()];
		for(int i = 0; i < nodes.length; i++) {
			for(int j = 0; j < nodes[0].length; j++) {
				nodes[i][j] = new Node(Integer.parseInt(lines.get(i).substring(j, j+1)));
			}
		}

		for(int i = 0; i < nodes.length; i++) {
			for(int j = 0; j < nodes[0].length; j++) {
				if(i < nodes.length-1) {
					nodes[i][j].addConnection(nodes[i+1][j]);
				}
				if(j < nodes[0].length-1) {
					nodes[i][j].addConnection(nodes[i][j+1]);
				}
			}
		}

		LinkedList<Node> unused = new LinkedList<Node>();
		for(int i = 0; i < nodes.length; i++) {
			for(int j = 0; j < nodes[i].length; j++) {
				unused.add(nodes[i][j]);
			}
		}


		Node start = nodes[0][0];
		start.shortest = 0;

		Node end = nodes[nodes.length-1][nodes[0].length-1];

		Node current = start;
		while(current.shortest != Integer.MAX_VALUE && !end.used && !current.equals(end)) {
			for(int i = 0; i < current.connections.size(); i++) {
				if(!current.connections.get(i).used) {
					int v = current.shortest + current.connections.get(i).value;
					if(v < current.connections.get(i).shortest) {
						current.connections.get(i).shortest = v;
					}
				}
			}
			current.used = true;
			unused.remove(current);
			
			Node smallest = unused.get(0);
			for(Node n : unused) {
				if(n.shortest < smallest.shortest) {
					smallest = n;
				}
			}
			current = smallest;
			
		}
		
		System.out.println(end.shortest);

	}

}
