import java.util.*;

public class Day12P1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner s = new Scanner(System.in);
		
		String input = s.nextLine();
		
		int count = 0;
		
		String current = "";
		
		for(int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if(c != '-' && !Character.isDigit(c)) {
				if(current.length() > 0) {
					count += Integer.parseInt(current);
				}
				current = "";
			} else {
				current += c;
			}
		}
		
		if(current.length() > 0) {
			count += Integer.parseInt(current);
		}
		
		System.out.println(count);
		
	}

}
