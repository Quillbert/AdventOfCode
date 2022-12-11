import java.util.*;

public class Room {
	public String name;
	public ArrayList<Room> connections;
	public boolean big;
	public Room(String name) {
		this.name = name;
		this.connections = new ArrayList<Room>();
		big = Character.isUpperCase(name.charAt(0));
	}
	public void addConnection(Room r) {
		connections.add(r);
		r.connections.add(this);
	}
}
