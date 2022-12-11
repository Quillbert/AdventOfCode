import java.util.*;

public class Day14P2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner s = new Scanner(System.in);
		String value = s.nextLine().trim();
		
		LinkedList<String> lines = new LinkedList<String>();
		
		String str = s.nextLine().trim();
		str = s.nextLine().trim();
		
		while(str.length() > 0) {
			lines.add(str);
			str = s.nextLine().trim();
		}
		
		String[][] commands = new String[lines.size()][];
		for(int j = 0; j < commands.length; j++) {
			commands[j] = lines.remove().split(" -> ");
		}
		
		long[] combos = new long[26*26];
		
		for(int i = 1; i < value.length(); i++) {
			combos[hash(value.substring(i-1, i+1))]++;
		}
		
		for(int i = 0; i < 40; i++) {
			long[] temp = new long[26*26];
			for(int j = 0; j < commands.length; j++) {
				temp[hash(commands[j][0].substring(0, 1) + commands[j][1])] += combos[hash(commands[j][0])];
				temp[hash(commands[j][1] + commands[j][0].substring(1))] += combos[hash(commands[j][0])];
			}
			combos = temp;
		}
		
		long[] letters = new long[26];
		for(int i = 0; i < combos.length; i++) {
			letters[i/26] += combos[i];
		}
		letters[((int)value.charAt(value.length()-1))-(int)'A']++;
		
		long max = letters[0];
		long min = letters[0];
		for(int i = 0; i < letters.length; i++) {
			if(letters[i] > max) {
				max = letters[i];
			}
			if(min == 0 || (letters[i] < min && letters[i] != 0)) {
				min = letters[i];
			}
		}
		
		System.out.println(max-min);
		
		System.out.print(Arrays.toString(letters));
	}
	
	
	public static int hash(String str) {
		return ((int)(str.charAt(0))-(int)'A')*26+((int)(str.charAt(1))-(int)'A');
	}

}
