import java.util.*;

public class Day10P1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner s = new Scanner(System.in);
		String str = s.nextLine();
		HashMap<Character, Character> opposites = new HashMap<Character, Character>();
		opposites.put('{', '}');
		opposites.put('[', ']');
		opposites.put('(', ')');
		opposites.put('<', '>');
		
		int total = 0;		
		
		while(str.length() > 0) {
			Stack<Character> order = new Stack<Character>();
			
			for(int i = 0; i < str.length(); i++) {
				if("({[<".contains("" + str.charAt(i))) {
					order.push(str.charAt(i));
				} else {
					if(opposites.get(order.peek()).equals(str.charAt(i))) {
						order.pop();
					} else {
						switch(str.charAt(i)) {
						case ')':
							total += 3;
							break;
						case ']':
							total += 57;
							break;
						case '}':
							total += 1197;
							break;
						case '>':
							total += 25137;
							break;
						}
						break;
					}
				}
			}
			
			str = s.nextLine();
		}
		
		System.out.println(total);
	}

}
