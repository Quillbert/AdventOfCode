import java.util.*;

public class Day11P1 {
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int[][] values = new int[10][10];
		for(int i = 0; i < 10; i++) {
			String str = s.nextLine().trim();
			for(int j = 0; j < 10; j++) {
				values[i][j] = Integer.parseInt(str.substring(j, j+1));
			}
		}
		
		int total = 0;
		
		for(int i = 0; i < 100; i++) {
			for(int j = 0; j < 10; j++) {
				for(int k = 0; k < 10; k++) {
					values[j][k]++;
				}
			}
			boolean flash = true;
			while(flash) {
				flash = false;
				for(int j = 0; j < 10; j++) {
					for(int k = 0; k < 10; k++) {
						if(values[j][k] > 9) {
							flash = true;
							total++;
							values[j][k] = 0;
							boolean top = j > 0;
							boolean bottom = j < 9;
							boolean left = k > 0;
							boolean right = k < 9;
							if(top) {
								if(values[j-1][k] != 0) {
									values[j-1][k]++;
								}
							}
							if(bottom) {
								if(values[j+1][k] != 0) {
									values[j+1][k]++;
								}
							}
							if(left) {
								if(values[j][k-1] != 0) {
									values[j][k-1]++;
								}
							}
							if(right) {
								if(values[j][k+1] != 0) {
									values[j][k+1]++;
								}
							}
							if(top && left) {
								if(values[j-1][k-1] != 0) {
									values[j-1][k-1]++;
								}
							}
							if(top && right) {
								if(values[j-1][k+1] != 0) {
									values[j-1][k+1]++;
								}
							}
							if(bottom && left) {
								if(values[j+1][k-1] != 0) {
									values[j+1][k-1]++;
								}
							}
							if(bottom && right) {
								if(values[j+1][k+1] != 0) {
									values[j+1][k+1]++;
								}
							}
						}
					}
				}
			}
		}
		System.out.println(total);
	}
}
