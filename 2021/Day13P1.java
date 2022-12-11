import java.util.*;

public class Day13P1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner s = new Scanner(System.in);
		int[][] values = new int[1500][1500];
		String str = s.nextLine().trim();
		while(str.length() > 0) {
			String[] parts = str.split(",");
			values[Integer.parseInt(parts[0])][Integer.parseInt(parts[1])]++;
			str = s.nextLine().trim();
		}
		str = s.nextLine().trim();
		String[] parts = str.split(" ");
		parts = parts[2].split("=");
		int crease = Integer.parseInt(parts[1]);
		if(parts[0].equals("x")) {
			for(int j = crease-1, k = crease+1; j >= 0 && k < values.length; j--, k++) {
				for(int i = 0; i < values.length; i++) {
					values[i][j] += values[i][j];
					values[i][k] = 0;
				}
			}
		} else {
			for(int j = crease-1, k = crease+1; j >= 0 && k < values.length; j--, k++) {
				for(int i = 0; i < values.length; i++) {
					values[j][i] += values[k][i];
					values[k][i] = 0;
				}
			}
		}
		
		int count = 0; 
		for(int i = 0; i < values.length; i++) {
			for(int j = 0; j < values[0].length; j++) {
				if(values[i][j] > 0) {
					count++;
				}
			}
		}
		System.out.println(count);
	}

}
