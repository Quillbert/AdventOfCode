import java.util.*;

public class Day9P1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner s = new Scanner(System.in);
		ArrayList<String> lines = new ArrayList<String>();
		String str = s.nextLine();
		while(str.length() > 0) {
			lines.add(str);
			str = s.nextLine();
		}
		int[][] heights = new int[lines.size()][lines.get(0).length()];
		int sum = 0;
		for(int i = 0; i < heights.length; i++) {
			for(int j = 0; j < heights[0].length; j++) {
				heights[i][j] = Integer.parseInt(lines.get(i).substring(j, j+1));
			}
		}
		for(int i = 0; i < heights.length; i++) {
			for(int j = 0; j < heights[0].length; j++) {
				if(isLowPoint(heights, i, j)) {
					sum += heights[i][j]+1;
				}
			}
		}
		
		System.out.println(sum);
		
	}
	
	public static boolean isLowPoint(int[][] heights, int i, int j) {
		if(i > 0 && heights[i-1][j] <= heights[i][j]) {
			return false;
		}
		if(j > 0 && heights[i][j-1] <= heights[i][j]) {
			return false;
		}
		if(i < heights.length-1 && heights[i+1][j] <= heights[i][j]) {
			return false;
		}
		if(j < heights[i].length-1 && heights[i][j+1] <= heights[i][j]) {
			return false;
		}
		return true;
	}

}
