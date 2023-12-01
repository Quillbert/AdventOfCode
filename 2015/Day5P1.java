import java.util.*;

public class Day5P1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner s = new Scanner(System.in);
		
		int count = 0;
		
		String str = s.nextLine().trim();
		
		while(str.length() > 0) {
			
			int vowelCount = 0;
			
			for(int i = 0; i < str.length(); i++) {
				if("aeiou".contains(str.substring(i, i+1))) {
					vowelCount++;
				}
			}
			
			boolean twoInARow = false;
			
			for(int i = 0; i < str.length()-1; i++) {
				if(str.charAt(i) == str.charAt(i+1)) {
					twoInARow = true;
					break;
				}
			}
			
			boolean badPairs = 
					str.contains("ab") ||
					str.contains("cd") ||
					str.contains("pq") ||
					str.contains("xy");
			
			if(vowelCount >= 3 && twoInARow && !badPairs) {
				count++;
			}
			
			str = s.nextLine().trim();
		}
		
		System.out.println(count);
	}

}
