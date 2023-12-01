
public class Day25P1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int count = 0;
		
		int spot = -1;
		
		int i = 1;
		int j = 1;
		
		while(true) {
			int i1 = i;
			int j1 = j;
			while(j1 > 0) {
				if(i1 == 3019 && j1 == 3010) {
					spot = count;
					break;
				}
				i1++;
				j1--;
				count++;
			}
			if(spot != -1) {
				break;
			}
			j++;
			//count++;
		}
		
		long val = 20151125;
		
		for(i = 0; i < spot; i++) {
			val *= 252533;
			val %= 33554393;
		}
		
		System.out.println(val);
		
	}

}
