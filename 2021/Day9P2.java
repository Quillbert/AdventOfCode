import java.util.*;

public class Day9P2 {
	static int[][] values;
	
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		ArrayList<String> lines = new ArrayList<String>();
		String str = s.nextLine();
		while(str.length() > 0) {
			lines.add(str);
			str = s.nextLine();
		}
		int[][] heights = new int[lines.size()][lines.get(0).length()];
		int[][] lowpoints = new int[heights.length][heights[0].length];
		for(int i = 0; i < heights.length; i++) {
			for(int j = 0; j < heights[0].length; j++) {
				heights[i][j] = Integer.parseInt(lines.get(i).substring(j, j+1));
			}
		}
		for(int i = 0; i < heights.length; i++) {
			for(int j = 0; j < heights[0].length; j++) {
				if(isLowPoint(heights, i, j)) {
					lowpoints[i][j] = 1;
				}
			}
		}
		ArrayList<Integer> sizes = new ArrayList<Integer>();
		for(int i = 0; i < heights.length; i++) {
			for(int j = 0; j < heights[0].length; j++) {
				if(lowpoints[i][j] == 1) {
					values = new int[heights.length][heights[0].length];
					sizes.add(basinSize(heights, i, j));
				}
			}
		}
		int[] maxes = new int[3];
		for(int i = 0; i < 3; i++) {
			int max = 0;
			int maxIndex = 0;
			for(int j = 0; j < sizes.size(); j++) {
				if(sizes.get(j) > max) {
					max = sizes.get(j);
					maxIndex = j;
				}
			}
			maxes[i] = max;
			sizes.remove(maxIndex);
		}
		System.out.println(maxes[0] * maxes[1] * maxes[2]);
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
	public static int basinSize(int[][] heights, int i, int j) {
		if(i < 0 || j < 0 || i >= heights.length || j >= heights[i].length) {
			return 0;
		}
		if(heights[i][j] == 9) {
			return 0;
		}
		int sum = 0;
		if(values[i][j] != 1) {
			sum++;
			values[i][j] = 1;
		}
		if(i > 0 && heights[i-1][j] != 9 && values[i-1][j] != 1) {
			sum++;
			values[i-1][j] = 1;
		}
		if(j > 0 && heights[i][j-1] != 9 && values[i][j-1] != 1) {
			sum++;
			values[i][j-1] = 1;
		}
		if(i < heights.length-1 && heights[i+1][j] != 9 && values[i+1][j] != 1) {
			sum++;
			values[i+1][j] = 1;
		}
		if(j < heights[i].length-1 && heights[i][j+1] != 9 && values[i][j+1] != 1) {
			sum++;
			values[i][j+1] = 1;
		}
		if(sum == 0) {
			return 0;
		}
		return sum + basinSize(heights, i-1, j) + basinSize(heights, i+1, j) + basinSize(heights, i, j-1) + basinSize(heights, i, j+1);
	}
	
}
