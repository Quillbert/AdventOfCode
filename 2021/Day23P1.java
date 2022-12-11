import java.util.*;

public class Day23P1 {

	public static HashMap<String, Integer> cache = new HashMap<String, Integer>();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner s = new Scanner(System.in);

		Space start = new Space();

		for(int i = 0; i < 4; i++) {
			String str = s.nextLine();
			if(i == 0) {
				continue;
			}
			str = str.substring(1);
			for(int j = 0; j < start.values[i-1].length; j++) {
				if(j < str.length()) {
					start.values[i-1][j] = str.charAt(j) == ' ' ? '#' : str.charAt(j);
				} else {
					start.values[i-1][j] = '#';
				}
			}
		}


		/*HashMap<String, Integer> all = new HashMap<String, Integer>();
		HashMap<String, Boolean> used = new HashMap<String, Boolean>();

		ArrayList<Space> unused = new ArrayList<Space>();

		all.put(start.stringify(), 0);

		Space current = start;

		while(!current.isWinning()) {
			//System.out.println(all.get(current.stringify()));
			ArrayList<Space> connections = current.moves();
			for(int i = 0; i < connections.size(); i++) {
				if(!used.containsKey(connections.get(i).stringify())) {
					if(!unused.contains(connections.get(i))) {
						unused.add(connections.get(i));
					}
					int v = all.get(current.stringify()) + current.cost(connections.get(i));
					if(all.containsKey(connections.get(i).stringify())) {
						if(v < all.get(connections.get(i).stringify())) {
							all.put(connections.get(i).stringify(), v);
						}
					} else {
						all.put(connections.get(i).stringify(), v);
					}
				}
			}

			used.put(current.stringify(), true);

			unused.remove(current);

			Space smallest = unused.get(0);
			for(Space sp : unused) {
				if(all.get(sp.stringify()) < all.get(smallest.stringify())) {
					smallest = sp;
				}
			}
			current = smallest;
		}

		System.out.println(all.get(current.stringify()));*/
		
		System.out.println(cost(start));


	}
	
	public static int cost(Space s) {
		if(cache.containsKey(s.stringify())) {
			return cache.get(s.stringify());
		}
		
		if(s.isWinning()) {
			cache.put(s.stringify(), 0);
			return 0;
		}
		ArrayList<Space> connections = s.moves();
		
		if(connections.size() == 0) {
			cache.put(s.stringify(), 99999999);
			return 99999999;
		}
		
		int min = 99999999;
		
		for(int i = 0; i < connections.size(); i++) {
			int value = cost(connections.get(i));
			if(value + s.cost(connections.get(i)) < min) {
				min = value + s.cost(connections.get(i));
			}
		}
		
		cache.put(s.stringify(), min);
		return min;
	}

}
