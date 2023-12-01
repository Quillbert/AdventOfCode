import java.util.*;

public class Place {
	String name = "";
	ArrayList<Connection> connections = new ArrayList<Connection>();
	
	public Place(String n) {
		name = n;
	}
	
	public void addConnection(Place t, int d) {
		connections.add(new Connection(this, t, d));
	}
	
	public Connection getConnection(Place t) {
		for(Connection c : connections) {
			if(c.to.equals(t)) {
				return c;
			}
		}
		return null;
	}
	
	public boolean equals(Place other) {
		return other.name.equals(name);
	}
}
