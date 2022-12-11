import java.util.*;

public class Day8P1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner s = new Scanner(System.in);
		int occurences[] = new int[10];
		String str = s.nextLine();
		while(str.length() > 0) {
			String ending = str.split("\\|")[1].trim();
			String[] parts = ending.split(" ");
			for(int i = 0; i < parts.length; i++) {
				if(parts[i].length() == 2) {
					occurences[1]++;
				} else if(parts[i].length() == 4) {
					occurences[4]++;
				} else if(parts[i].length() == 3) {
					occurences[7]++;
				} else if(parts[i].length() == 7) {
					occurences[8]++;
				}
			}
			str = s.nextLine();
		}
		int total = 0;
		for(int i = 0; i < occurences.length; i++) {
			total += occurences[i];
		}
		System.out.println(total);
	}

}
