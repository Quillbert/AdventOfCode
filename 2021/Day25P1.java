import java.util.*;

public class Day25P1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner s = new Scanner(System.in);
		
		ArrayList<String> lines = new ArrayList<String>();
		
		String str = s.nextLine().trim();
		
		while(str.length() > 0) {
			lines.add(str);
			str = s.nextLine().trim();
		}
		
		char[][] grid = new char[lines.size()][lines.get(0).length()];
		
		for(int i = 0; i < lines.size(); i++) {
			for(int j = 0; j < grid[0].length; j++) {
				grid[i][j] = lines.get(i).charAt(j);
			}
		}
		
		boolean moved = true;
		int turns = 0;
		
		while(moved) {
			moved = false;
			turns++;
			char[][] copy = new char[grid.length][grid[0].length];
			
			for(int i = 0; i < grid.length; i++) {
				for(int j = 0; j < grid[0].length; j++) {
					copy[i][j] = '.';
				}
			}
			
			for(int i = 0; i < grid.length; i++) {
				for(int j = grid[0].length-1; j >= 0; j--) {
					if(grid[i][j] == '>' && grid[i][(j+1)%grid[0].length] == '.') {
						copy[i][(j+1)%grid[0].length] = '>';
						copy[i][j] = '.';
						moved = true;
					} else if(copy[i][j] == '.'){
						copy[i][j] = grid[i][j];
					}
				}
			}
			
			
			for(int i = 0; i < grid.length; i++) {
				for(int j = 0; j < grid[0].length; j++) {
					grid[i][j] = copy[i][j];
				}
			}
			
			for(int i = 0; i < grid.length; i++) {
				for(int j = 0; j < grid[0].length; j++) {
					copy[i][j] = '.';
				}
			}
			
			
			for(int i = grid.length-1; i >= 0; i--) {
				for(int j = 0; j < grid[0].length; j++) {
					if(grid[i][j] == 'v' && grid[(i+1)%grid.length][j] == '.') {
						copy[(i+1)%grid.length][j] = 'v';
						copy[i][j] = '.';
						moved = true;
					} else if(copy[i][j] == '.') {
						copy[i][j] = grid[i][j];
					}
				}
			}
			
			for(int i = 0; i < grid.length; i++) {
				for(int j = 0; j < grid[0].length; j++) {
					grid[i][j] = copy[i][j];
				}
			}
			
		}
		
		System.out.println(turns);
	}

}
