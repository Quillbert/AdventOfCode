import java.util.*;

public class Day23P1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner s = new Scanner(System.in);
		
		ArrayList<String> commands = new ArrayList<>();
		
		String str = s.nextLine().trim();
		
		while(str.length() > 0) {
			commands.add(str);
			str = s.nextLine().trim();
		}
		
		int ptr = 0;
		int a = 1; // All that is different between two parts
		int b = 0;
		while(ptr < commands.size()) {
			String[] parts = commands.get(ptr).split(" ");
			if(parts[0].equals("hlf")) {
				if(parts[1].equals("a")) {
					a /= 2;
				} else {
					b /= 2;
				}
			} else if(parts[0].equals("tpl")) {
				if(parts[1].equals("a")) {
					a *= 3;
				} else {
					b *= 3;
				}
			} else if(parts[0].equals("inc")) {
				if(parts[1].equals("a")) {
					a++;
				} else {
					b++;
				}
			} else if(parts[0].equals("jmp")) {
				int distance = Integer.parseInt(parts[1].substring(1));
				if(parts[1].charAt(0) == '+') {
					ptr += distance;
				} else {
					ptr -= distance;
				}
				continue;
			} else if(parts[0].equals("jie")) {
				int distance = Integer.parseInt(parts[2].substring(1));
				int value = 0;
				if(parts[1].equals("a,")) {
					value = a;
				} else {
					value = b;
				}
				if(value % 2 == 0) {
					if(parts[2].charAt(0) == '+') {
						ptr += distance;
					} else {
						ptr -= distance;
					}
					continue;
				}
			} else if(parts[0].equals("jio")) {
				int distance = Integer.parseInt(parts[2].substring(1));
				int value = 0;
				if(parts[1].equals("a,")) {
					value = a;
				} else {
					value = b;
				}
				if(value == 1) {
					if(parts[2].charAt(0) == '+') {
						ptr += distance;
					} else {
						ptr -= distance;
					}
					continue;
				}
			}
			ptr++;
		}
		System.out.println(a);
		System.out.println(b);
	}

}
