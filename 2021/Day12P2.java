import java.util.HashMap;
import java.util.Scanner;

public class Day12P2 {

	static int count = 0;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner s = new Scanner(System.in);
		HashMap<String, Room> rooms = new HashMap<String, Room>();
		String str = s.nextLine().trim();
		while(str.length() > 0) {
			String[] parts = str.split("-");
			for(int i = 0; i < 2; i++) {
				if(!rooms.containsKey(parts[i])) {
					rooms.put(parts[i], new Room(parts[i]));
				}
			}
			rooms.get(parts[0]).addConnection(rooms.get(parts[1]));
			str = s.nextLine().trim();
		}
		Path2 p = new Path2(rooms.get("start"));
		p.generate();
		System.out.println(count);
	}

}
