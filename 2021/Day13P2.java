import java.util.*;

public class Day13P2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner s = new Scanner(System.in);
		int[][] values = new int[1500][1500];
		String str = s.nextLine().trim();
		while(str.length() > 0) {
			String[] parts = str.split(",");
			values[Integer.parseInt(parts[1])][Integer.parseInt(parts[0])]++;
			str = s.nextLine().trim();
		}

		str = s.nextLine().trim();
		while(str.length() > 0) {
			String[] parts = str.split(" ");
			parts = parts[2].split("=");
			int crease = Integer.parseInt(parts[1]);
			if(parts[0].equals("x")) {
				for(int i = 0; i < values.length; i++) {
					for(int j = crease-1, k = crease+1; j >= 0 && k < values[0].length; j--, k++) {
						values[i][j] += values[i][k];
						values[i][k] = 0;
					}
				}
			} else {
				for(int i = crease-1, j = crease+1; i >= 0 && j <= values.length; i--, j++) {
					for(int k = 0; k < values[0].length; k++) {
						values[i][k] += values[j][k];
						values[j][k] = 0;
					}
				}
			}
			str = s.nextLine().trim();
		}

		System.out.println();
		for(int i = 0; i < 40; i++) {
			for(int j = 0; j < 40; j++) {
				char out = values[i][j] > 0 ? '#' : '.';
				System.out.print(out);
			}
			System.out.println();
		}
	}

}
