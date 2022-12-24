import java.util.ArrayList;
import java.util.Scanner;

public class Day20P2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner s = new Scanner(System.in);
		String str = s.nextLine().trim();
		char[] key = str.toCharArray();
		
		ArrayList<String>lines = new ArrayList<String>();
		
		str = s.nextLine().trim();
		str = s.nextLine().trim();
		
		while(str.length() > 0) {
			lines.add(str);
			str = s.nextLine().trim();
		}
		
		char[][] map = new char[lines.size()][];
		
		for(int i = 0; i < lines.size(); i++) {
			map[i] = lines.get(i).toCharArray();
		}
		
		//print(map);
		
		for(int i = 0; i < 50; i++) {
			char[][] bigMap = new char[map.length+4][map[0].length+4];
			char[][] out = new char[map.length+2][map[0].length+2];
			for(int j = 0; j < bigMap.length; j++) {
				for(int k = 0; k < bigMap[0].length; k++) {
					if(key[0] == '#' && i % 2 == 1) {
						bigMap[j][k] = '#';
					} else {
						bigMap[j][k] = '.';
					}
				}
			}
			for(int j = 0; j < map.length; j++) {
				for(int k = 0; k < map[0].length; k++) {
					bigMap[j+2][k+2] = map[j][k];
				}
			}
			
			for(int j = 1; j < bigMap.length-1; j++) {
				for(int k = 1; k < bigMap[0].length-1; k++) {
					String place = "";
					for(int y = j-1; y <= j+1; y++) {
						for(int x = k-1; x <= k+1; x++) {
							place += bigMap[y][x] == '#' ? "1" : "0";
						}
					}
					int loc = Integer.parseInt(place, 2);
					out[j-1][k-1] = key[loc];
				}
			}
			map = out;
			//print(map);
		}
		
		int total = 0;
		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map[0].length; j++) {
				if(map[i][j] == '#') {
					total++;
				}
			}
		}
		System.out.println(total);
	}

	public static void print(char[][] arr) {
		for(int i = 0; i < arr.length; i++) {
			for(int j = 0; j < arr[0].length; j++) {
				System.out.print(arr[i][j]);
			}
			System.out.println();
		}
		System.out.println();
	}
	
}
