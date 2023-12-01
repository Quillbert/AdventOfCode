import java.util.*;

public class Day3P1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner s = new Scanner(System.in);
		
		HashSet<String> points = new HashSet<String>();
		
		points.add("0,0");
		
		int x = 0;
		int y = 0;
		
		String str = s.nextLine().trim();
		
		for(int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			
			switch(c) {
			case 'v':
				y--;
				break;
			case '^':
				y++;
				break;
			case '<':
				x--;
				break;
			case '>':
				x++;
				break;
			}
			
			points.add(x + "," + y);
		}
		
		System.out.println(points.size());
		
	}

}
