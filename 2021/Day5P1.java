import java.util.*;

public class Day5P1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner s = new Scanner(System.in);
		int[][] counts = new int[1000][1000];
		String str = s.nextLine().trim();
		while(str.length() > 0) {
			String[] parts = str.split(" -> ");
			String[] p1 = parts[0].split(",");
			String[] p2 = parts[1].split(",");
			int[] point1 = {Integer.parseInt(p1[0]), Integer.parseInt(p1[1])};
			int[] point2 = {Integer.parseInt(p2[0]), Integer.parseInt(p2[1])};
			if(point1[0] != point2[0] && point1[1] != point2[1]) {
				str = s.nextLine().trim();
				continue;
			}
			for(int i = Math.min(point1[0], point2[0]); i <= Math.max(point1[0], point2[0]); i++) {
				for(int j = Math.min(point1[1], point2[1]); j <= Math.max(point1[1], point2[1]); j++) {
					counts[i][j]++;
				}
			}
			str = s.nextLine().trim();
		}
		int count = 0;
		for(int i = 0; i < 1000; i++) {
			for(int j = 0; j < 1000; j++) {
				if(counts[i][j] > 1) {
					count++;
				}
			}
		}
		System.out.println(count);
	}

}
