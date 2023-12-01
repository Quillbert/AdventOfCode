import java.util.*;

public class Day17P1 {
	
	public static HashSet<ArrayList<Container>> set = new HashSet<ArrayList<Container>>();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner s = new Scanner(System.in);
		
		ArrayList<Container> containers = new ArrayList<Container>();
		
		String str = s.nextLine().trim();
		
		while(str.length() > 0) {
			containers.add(new Container(Integer.parseInt(str)));
			str = s.nextLine().trim();
		}
		
		combos(containers);
		
		int minSize = Integer.MAX_VALUE;
		
		for(ArrayList<Container> c : set) {
			int sum = 0;
			for(Container i : c) {
				sum += i.value;
			}
			if(sum == 150 && c.size() < minSize) {
				minSize = c.size();
			}
		}
		
		int total = 0;
		
		for(ArrayList<Container> c : set) {
			int sum = 0;
			for(Container i : c) {
				sum += i.value;
			}
			if(sum == 150 && c.size() == minSize) {
				total++;
			}
		}
		
		System.out.println(total);
		
	}
	
	public static void combos(ArrayList<Container> containers) {
		if(set.contains(containers)) {
			return;
		}
		set.add(containers);
		for(int i = 0; i < containers.size(); i++) {
			ArrayList<Container> copy = new ArrayList<Container>(containers);
			copy.remove(i);
			combos(copy);
		}
	}

}
