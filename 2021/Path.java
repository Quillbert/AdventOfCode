import java.util.*;

public class Path {
	public boolean ends;
	public ArrayList<Path> paths;
	public ArrayList<Room> previous;
	public Room start;
	public Path(Room s) {
		start = s;
		ends = start.name.equals("end");
		paths = new ArrayList<Path>();
		previous = new ArrayList<Room>();
		previous.add(start);
	}
	
	public Path(Room s, ArrayList<Room> p) {
		start = s;
		ends = start.name.equals("end");
		paths = new ArrayList<Path>();
		previous = new ArrayList<Room>(p);
		previous.add(start);
	}
	public void generate() {
		if(ends) {
			Day12P1.count++;
			return;
		}
		boolean bad = true;
		for(int i = 0; i < start.connections.size(); i++) {
			if(start.connections.get(i).big || !previous.contains(start.connections.get(i))) {
				bad = false;
				paths.add(new Path(start.connections.get(i), previous));
			}
		}
		if(bad) return;
		for(Path p : paths) {
			p.generate();
			if(p.ends) {
				ends = true;
			}
		}
	}
}
