import java.util.*;

public class Day3P1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner s = new Scanner(System.in);
		int[] zeros = new int[12];
		int[] ones = new int[zeros.length];
		String str = s.nextLine().trim();
		while(str.length() > 0) {
			for(int i = 0; i < str.length(); i++) {
				if(str.charAt(i) == '0') {
					zeros[i]++;
				} else {
					ones[i]++;
				}
			}
			str = s.nextLine().trim();
		}
		String gamma = "";
		String epsilon = "";
		for(int i = 0; i < ones.length; i++) {
			if(zeros[i] > ones[i]) {
				gamma += "1";
				epsilon += "0";
			} else {
				gamma += "0";
				epsilon += "1";
			}
		}
		System.out.println(Integer.parseInt(gamma, 2) * Integer.parseInt(epsilon, 2));
	}

}
