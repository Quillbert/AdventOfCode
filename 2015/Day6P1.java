import java.util.*;

public class Day6P1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner s = new Scanner(System.in);
		
		boolean[][] lights = new boolean[1000][1000];
		
		String str = s.nextLine().trim();
		
		while(str.length() > 0) {
			String[] parts = str.split(" ");
			
			
			if(parts[0].equals("turn")) {
				String[] firstPoint = parts[2].split(",");
				String[] secondPoint = parts[4].split(",");
				
				for(int i = Integer.parseInt(firstPoint[0]); i <= Integer.parseInt(secondPoint[0]); i++) {
					for(int j = Integer.parseInt(firstPoint[1]); j <= Integer.parseInt(secondPoint[1]); j++) {
						if(parts[1].equals("on")) {
							lights[i][j] = true;
						} else {
							lights[i][j] = false;
						}
					}
				}
			} else {
				String[] firstPoint = parts[1].split(",");
				String[] secondPoint = parts[3].split(",");
				for(int i = Integer.parseInt(firstPoint[0]); i <= Integer.parseInt(secondPoint[0]); i++) {
					for(int j = Integer.parseInt(firstPoint[1]); j <= Integer.parseInt(secondPoint[1]); j++) {
						lights[i][j] = !lights[i][j];
					}
				}
			}
			
			str = s.nextLine().trim();
		}
		
		int count = 0;
		
		for(int i = 0; i < lights.length; i++) {
			for(int j = 0; j < lights.length; j++) {
				if(lights[i][j]) {
					count++;
				}
			}
		}
		
		System.out.println(count);
	}

}
