import java.util.*;

public class Day10P2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner s = new Scanner(System.in);

		String input = s.nextLine().trim();

		for(int t = 0; t < 50; t++) {

			int count = 0;

			char current = '\0';

			StringBuffer out = new StringBuffer();

			for(int i = 0; i < input.length(); i++) {
				if(input.charAt(i) != current) {
					if(count > 0) {
						out.append(count + "" + current);
					}
					count = 1;
					current = input.charAt(i);
				} else {
					count++;
				}
			}

			out.append(count + "" + current);

			input = out.toString();
			
			System.out.println(t);
		}

		System.out.println(input.length());
	}

}
