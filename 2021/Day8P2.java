import java.util.*;

public class Day8P2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner s = new Scanner(System.in);
		ArrayList<Integer> values = new ArrayList<Integer>();
		String str = s.nextLine();
		int total = 0;
		while(str.length() > 0) {
			String[] numbers = new String[10];
			String[] parts = str.split("\\|");
			String[] wirings = parts[0].trim().split(" ");
			char[] segments = new char[7];
			for(int i = 0; i < wirings.length; i++) {
				if(wirings[i].length() == 2) {
					numbers[1] = wirings[i];
				} else if(wirings[i].length() == 4) {
					numbers[4] = wirings[i];
				} else if(wirings[i].length() == 3) {
					numbers[7] = wirings[i];
				} else if(wirings[i].length() == 7) {
					numbers[8] = wirings[i];
				}
			}
			for(int i = 0; i < numbers[7].length(); i++) {
				if(!numbers[1].contains(numbers[7].substring(i, i+1))) {
					segments[0] = numbers[7].charAt(i);
				}
			}
			for(int i = 0; i < wirings.length; i++) {
				if(wirings[i].length() == 6) {
					for(int j = 0; j < numbers[1].length(); j++) {
						if(!wirings[i].contains(numbers[1].substring(j, j+1))) {
							numbers[6] = wirings[i];
							segments[2] = numbers[1].charAt(j);
						}
					}
				}
			}
			for(int i = 0; i < wirings.length; i++) {
				if(wirings[i].length() == 5 && !wirings[i].contains("" + segments[2])) {
					numbers[5] = wirings[i];
					for(int j = 0; j < numbers[6].length(); j++) {
						if(!numbers[5].contains(numbers[6].substring(j, j+1))) {
							segments[4] = numbers[6].charAt(j);
						}
					}
				}
			}
			for(int i = 0; i < wirings.length; i++) {
				if(wirings[i].length() == 6 && !wirings[i].equals(numbers[6])) {
					if(wirings[i].contains("" + segments[4])) {
						numbers[0] = wirings[i];
					} else {
						numbers[9] = wirings[i];
					}
				} else if(wirings[i].length() == 5 && !wirings[i].equals(numbers[5])) {
					if(wirings[i].contains("" + segments[4])) {
						numbers[2] = wirings[i];
					} else {
						numbers[3] = wirings[i];
					}
				}
			}
			for(int i = 0; i < numbers.length; i++) {
				numbers[i] = alphabetize(numbers[i]);
			}
			String out = "";
			parts = parts[1].trim().split(" ");
			for(int i = 0; i < parts.length; i++) {
				for(int j = 0; j < numbers.length; j++) {
					if(alphabetize(parts[i]).equals(numbers[j])) {
						out += String.valueOf(j);
					}
				}
			}
			total += Integer.parseInt(out);
			str = s.nextLine();
		}
		
		System.out.println(total);
	}
	
	public static String alphabetize(String str) {
		char[] temp = str.toCharArray();
		Arrays.sort(temp);
		return new String(temp);
	}

}
