import java.util.*;

public class Day5P2 {
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		
		int count = 0;
		
		String str = s.nextLine().trim();
		
		while(str.length() > 0) {
			
			boolean sepByOne = false;
			
			for(int i = 0; i < str.length() - 2; i++) {
				if(str.charAt(i) == str.charAt(i+2)) {
					sepByOne = true;
					break;
				}
			}
			
			boolean repPair = false;
			
			for(int i = 0; i < str.length() - 3; i++) {
				for(int j = i+2; j < str.length()-1; j++) {
					if(str.substring(i, i+2).equals(str.substring(j, j+2))) {
						repPair = true;
						break;
					}
				}
			}
			
			if(sepByOne && repPair) {
				count++;
			}
			
			str = s.nextLine().trim();
		}
		
		System.out.println(count);
	}
}
