import java.util.*;

public class Day2P1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner s = new Scanner(System.in);
		
		int x = 0;
		int y = 0;
		
		String str = s.nextLine().trim();
		while(str.length() > 0) {
			String[] values = str.split(" ");
			int amount = Integer.parseInt(values[1]);
			switch(values[0]) {
			case "forward":
				x += amount;
				break;
			case "down":
				y += amount;
				break;
			case "up":
				y -= amount;
				break;
			}
			str = s.nextLine().trim();
		}
		System.out.println(x*y);
	}

}
