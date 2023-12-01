import java.util.*;

public class Day18P1 {

	//Slight mod for p2
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner s = new Scanner(System.in);
		
		char[][] grid = new char[100][100];
		
		for(int i = 0; i < grid.length; i++) {
			String str = s.nextLine().trim();
			for(int j = 0; j < grid[i].length; j++) {
				grid[i][j] = str.charAt(j);
			}
		}
		
		grid[0][0] = '#';
		grid[0][99] = '#';
		grid[99][0] = '#';
		grid[99][99] = '#';
		
		for(int i = 0; i < 100; i++) {
			grid = step(grid);
			grid[0][0] = '#';
			grid[0][99] = '#';
			grid[99][0] = '#';
			grid[99][99] = '#';
			for(int j = 0; j < grid.length; j++) {
				//System.out.println(Arrays.toString(grid[j]));
			}
			//System.out.println();
		}
		
		int count = 0;
		
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[i].length; j++) {
				if(grid[i][j] == '#') {
					count++;
				}
			}
		}
		
		System.out.println(count);

	}
	
	public static char[][] step(char[][] grid) {
		char[][] out = new char[grid.length][grid[0].length];
		
		
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[0].length; j++) {
				int neighbors = 0;
				if(i-1 >= 0 && j-1 >= 0 && grid[i-1][j-1] == '#') {
					neighbors++;
				}
				if(j-1 >= 0 && grid[i][j-1] == '#') {
					neighbors++;
				}
				if(i+1 < grid.length && j-1 >= 0 && grid[i+1][j-1] == '#') {
					neighbors++;
				}
				if(i-1 >= 0 && grid[i-1][j] == '#') {
					neighbors++;
				}
				if(i+1 < grid.length && grid[i+1][j] == '#') {
					neighbors++;
				}
				if(i-1 >= 0 && j+1 < grid[i].length && grid[i-1][j+1] == '#') {
					neighbors++;
				}
				if(j+1 < grid[i].length && grid[i][j+1] == '#') {
					neighbors++;
				}
				if(i+1 < grid.length && j+1 < grid[i].length && grid[i+1][j+1] == '#') {
					neighbors++;
				}
				if(neighbors == 2 && grid[i][j] == '#') {
					out[i][j] = '#';
				} else if(neighbors == 3) {
					out[i][j] = '#';
				} else {
					out[i][j] = '.';
				}
			}
		}
		
		return out;
	}

}
