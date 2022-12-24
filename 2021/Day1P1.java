import java.util.*;

public class Day1P1 {
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int previous;
		int count = 0;
		previous = s.nextInt();
		while(previous >= 0) {
			int now = s.nextInt();
			if(now > previous) {
				count++;
			}
			previous = now;
		}
		System.out.println(count);
	}
}
