import java.util.*;

public class Day4P1 {

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
		String[] nums = order.split(",");
		for(String n : nums) {
			for(Board b : boards) {
				b.call(Integer.parseInt(n));
				if(b.winning()) {
					int total = 0;
					for(int i = 0; i < 5; i++) {
						for(int j = 0; j < 5; j++) {
							if(!b.values[i][j].selected) {
								total += b.values[i][j].value;
							}
						}
					}
					System.out.println(total*Integer.parseInt(n));
					System.exit(0);
				}
			}
		}
	}

}
