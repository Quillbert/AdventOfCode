import java.util.*;

public class Day6P1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner s = new Scanner(System.in);
		String str = s.nextLine();
		String[] parts = str.split(",");
		ArrayList<Integer> values = new ArrayList<Integer>();
		for(int i = 0; i < parts.length; i++) {
			values.add(Integer.parseInt(parts[i]));
		}
		for(int i = 0; i < 80; i++) {
			for(int j = 0; j < values.size(); j++) {
				values.set(j, values.get(j)-1);
			}
			for(int j = 0; j < values.size(); j++) {
				if(values.get(j) < 0) {
					values.set(j, 6);
					values.add(8);
				}
			}
		}
		System.out.println(values.size());
	}

}
