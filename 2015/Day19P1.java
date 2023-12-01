import java.util.*;

public class Day19P1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner s = new Scanner(System.in);
		
		HashMap<String, ArrayList<String>> changes = new HashMap<>();
		HashSet<String> set = new HashSet<>();
		
		String str = s.nextLine().trim();
		
		while(str.length() > 0) {
			String[] parts = str.split(" => ");
			if(!changes.containsKey(parts[0])) {
				changes.put(parts[0], new ArrayList<String>());
			}
			changes.get(parts[0]).add(parts[1]);
			
			str = s.nextLine().trim();
		}
		
		str = s.nextLine().trim();
		
		for(int i = 0; i < str.length(); i++) {
			if(changes.containsKey(str.substring(i, i+1))) {
				for( String c : changes.get(str.substring(i, i+1))) {
					set.add(str.substring(0, i) + c + str.substring(i+1));
				}
			}
		}
		
		for(int i = 0; i < str.length()-1; i++) {
			if(changes.containsKey(str.substring(i, i+2))) {
				for( String c : changes.get(str.substring(i, i+2))) {
					set.add(str.substring(0, i) + c + str.substring(i+2));
				}
			}
		}
		
		System.out.println(set.size());
	}

}
