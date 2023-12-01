import java.util.*;

public class Day9P1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner s = new Scanner(System.in);
		
		HashMap<String, Place> places = new HashMap<String, Place>();
		
		String str = s.nextLine().trim();
		
		while(str.length() > 0) {
			String[] parts = str.split(" ");
			
			places.putIfAbsent(parts[0], new Place(parts[0]));
			places.putIfAbsent(parts[2], new Place(parts[2]));
			
			places.get(parts[0]).addConnection(places.get(parts[2]), Integer.parseInt(parts[4]));
			places.get(parts[2]).addConnection(places.get(parts[0]), Integer.parseInt(parts[4]));
			
			str = s.nextLine().trim();
		}
		
		ArrayList<Place> p = new ArrayList<Place>();
		
		for(Place pl : places.values()) {
			p.add(pl);
		}
		
		int min = Integer.MAX_VALUE;
		
		for(Place pl : p) {
			ArrayList<Place> copy = new ArrayList<Place>(p);
			
			copy.remove(pl);
			
			int d = distance(copy, pl);
			if(d < min) {
				min = d;
			}
		}
		
		System.out.println(min);
	}

	public static int distance(ArrayList<Place> p, Place start) {
		if(p.size() == 0) {
			return 0;
		}
		
		int min = Integer.MAX_VALUE;
		
		for(Place place : p) {
			
			ArrayList<Place> copy = new ArrayList<Place>(p);
			
			int distance = start.getConnection(place).distance;
			
			copy.remove(place);
			
			distance += distance(copy, place);
			
			if(distance < min) {
				min = distance;
			}
		}
		
		return min;
	}
	
}
