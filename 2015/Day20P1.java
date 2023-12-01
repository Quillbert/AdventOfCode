
public class Day20P1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int input = 34000000;
		
		int num = 1;
		
		while(true) {
			int total = 0;
			for(int i = 1; i <= num; i++) {
				if(num % i == 0) {
					total += i*10;
				}
			}
			System.out.println(total + " " + num);
			if(total >= input) {
				break;
			}
			num++;
		}
		
		System.out.println(num);
	}

}
