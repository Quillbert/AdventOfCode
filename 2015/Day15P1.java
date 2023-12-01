import java.util.*;

public class Day15P1 {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner s = new Scanner(System.in);
		
		int[][] stats = {
				{4, -2, 0, 0, 5},
				{0, 5, -1, 0, 8},
				{-1, 0, 5, 0, 6},
				{0, 0, -2, 2, 1}};
		
		int max = 0;
		
		for(int i = 0; i <= 100; i++) {
			for(int j = 0; j <= 100; j++) {
				for(int k = 0; k <= 100; k++) {
					int num = 1;
					for(int l = 0; l <= 100; l++) {
						if(i+j+k+l != 100) {
							continue;
						}
						for(int m = 0; m < 4; m++) {
							num *= Math.max(0, i*stats[0][m]+j*stats[1][m]+k*stats[2][m]+l*stats[3][m]);
						}
						if(num > max) {
							max = num;
						}
					}
				}
			}
		}
		
		System.out.println(max);
		
	}

}
