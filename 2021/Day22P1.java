import java.util.*;

public class Day22P1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner s = new Scanner(System.in);
		
		boolean[][][] grid = new boolean[101][101][101];
		
		String str = s.nextLine().trim();
		
		while(str.length() > 0) {
			String[] parts = str.split(" ");
			
			boolean on = parts[0].equals("on");
			
			parts = parts[1].split(",");
			
			String xStr = parts[0].substring(2, parts[0].length());
			String yStr = parts[1].substring(2, parts[1].length());
			String zStr = parts[2].substring(2);
			
			
			
			parts = xStr.split("\\.\\.");
			
			int xMin = Integer.parseInt(parts[0]);
			int xMax = Integer.parseInt(parts[1]);
			
			parts = yStr.split("\\.\\.");
			
			int yMin = Integer.parseInt(parts[0]);
			int yMax = Integer.parseInt(parts[1]);
			
			parts = zStr.split("\\.\\.");
			
			int zMin = Integer.parseInt(parts[0]);
			int zMax = Integer.parseInt(parts[1]);
			
			if(xMin < -50 || xMax > 50) {
				str = s.nextLine().trim();
				continue;
			}
			
			for(int x = xMin; x <= xMax; x++) {
				for(int y = yMin; y <= yMax; y++) {
					for(int z = zMin; z <= zMax; z++) {
						grid[x+50][y+50][z+50] = on;
					}
				}
			}
			
			
			str = s.nextLine().trim();
		}
		
		int count = 0;
		for(int x = 0; x < grid.length; x++) {
			for(int y = 0; y < grid[0].length; y++) {
				for(int z = 0; z < grid[0][0].length; z++) {
					if(grid[x][y][z]) {
						count++;
					}
				}
			}
		}
		
		System.out.println(count);
	}

}
