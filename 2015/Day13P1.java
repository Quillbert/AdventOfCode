import java.util.*;

public class Day13P1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner s = new Scanner(System.in);
		
		HashMap<String, Person> tempPeople = new HashMap<String, Person>();
		
		String str = s.nextLine().trim();
		
		while(str.length() > 0) {
			String[] parts = str.split(" ");
			
			tempPeople.putIfAbsent(parts[0], new Person(parts[0]));
			
			Person p = tempPeople.get(parts[0]);
			
			int happyLevel = Integer.parseInt(parts[3]);
			
			if(parts[2].equals("lose")) {
				happyLevel *= -1;
			}
			
			p.happiness.put(parts[10].substring(0, parts[10].length()-1), happyLevel);
			
			str = s.nextLine().trim();
		}
		
		System.out.println(best(new ArrayList<Person>(), new ArrayList<Person>(tempPeople.values())));
	}
	
	public static int best(ArrayList<Person> used, ArrayList<Person> unused) {
		int max = 0;
		
		if(unused.size() == 0) {
			int total = 0;
			
			for(int i = used.size(); i < used.size()*2; i++) {
				Person p = used.get(i%used.size());
				Person left = used.get((i-1)%used.size());
				Person right = used.get((i+1)%used.size());
				
				int leftNum = p.happiness.get(left.name);
				int rightNum = p.happiness.get(right.name);
				
				total += leftNum;
				total += rightNum;
			}
			
			return total;
		}
		
		for(int i = 0; i < unused.size(); i++) {
			ArrayList<Person> usedCopy = new ArrayList<Person>(used);
			ArrayList<Person> unusedCopy = new ArrayList<Person>(unused);
			usedCopy.add(unusedCopy.remove(i));
			int b = best(usedCopy, unusedCopy);
			if(b > max) {
				max = b;
			}
		}
		
		return max;
	}

}
