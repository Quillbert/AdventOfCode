import java.util.*;

public class Day17P2 {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner s = new Scanner(System.in);
		String input = s.nextLine().trim();
		String[] parts = input.split(" ");
		String xrange = parts[2].substring(2, parts[2].length()-1);
		String yrange = parts[3].substring(2);
		parts = xrange.split("\\.\\.");
		int xmin = Integer.parseInt(parts[0]);
		int xmax = Integer.parseInt(parts[1]);
		parts = yrange.split("\\.\\.");
		int ymin = Integer.parseInt(parts[0]);
		int ymax = Integer.parseInt(parts[1]);

		int minxv = 0;
		while(true) {
			int x = (int)((minxv+1)*(minxv/2.0));
			if(x >= xmin) {
				break;
			}
			minxv++;
		}

		int maxxv = xmax+1;

		int minyv = ymin-1;

		/*while(true) {
			int y = 0;
			int yv = minyv;
			for(int i = 0; i < minxv; i++) {
				y += yv;
				yv--;
			}
			if(y > ymin) {
				break;
			}
			minyv++;
		}*/


		int total = 0;


		for(int x = minxv; x <= maxxv; x++ ) {
			for(int i = minyv; i <= 1000; i++) {
				int yv = i;
				int y = 0;
				int xpos = 0;
				int xv = x;
				int maxHeight = y;
				boolean hits = false;

				while(y >= ymin) {
					xpos += xv;
					if(xv > 0) {
						xv--;
					}
					y += yv;
					yv--;
					boolean left = xpos >= xmin;
					boolean right = xpos <= xmax;
					boolean top = y <= ymax;
					boolean bottom = y >= ymin;
					if(left && right && top && bottom) {
						hits = true;
					}
				}

				if(hits) {
					total++;
					System.out.println(x + ", " + i);
				}
			}
		}
		System.out.println(total);

	}
}
