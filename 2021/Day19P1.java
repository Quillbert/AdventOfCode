import java.util.*;

public class Day19P1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner s = new Scanner(System.in);
		
		ArrayList<String> lines = new ArrayList<String>();
		
		String str = s.nextLine().trim();
		while(!str.equals("done")) {
			lines.add(str);
			str = s.nextLine().trim();
		}
		
		ArrayList<Beacon> all = new ArrayList<Beacon>();
		ArrayList<Locator> scanners = new ArrayList<Locator>();
		for(int i = 0; i < lines.size(); i++) {
			if(lines.get(i).length() <= 0) {
				continue;
			}
			if(lines.get(i).substring(0, 3).equals("---")) {
				scanners.add(new Locator());
				scanners.get(scanners.size()-1).all = all;
				continue;
			}
			String[] parts = lines.get(i).split(",");
			scanners.get(scanners.size()-1).origLocs.add(new Beacon(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]),
					Integer.parseInt(parts[2])));
		}
		
		scanners.get(0).solved = true;
		scanners.get(0).movedLocs = scanners.get(0).origLocs;
		for(int i = 0; i < scanners.get(0).origLocs.size(); i++) {
			all.add(scanners.get(0).origLocs.get(i));
		}
		
		while(!allSolved(scanners)) {
			for(int i = 1; i < scanners.size(); i++) {
				if(scanners.get(i).solved) {
					continue;
				}
				scanners.get(i).solve(scanners);
			}
		}
		
		
		System.out.println(all.size());
		
	}
	
	public static boolean allSolved(ArrayList<Locator> list) {
		for(Locator l : list) {
			if(!l.solved) {
				return false;
			}
		}
		return true;
	}

}
