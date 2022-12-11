import java.util.*;

public class Day23P2 {

public static HashMap<String, Integer> cache = new HashMap<String, Integer>();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner s = new Scanner(System.in);

		Space2 start = new Space2();

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
		
		for(int i = 3; i <= 4; i++) {
			for(int j = 0; j < start.values[i].length; j++) {
				start.values[i][j] = '#';
			}
		}
		
		for(int j = 2; j < start.values[0].length; j += 2) {
			start.values[4][j] = start.values[2][j];
		}
		
		start.values[2][2] = 'D';
		start.values[3][2] = 'D';
		start.values[2][4] = 'C';
		start.values[3][4] = 'B';
		start.values[2][6] = 'B';
		start.values[3][6] = 'A';
		start.values[2][8] = 'A';
		start.values[3][8] = 'C';
		
		System.out.println(cost(start));


	}
	
	public static int cost(Space2 s) {
		if(cache.containsKey(s.stringify())) {
			return cache.get(s.stringify());
		}
		
		if(s.isWinning()) {
			cache.put(s.stringify(), 0);
			return 0;
		}
		ArrayList<Space2> connections = s.moves();
		
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
