import java.util.*;

// Worked for part 2 as well with an input edit

public class Day7P1 {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner s = new Scanner(System.in);
		
		HashMap<String, Component> components = new HashMap<String, Component>();
		
		String str = s.nextLine().trim();
		
		while(str.length() > 0) {
			String[] halves = str.split(" -> ");
			
			if(!components.containsKey(halves[1])) {
				components.put(halves[1], new Component());
			}
			
			Component c = components.get(halves[1]);
			
			String[] parts = halves[0].split(" ");
			
			for(int i = 0; i < parts.length; i++) {
				if(Character.isDigit(parts[i].charAt(0))) {
					components.putIfAbsent(parts[i], new Component());
					components.get(parts[i]).value = Integer.parseInt(parts[i]);
					c.parents.add(components.get(parts[i]));
				} else if(Character.isLowerCase(parts[i].charAt(0))) {
					components.putIfAbsent(parts[i], new Component());
					c.parents.add(components.get(parts[i]));
				} else {
					c.operation = parts[i];
				}
			}
			
			str = s.nextLine().trim();
		}
		
		components.get("a").eval();
		
		System.out.println(components.get("a").value);
		
	}

}
