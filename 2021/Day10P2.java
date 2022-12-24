import java.util.*;

public class Day10P2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner s = new Scanner(System.in);
		String str = s.nextLine();
		HashMap<Character, Character> opposites = new HashMap<Character, Character>();
		opposites.put('{', '}');
		opposites.put('[', ']');
		opposites.put('(', ')');
		opposites.put('<', '>');
		
		ArrayList<Long> scores = new ArrayList<Long>();		
		
		while(str.length() > 0) {
			Stack<Character> order = new Stack<Character>();
			
			boolean end = false;
			
			for(int i = 0; i < str.length(); i++) {
				if("({[<".contains("" + str.charAt(i))) {
					order.push(str.charAt(i));
				} else {
					if(opposites.get(order.peek()).equals(str.charAt(i))) {
						order.pop();
					} else {
						end = true;
						break;
					}
				}
			}
			
			if(end) {
				str = s.nextLine();
				continue;
			}
			
			long total = 0;
			while(!order.isEmpty()) {
				total *= 5;
				switch(order.pop()) {
				case '(':
					total += 1;
					break;
				case '[':
					total += 2;
					break;
				case '{':
					total += 3;
					break;
				case '<':
					total += 4;
					break;
				}
			}
			
			scores.add(total);
			
			str = s.nextLine();
		}
		
		Collections.sort(scores);
		System.out.println(scores);
		System.out.println(scores.get(scores.size()/2));
	}
	
}
