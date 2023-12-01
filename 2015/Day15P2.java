import java.util.Scanner;

public class Day15P2 {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner s = new Scanner(System.in);
		
		int[][] stats = {
				{4, -2, 0, 0, 5},
				{0, 5, -1, 0, 8},
				{-1, 0, 5, 0, 6},
				{0, 0, -2, 2, 1}};
		
		long max = 0;
		
		for(int i = 0; i <= 100; i++) {
			for(int j = 0; j <= 100; j++) {
				for(int k = 0; k <= 100; k++) {
					long num = 1;
					long calories = 0;
					for(int l = 0; l <= 100; l++) {
						if(i+j+k+l != 100) {
							continue;
						}
						for(int m = 0; m < 4; m++) {
							num *= Math.max(0, i*stats[0][m]+j*stats[1][m]+k*stats[2][m]+l*stats[3][m]);
						}
						
						calories = i*stats[0][4]+j*stats[1][4]+k*stats[2][4]+l*stats[3][4];
						
						if(num > max && calories == 500) {
							max = num;
						}
					}
				}
			}
		}
		
		System.out.println(max);
		
	}

}
