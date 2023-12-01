import java.util.*;

public class Day14P1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner s = new Scanner(System.in);
		
		ArrayList<Reindeer> reindeer = new ArrayList<Reindeer>();
		
		String str = s.nextLine().trim();
		
		while(str.length() > 0) {
			String[] parts = str.split(" ");
			
			Reindeer r = new Reindeer(Integer.parseInt(parts[3]), Integer.parseInt(parts[6]), Integer.parseInt(parts[13]));
			
			reindeer.add(r);
			
			str = s.nextLine().trim();
		}
		
		for(int i = 0; i < 2503; i++) {
			for(Reindeer r : reindeer) {
				r.move();
			}
		}
		
		int max = 0;
		for(Reindeer r : reindeer) {
			if(r.distance > max) {
				max = r.distance;
			}
		}
		
		System.out.println(max);
	}

}
