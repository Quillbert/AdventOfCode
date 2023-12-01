import java.util.*;

public class Day1P2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner s = new Scanner(System.in);
		
		int floor = 0;
		
		String str = s.nextLine().trim();
		
		while(str.length() > 0) {
			for(int i = 0; i < str.length(); i++) {
				if(str.charAt(i) == '(') {
					floor++;
				} else if(str.charAt(i) == ')') {
					floor--;
				}
				if(floor < 0) {
					System.out.println(i+1);
					System.exit(0);
				}
			}
			str = s.nextLine().trim();
		}
		
		System.out.println(floor);
	}

}
