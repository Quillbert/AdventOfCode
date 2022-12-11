import java.util.*;

public class Day17P1 {

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
		
		int minyv = ymin;
		
		while(true) {
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
		}
		
		
		int max = -Integer.MAX_VALUE;
		for(int i = minyv; i <= 100000; i++) {
			int yv = i;
			int y = 0;
			int maxHeight = y;
			boolean hits = false;
			
			while(y + yv >= ymin) {
				y += yv;
				yv--;
				if(y > maxHeight) {
					maxHeight = y;
				}
				if(y >= ymin && y <= ymax) {
					hits = true;
				}
			}
			
			if(hits && maxHeight > max) {
				max = maxHeight;
			}
		}
		
		System.out.println(max);
		
	}

}
