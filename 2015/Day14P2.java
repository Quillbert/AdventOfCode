import java.util.ArrayList;
import java.util.Scanner;

public class Day14P2 {

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
		
		int[] count = new int[reindeer.size()];
		
		for(int i = 0; i < 2503; i++) {
			for(Reindeer r : reindeer) {
				r.move();
			}
			
			int max = 0;
			int maxIndex = 0;
			
			for(int j = 0; j < reindeer.size(); j++) {
				if(reindeer.get(j).distance > max) {
					max = reindeer.get(j).distance;
					maxIndex = j;
				}
			}
			count[maxIndex]++;
		}
		
		int max = 0;
		for(int r : count) {
			if(r > max) {
				max = r;
			}
		}
		
		System.out.println(max);
	}

}
