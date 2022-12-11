import java.util.*;

public class Day15P2 {

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
		
		Node[][] big = new Node[nodes.length*5][nodes[0].length*5];
		for(int i = 0; i < big.length; i++) {
			for(int j = 0; j < big[0].length; j++) {
				int v = (nodes[i % nodes.length][j % nodes[0].length].value + (i/nodes.length+j/nodes[0].length));
				if(v > 9) {
					v -= 9;
				}
				big[i][j] = new Node(v);
			}
		}

		/*for(int i = 0; i < big.length; i++) {
			for(int j = 0; j < big[0].length; j++) {
				System.out.print(big[i][j].value);
			}
			System.out.println();
		}*/
		
		for(int i = 0; i < big.length; i++) {
			for(int j = 0; j < big[0].length; j++) {
				if(i < big.length-1) {
					big[i][j].addConnection(big[i+1][j]);
				}
				if(j < big[0].length-1) {
					big[i][j].addConnection(big[i][j+1]);
				}
			}
		}

		//PriorityQueue<Node> unused = new PriorityQueue<Node>();
		LinkedList<Node> unused = new LinkedList<Node>();
		/*for(int i = 0; i < big.length; i++) {
			for(int j = 0; j < big[i].length; j++) {
				unused.add(big[i][j]);
			}
		}*/


		Node start = big[0][0];
		start.shortest = 0;

		Node end = big[big.length-1][big[0].length-1];

		Node current = start;
		unused.add(start);
		
		while(current.shortest != Integer.MAX_VALUE && !end.used && !current.equals(end)) {
			for(int i = 0; i < current.connections.size(); i++) {
				if(!current.connections.get(i).used) {
					if(!unused.contains(current.connections.get(i))) {
						unused.add(current.connections.get(i));
					}
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
