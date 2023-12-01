
public class Day20P2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int input = 34000000;
		
		int[] houses = new int[40000000];
		
			for(int j = 1; j < houses.length; j++) {
				for(int k = 1; k <= 50; k ++) {
					if(j*k >= houses.length) {
						continue;
					}
					houses[j*k] += j*11;
				}
			}
		
		
		for(int i = 0; i < houses.length; i++) {
			if(houses[i] >= input) {
				System.out.println(i);
				break;
			}
		}
	}

}
