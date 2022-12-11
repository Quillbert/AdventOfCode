import java.util.*;

public class Node implements Comparable<Node> {
	public ArrayList<Node> path;
	public ArrayList<Node> connections;
	public int value;
	public int shortest = Integer.MAX_VALUE;
	public boolean used;
	public Node(int v) {
		value = v;
		used = false;
		path = new ArrayList<Node>();
		connections = new ArrayList<Node>();
	}
	
	public void addConnection(Node other) {
		if(connections.contains(other)) return;
		connections.add(other);
		other.connections.add(this);
	}

	@Override
	public int compareTo(Node arg0) {
		// TODO Auto-generated method stub
		return shortest-arg0.shortest;
	}
}
