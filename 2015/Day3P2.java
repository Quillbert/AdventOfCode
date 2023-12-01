import java.util.HashSet;
import java.util.Scanner;

public class Day3P2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner s = new Scanner(System.in);
		
		HashSet<String> points = new HashSet<String>();
		
		points.add("0,0");
		
		int[] x = {0, 0};
		int[] y = {0, 0};
		
		String str = s.nextLine().trim();
		
		for(int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			
			switch(c) {
			case 'v':
				y[i%2]--;
				break;
			case '^':
				y[i%2]++;
				break;
			case '<':
				x[i%2]--;
				break;
			case '>':
				x[i%2]++;
				break;
			}
			
			points.add(x[i%2] + "," + y[i%2]);
		}
		
		System.out.println(points.size());
		
	}

}
