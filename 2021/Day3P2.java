import java.util.*;

public class Day3P2 {
	public static void main(String args[]) {
		Scanner s = new Scanner(System.in);
		ArrayList<String> values = new ArrayList<String>();
		String str = s.nextLine().trim();
		while(str.length() > 0) {
			values.add(str);
			str = s.nextLine().trim();
		}
		ArrayList<String> oxygenWorking = new ArrayList<String>(values);
		ArrayList<String> co2Working = new ArrayList<String>(values);
		
		String o = "";
		String c = "1";
		
		for(int i = 0; i < 12; i++) {
			if(oxygenWorking.size() == 1) {
				o = oxygenWorking.get(0);
				break;
			}
			int[] zeros = new int[12];
			int[] ones = new int[12];
			for(String value : oxygenWorking) {
				if(value.charAt(i) == '0') {
					zeros[i]++;
				} else {
					ones[i]++;
				}
			}
			char good = '0';
			if(zeros[i] == ones[i]) good = '1';
			if(ones[i] > zeros[i]) good = '1';
			for(int j = oxygenWorking.size()-1; j >= 0; j--) {
				if(oxygenWorking.get(j).charAt(i) != good) {
					oxygenWorking.remove(j);
				}
			}
			
		}
		o = oxygenWorking.get(0);
		
		for(int i = 0; i < 12; i++) {
			if(co2Working.size() == 1) {
				c = co2Working.get(0);
				break;
			}
			int[] zeros = new int[12];
			int[] ones = new int[12];
			for(String value : co2Working) {
				if(value.charAt(i) == '0') {
					zeros[i]++;
				} else {
					ones[i]++;
				}
			}
			char good = '1';
			if(zeros[i] == ones[i]) good = '0';
			if(ones[i] > zeros[i]) good = '0';
			for(int j = co2Working.size()-1; j >= 0; j--) {
				if(co2Working.get(j).charAt(i) != good) {
					co2Working.remove(j);
				}
			}
			
		}
		c = co2Working.get(0);
		
		
		System.out.println(Integer.parseInt(o, 2) * Integer.parseInt(c, 2));
	}
}
