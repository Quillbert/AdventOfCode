import java.util.*;

public class Day2P2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner s = new Scanner(System.in);

		long total = 0;

		String str = s.nextLine().trim();

		while(str.length() > 0) {
			String[] parts = str.split("x");

			int x = Integer.parseInt(parts[0]);
			int y = Integer.parseInt(parts[1]);
			int z = Integer.parseInt(parts[2]);

			int side1 = x*y;
			int side2 = y*z;
			int side3 = x*z;

			total += (2*x+2*y+2*z-2*Math.max(x, Math.max(y, z)) + (x*y*z));

			str = s.nextLine().trim();
		}
		
		System.out.println(total);
	}

}
