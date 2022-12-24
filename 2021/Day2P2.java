import java.util.Scanner;

public class Day2P2 {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner s = new Scanner(System.in);
		
		int x = 0;
		int y = 0;
		int aim = 0;
		
		String str = s.nextLine().trim();
		while(str.length() > 0) {
			String[] values = str.split(" ");
			int amount = Integer.parseInt(values[1]);
			switch(values[0]) {
			case "forward":
				x += amount;
				y += amount*aim;
				break;
			case "down":
				aim += amount;
				break;
			case "up":
				aim -= amount;
				break;
			}
			str = s.nextLine().trim();
		}
		System.out.println(x*y);
	}
}
