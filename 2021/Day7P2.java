import java.util.Scanner;

public class Day7P2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner s = new Scanner(System.in);
		String[] parts = s.nextLine().trim().split(",");
		int[] values = new int[parts.length];
		for(int i = 0; i < parts.length; i++) {
			values[i] = Integer.parseInt(parts[i]);
		}
		int min = values[0];
		int max = values[0];
		for(int i = 1; i < values.length; i++) {
			if(values[i] < min) {
				min = values[i];
			}
			if(values[i] > max) {
				max = values[i];
			}
		}
		int minFuel = Integer.MAX_VALUE;
		for(int i = min; i <= max; i++) {
			int fuel = 0;
			for(int j = 0; j < values.length; j++) { 
				for(int k = 1; k <= Math.abs(values[j]-i); k++) {
					fuel += k;
				}
			}
			if(fuel < minFuel) {
				minFuel = fuel;
			}
		}
		System.out.println(minFuel);
	}

}
