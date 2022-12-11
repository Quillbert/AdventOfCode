import java.util.*;

public class Day14P1 {

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
		
		for(int i = 0; i < 10; i++) {
			for(int j = 1; j < value.length(); j++) {
				for(int k = 0; k < commands.length; k++) {
					if(value.substring(j-1, j+1).equals(commands[k][0])) {
						value = value.substring(0, j) + commands[k][1] + value.substring(j);
						j++;
						break;
					}
				}
			}
		}
		
		int[] occurences = new int[26];
		for(int i = 0; i < value.length(); i++) {
			occurences[(int)(value.charAt(i))-(int)'A']++;
		}
		int max = occurences[0];
		int min = occurences[0];
		for(int i = 0; i < occurences.length; i++) {
			if(occurences[i] > max) {
				max = occurences[i];
			}
			if(min == 0 || (occurences[i] < min && occurences[i] != 0)) {
				min = occurences[i];
			}
		}
		
		System.out.println(max-min);
		
	}

}
