import java.util.*;

public class Day1P2 {
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int count = 0;
		ArrayList<Integer> depths = new ArrayList<Integer>();
		while(s.hasNext()) {
			int n = s.nextInt();
			if(n < 0) {
				break;
			} else {
				depths.add(n);
			}
		}
		for(int i = 3; i < depths.size(); i++) {
			int oldsum = depths.get(i-3) + depths.get(i-2) + depths.get(i-1);
			int newsum = depths.get(i-2) + depths.get(i-1) + depths.get(i);
			if(newsum > oldsum) count++;
		}
		
		System.out.println(count);
	}
}
