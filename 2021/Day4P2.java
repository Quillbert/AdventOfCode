import java.util.ArrayList;
import java.util.Scanner;

public class Day4P2 {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner s = new Scanner(System.in);
		String order = s.nextLine();
		String str = s.nextLine();
		ArrayList<Board> boards = new ArrayList<Board>();
		while(!str.equals("done")) {
			Board next = new Board();
			boards.add(next);
			for(int i = 0; i < 5; i++) {
				next.add(s.nextLine());
			}
			str = s.nextLine();
		}
		int last = 0;
		String[] nums = order.split(",");
		Board b = null;
		for(String n : nums) {
			if(boards.size() == 1) b = boards.get(0);
			if(boards.size() == 0) break;
			for(int j = boards.size()-1; j >= 0; j--) {
				boards.get(j).call(Integer.parseInt(n));
				if(boards.get(j).winning()) boards.remove(j);
			}
			last = Integer.parseInt(n);
		}
		int total = 0;
		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < 5; j++) {
				if(!b.values[i][j].selected) {
					total += b.values[i][j].value;
				}
			}
		}
		System.out.println(boards.size());
		System.out.println(total*last);
	}
}
