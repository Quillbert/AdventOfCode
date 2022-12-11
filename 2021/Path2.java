import java.util.ArrayList;

public class Path2 {
	public boolean ends;
	public ArrayList<Path2> paths;
	public ArrayList<Room> previous;
	public Room start;
	public Path2(Room s) {
		start = s;
		ends = start.name.equals("end");
		paths = new ArrayList<Path2>();
		previous = new ArrayList<Room>();
		previous.add(start);
	}
	
	public Path2(Room s, ArrayList<Room> p) {
		start = s;
		ends = start.name.equals("end");
		paths = new ArrayList<Path2>();
		previous = new ArrayList<Room>(p);
		previous.add(start);
	}
	public void generate() {
		if(ends) {
			Day12P2.count++;
			return;
		}
		boolean bad = true;
		for(int i = 0; i < start.connections.size(); i++) {
			String name = start.connections.get(i).name;
			if(start.connections.get(i).big || !previous.contains(start.connections.get(i)) || (!doubleVisited(previous) && !name.equals("start") && !name.equals("end"))) {
				bad = false;
				paths.add(new Path2(start.connections.get(i), previous));
			}
		}
		if(bad) return;
		for(Path2 p : paths) {
			p.generate();
			if(p.ends) {
				ends = true;
			}
		}
	}
	
	private boolean doubleVisited(ArrayList<Room> p) {
		for(int i = 0; i < p.size(); i++) {
			if(p.get(i).big) continue;
			for(int j = i+1; j < p.size(); j++) {
				if(p.get(i).equals(p.get(j))) {
					return true;
				}
			}
		}
		return false;
	}
}
