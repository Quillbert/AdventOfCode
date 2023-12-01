import java.util.*;

// Same code for both parts

public class Day11P1 {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner s = new Scanner(System.in);
		
		String input = s.nextLine().trim();
		
		char[] in = input.toCharArray();
		
		in = increment(in);
		
		while(true) {
			boolean threeIncLetters = false;
			
			for(int i = 0; i < in.length-2; i++) {
				if(in[i+1] == in[i]+1 && in[i+2] == in[i]+2) {
					threeIncLetters = true;
					break;
				}
			}
			
			boolean badLetters = false;
			
			for(int i = 0; i < in.length; i++) {
				if(in[i] == 'i' || in[i] == 'l' || in[i] == 'o') {
					badLetters = true;
					break;
				}
			}
			
			int pairs = 0;
			
			for(int i = 0; i < in.length-1; i++) {
				if(in[i] == in[i+1]) {
					pairs++;
					i++;
				}
			}
			
			if(threeIncLetters && !badLetters && pairs >= 2) {
				break;
			}
			
			in = increment(in);
		}
		
		System.out.println(new String(in));
		
	}
	
	public static char[] increment(char[] in) {
		in[in.length-1]++;
		
		for(int i = in.length-1; i >= 0; i--) {
			if(in[i] > 'z') {
				in[i] = 'a';
				if(i > 0) {
					in[i-1]++;
				}
			} else {
				break;
			}
		}
		
		return in;
	}

}
