import java.util.*;

public class Day8P1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner s = new Scanner(System.in);
		
		int total = 0; 
		int space = 0;
		
		String str = s.nextLine();
		
		while(str.length() > 0) {
			total += str.length();
			
			int localSpace = str.length()-2;
			
			for(int i = 0; i < str.length(); i++) {
				if(str.charAt(i) == '\\') {
						if(str.charAt(i+1) == 'x') {
							localSpace -= 3;
						} else {
							localSpace--;
						}
						i++;
				}
			}
			
			space += localSpace;
			
			str = s.nextLine();
		}
		
		System.out.println(total-space);
		
	}

}
